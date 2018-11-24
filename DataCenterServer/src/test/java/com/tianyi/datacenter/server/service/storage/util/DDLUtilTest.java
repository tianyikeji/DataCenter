package com.tianyi.datacenter.server.service.storage.util;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.vo.DataStorageDDLVo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DDLUtilTest {

    @Test
    public void generateDDL() {
        DataStorageDDLVo ddlVo = new DataStorageDDLVo();
        //create table
        ddlVo.setDdlType("C");
        //表名，test
        DataObject object = new DataObject();
        object.setDefined("Test");
        object.setDescription("comment for table test");
        ddlVo.setDataObject(object);

        //字段
        List<DataObjectAttribute> attributes = new ArrayList<>();
        DataObjectAttribute attribute = new DataObjectAttribute();
        attribute.setColumnName("field1");
        attribute.setDescription("comment for field1");
        attribute.setJdbcType("int");
        attribute.setLength(10);
        attributes.add(attribute);
        attribute = new DataObjectAttribute();
        attribute.setColumnName("field2");
        attribute.setDescription("comment for field2");
        attribute.setJdbcType("int");
        attribute.setLength(20);
        attributes.add(attribute);

        ddlVo.setAttributes(attributes);


        String sql = "";
        try {
            sql = DDLUtil.generateDDL(ddlVo);
        } catch (DataCenterException e) {
            e.printStackTrace();
        }

        System.out.println(sql);
    }
}