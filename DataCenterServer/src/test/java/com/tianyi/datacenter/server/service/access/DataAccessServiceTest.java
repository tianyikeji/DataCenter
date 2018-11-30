package com.tianyi.datacenter.server.service.access;

import com.tianyi.datacenter.server.service.access.DataAccessService;
import com.tianyi.datacenter.server.service.access.impl.DataAccessServiceImpl;
import com.tianyi.datacenter.server.vo.DataObjectDMLVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenxinyan on 2018/11/21.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DataAccessServiceTest {

    @Autowired
    private DataAccessService dataAccessService;

    @Test
    public void integrateDataTest(){
//        DataAccessService dataAccessService = new DataAccessServiceImpl();

        Map<String, Object> condition = new HashMap<>();
        condition.put("name", "xxx");
        condition.put("username", "xxxx");
        condition.put("password", "1234");
        Map<String, Object> updateInfo = new HashMap<>();
        updateInfo.put("name", "xxx");
        updateInfo.put("username", "xxxx");
        updateInfo.put("password", "1234");

        RequestVo<DataObjectDMLVo> requestVo = dataAccessService.integrateData("001", "add", 0, 0, null, updateInfo);

        System.out.println("integrateDataTestResult");
        System.out.println(requestVo.getPageInfo());
    }

}
