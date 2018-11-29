<#import "MySQL.ftl" as columnInfo>
ALTER TABLE ${dataObject.defined}
<#if addColumns?exists>
 <#list addColumns as column>
 ADD COLUMN<@columnInfo.column_definition column=column></@columnInfo.column_definition>
 <#if column_has_next>,</#if>
 </#list>
</#if>
<#if alterColumns?exists>
 <#list addColumns as column>
 CHANGE COLUMN ${column.columnName} <@columnInfo.column_definition column=column></@columnInfo.column_definition>
 <#if column_has_next>,</#if>
 </#list>
</#if>
<#if dropColumns?exists>
 <#list addColumns as column>
 DROP COLUMN ${column.columnName}
 <#if column_has_next>,</#if>
 </#list>
</#if>
