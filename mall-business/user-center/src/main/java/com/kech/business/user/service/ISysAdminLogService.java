package com.kech.business.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kech.common.model.SysAdminLog;
import com.kech.common.vo.LogParam;
import com.kech.common.vo.LogStatisc;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
public interface ISysAdminLogService extends IService<SysAdminLog> {
     List<LogStatisc> selectPageExt(LogParam entity);
}
