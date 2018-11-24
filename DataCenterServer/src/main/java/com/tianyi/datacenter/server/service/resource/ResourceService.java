package com.tianyi.datacenter.server.service.resource;


import com.tianyi.datacenter.server.vo.RequestVo;

import java.util.Map;

public interface ResourceService {

    /**
     * 整合对象数据
     *
     *
     * */
    RequestVo<Map> integrateData(String dataObjectId, String keyword, int page, int pageSize);
}
