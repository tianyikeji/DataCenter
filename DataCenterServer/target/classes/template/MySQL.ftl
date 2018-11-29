<#macro getJdbcType attribute={}>
 <#if attribute.type?exists>
  <#if attribute.type=="text" >
   <#if attribute.oper?exists && attribute.oper=="like">'%<#nested >%'
   <#else>'<#nested >'
   </#if>
  <#elseif attribute.type=="int"><#nested >
  <#else >'<#nested >'
  </#if>
 <#else >'<#nested >'
 </#if>
</#macro>

<#macro getOper attribute={}>
<#if !attribute.oper?exists || attribute.oper=="equals"> =
<#elseif attribute.oper=="greate than"> >
<#elseif attribute.oper=="greate than equals"> >=
<#elseif attribute.oper=="less than"> <
<#elseif attribute.oper=="less than equals"> <=
<#elseif attribute.oper=="like"> like
<#else> =
</#if>
</#macro>

<#macro column_definition column={}>
 ${column.columnName} ${column.jdbcType}<#if column.length gt 0>(${column.length})</#if>
 <#if column.isNull?exists && column.isNull=="true"> NULL<#else> NOT NULL</#if>
 <#if column.isIncrement?exists && column.isIncrement=="true"> AUTO_INCREMENT</#if>
 <#if column.isKey?exists && column.isKey=="true"> PRIMARY KEY</#if>
 <#if column.description?exists> COMMENT '${column.description}'</#if>
</#macro>