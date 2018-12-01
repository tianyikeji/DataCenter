package com.tianyi.datacenter.server.service.access;

import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.service.access.impl.DataAccessServiceImpl;
import com.tianyi.datacenter.server.vo.DataStorageDMLVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenxinyan on 2018/11/21.
 */
public class DataAccessServiceUnitTest {

    private DataAccessService dataAccessService = new DataAccessServiceImpl();

    @Test
    public void createBusinessData() {
//        DataAccessService dataAccessService = new DataAccessServiceImpl();

        //新增数据
        List<DataObjectAttribute> updateAttributes = new ArrayList<>();
        DataObjectAttribute attribute = new DataObjectAttribute();
        attribute.setName("updateField1");
        attribute.setValue("value1");
        attribute.setType("text");
        updateAttributes.add(attribute);
        attribute = new DataObjectAttribute();
        attribute.setName("updateField2");
        attribute.setValue("value2");
        attribute.setType("text");
        updateAttributes.add(attribute);

        List<DataObjectAttribute> conditionAttributes = new ArrayList<>();
        attribute = new DataObjectAttribute();
        attribute.setName("conditionField1");
        attribute.setValue("value3");
        attribute.setType("text");
        conditionAttributes.add(attribute);
        attribute = new DataObjectAttribute();
        attribute.setName("conditionField2");
        attribute.setValue("value4");
        attribute.setType("text");
        conditionAttributes.add(attribute);

        DataObject dataObject = new DataObject();

        RequestVo<DataStorageDMLVo> requestVo = dataAccessService.integrateData(dataObject, "C", 0, 0,
                conditionAttributes, updateAttributes);


        assertEquals(requestVo.getRequest().getAttributes(), updateAttributes);
        assertEquals(requestVo.getRequest().getCondition(), conditionAttributes);
    }

}
