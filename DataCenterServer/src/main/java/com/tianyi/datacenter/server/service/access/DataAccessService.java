package com.tianyi.datacenter.server.service.access;

import com.tianyi.datacenter.server.vo.DataObjectDMLVo;
import com.tianyi.datacenter.server.vo.RequestVo;

import java.util.Map;

/**
 * 数据访问
 *
 * @author wenxinyan
 * @version 0.1
 */
public interface DataAccessService {

    /**
     * 整合对象数据
     *
     * @author wenxinyan
     * @version 0.1
     * */
    RequestVo<DataObjectDMLVo> integrateData(String dataObjectId, String operaType, int page, int pageSize,
                                             Map condition, Map updateInfo);

}
