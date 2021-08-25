package com.kech.oauth.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kech.db.mapper.SuperMapper;
import com.kech.oauth.model.Client;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author mall
 */
public interface ClientMapper extends SuperMapper<Client> {
    List<Client> findList(Page<Client> page, @Param("params") Map<String, Object> params );
}
