webpackJsonp([5],{UEUh:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var l=a("fZjL"),i=a.n(l),d=a("woOf"),s=a.n(d),n=(a("4Zt/"),a("z/Aw")),o=a("zL8q"),r={name:"dictBindAttr",props:{resId:Number},data:function(){return{objlist:[],key:"",value:"",parentId:"",data:{}}},created:function(){var e=this;n.a.getDataObjectAttrsList({resId:this.resId,pageInfo:{page:0,pageSize:0}}).then(function(t){e.objlist=t.data.list}).catch(function(e){console.log(e),o.Message.error("获取对象列表失败。",e)})},methods:{handleSubmit:function(){var e={};e.dataObjectId=this.resId,""==this.pid&&(e.pid=this.parentId),e.keyName=this.key,e.valueName=this.value,n.a.dictBind(e).then(function(e){o.Message.success("绑定成功。")}).catch(function(e){o.Message.error(" 绑定失败。"+e.data.message)})}}},c={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-form",{attrs:{inline:"",model:e.data,"label-position":"right","label-width":"80px"}},[a("label",[e._v("数据字典映射：")]),e._v(" "),a("el-form-item",{attrs:{label:"key:"}},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:e.key,callback:function(t){e.key=t},expression:"key"}},e._l(e.objlist,function(e,t){return a("el-option",{key:t,attrs:{label:e.name,value:e.columnName}})}))],1),e._v(" "),a("el-form-item",{attrs:{label:"value:"}},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:e.value,callback:function(t){e.value=t},expression:"value"}},e._l(e.objlist,function(e,t){return a("el-option",{key:t,attrs:{label:e.name,value:e.columnName}})}))],1),e._v(" "),a("el-form-item",{attrs:{label:"父ID:"}},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:e.parentId,callback:function(t){e.parentId=t},expression:"parentId"}},[a("el-option",{attrs:{label:"--",value:""}}),e._v(" "),e._l(e.objlist,function(e,t){return a("el-option",{key:t,attrs:{label:e.name,value:e.id}})})],2)],1),e._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.handleSubmit()}}},[e._v("提交")])],1)],1)},staticRenderFns:[]},v={name:"viewDataObj",components:{dictBindAttr:a("VU/8")(r,c,!1,null,null,null).exports},data:function(){return{headerTitle:"对象属性列表",resId:"",viewTable:{domId:"",className:"",data:[]},viewAdd:{data:{isIncrement:"false",isNull:"true",indexType:"--"},show:!1},viewEdit:{index:"",data:{isIncrement:"",isNull:"",indexType:""},show:!1},viewDelet:{show:!1,data:{},index:""},plugs:{select1:[{label:"varchar",value:"varchar"},{label:"char",value:"char"},{label:"tinyint",value:"tinyint"},{label:"smallint",value:"smallint"},{label:"mediumint",value:"mediumint"},{label:"integer",value:"integer"},{label:"bigint",value:"bigint"},{label:"float",value:"float"},{label:"double",value:"double"},{label:"decimal",value:"decimal"},{label:"tinytext",value:"tinytext"},{label:"text",value:"text"},{label:"mediumtext",value:"mediumtext"},{label:"longtext",value:"longtext"},{label:"tinyblog",value:"tinyblog"},{label:"blog",value:"blog"},{label:"mediumblog",value:"mediumblog"},{label:"longblog",value:"longblog"}],select2:[{label:"字典",value:"字典"},{label:"邮箱",value:"邮箱"},{label:"手机号",value:"手机号"},{label:"文本",value:"文本"},{label:"数字",value:"数字"},{label:"身份证",value:"身份证"}],select3:[{label:"查询列表失败",value:""}]},rules:{columnName:[{required:!0,validator:function(e,t,a){if(!t)return a(new Error("字段名不能为空"));t&&setTimeout(function(){/^[a-zA-Z]\w{0,19}$/.test(t)?a():a(new Error("有效的字段名为第一个必须是字母，后面可以是字母、数字、下划线，总长度为5-20！"))},500)},trigger:"blur"}],jdbcType:[{required:!0,message:"请选择数据类型",trigger:"change"}],length:[{required:!0,validator:function(e,t,a){if(console.log(11,e),!t)return a(new Error("数据长度不能为空"));setTimeout(function(){/^[1-9]\d*$/.test(t)?a():a(new Error("数据长度为大于0的正整数"))},1e3)},trigger:"blur"}],name:[{required:!0,message:"请输入名称",trigger:"blur"}],type:[{required:!0,message:"请选择类型",trigger:"change"}]}}},created:function(){this.resId=parseInt(this.$route.path.split("/")[3]),this.handleSearch(),this.getDics()},methods:{getDics:function(){var e=this;n.a.getDataObjectsList({type:"",isDic:"true",name:"",pageInfo:{page:0,pageSize:0}}).then(function(t){t.success?e.plugs.select3=t.data.list:o.Message.error("获取字典列表失败。")}).catch(function(e){console.log(e),o.Message.error("网络错误。"+e)})},handleSearch:function(){var e=this;n.a.getDataObjectAttrsList({resId:this.resId,pageInfo:{page:0,pageSize:0}}).then(function(t){e.viewTable.data=t.data.list}).catch(function(e){console.log(e),o.Message.error("获取属性列表失败。",e)})},handleEdit:function(e,t){console.log("edit",e,t),this.viewEdit.data=s()({},e),this.viewEdit.old=s()({},e),this.viewEdit.data.resId=this.resId,this.viewEdit.index=t,this.viewEdit.show=!0},diff:function(e){function t(t,a){return e.apply(this,arguments)}return t.toString=function(){return e.toString()},t}(function(e,t){console.log("我是diff",1111);var a=e instanceof Object,l=t instanceof Object;if(console.log(22,a,l),!a||!l)return e===t;if(i()(e).length!==i()(t).length)return!1;for(var d in e){console.log("属性",d);var s=e[d]instanceof Object,n=t[d]instanceof Object;if(console.log(55,s,n),s&&n)return console.log(888),diff(e[d],t[d]);if(e[d]!==t[d])return console.log(999),!1}return!0}),editSingle:function(){var e=this;1==this.diff(this.viewEdit.data,this.viewEdit.old)?o.Message.warning("修改前和修改后的数据一致"):n.a.editDataObjectAttr(this.viewEdit.data).then(function(t){(t.success=!0)&&(o.Message.success("修改成功"),e.handleSearch(),e.viewEdit.show=!1)}).catch(function(e){console.log(e),o.Message.error(e)})},editCancel:function(){this.viewEdit.show=!1,this.$refs["viewEdit.data"].resetFields()},handleDelet:function(e,t){console.log("delet",e),this.viewDelet.data=e,this.viewDelet.data.resId=this.resId,this.viewDelet.index=t,this.viewDelet.show=!0},deletSingle:function(){var e=this;n.a.deleteDataObjectAttr({id:this.viewDelet.data.id}).then(function(t){1==t.success&&(o.Message.success("删除成功"),e.handleSearch(),e.viewDelet.show=!1)}).catch(function(e){console.log(e),o.Message.error(e)})},handleAdd:function(e){this.viewAdd.show=!0},closeDialog:function(){this.$refs.form.resetFields()},addSingle:function(){var e=this;this.$refs.form.validate(function(t){if(!t)return console.log("error submit!!"),!1;var a=s()({},e.viewAdd.data);a.resId=e.resId,"主键"==a.indexType?(a.isKey="true",a.isNull="false"):(a.isKey="false",a.isIncrement="false");var l=[];l[0]=a,n.a.addDataObjectAttr({objectId:e.resId,list:l}).then(function(t){if(console.log("添加",t),1==t.success){for(var a in o.Message.success("添加成功"),e.handleSearch(),e.viewAdd.data)e.viewAdd.data[a]="";e.viewAdd.data.isIncrement=!1,e.viewAdd.data.isNull=!0,e.viewAdd.show=!1}else o.Message.error("添加失败。")}).catch(function(e){console.log(e),o.Message.error(e)})})},addCancel:function(){this.viewAdd.show=!1,this.$refs.form.resetFields()}}},u={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("el-breadcrumb",{attrs:{"separator-class":"el-icon-arrow-right"}},[a("div",{staticClass:"breadxian"}),e._v(" "),a("el-breadcrumb-item",[e._v(e._s(e.headerTitle))])],1),e._v(" "),a("div",{staticClass:"breadbottom"}),e._v(" "),a("br"),e._v(" "),a("p",[a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.handleAdd()}}},[e._v("添加1")])],1),e._v(" "),a("br"),e._v(" "),a("div",{staticClass:"viewTableClass"},[a("el-table",{attrs:{data:e.viewTable.data,border:"","header-cell-style":{background:"#f9fafc"}}},[a("el-table-column",{attrs:{prop:"id",label:"ID",width:"50"}}),e._v(" "),a("el-table-column",{attrs:{prop:"columnName",label:"字段名",width:"100"}}),e._v(" "),a("el-table-column",{attrs:{prop:"jdbcType",label:"数据类型",width:"100"}}),e._v(" "),a("el-table-column",{attrs:{prop:"length",label:"数据长度",width:"100"}}),e._v(" "),a("el-table-column",{attrs:{prop:"name",label:"名称",width:"100"}}),e._v(" "),a("el-table-column",{attrs:{prop:"description",label:"说明"}}),e._v(" "),a("el-table-column",{attrs:{prop:"type",label:"类型",width:"100"}}),e._v(" "),a("el-table-column",{attrs:{prop:"dicRes",label:"引用字典对象"}}),e._v(" "),a("el-table-column",{attrs:{prop:"rule",label:"规则",width:"100"}}),e._v(" "),a("el-table-column",{attrs:{prop:"isNull",label:"是否为空"}}),e._v(" "),a("el-table-column",{attrs:{prop:"indexType",label:"索引类型",width:"100"}}),e._v(" "),a("el-table-column",{attrs:{prop:"isKey",label:"是否主键"}}),e._v(" "),a("el-table-column",{attrs:{prop:"isIncrement",label:"自增序列",width:"100"}}),e._v(" "),a("el-table-column",{attrs:{label:"操作",width:"200"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"primary",size:"small"},on:{click:function(a){e.handleEdit(t.row,t.$index)}}},[e._v("修改")]),e._v(" "),a("el-button",{attrs:{type:"danger",size:"small"},on:{click:function(a){e.handleDelet(t.row,t.$index)}}},[e._v("删除")])]}}])})],1)],1),e._v(" "),a("el-dialog",{attrs:{title:"添加数据对象属性",visible:e.viewAdd.show,"close-on-click-modal":!1,width:"30%",id:"viewAdd"},on:{"update:visible":function(t){e.$set(e.viewAdd,"show",t)},close:e.closeDialog}},[a("el-form",{ref:"form",attrs:{model:e.viewAdd.data,rules:e.rules,"label-position":"right","label-width":"120px"}},[a("el-form-item",{attrs:{label:"字段名：",prop:"columnName"}},[a("el-input",{attrs:{placeholder:"请输入字段名"},model:{value:e.viewAdd.data.columnName,callback:function(t){e.$set(e.viewAdd.data,"columnName",t)},expression:"viewAdd.data.columnName"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"数据类型：",prop:"jdbcType"}},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:e.viewAdd.data.jdbcType,callback:function(t){e.$set(e.viewAdd.data,"jdbcType",t)},expression:"viewAdd.data.jdbcType"}},e._l(e.plugs.select1,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1),e._v(" "),a("el-form-item",{attrs:{label:"数据长度：",prop:"length"}},[a("el-input",{attrs:{placeholder:"请输入数据长度"},model:{value:e.viewAdd.data.length,callback:function(t){e.$set(e.viewAdd.data,"length",t)},expression:"viewAdd.data.length"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"名称：",prop:"name"}},[a("el-input",{attrs:{placeholder:"请输入名称"},model:{value:e.viewAdd.data.name,callback:function(t){e.$set(e.viewAdd.data,"name",t)},expression:"viewAdd.data.name"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"说明："}},[a("el-input",{attrs:{placeholder:"请输入说明"},model:{value:e.viewAdd.data.description,callback:function(t){e.$set(e.viewAdd.data,"description",t)},expression:"viewAdd.data.description"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"类型：",prop:"type"}},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:e.viewAdd.data.type,callback:function(t){e.$set(e.viewAdd.data,"type",t)},expression:"viewAdd.data.type"}},e._l(e.plugs.select2,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1),e._v(" "),a("el-form-item",{attrs:{label:"引用字典对象："}},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:e.viewAdd.data.dicRes,callback:function(t){e.$set(e.viewAdd.data,"dicRes",t)},expression:"viewAdd.data.dicRes"}},e._l(e.plugs.select3,function(e){return a("el-option",{key:e.id,attrs:{label:e.name,value:e.name}})}))],1),e._v(" "),a("el-form-item",{attrs:{label:"规则："}},[a("el-input",{attrs:{placeholder:"请输入规则"},model:{value:e.viewAdd.data.rule,callback:function(t){e.$set(e.viewAdd.data,"rule",t)},expression:"viewAdd.data.rule"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"索引类型："}},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:e.viewAdd.data.indexType,callback:function(t){e.$set(e.viewAdd.data,"indexType",t)},expression:"viewAdd.data.indexType"}},[a("el-option",{attrs:{label:"--",value:"--"}}),e._v(" "),a("el-option",{attrs:{label:"主键",value:"主键"}}),e._v(" "),a("el-option",{attrs:{label:"索引",value:"索引"}})],1)],1),e._v(" "),a("el-form-item",{directives:[{name:"show",rawName:"v-show",value:"主键"!=e.viewAdd.data.indexType,expression:"viewAdd.data.indexType!='主键'"}],attrs:{label:"是否为空："}},[[a("el-radio",{attrs:{label:"true"},model:{value:e.viewAdd.data.isNull,callback:function(t){e.$set(e.viewAdd.data,"isNull",t)},expression:"viewAdd.data.isNull"}},[e._v("是")]),e._v(" "),a("el-radio",{attrs:{label:"false"},model:{value:e.viewAdd.data.isNull,callback:function(t){e.$set(e.viewAdd.data,"isNull",t)},expression:"viewAdd.data.isNull"}},[e._v("否")])]],2),e._v(" "),a("el-form-item",{directives:[{name:"show",rawName:"v-show",value:"主键"==e.viewAdd.data.indexType,expression:"viewAdd.data.indexType=='主键'"}],attrs:{label:"自增序列："}},[[a("el-radio",{attrs:{label:"true"},model:{value:e.viewAdd.data.isIncrement,callback:function(t){e.$set(e.viewAdd.data,"isIncrement",t)},expression:"viewAdd.data.isIncrement"}},[e._v("是")]),e._v(" "),a("el-radio",{attrs:{label:"false"},model:{value:e.viewAdd.data.isIncrement,callback:function(t){e.$set(e.viewAdd.data,"isIncrement",t)},expression:"viewAdd.data.isIncrement"}},[e._v("否")])]],2)],1),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{attrs:{type:"danger"},on:{click:function(t){e.addCancel()}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.addSingle()}}},[e._v("确定")])],1)],1),e._v(" "),a("el-dialog",{attrs:{title:"修改数据对象属性",visible:e.viewEdit.show,width:"30%",id:"viewEdit"},on:{"update:visible":function(t){e.$set(e.viewEdit,"show",t)}}},[a("el-form",{attrs:{model:e.viewEdit.data,"label-position":"right","label-width":"120px"}},[a("el-form-item",{attrs:{label:"字段名："}},[a("el-input",{attrs:{placeholder:"请输入字段名"},model:{value:e.viewEdit.data.columnName,callback:function(t){e.$set(e.viewEdit.data,"columnName",t)},expression:"viewEdit.data.columnName"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"数据类型："}},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:e.viewEdit.data.jdbcType,callback:function(t){e.$set(e.viewEdit.data,"jdbcType",t)},expression:"viewEdit.data.jdbcType"}},e._l(e.plugs.select1,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1),e._v(" "),a("el-form-item",{attrs:{label:"数据长度："}},[a("el-input",{attrs:{placeholder:"请输入数据长度"},model:{value:e.viewEdit.data.length,callback:function(t){e.$set(e.viewEdit.data,"length",t)},expression:"viewEdit.data.length"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"名称："}},[a("el-input",{attrs:{placeholder:"请输入名称"},model:{value:e.viewEdit.data.name,callback:function(t){e.$set(e.viewEdit.data,"name",t)},expression:"viewEdit.data.name"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"说明："}},[a("el-input",{attrs:{placeholder:"请输入说明"},model:{value:e.viewEdit.data.description,callback:function(t){e.$set(e.viewEdit.data,"description",t)},expression:"viewEdit.data.description"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"类型："}},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:e.viewEdit.data.type,callback:function(t){e.$set(e.viewEdit.data,"type",t)},expression:"viewEdit.data.type"}},e._l(e.plugs.select2,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1),e._v(" "),a("el-form-item",{attrs:{label:"引用字典对象：",prop:"dicRes"}},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:e.viewEdit.data.dicRes,callback:function(t){e.$set(e.viewEdit.data,"dicRes",t)},expression:"viewEdit.data.dicRes"}},e._l(e.plugs.select3,function(e){return a("el-option",{key:e.id,attrs:{label:e.name,value:e.name}})}))],1),e._v(" "),a("el-form-item",{attrs:{label:"规则："}},[a("el-input",{attrs:{placeholder:"请输入规则"},model:{value:e.viewEdit.data.rule,callback:function(t){e.$set(e.viewEdit.data,"rule",t)},expression:"viewEdit.data.rule"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"索引类型：",prop:"indexType"}},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:e.viewEdit.data.indexType,callback:function(t){e.$set(e.viewEdit.data,"indexType",t)},expression:"viewEdit.data.indexType"}},[a("el-option",{attrs:{label:"--",value:"--"}}),e._v(" "),a("el-option",{attrs:{label:"主键",value:"主键"}}),e._v(" "),a("el-option",{attrs:{label:"索引",value:"索引"}})],1)],1),e._v(" "),a("el-form-item",{directives:[{name:"show",rawName:"v-show",value:"主键"!=e.viewEdit.data.indexType,expression:"viewEdit.data.indexType!='主键'"}],attrs:{label:"是否为空：",prop:"isNull"}},[[a("el-radio",{attrs:{label:"true"},model:{value:e.viewEdit.data.isNull,callback:function(t){e.$set(e.viewEdit.data,"isNull",t)},expression:"viewEdit.data.isNull"}},[e._v("是")]),e._v(" "),a("el-radio",{attrs:{label:"false"},model:{value:e.viewEdit.data.isNull,callback:function(t){e.$set(e.viewEdit.data,"isNull",t)},expression:"viewEdit.data.isNull"}},[e._v("否")])]],2),e._v(" "),a("el-form-item",{directives:[{name:"show",rawName:"v-show",value:"主键"==e.viewEdit.data.indexType,expression:"viewEdit.data.indexType=='主键'"}],attrs:{label:"自增序列：",prop:"isIncrement"}},[[a("el-radio",{attrs:{label:"true"},model:{value:e.viewEdit.data.isIncrement,callback:function(t){e.$set(e.viewEdit.data,"isIncrement",t)},expression:"viewEdit.data.isIncrement"}},[e._v("是")]),e._v(" "),a("el-radio",{attrs:{label:"false"},model:{value:e.viewEdit.data.isIncrement,callback:function(t){e.$set(e.viewEdit.data,"isIncrement",t)},expression:"viewEdit.data.isIncrement"}},[e._v("否")])]],2)],1),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{attrs:{type:"danger"},on:{click:function(t){e.editCancel()}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.editSingle()}}},[e._v("确定")])],1)],1),e._v(" "),a("el-dialog",{attrs:{title:"删除",visible:e.viewDelet.show,width:"30%",id:"viewDelet"},on:{"update:visible":function(t){e.$set(e.viewDelet,"show",t)}}},[a("div",{staticClass:"modal-body"},[e._v("\n      您确定删除要删除该项吗?\n    ")]),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{attrs:{type:"danger"},on:{click:function(t){e.viewDelet.show=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.deletSingle()}}},[e._v("确定")])],1)]),e._v(" "),a("div",{staticStyle:{width:"100%",position:"absolute",bottom:"10px",left:"0%","text-align":"center"}},[a("dictBindAttr",{attrs:{resId:e.resId}})],1)],1)},staticRenderFns:[]},p=a("VU/8")(v,u,!1,null,null,null);t.default=p.exports}});
//# sourceMappingURL=5.c4553f08d0425467580b.js.map