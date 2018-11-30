package com.tianyi.datacenter.common.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * //TODO 说明
 *
 * @author zhouwei
 * 2018/11/19 21:48
 * @version 0.1
 **/
public class FreeMarkerUtil {

    private static Configuration config = new Configuration(Configuration.getVersion());


    /**
     * 获取freemarker配置
     *
     * @param
     * @return
     * @author zhouwei
     * 2018/11/19 22:00
     */
    private static Configuration getConfiguration() throws IOException {
        //获取freemarker配置
        config.setClassForTemplateLoading(FreeMarkerUtil.class, "/template");

        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return config;
    }

    /**
     * 数据与模板绑定，生成查询sql //todo
     * @author zhouwei
     * 2018/11/19 21:58
     *
     * @param root 数据对象
     * @param name 模板名称
     * @return 查询sql语句
     */
    public static String process(Map root, String name) {
        Configuration config = null;
        try {
            config = getConfiguration();
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
            return null;
        }

        //获取模板
        Template template = null;
        try {
            template = config.getTemplate(name+".ftl");
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
            return null;
        }

        //输出到string中
        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw);

        try {
            template.process(root, bw);
        } catch (TemplateException e) {
            //TODO
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
            return null;
        }

        return sw.toString();
    }
}
