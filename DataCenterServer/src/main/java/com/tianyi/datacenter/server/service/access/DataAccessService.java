package com.tianyi.datacenter.server.service.access;

import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.vo.DataStorageDMLVo;
import com.tianyi.datacenter.server.vo.RequestVo;

import java.util.List;
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
     * */
    RequestVo<DataStorageDMLVo> integrateData(DataObject dataObject, String operaType, int page, int pageSize,
                                              List<DataObjectAttribute> conditionAttributes,
                                              List<DataObjectAttribute> updateAttributes);

}
