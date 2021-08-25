package com.kech.business.user.service;


import com.kech.business.user.vo.OssCallbackResult;
import com.kech.business.user.vo.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

/**
 * oss上传管理Service
 */
public interface OssService {
    OssPolicyResult policy();

    OssCallbackResult callback(HttpServletRequest request);
}
