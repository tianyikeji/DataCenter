package com.tianyi.datacenter.server.service.object.impl;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.dao.object.DataObjectDao;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.service.object.DataObjectService;
import com.tianyi.datacenter.server.vo.PageListVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataObjectServiceImpl implements DataObjectService {

    @Autowired
    private DataObjectDao dataObjectDao;

    @Override
    public int insert(DataObject dataObject) {
        return dataObjectDao.insert(dataObject);
    }

    @Override
    public int delete(int id) {
        return dataObjectDao.delete(id);
    }

    @Override
    public int update(DataObject dataObject) {
        return dataObjectDao.update(dataObject);
    }

    @Override
    /*
     * @author xiayuan
     * @version 0.1
     * @description //TODO
     * @date
     * @param [requestVo]
     * @return java.lang.Object
     */
    public ResponseVo list(RequestVo<Map> requestVo) throws DataCenterException {
        PageListVo pageInfo = requestVo.getPageInfo();
        Map<String, Object> param = PageListVo.createParamMap(pageInfo.getPage(), pageInfo.getPageSize());
        param.putAll(requestVo.getRequest());

        List<DataObject> dataObjectList = dataObjectDao.listBy(param);
        int count = dataObjectDao.countBy(param);

        pageInfo.setTotal(count);

        Map<String, Object> result = new HashMap<>();
        result.put("pageInfo", pageInfo);
        result.put("list", dataObjectList);

        ResponseVo responseVo = ResponseVo.success(result);

        return responseVo;
    }

}
