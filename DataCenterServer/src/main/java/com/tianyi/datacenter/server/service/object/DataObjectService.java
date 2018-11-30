package com.tianyi.datacenter.server.service.object;


import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;

import java.util.Map;

public interface DataObjectService {

    int insert(DataObject dataObject);

    int delete(int id);

    int update(DataObject dataObject);

    ResponseVo list(RequestVo<Map> requestVo) throws DataCenterException;

}
