package com.kech.business.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kech.business.user.mapper.SysRoleMapper;
import com.kech.business.user.mapper.SysRolePermissionMapper;
import com.kech.business.user.mapper.SysUserMapper;
import com.kech.business.user.mapper.SysUserRoleMapper;
import com.kech.business.user.service.ISysUserRoleService;
import com.kech.business.user.service.ISysUserService;
import com.kech.common.constant.CommonConstant;
import com.kech.common.lock.DistributedLock;
import com.kech.common.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    private final static String LOCK_KEY_USERNAME = CommonConstant.LOCK_KEY_PREFIX+"username:";

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;
    @Resource
    private SysUserMapper userMapper;
    @Resource
    private SysRolePermissionMapper roleMenuMapper;
    @Resource
    private ISysUserRoleService userRoleService;
    @Autowired
    private DistributedLock lock;

    @Override
    public LoginAppUser findByUsername(String username) {
        SysUser sysUser = this.selectByUsername(username);
        return getLoginAppUser(sysUser);
    }

    @Override
    public LoginAppUser findByOpenId(String username) {
        SysUser sysUser = this.selectByOpenId(username);
        return getLoginAppUser(sysUser);
    }

    @Override
    public LoginAppUser findByMobile(String username) {
        SysUser sysUser = this.selectByMobile(username);
        return getLoginAppUser(sysUser);
    }

    @Override
    public LoginAppUser getLoginAppUser(SysUser sysUser) {
        LoginAppUser loginAppUser = new LoginAppUser();
        BeanUtils.copyProperties(sysUser, loginAppUser);
        return loginAppUser;
        /*if (sysUser != null) {
            LoginAppUser loginAppUser = new LoginAppUser();
            BeanUtils.copyProperties(sysUser, loginAppUser);

            List<SysRole> sysRoles = roleMapper.findRolesByUserId(sysUser.getId());
            // ????????????
            loginAppUser.setRoles(sysRoles);

            if (!CollectionUtils.isEmpty(sysRoles)) {
                Set<Long> roleIds = sysRoles.parallelStream().map(SuperEntity::getId).collect(Collectors.toSet());
                List<SysPermission> menus = roleMenuMapper.findMenusByRoleIds(roleIds, CommonConstant.PERMISSION);
                if (!CollectionUtils.isEmpty(menus)) {
                    Set<String> permissions = menus.parallelStream().map(p -> p.getPathMethod()+":"+p.getPath())
                            .collect(Collectors.toSet());
                    // ??????????????????
                    loginAppUser.setPermissions(permissions);
                }
            }
            return loginAppUser;
        }
        return null;*/
    }


    /**
     * ???????????????????????????
     * @param username
     * @return
     */
    @Override
    public SysUser selectByUsername(String username) {
        List<SysUser> users = baseMapper.selectList(
                new QueryWrapper<SysUser>().eq("username", username)
        );
        return getUser(users);
    }

    /**
     * ???????????????????????????
     * @param mobile
     * @return
     */
    @Override
    public SysUser selectByMobile(String mobile) {
        List<SysUser> users = baseMapper.selectList(
                new QueryWrapper<SysUser>().eq("mobile", mobile)
        );
        return getUser(users);
    }

    /**
     * ??????openId????????????
     * @param openId
     * @return
     */
    @Override
    public SysUser selectByOpenId(String openId) {
        List<SysUser> users = baseMapper.selectList(
                new QueryWrapper<SysUser>().eq("open_id", openId)
        );
        return getUser(users);
    }

    private SysUser getUser(List<SysUser> users) {
        SysUser user = null;
        if (users != null && !users.isEmpty()) {
            user = users.get(0);
        }
        return user;
    }

    /**
     * ?????????????????????
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setRoleToUser(Long id, Set<Long> roleIds) {
       /* SysUser sysUser = baseMapper.selectById(id);
        if (sysUser == null) {
            throw new IllegalArgumentException("???????????????");
        }

        userRoleMapper.deleteUserRole(id, null);
        if (!CollectionUtils.isEmpty(roleIds)) {
            roleIds.forEach(roleId -> userRoleMapper.saveUserRoles(id, roleId));
        }*/
    }

    @Transactional
    @Override
    public Result updatePassword(Long id, String oldPassword, String newPassword) {
        SysUser sysUser = baseMapper.selectById(id);
        if (StrUtil.isNotBlank(oldPassword)) {
            if (!passwordEncoder.matches(oldPassword, sysUser.getPassword())) {
                return Result.failed("???????????????");
            }
        }
        if (StrUtil.isBlank(newPassword)) {
            newPassword = CommonConstant.DEF_USER_PASSWORD;
        }
        SysUser user = new SysUser();
        user.setId(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        baseMapper.updateById(user);
        return Result.succeed("????????????");
    }



    @Override
    public List<SysRole> findRolesByUserId(Long userId) {
        return roleMapper.findRolesByUserId(userId);
    }

    @Override
    public Result updateEnabled(Map<String, Object> params) {
        Long id = MapUtils.getLong(params, "id");
        Boolean enabled = MapUtils.getBoolean(params, "enabled");

        SysUser appUser = baseMapper.selectById(id);
        if (appUser == null) {
            return Result.failed("???????????????");
        }
      //  appUser.setEnabled(enabled);
     //   appUser.setUpdateTime(new Date());

        int i = baseMapper.updateById(appUser);
        log.info("???????????????{}", appUser);

        return i > 0 ? Result.succeed(appUser, "????????????") : Result.failed("????????????");
    }



//    @Override
//    public List<SysUserExcel> findAllUsers(Map<String, Object> params) {
//        List<SysUserExcel> sysUserExcels = new ArrayList<>();
//        List<SysUser> list = baseMapper.findList(new Page<>(1, -1), params);
//
//        for (SysUser sysUser : list) {
//            SysUserExcel sysUserExcel = new SysUserExcel();
//            BeanUtils.copyProperties(sysUser, sysUserExcel);
//            sysUserExcels.add(sysUserExcel);
//        }
//        return sysUserExcels;
//    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUsers(List<SysUser> users) {
        users.forEach(u -> baseMapper.insert(u));
    }

    @Override
    public boolean saves(SysUser umsAdmin) {
        umsAdmin.setCreateTime(new Date());
      //  umsAdmin.setEnabled(true);
        //???????????????????????????????????????

        List<SysUser> umsAdminList = userMapper.selectList(new QueryWrapper<SysUser>().eq("username",umsAdmin.getUsername()));
        if (umsAdminList.size() > 0) {
            return false;
        }
        //???????????????????????????
        if (StringUtils.isEmpty(umsAdmin.getPassword())){
            umsAdmin.setPassword("123456");
        }
        String md5Password = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(md5Password);
        userMapper.insert(umsAdmin);
        updateRole(umsAdmin.getId(),umsAdmin.getRoleIds());

        return true;
    }

    @Override
    @Transactional
    public boolean updates(Long id, SysUser admin) {
        admin.setUsername(null);
        admin.setId(id);
        String md5Password = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(md5Password);
        updateRole(id,admin.getRoleIds());
        userMapper.updateById(admin);
        return true;
    }
    public void updateRole(Long adminId, String roleIds) {
      //  this.removePermissRedis(adminId);
        userRoleMapper.delete(new QueryWrapper<SysRoleUser>().eq("user_id",adminId));
        //???????????????
        if (!StringUtils.isEmpty(roleIds)) {
            String[] rids = roleIds.split(",");
            List<SysRoleUser> list = new ArrayList<>();
            for (String roleId : rids) {
                SysRoleUser roleRelation = new SysRoleUser();
                roleRelation.setUserId(adminId);
                roleRelation.setRoleId(Long.valueOf(roleId));
                list.add(roleRelation);
            }
            userRoleService.saveBatch(list);
        }
    }
}