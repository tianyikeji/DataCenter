package com.tianyi.datacenter.server.service.resource.impl;


import com.tianyi.datacenter.server.service.resource.ResourceService;
import com.tianyi.datacenter.server.vo.PageListVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Override
    public RequestVo<Map> integrateData(String dataObjectId, String keyword, int page, int pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("dataObjectId",dataObjectId);
        map.put("name",keyword);
        RequestVo<Map> requestVo = new RequestVo<>(map);
        if(page != 0 && pageSize != 0){
            PageListVo pageListVo = new PageListVo(page, pageSize);
            requestVo.setPageInfo(pageListVo);
        }
        return requestVo;
    }
}
