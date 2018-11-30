package com.tianyi.datacenter.server.service.object;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;

import java.util.Map;

/**
 * Created by wenxinyan on 2018/11/21.
 */
public interface DataObjectAttributeService {

    int insert(DataObjectAttribute dataObjectAttribute);

    int delete(Map<String, Object> param);

    int update(DataObjectAttribute dataObjectAttribute);

    ResponseVo list(RequestVo<Map> requestVo) throws DataCenterException;

}
