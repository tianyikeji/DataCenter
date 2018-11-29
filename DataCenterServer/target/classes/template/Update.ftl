<#import "MySQL.ftl" as col_type>

UPDATE ${dataObject.defined}
 SET
<#list updateInfo as column>
 ${column.columnName} =<@col_type.getJdbcType attribute=column>${column.value}</@col_type.getJdbcType><#if
column_has_next>,</#if>
</#list>
 WHERE
<#list condition as column>
 ${column.columnName}<@col_type.getOper attribute=column></@col_type.getOper><@col_type.getJdbcType
attribute=column>${column.value}</@col_type.getJdbcType><#if column_has_next> and</#if>
</#list>