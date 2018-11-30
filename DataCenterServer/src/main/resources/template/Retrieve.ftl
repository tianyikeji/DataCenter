SELECT DISTINCT
<#if columns?exists>
    <#list columns as column> ${column.columnName}<#if column_has_next>, </#if></#list>
<#else > *
</#if>
 FROM ${dataObject.defined}
<#if condition?exists>
 WHERE <#list condition ?keys as key>${key}='${condition["${key}"]}'<#if key_has_next> and </#if></#list>
</#if>
<#if pageInfo?exists>
 LIMIT ${pageInfo.page},${pageInfo.pageSize}
</#if>