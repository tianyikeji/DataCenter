package com.tianyi.datacenter.server.service.storage.util;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.common.util.FreeMarkerUtil;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.vo.DataStorageDMLVo;
import com.tianyi.datacenter.server.vo.PageListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tianyi.datacenter.common.exception.DataCenterException.DC_DO_8901;
import static com.tianyi.datacenter.common.exception.DataCenterException.DC_DO_8901_MSG;

/**
 * 预处理DML语句的工具类
 * 2018/11/19 15:25
 *
 * @author zhouwei
 * @version 0.1
 **/
@Component
public class DMLUtil {

    private Logger logger = LoggerFactory.getLogger(DMLUtil.class);

    /**
     * 生成update语句
     *
     * @param dataObject 表信息
     * @param updateInfo 字段信息
     * @param condition 条件字段
     * @return 插入语句
     * @author xiayuan
     * 2018/11/24 17:39
     */
    private static String generateUpdateSql(DataObject dataObject, List<DataObjectAttribute> updateInfo,
                                            List<DataObjectAttribute> condition) {
        String dmlSql;
        //组装ftl的root节点
        Map<String, Object> ftlRoot = new HashMap<>();
        //字段值
        ftlRoot.put("updateInfo", updateInfo);
        //数据库表
        ftlRoot.put("dataObject", dataObject);
        ftlRoot.put("condition", condition);
        dmlSql = FreeMarkerUtil.process(ftlRoot, "Update");
        return dmlSql;
    }

    /**
     * 根据DML对象生成DML语句
     *
     * @param dmlVo    dml信息
     * @param pageInfo 分页信息
     * @return 查询sql语句
     * @author zhouwei
     * 2018/11/19 15:26
     */
    public String generateDML(DataStorageDMLVo dmlVo, PageListVo pageInfo) throws DataCenterException {
        String dmlSql = "";

        switch (dmlVo.getDmlType()) {
            case "R":
                //查询操作
                dmlSql = generateRetrieveSql(dmlVo.getCondition(), dmlVo.getDataObject(), dmlVo.getAttributes(), pageInfo);
                if (dmlSql == null) {
                    throw new DataCenterException("", "");
                }
                break;
            case "C":
                //新增操作
                dmlSql = generateInsertSql(dmlVo.getDataObject(), dmlVo.getAttributes());
                if (dmlSql == null) {
                    throw new DataCenterException("", "");
                }
                break;
            case "U":
                dmlSql = generateUpdateSql(dmlVo.getDataObject(), dmlVo.getAttributes(), dmlVo.getCondition());
                if (dmlSql == null) {
                    throw new DataCenterException("", "");
                }
                break;
            case "D":
                dmlSql = generateDeleteSql(dmlVo.getDataObject(), dmlVo.getCondition());
                if (dmlSql == null) {
                    throw new DataCenterException("", "");
                }
                break;
            default:
                throw new DataCenterException(DC_DO_8901, DC_DO_8901_MSG);
        }
        //替换模板文件中的换行
        dmlSql = dmlSql.replaceAll("[\r\n]", "").replaceAll("\\s{3,}?", " ");
        logger.debug("生成的dml语句：" + dmlSql);
        return dmlSql;
    }

    /**
     * 生成delete语句
     * @author zhouwei
     * 2018/11/28 09:48
     * @param dataObject 表信息
     * @param condition 删除条件
     * @return 删除sql语句
    */
    private String generateDeleteSql(DataObject dataObject, List<DataObjectAttribute> condition) {
        String dmlSql;
       //组装ftl的root节点
        Map<String, Object> ftlRoot = new HashMap<>();

        //字段值
        ftlRoot.put("condition", condition);
        //数据库表
        ftlRoot.put("dataObject", dataObject);
        dmlSql = FreeMarkerUtil.process(ftlRoot, "Delete");
        return dmlSql;
    }

    /**
     * 生成insert语句
     *
     * @param dataObject 表信息
     * @param condition 要插入的字段信息，复用条件字段
     * @return 插入语句
     * @author zhouwei
     * 2018/11/22 17:39
     */
    private String generateInsertSql(DataObject dataObject, List<DataObjectAttribute> condition) {
        String dmlSql;
        //组装ftl的root节点
        Map<String, Object> ftlRoot = new HashMap<>();

        //字段值
        ftlRoot.put("insertInfo", condition);
        //数据库表
        ftlRoot.put("dataObject", dataObject);
        dmlSql = FreeMarkerUtil.process(ftlRoot, "Insert");
        return dmlSql;
    }

    /**
     * 生成查询sql语句，暂时不支持group by
     *
     * @param conditions 查询条件
     * @param dataObject 数据对象
     * @param attributes 数据对象属性列表
     * @param pageInfo   分页信息
     * @return String 查询sql
     * @author zhouwei
     * 2018/11/19 16:39
     */
    private String generateRetrieveSql(List<DataObjectAttribute> conditions, DataObject dataObject,
                                       List<DataObjectAttribute> attributes, PageListVo pageInfo) {
        if (conditions == null || conditions.size() == 0) {
            conditions = null;
        }
        String dmlSql;
        //组装ftl的root节点
        Map<String, Object> ftlRoot = new HashMap<>();

        //查询条件
        ftlRoot.put("condition", conditions);
        //字段
        ftlRoot.put("columns", attributes);
        //数据库表
        ftlRoot.put("dataObject", dataObject);

        //组装分页信息
        Map<String, Object> pageInfoTmp = null;
        if(pageInfo!=null){
            pageInfoTmp = PageListVo.createParamMap(pageInfo.getPage(), pageInfo.getPageSize());
        }
        //分页信息
        ftlRoot.put("pageInfo", pageInfoTmp);

        dmlSql = FreeMarkerUtil.process(ftlRoot, "Retrieve");
        return dmlSql;
    }

}
