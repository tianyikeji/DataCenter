package com.tianyi.datacenter.server.controller.resource;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.controller.access.DataAccessController;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.service.object.DataObjectService;
import com.tianyi.datacenter.server.service.resource.ResourceService;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源目录
 *
 * @author xiayuan
 * 2018/11/19 16:39
 */
@RestController
@RequestMapping("resource")
public class ResourceController {

    private Logger logger = LoggerFactory.getLogger(DataAccessController.class);
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private DataObjectService dataObjectService;
    /**
     *
     * @param dataObjectId  对象id
     * @param keyword 查询关键字
     * @param page 当前页数
     * @param pageSize  每页条数
     * @return ResponseVo json
     * @author xiayuan
     * 2018/11/19 16:39
     */

    @RequestMapping("get")
    @CrossOrigin
    public ResponseVo getResources(String dataObjectId, String keyword, Integer page, Integer pageSize) {
        RequestVo<Map> requestVo = resourceService.integrateData(dataObjectId, keyword, page, pageSize);
        ResponseVo responseVo = ResponseVo.fail("数据查询失败");
        try {
            responseVo = dataObjectService.list(requestVo);
        } catch (DataCenterException e) {
            logger.error("", e.toString());
        }
        return responseVo;
    }
}
