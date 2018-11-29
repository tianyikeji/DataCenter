<#import "MySQL.ftl" as col_type>

INSERT INTO ${dataObject.defined}
 (
<#list insertInfo as column>
 ${column.columnName}<#if column_has_next>,</#if>
</#list>
 ) VALUES (
<#list insertInfo as column>
    <@col_type.getJdbcType attribute=column >${column.value}</@col_type.getJdbcType> <#if column_has_next>,</#if>
</#list>
 )