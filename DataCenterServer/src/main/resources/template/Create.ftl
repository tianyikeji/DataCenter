CREATE TABLE ${dataObject.defined}
(
<#list columns as column>
    ${column.columnName} ${column.jdbcType} <#if column.length gt 0>(${column.length}) </#if>
    <#--<#if column.isNull?exists || column.isNull>NULL<#else> NOT NULL  </#if>-->
    <#if column.description?exists> COMMENT '${column.description}'</#if>
    <#if column_has_next>, </#if>
</#list>
)
 default character set = 'utf8'
 <#if dataObject.description?exists> COMMENT '${dataObject.description}'</#if>
