package com.tianyi.datacenter.server.service.storage.util;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.common.util.FreeMarkerUtil;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.vo.DataStorageDMLVo;
import com.tianyi.datacenter.server.vo.PageListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tianyi.datacenter.common.exception.DataCenterException.DC_DO_8901;
import static com.tianyi.datacenter.common.exception.DataCenterException.DC_DO_8901_MSG;

/**
 * 预处理DML语句的工具类
 * 2018/11/19 15:25
 * @author zhouwei
 * @version 0.1
 **/
public class DMLUtil {

    private static Logger logger = LoggerFactory.getLogger(DMLUtil.class);

    /**
     * 根据DML对象生成DML语句 //TODO
     * @author zhouwei
     * 2018/11/19 15:26
     * @param dmlVo dml信息
     * @param pageInfo 分页信息
     * @return 查询sql语句
     */
    public static String generateDML(DataStorageDMLVo dmlVo, PageListVo pageInfo) throws DataCenterException {
        String dmlSql = "";

        switch (dmlVo.getDmlType()) {
            case "R":
                //查询操作
                dmlSql = generateRetrieveSql(dmlVo.getCondition(), dmlVo.getDataObject(), dmlVo.getAttributes(), pageInfo);

                if(dmlSql==null){
                    throw new DataCenterException("", "");
                }
                break;
            case "C":
                //新增操作
                dmlSql = generateInsertSql(dmlVo.getDataObject(), dmlVo.getUpdateInfo());
                break;
            default:
                throw new DataCenterException(DC_DO_8901, DC_DO_8901_MSG);
        }
        //替换模板文件中的换行
        dmlSql = dmlSql.replaceAll("[\r\n]", "");
        logger.debug("生成的dml语句："+ dmlSql);
        return dmlSql;
    }

    /**
     * //TODO 说明
     *
     * @param dataObject 表信息
     * @param updateInfo 字段信息
     * @return 插入语句
     * @author zhouwei
     * 2018/11/22 17:39
     */
    private static String generateInsertSql(DataObject dataObject, Map<String, Object> updateInfo) {
        String dmlSql;
        //组装ftl的root节点
        Map<String, Object> ftlRoot = new HashMap<>();
        //TODO 支持不同字段类型的拼接
        //字段值
        ftlRoot.put("updateInfo", updateInfo);
        //数据库表
        ftlRoot.put("dataObject", dataObject);
        dmlSql = FreeMarkerUtil.process(ftlRoot, "Insert");
        return dmlSql;
    }

    /**
     * //TODO 生成查询sql语句，暂时不支持group by
     * @author zhouwei
     * 2018/11/19 16:39
     * @param condition 查询条件
     * @param dataObject 数据对象
     * @param attributes 数据对象属性列表
     * @param pageInfo 分页信息
     * @return String 查询sql
     */
    private static String generateRetrieveSql(Map condition, DataObject dataObject,
                                        List<DataObjectAttribute> attributes, PageListVo pageInfo) {
        String dmlSql;
        //组装ftl的root节点
        Map<String, Object> ftlRoot = new HashMap<>();

        //TODO 支持不同字段类型的拼接 条件
        ftlRoot.put("condition", condition);
        //字段
        ftlRoot.put("columns", attributes);
        //数据库表
        ftlRoot.put("dataObject", dataObject);
        //分页信息
        ftlRoot.put("pageInfo", pageInfo);
        //TODO 支持order by

        dmlSql = FreeMarkerUtil.process(ftlRoot, "Retrieve");
        return dmlSql;
    }

}
