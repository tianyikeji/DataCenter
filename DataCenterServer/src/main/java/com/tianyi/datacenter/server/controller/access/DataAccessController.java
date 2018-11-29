package com.tianyi.datacenter.server.controller.access;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.service.access.DataAccessService;
import com.tianyi.datacenter.server.service.object.DataObjectAttributeService;
import com.tianyi.datacenter.server.service.object.DataObjectService;
import com.tianyi.datacenter.server.service.storage.DataStorageDMLService;
import com.tianyi.datacenter.server.vo.PageListVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private DataObjectService dataObjectService;
    @Autowired
    private DataObjectAttributeService dataObjectAttributeService;

    /**
     * 新增数据接口
     *
     * @author wenxinyan
     */
    @RequestMapping("add")
    public ResponseVo add(@RequestBody JSONObject jsonObject) {
        int dataObjectId = (int) jsonObject.get("dataObjectId");
        Map<String, Object> data = (Map<String, Object>) jsonObject.get("data");
        //获取新增字段属性信息
        List<DataObjectAttribute> insertAttributes = getAllAttributes(dataObjectId, data);
        //组装dml请求vo
        RequestVo requestVo = dataAccessService.integrateData(dataObjectService.getById(dataObjectId), "C", 0, 0,
                null, insertAttributes);

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
     */
    @RequestMapping("delete")
    public ResponseVo delete(@RequestBody JSONObject jsonObject) {
        int dataObjectId = (int) jsonObject.get("dataObjectId");
        List<Map<String, Object>> condition = (List<Map<String, Object>>) jsonObject.get("condition");
        //获取条件字段属性信息
        List<DataObjectAttribute> conditionAttributes = getAllAttributes(dataObjectId, condition);
        //组装dml请求vo
        RequestVo requestVo = dataAccessService.integrateData(dataObjectService.getById(dataObjectId), "D", 0, 0,
                conditionAttributes, null);

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
     */
    @RequestMapping("update")
    public ResponseVo update(@RequestBody JSONObject jsonObject) {
        int dataObjectId = (int) jsonObject.get("dataObjectId");
        Map<String, Object> data = (Map<String, Object>) jsonObject.get("data");
        List<Map<String, Object>> condition = (List<Map<String, Object>>) jsonObject.get("condition");
        //获取更新字段属性信息
        List<DataObjectAttribute> updateAttributes = getAllAttributes(dataObjectId, data);
        //获取条件字段属性信息
        List<DataObjectAttribute> conditionAttributes = getAllAttributes(dataObjectId, condition);
        //组装dml请求vo
        RequestVo requestVo = dataAccessService.integrateData(dataObjectService.getById(dataObjectId), "U", 0, 0,
                conditionAttributes, updateAttributes);

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
     */
    @RequestMapping("retrieve")
    public ResponseVo retrieve(@RequestBody JSONObject jsonObject) {
        int dataObjectId = (int) jsonObject.get("dataObjectId");
        List<Map<String, Object>> condition = (List<Map<String, Object>>) jsonObject.get("condition");
        Map pageInfoTmp = (Map) jsonObject.get("pageInfo");
        PageListVo pageInfo = new PageListVo((int)pageInfoTmp.get("page"),(int)pageInfoTmp.get("pageSize"));
        //获取条件字段属性信息
        List<DataObjectAttribute> conditionAttributes = getAllAttributes(dataObjectId, condition);
        //组装dml请求vo
        RequestVo requestVo = dataAccessService.integrateData(dataObjectService.getById(dataObjectId), "R", pageInfo.getPage(), pageInfo.getPageSize(),
                conditionAttributes, null);

        ResponseVo responseVo = ResponseVo.fail("查询数据失败！");
        try {
            responseVo = dataStorageDMLService.doServer(requestVo);
        } catch (DataCenterException e) {
            logger.error("",e.toString());
        }

        return responseVo;
    }

    /**
     * 获取数据对象属性交集
     * @author zhouwei
     * 2018/11/27 09:39
     * @param dataObjectId 数据对象ID
     * @param columnInfo 请求的字段信息
     * @return 数据对象所有属性与传入属性的交集
    */
    private List<DataObjectAttribute> getAllAttributes(int dataObjectId, List<Map<String, Object>> columnInfo) {

        //获取所有字段属性
        Map tmpInfo = new HashMap();
        tmpInfo.put("resId", dataObjectId);
        List<DataObjectAttribute> allAttributes = dataObjectAttributeService.listNoPage(tmpInfo);

        //初始化送往模板的字段属性
        List<DataObjectAttribute> conditionInfo = new ArrayList<>();
        //与传入的字段做合并
        for (Map<String, Object> attr : columnInfo) {
            for (DataObjectAttribute tmpAttribute : allAttributes) {
                if (tmpAttribute.getColumnName().toUpperCase().equals(attr.get("key").toString().toUpperCase())) {
                //匹配到相同的属性
                    //设置条件
                    tmpAttribute.setOper((String) attr.get("condition"));
                    //设置值
                    tmpAttribute.setValue((String) attr.get("value"));
                    conditionInfo.add(tmpAttribute);
                }
            }
        }
        return conditionInfo;
    }

    private List<DataObjectAttribute> getAllAttributes(int dataObjectId, Map<String, Object> columnInfo) {

        //获取所有字段属性
        Map tmpInfo = new HashMap();
        tmpInfo.put("resId", dataObjectId);
        List<DataObjectAttribute> allAttributes = dataObjectAttributeService.listNoPage(tmpInfo);

        //初始化送往模板的字段属性
        List<DataObjectAttribute> conditionInfo = new ArrayList<>();
        //与传入的字段做合并
        for (Object key : columnInfo.keySet()) {
            for (DataObjectAttribute tmpAttribute : allAttributes) {
                if (tmpAttribute.getColumnName().toUpperCase().equals(key.toString().toUpperCase())) {
                    //匹配到相同的属性
                    //设置值
                    tmpAttribute.setValue((String) columnInfo.get(key));
                    conditionInfo.add(tmpAttribute);
                }
            }
        }
        return conditionInfo;
    }

}
