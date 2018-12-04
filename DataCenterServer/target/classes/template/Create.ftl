<#import 'MySQL.ftl' as columnInfo>
CREATE TABLE ${dataObject.defined}
(
<#list columns as column>
 <@columnInfo.column_definition column=column></@columnInfo.column_definition>
 <#if column_has_next>, </#if>
</#list>
)
 default character set = 'utf8'
<#if dataObject.description?exists> COMMENT '${dataObject.description}'</#if>
