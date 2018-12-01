<#import "MySQL.ftl" as col_type>
SELECT DISTINCT
<#if columns?exists>
    <#list columns as column> ${column.columnName}<#if column_has_next>, </#if></#list>
<#else > *
</#if>
 FROM ${dataObject.defined}
<#if condition?exists>
 WHERE <#list condition as col>
 ${col.columnName}<@col_type.getOper attribute=col></@col_type.getOper><@col_type.getJdbcType attribute=col >${col
 .value}</@col_type.getJdbcType>
 <#if  col_has_next> and  </#if></#list>
</#if>
<#if pageInfo?exists>
 LIMIT ${pageInfo.start},${pageInfo.length}
</#if>