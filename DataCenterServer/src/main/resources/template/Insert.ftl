<#assign columns=updateInfo?keys/>
<#assign values=updateInfo?values/>

INSERT INTO ${dataObject.defined}
 (
<#list columns as column>
 ${column} <#if column_has_next>,</#if>
</#list>
 ) VALUES (
<#list values as value>
    '${value}' <#if value_has_next>,</#if>
</#list>
 )