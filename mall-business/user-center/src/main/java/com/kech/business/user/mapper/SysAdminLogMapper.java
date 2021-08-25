package com.kech.business.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kech.common.model.SysAdminLog;
import com.kech.common.vo.LogParam;
import com.kech.common.vo.LogStatisc;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
public interface SysAdminLogMapper extends BaseMapper<SysAdminLog> {

   List<LogStatisc> getLogStatisc(LogParam entity);


}
