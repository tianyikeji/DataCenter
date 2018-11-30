package com.tianyi.datacenter.server.service.object.impl;

import com.tianyi.datacenter.server.dao.object.DataObjectAttributeDao;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.service.object.DataObjectAttributeService;
import com.tianyi.datacenter.server.vo.PageListVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wenxinyan on 2018/11/21.
 */
@Service
public class DataObjectAttributeServiceImpl implements DataObjectAttributeService {

    @Autowired
    private DataObjectAttributeDao dataObjectAttributeDao;

    @Override
    public int insert(DataObjectAttribute dataObjectAttribute){
        return dataObjectAttributeDao.insert(dataObjectAttribute);
    }

    @Override
    public int delete(Map<String, Object> param){
        return dataObjectAttributeDao.delete(param);
    }


    public int update(DataObjectAttribute dataObjectAttribute){
        return dataObjectAttributeDao.update(dataObjectAttribute);
    }

    @Override
    public ResponseVo list(RequestVo<Map> requestVo){
        PageListVo pageInfo = requestVo.getPageInfo();
        Map<String, Object> param = PageListVo.createParamMap(pageInfo.getPage(), pageInfo.getPageSize());
        param.putAll(requestVo.getRequest());

        List<DataObjectAttribute> attributeList = dataObjectAttributeDao.listBy(param);
        int count = dataObjectAttributeDao.countBy(param);

        pageInfo.setTotal(count);

        Map<String, Object> result = new HashMap<>();
        result.put("pageInfo", pageInfo);
        result.put("list", attributeList);

        ResponseVo responseVo = ResponseVo.success(result);

        return responseVo;
    }
}
