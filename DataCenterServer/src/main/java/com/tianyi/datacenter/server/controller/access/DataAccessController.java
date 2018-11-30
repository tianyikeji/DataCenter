package com.tianyi.datacenter.server.controller.access;

import com.alibaba.fastjson.JSON;
import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.service.access.DataAccessService;
import com.tianyi.datacenter.server.service.storage.DataStorageDMLService;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 数据访问
 *
 * @author wenxinyan
 * @version 0.1
 */
@RestController
@RequestMapping("data")
public class DataAccessController {

    Logger logger = LoggerFactory.getLogger(DataAccessController.class);
    @Autowired
    private DataAccessService dataAccessService;
    @Autowired
    private DataStorageDMLService dataStorageDMLService;

    /**
     * 新增数据接口
     *
     * @author wenxinyan
     * @version 0.1
     */
    @RequestMapping("add")
    public ResponseVo add(String dataObjectId, String data) {
        RequestVo requestVo = dataAccessService.integrateData(dataObjectId, "add", 0, 0,
                null, (Map<String, Object>) JSON.parse(data));

        ResponseVo responseVo = ResponseVo.fail("新增数据失败!");
        try {
            responseVo = dataStorageDMLService.doServer(requestVo);
        } catch (DataCenterException e) {
            logger.error(e.toString());
        }

        return responseVo;
    }

    /**
     * 删除数据接口
     *
     * @author wenxinyan
     * @version 0.1
     */
    @RequestMapping("delete")
    public ResponseVo delete(String dataObjectId, String condition) {
        RequestVo requestVo = dataAccessService.integrateData(dataObjectId, "delete", 0, 0,
                (Map<String, Object>) JSON.parse(condition), null);

        ResponseVo responseVo = ResponseVo.fail("删除数据失败！");
        try {
            responseVo = dataStorageDMLService.doServer(requestVo);
        } catch (DataCenterException e) {
            logger.error(e.toString());
        }

        return responseVo;
    }

    /**
     * 修改数据接口
     *
     * @author wenxinyan
     * @version 0.1
     */
    @RequestMapping("update")
    public ResponseVo update(String dataObjectId, String data, String condition) {
        RequestVo requestVo = dataAccessService.integrateData(dataObjectId, "update", 0, 0,
                (Map<String, Object>) JSON.parse(condition), (Map<String, Object>) JSON.parse(data));

        ResponseVo responseVo = ResponseVo.fail("修改数据失败！");
        try {
            responseVo = dataStorageDMLService.doServer(requestVo);
        } catch (DataCenterException e) {
            logger.error(e.toString());
        }

        return responseVo;
    }

    /**
     * 查询数据接口
     *
     * @author wenxinyan
     * @version 0.1
     */
    @RequestMapping("retrieve")
    public ResponseVo retrieve(String dataObjectId, String condition, Integer page, Integer pageSize) {
        RequestVo requestVo = dataAccessService.integrateData(dataObjectId, "retrieve", page, pageSize,
                (Map<String, Object>) JSON.parse(condition), null);

        ResponseVo responseVo = ResponseVo.fail("查询数据失败！");
        try {
            responseVo = dataStorageDMLService.doServer(requestVo);
        } catch (DataCenterException e) {
            logger.error("",e.toString());
        }

        return responseVo;
    }

}
