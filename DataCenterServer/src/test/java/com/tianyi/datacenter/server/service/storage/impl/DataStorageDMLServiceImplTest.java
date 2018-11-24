package com.tianyi.datacenter.server.service.storage.impl;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.service.storage.DataStorageDMLService;
import com.tianyi.datacenter.server.vo.DataStorageDMLVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DataStorageDMLServiceImplTest {


    @Autowired
    DataStorageDMLService dataStorageDMLService;

    @Test
    public void doServer() {

        DataStorageDMLVo vo = new DataStorageDMLVo();
        vo.setDmlSql("select 1");
        vo.setDmlType("R");

        RequestVo<DataStorageDMLVo> req = new RequestVo(vo);

        ResponseVo resp = null;
        try {
            resp = dataStorageDMLService.doServer(req);
        } catch (DataCenterException e) {
            e.printStackTrace();
        }

        assertTrue(resp.isSuccess());
    }
}