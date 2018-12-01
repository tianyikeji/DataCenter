package com.tianyi.datacenter.server.service.access.impl;

import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.service.access.DataAccessService;
import com.tianyi.datacenter.server.vo.DataStorageDMLVo;
import com.tianyi.datacenter.server.vo.PageListVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 数据访问
 *
 * @author wenxinyan
 * @version 0.1
 */
@Service
public class DataAccessServiceImpl implements DataAccessService {
    /**
     * 将请求数据封装成dmlvo
     * @author wenxinyan
     * 2018/11/27 10:07
     * @param  dataObject 数据对象id
     * @param page 当前页码
     * @param pageSize 每页条数
     * @param conditionAttributes 条件属性列表
     * @param updateAttributes 更新、新增属性列表
     * @return dml请求对象
    */
    @Override
    public RequestVo<DataStorageDMLVo> integrateData(DataObject dataObject, String operaType, int page, int pageSize,
                                                     List<DataObjectAttribute> conditionAttributes,
                                                     List<DataObjectAttribute> updateAttributes) {

        DataStorageDMLVo dmlVo = new DataStorageDMLVo();
        //表信息
        dmlVo.setDataObject(dataObject);
        //dml操作类型，增删改查
        dmlVo.setDmlType(operaType);
        //更新字段信息
        dmlVo.setAttributes(updateAttributes);
        //条件字段信息
        dmlVo.setCondition(conditionAttributes);
        //遍历组装更新信息
        RequestVo<DataStorageDMLVo> requestVo = new RequestVo<>(dmlVo);

        if(page != 0 && pageSize != 0){
            PageListVo pageListVo = new PageListVo(page, pageSize);
            requestVo.setPageInfo(pageListVo);
        }

        return requestVo;
    }

}
