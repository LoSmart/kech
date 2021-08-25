package com.kech.oauth.service;

import com.kech.common.model.PageResult;
import com.kech.common.model.Result;
import com.kech.common.service.ISuperService;
import com.kech.oauth.model.Client;

import java.util.Map;

/**
 * @author mall
 */
public interface IClientService extends ISuperService<Client> {
    Result saveClient(Client clientDto);

    /**
     * 查询应用列表
     * @param params
     * @param isPage 是否分页
     */
    PageResult<Client> listClent(Map<String, Object> params, boolean isPage);

    void delClient(long id);
}
