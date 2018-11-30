package com.tianyi.datacenter.server.service.storage.util;

import ch.qos.logback.core.db.dialect.DBUtil;
import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.common.util.FreeMarkerUtil;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.vo.DataStorageDDLVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tianyi.datacenter.common.exception.DataCenterException.DC_DO_8901;
import static com.tianyi.datacenter.common.exception.DataCenterException.DC_DO_8901_MSG;

@Component
public class DDLUtil {

    private static Logger logger = LoggerFactory.getLogger(DBUtil.class);

    /**
     * 根据DDL对象生成DDL语句
     *
     * @param ddlVo ddl信息
     * @return 查询sql语句
     * @author zhouwei
     * 2018/11/19 15:26
     */
    public static String generateDDL(DataStorageDDLVo ddlVo) throws DataCenterException {
        String ddlSql = "";

        switch (ddlVo.getDdlType()) {
            case "C":
                //创建表操作
                String ftlName = "Create";
                ddlSql = generateCreateTableSql(ddlVo.getDataObject(), ddlVo.getAttributes(), ftlName);
                if (ddlSql == null) {
                    //TODO
                    throw new DataCenterException("", "");
                }
                break;
            case "U":
                ftlName = "Update";
                //TODO 完成修改表的逻辑
                if (ddlSql == null) {
                    //TODO
                    throw new DataCenterException("", "");
                }
                break;
            default:
                throw new DataCenterException(DC_DO_8901, DC_DO_8901_MSG);
        }
        //删除回车换行符
        ddlSql = ddlSql.replaceAll("[\r\n]", "");
        return ddlSql;
    }

    /**
     * //TODO 生成创建表的ddl语句
     * 不支持联合主键，联合索引，触发器
     *
     * @param dataObject 数据对象
     * @param attributes 数据对象属性列表
     * @param ftlName 模板名称
     * @return String 查询sql
     * @author zhouwei
     * 2018/11/19 16:39
     */
    private static String generateCreateTableSql(DataObject dataObject,
                                                 List<DataObjectAttribute> attributes, String ftlName) {
        String ddlSql = "";

        Map ftlRoot = new HashMap();
        ftlRoot.put("columns", attributes);
        ftlRoot.put("dataObject", dataObject);
        ddlSql = FreeMarkerUtil.process(ftlRoot, ftlName);

        logger.debug("生成create table语句:" + ddlSql);

        return ddlSql;
    }
}
