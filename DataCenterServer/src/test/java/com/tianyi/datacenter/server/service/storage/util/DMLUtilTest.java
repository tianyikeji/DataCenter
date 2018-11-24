package com.tianyi.datacenter.server.service.storage.util;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.vo.DataStorageDMLVo;
import com.tianyi.datacenter.server.vo.PageListVo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DMLUtilTest {

    @Test
    public void generateInsert() {

        DataStorageDMLVo dmlVo = new DataStorageDMLVo();

        //插入
        dmlVo.setDmlType("C");
        //表信息
        DataObject dataObject = new DataObject();
        dataObject.setDefined("test");
        //字段
        Map<String, Object> info = new HashMap<>();
        info.put("field1", "value1");
        info.put("field2", "value2");
        dmlVo.setUpdateInfo(info);

        dmlVo.setDataObject(dataObject);

        String sql = null;
        try {
            sql = DMLUtil.generateDML(dmlVo, null);
        } catch (DataCenterException e) {
            e.printStackTrace();
        }

        System.out.println(sql);
        assertEquals("INSERT INTO test ( field1 , field2  ) VALUES (    'value1' ,    'value2'  )", sql);
    }

    @Test
    public void generateQuery() {

        DataStorageDMLVo dmlVo = new DataStorageDMLVo();

        //查询类型
        dmlVo.setDmlType("R");

        //条件字段
        Map<String, String> condition = new HashMap<>();
        condition.put("field1", "value1");
        condition.put("field2", "value2");
        dmlVo.setCondition(condition);

        //表名
        DataObject dataObject = new DataObject();
        dataObject.setDefined("test");
        dmlVo.setDataObject(dataObject);

        //查询字段
        List<DataObjectAttribute> attributes = new ArrayList<>();
        DataObjectAttribute attribute = new DataObjectAttribute();
        attribute.setColumnName("fieldA");
        attributes.add(attribute);
        attribute = new DataObjectAttribute();
        attribute.setColumnName("fieldB");
        attributes.add(attribute);
        attribute = new DataObjectAttribute();
        attribute.setColumnName("fieldC");
        attributes.add(attribute);
        dmlVo.setAttributes(attributes);

        //分页信息
        PageListVo pageInfo = new PageListVo(2, 50);

        //调用生成sql工具类
        String sql = null;
        try {
            sql = DMLUtil.generateDML(dmlVo, pageInfo);
        } catch (DataCenterException e) {
            e.printStackTrace();
        }

        System.out.println(sql);
        assertEquals("SELECT DISTINCT     fieldA,  fieldB,  fieldC FROM test WHERE field1='value1' and " +
                "field2='value2' LIMIT 2,50", sql);
    }
}