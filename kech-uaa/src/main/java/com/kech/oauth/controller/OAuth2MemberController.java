package com.kech.oauth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kech.common.annotation.IgnoreAuth;
import com.kech.common.constant.SecurityMemberConstants;
import com.kech.common.entity.ums.UmsMember;
import com.kech.common.exception.ApiMallPlusException;
import com.kech.common.feign.MemberFeignClient;
import com.kech.common.feign.UserService;
import com.kech.common.model.Result;
import com.kech.common.model.SysUser;
import com.kech.common.utils.CommonResult;
import com.kech.common.utils.SpringUtil;
import com.kech.oauth.mobile.member.MobileCodeMemberAuthenticationToken;
import com.kech.oauth.mobile.member.MobileMemberAuthenticationToken;
import com.kech.oauth.openid.member.OpenIdMemberAuthenticationToken;
import com.kech.oauth.service.impl.RedisClientDetailsService;
import com.kech.redis.template.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Map;

/**
 * OAuth2????????????
 *
 * @author mall
 */
@Slf4j
@RestController
public class OAuth2MemberController {
    @Resource
    private MemberFeignClient memberFeignClient;
    @Resource
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisRepository redisRepository;
    @Resource
    private UserService userService;


    //??????
    @IgnoreAuth
    @PostMapping(SecurityMemberConstants.PASSWORD_RRG_PRO_URL)
    public Object simpleReg(@RequestBody UmsMember ums) {
         String phone = ums.getPhone();
         String password = ums.getPassword();
         String confimpassword = ums.getConfimpassword();
        if (phone == null || "".equals(phone)) {
            return new CommonResult().validateFailed("????????????????????????");
        }
        if (password == null || "".equals(password)) {
            return new CommonResult().validateFailed("????????????????????????");
        }
        if (confimpassword == null || "".equals(confimpassword)) {
            return new CommonResult().validateFailed("????????????????????????");
        }
        //?????????????????????????????????
        UmsMember user = new UmsMember();
        user.setUsername(phone);
        user.setPhone(phone);
        user.setPassword(password);
        user.setConfimpassword(confimpassword);


        if (!user.getPassword().equals(user.getConfimpassword())) {
            return new CommonResult().failed("???????????????");
        }
        //???????????????????????????

        UmsMember queryM = new UmsMember();
        queryM.setUsername(user.getUsername());

        UmsMember umsMembers = memberFeignClient.findByMobile(phone);
        if (umsMembers != null) {
            return new CommonResult().failed("????????????????????????");
        }
        //?????????????????????????????????

        UmsMember umsMember = new UmsMember();
        umsMember.setMemberLevelId(4L);
        umsMember.setUsername(user.getUsername());
        umsMember.setPhone(user.getPhone());
        umsMember.setPassword(passwordEncoder.encode(user.getPassword()));
        umsMember.setCreateTime(new Date());
        umsMember.setStatus(1);
        memberFeignClient.saveUmsMember(umsMember);
        return new CommonResult().success("????????????", "????????????");

    }

    //?????????????????????token
    @PostMapping(SecurityMemberConstants.PASSWORD_LOGIN_PRO_URL)
    public void getUserTokenInfo(@RequestBody SysUser umsAdminLoginParam,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (umsAdminLoginParam.getUsername() == null || "".equals(umsAdminLoginParam.getUsername())) {
            throw new UnapprovedClientAuthenticationException("???????????????");
        }
        if (umsAdminLoginParam.getPassword() == null || "".equals(umsAdminLoginParam.getPassword())) {
            throw new UnapprovedClientAuthenticationException("????????????");
        }
        MobileMemberAuthenticationToken token = new MobileMemberAuthenticationToken(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        UmsMember user = memberFeignClient.findByUsername(umsAdminLoginParam.getUsername());
       if (user!=null){
           writerToken(request, response, token, "????????????????????????",user.getId());
       }else {
           exceptionHandler(response, "????????????????????????");
       }

    }

    //???????????????????????????token
    @PostMapping(SecurityMemberConstants.CODE_LOGIN_PRO_URL)
    public void getUserTokenByCode(@RequestBody UmsMember umsAdminLoginParam,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (umsAdminLoginParam.getUsername() == null || "".equals(umsAdminLoginParam.getUsername())) {
            throw new UnapprovedClientAuthenticationException("???????????????");
        }
        if (umsAdminLoginParam.getCode() == null || "".equals(umsAdminLoginParam.getCode())) {
            throw new UnapprovedClientAuthenticationException("???????????????");
        }
        MobileCodeMemberAuthenticationToken token = new MobileCodeMemberAuthenticationToken(umsAdminLoginParam.getPhone());
        UmsMember member = memberFeignClient.findByMobile(umsAdminLoginParam.getPhone());
        if (member!=null){
            //???????????????
            if (!verifyAuthCode(member.getCode(), member.getPhone())) {
                throw new ApiMallPlusException("???????????????");
            }
            writerToken(request, response, token, "???????????????",member.getId());
        }else {
            exceptionHandler(response, "???????????????");
        }

    }

    //openId??????token
    @PostMapping(SecurityMemberConstants.OPENID_TOKEN_URL)
    public void getTokenByOpenId(
            String openId,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        OpenIdMemberAuthenticationToken token = new OpenIdMemberAuthenticationToken(openId);
        UmsMember member = memberFeignClient.findByOpenId(openId);
        if (member!=null){
            writerToken(request, response, token, "openId??????",member.getId());
        }else {
            exceptionHandler(response, "openId??????");
        }

    }


    //mobile??????token
    @PostMapping(SecurityMemberConstants.MOBILE_TOKEN_URL)
    public void getTokenByMobile(@RequestBody UmsMember umsAdminLoginParam,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        MobileMemberAuthenticationToken token = new MobileMemberAuthenticationToken(umsAdminLoginParam.getPhone(), umsAdminLoginParam.getPassword());
        writerToken(request, response, token, "????????????????????????",1L);
    }

    /**
     * ????????????????????????
     * security????????????????????????????????????SecurityContextHolder.getContext().getAuthentication()
     * ?????????????????????org.springframework.security.oauth2.provider.OAuth2Authentication
     *
     * @return
     */
    @RequestMapping(value = { "/oauth/member/userinfo" }, produces = "application/json") // ?????????????????????/auth/user
    public Object getCurrentUserDetail() {
        return new CommonResult().success(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
    private void writerToken(HttpServletRequest request, HttpServletResponse response, AbstractAuthenticationToken token
            , String badCredenbtialsMsg,Long userId) throws IOException {
        try {
            String clientId = request.getHeader("client_id");
            String clientSecret = request.getHeader("client_secret");
            if (clientId == null || "".equals(clientId)) {
                throw new UnapprovedClientAuthenticationException("???????????????client_id??????");
            }

            if (clientSecret == null || "".equals(clientSecret)) {
                throw new UnapprovedClientAuthenticationException("???????????????client_secret??????");
            }
            Map<String, String> requestParameters = new HashedMap();
            requestParameters.put("memberId",userId+"");
            ClientDetails clientDetails = getClient(clientId, clientSecret, null);
            TokenRequest tokenRequest = new TokenRequest(requestParameters, clientId, clientDetails.getScope(), "customer");
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
            OAuth2AccessToken oAuth2AccessToken =  authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
            oAuth2Authentication.setAuthenticated(true);

            writerObj(response, oAuth2AccessToken);
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            exceptionHandler(response, badCredenbtialsMsg);
            e.printStackTrace();
        } catch (Exception e) {
            exceptionHandler(response, e);
        }
    }

    private void exceptionHandler(HttpServletResponse response, Exception e) throws IOException {
        log.error("exceptionHandler-error:", e);
        exceptionHandler(response, e.getMessage());
    }

    private void exceptionHandler(HttpServletResponse response, String msg) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        writerObj(response, Result.failed(msg));
    }

    private void writerObj(HttpServletResponse response, Object obj) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try (
                Writer writer = response.getWriter()
        ) {
            writer.write(objectMapper.writeValueAsString(obj));
            writer.flush();
        }
    }

    private ClientDetails getClient(String clientId, String clientSecret, RedisClientDetailsService clientDetailsService) {
        if (clientDetailsService == null) {
            clientDetailsService = SpringUtil.getBean(RedisClientDetailsService.class);
        }
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId????????????????????????");
        } else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("clientSecret?????????");
        }
        return clientDetails;
    }
    //?????????????????????????????????
    private boolean verifyAuthCode(String authCode, String telephone) {
        if (StringUtils.isEmpty(authCode)) {
            return false;
        }
        String realAuthCode = redisRepository.get("member:code:" + telephone).toString();
        return authCode.equals(realAuthCode);
    }

}
