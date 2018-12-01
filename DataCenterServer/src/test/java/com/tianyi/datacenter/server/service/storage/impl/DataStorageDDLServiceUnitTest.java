package com.tianyi.datacenter.server.service.storage.impl;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.service.storage.DataStorageDDLService;
import com.tianyi.datacenter.server.service.storage.util.DBUtil;
import com.tianyi.datacenter.server.vo.DataStorageDDLVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DataStorageDDLServiceUnitTest {

    @InjectMocks
    private DataStorageDDLService dataStorageDDLService = new DataStorageDDLServiceImpl();

    @Mock
    private DBUtil database;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        doNothing().when(database).executeDDL(anyString(), anyString());
        Map<String, Map<String, Object>> schemaColumnInfo = new HashMap<>();
        schemaColumnInfo.put("field1", new HashMap<>());
        when(database.getColumnsInfo("Test")).thenReturn(schemaColumnInfo);
    }

    @Test
    public void createTable() {

        assertNotNull(dataStorageDDLService);

        //ddl请求
        DataStorageDDLVo ddlVo = new DataStorageDDLVo();
        //创建表
        ddlVo.setDdlType("C");
        //表名
        DataObject object = new DataObject();
        object.setDefined("Test");
        object.setDescription("comment for table test");
        ddlVo.setDataObject(object);
        //字段
        List<DataObjectAttribute> attributes = new ArrayList<>();
        DataObjectAttribute attribute = new DataObjectAttribute();
        attribute.setColumnName("field1");
        attribute.setJdbcType("int");
        attribute.setLength(10);
        attribute.setDescription("comment for column field1");
        attributes.add(attribute);
        attribute = new DataObjectAttribute();
        attribute.setColumnName("field2");
        attribute.setJdbcType("int");
        attribute.setLength(20);
        attribute.setDescription("comment for column field2");
        attributes.add(attribute);
        ddlVo.setAttributes(attributes);

        RequestVo<DataStorageDDLVo> requestVo = new RequestVo<>(ddlVo);

        //请求ddl服务
        ResponseVo responseVo = new ResponseVo();
        try {
            responseVo = dataStorageDDLService.doServer(requestVo);
        } catch (DataCenterException e) {
            e.printStackTrace();
        }

        verify(database, times(1));

        assertTrue(responseVo.isSuccess());
    }
    @Test
    public void alterTable(){


        /*
        测试删除表中所有主键的逻辑
         */


        //ddl请求
        DataStorageDDLVo ddlVo = new DataStorageDDLVo();
        //创建表
        ddlVo.setDdlType("U");
        //表名
        DataObject object = new DataObject();
        object.setDefined("Test");
        object.setDescription("comment for table test");
        ddlVo.setDataObject(object);
        //字段
        List<DataObjectAttribute> attributes = new ArrayList<>();
        ddlVo.setAttributes(attributes);
        RequestVo<DataStorageDDLVo> requestVo = new RequestVo<>(ddlVo);

        //请求ddl服务
        ResponseVo responseVo = new ResponseVo();
        try {
            responseVo = dataStorageDDLService.doServer(requestVo);
        } catch (DataCenterException e) {
            e.printStackTrace();
        }

        verify(database, times(1));

        assertFalse(responseVo.isSuccess());
        assertEquals("应该调用删除table服务", responseVo.getMessage());
    }
}