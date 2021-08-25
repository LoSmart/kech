package com.kech.business.user.controller;


import com.kech.business.user.service.impl.OssServiceImpl;
import com.kech.business.user.vo.OssCallbackResult;
import com.kech.business.user.vo.OssPolicyResult;
import com.kech.common.utils.CommonResult;
import com.kech.common.utils.CommonResult;
import com.kech.business.user.service.impl.OssServiceImpl;
import com.kech.business.user.vo.OssCallbackResult;
import com.kech.business.user.vo.OssPolicyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/aliyun/oss")
public class OssController {
    @Autowired
    private OssServiceImpl ossService;

    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    @ResponseBody
    public Object policy() {
        OssPolicyResult result = ossService.policy();
        return new CommonResult().success(result);
    }

    @RequestMapping(value = "callback", method = RequestMethod.POST)
    @ResponseBody
    public Object callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        return new CommonResult().success(ossCallbackResult);
    }

}
