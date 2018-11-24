package com.tianyi.datacenter.server.service.access.impl;

import com.tianyi.datacenter.server.service.access.DataAccessService;
import com.tianyi.datacenter.server.vo.DataObjectDMLVo;
import com.tianyi.datacenter.server.vo.PageListVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 数据访问
 *
 * @author wenxinyan
 * @version 0.1
 */
@Service
public class DataAccessServiceImpl implements DataAccessService {

    @Override
    public RequestVo<DataObjectDMLVo> integrateData(String dataObjectId, String operaType, int page, int pageSize,
                                                    Map condition, Map updateInfo) {
        DataObjectDMLVo dataObjectDMLVo = new DataObjectDMLVo();
        dataObjectDMLVo.setDataObjectId(dataObjectId);
        dataObjectDMLVo.setOperaType(operaType);
        dataObjectDMLVo.setCondition(condition);
        dataObjectDMLVo.setUpdateInfo(updateInfo);

        RequestVo<DataObjectDMLVo> requestVo = new RequestVo<>(dataObjectDMLVo);

        if(page != 0 && pageSize != 0){
            PageListVo pageListVo = new PageListVo(page, pageSize);
            requestVo.setPageInfo(pageListVo);
        }

        return requestVo;
    }

}
