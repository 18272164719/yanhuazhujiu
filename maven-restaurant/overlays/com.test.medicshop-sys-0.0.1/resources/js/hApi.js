
/**
 * headAPI
 */

var h = new Object();

(function($){
/**
 * 显示正确消息
 */
h.showMsg = function(text,delay) {
	top.$.bootstrapGrowl(text, {
		type : 'success',
		delay:delay||4000
	});
}
/**
 * 显示一般消息
 */
h.showInfo = function(text,delay) {
	top.$.bootstrapGrowl(text,{
		type: 'info',
		delay:delay||4000
	});
}
/**
 * 显示错误消息
 * 给出错控件obj 增加错误样式
 */
h.showErr = function(text, obj,delay) {
	top.$.bootstrapGrowl(text, {
		type : 'danger',
		delay:delay||4000
	});
	if (obj) {
		$.each($(".editError"), function(index) {
			$(this).removeClass("editError");
		});
		obj.addClass("editError"); 
		obj.focus();
	}
}
h.clearErr = function(obj){
	if(obj){
		obj.removeClass("editError");
	}
}

/**
 * top层显示提交进度条
 */
h.showProgressBar = function(text){
	top.showProgressBar();
}

/**
 * 关闭提交进度条
 */
h.hideProgressBar = function(){
	top.hideProgressBar();
}


/**
 * 获得选中的一行
 * if(!(row = h.getSelectRow($("#table")))) return;
 */
h.getSelectRow = function(table) {
	var selobj = table.bootstrapTable('getSelections');
	if (selobj.length == 0) {
		h.showInfo('请选择一行');
		return false;
	}
	return selobj[0];
}

/**
 * 获得选中的多行
 * if(!(rows = h.getSelectRows($("#table")))) return;
 */
h.getSelectRows = function(table) {
	var selobj = table.bootstrapTable('getSelections');
	if (selobj.length == 0) {
		h.showInfo('请选择一行');
		return false;
	}
	return selobj;
}


/**
 * 关闭当前bootbox
 * if (data.success) {
	h.bootboxHide("修改成功");
   }
 */
h.bootboxHide = function(msg) {
	if(msg) h.showMsg(msg);
	var uid = $(window.frameElement).data("id")||$(parent.frameElement).data("id");
	top.$("." + uid).modal("hide");
}

/**
 * 关闭当前bootbox并刷新table
 * 
 * 	  ...
      title: "添加成员",
	  width:600,
	  height:400,
	  table:$("#table"),
	  ...
 * 
 *  if (data.success) {
	h.bootboxRefresh("修改成功");
   }
 */
h.bootboxRefresh = function(msg) {
	var uid = $(window.frameElement).data("id")||$(parent.frameElement).data("id");
	var table = top.getBootboxTable(uid);
	if (table) {
		table.bootstrapTable('refresh');
	}
	if(msg) h.showMsg(msg);
	top.$("." + uid).modal("hide");
}

/**
 * 获得bootbox的iframe
 *  h.iframe(this).submit();
 */
h.iframe = function(obj){
	return  $(obj).find("iframe")[0].contentWindow;
}

/**
 * form表单验证
 * if(!h.isValid($("#form1"))) return false;
 */
h.isValid = function(obj){
	var bootstrapValidator = obj.data('bootstrapValidator');
	$.each(obj.find("[data-bv-field]"),function(index){
		bootstrapValidator.updateStatus($(this).attr("name"), 'NOT_VALIDATED');
	});
	bootstrapValidator.validate();
	if (!bootstrapValidator.isValid())
		return false;
	return true;
}

/**
 * 金额格式化
 */
h.fmoney = function(v){
	if(isNaN(v)){  
        return v;  
    }  
    v = (Math.round((v - 0) * 100)) / 100;  
    v = (v == Math.floor(v)) ? v + ".00" : ((v * 10 == Math.floor(v * 10)) ? v  
            + "0" : v);  
    v = String(v);  
    var ps = v.split('.');  
    var whole = ps[0];  
    var sub = ps[1] ? '.' + ps[1] : '.00';  
    var r = /(\d+)(\d{3})/;  
    while (r.test(whole)) {  
        whole = whole.replace(r, '$1' + ',' + '$2');  
    }  
    v = whole + sub;  
      
    return v;  
} 

/**
 * 常规ajax请求
 * h.ajax({
		url : "",
		data :{},
		dataSuccess : function(data) {
			h.bootboxRefresh("新增成功");
		}
	});
 * 
 */
h.ajax = function(options){
	options = $.extend({},h.ajaxDefaults, options || {});
	
	var beforeSendFunc = options.beforeSend;
	options.beforeSend = function() {
		
		var bsf = beforeSendFunc.call(this);
		if(typeof(bsf) == 'undefined' || bsf){
			try{
				h.showProgressBar();
				//提交前禁用按钮
				top.disableBootboxEventBtn();
			}catch(e){}
			
			if(options.btn){
				options.btn.attr("disabled", true);
			}
		}
		return bsf;
	};
	options.success = function(data){
		h.hideProgressBar();
		try{
			//反馈后启用按钮
			top.clearBootboxEventBtn();
		}catch(e){}
		
		if(options.btn){
			options.btn.attr("disabled", true);
		}
		if(data.success == null){
			options.dataSuccess.call(this,data,data.msg);
		}else{
			if(data.success){
				options.dataSuccess.call(this,data.data,data.msg);
			}else{
				options.dataError.call(this,data.msg);
			}
		}
	};
	var errorFunc = options.error;
	options.error = function(){
		h.hideProgressBar();
		try{
			//反馈后启用按钮
			top.clearBootboxEventBtn();
		}catch(e){}
		
		if(options.btn){
			options.btn.attr("disabled", true);
		}
		errorFunc.call(this);
	}
	
	$.ajax(options);
}

h.ajaxDefaults = {
		dataType : "json",
		type : "POST",
		cache : false,
		loading:false,
		timeout: 5*60*1000,   
		btn:null,//增加禁用启用效果的按钮
		beforeSend:function(){},
		success:function(data,msg){},
		dataSuccess : function(data,msg) {
		},
		dataError : function(msg) {
			showErr(msg || "业务处理出错，请刷新重新操作");
		},
		error : function() {
			h.hideProgressBar();
			showErr("服务器出错，请刷新重新操作");
		}
}

/**
 * 打日志 兼容ie
 */
h.log = function(d){
	try{
		console.log(d);
	}catch(e){
		alert(d);
	}
}

/**
 * 下拉年份
 */
$.fn.hSelectYear = function(options){
	var $this = this;
	//设置options
	options = $.extend({}, options || {});
	
	var year_datas = new Array();
	var myDate = new Date();
	var year = myDate.getFullYear();
	var k = 0;
	for(var i=year; i>year-8; i--){
		var year_data = new Object();
		year_data.id = i;
		year_data.text = i+"年";
		year_datas[k] = year_data;
		k++;
	}
	
	options.data = year_datas;
	$this.select2(options);
}

/**
 * 下拉表格
 * $("#id").hSelectTable({
  	url:"",
  	columns:[],
  	onSelect:function(row){}
   });
 * 参数见 h.selectTableDefaults
 */
h.selectTableDefaults = {
		key:null,//默认为input.name
		flkey:null,//简拼搜索
		url:null,//必须
		columns:null,//必须
		name:"",
//		queryParams:function(p){return p},
		onlyPagination:true,
		toolbarSize:12,
		pageSize:10,
		minRows:10,
		delay:1000,
		clearBtn:false,//是否有清空按钮
		addBtn:false,//是否有新增按钮
		addUrl:null,
		addTitle:"新增",
		addWidth:400,
		addHeight:300,
		readonly:true,
		selectType:"click",//可选值：click dblclick
		onSelect:function(row){},
		//onAdd:function(){},
		onClear:function(){}
}

$.fn.hSelectTable = function(options,opts){
	if(typeof options == "string"){
		var table = $("#"+this.attr("id")+"_ST");
		if(opts == null){
			table.bootstrapTable(options);
		}else{
			table.bootstrapTable(options,opts);
		}
		return;
	}
	
	var inputtimeout;
	var $this = this;
	var boxId = (this.attr("id") || this.attr("name"));
	
	//设置options
	options = $.extend({},h.selectTableDefaults, options || {});

	options.key = options.key || this.attr("name");//搜索key
	var inputGroup = $("<div class='input-group'  style='width:100%'></div>");
	if(options.addBtn){//新增按钮
		var addBtn = $("<span class='input-group-btn'  style='width:40px' ><button class='btn btn-default' type='button' id='"+boxId+"_ST_add'><i class='fa fa-plus'></i></button></span>"); 
		inputGroup.append(addBtn);
	}
	var searchInput = $("<input id='"+boxId+"_ST_key' class='form-control' style='width:100%' />"); //搜索输入框
	inputGroup.append(searchInput);
	if(options.clearBtn){//清空按钮
		var clearBtn = $("<span class='input-group-btn'  style='width:40px' ><button class='btn btn-default' type='button' id='"+boxId+"_ST_clear'><i class='fa fa-remove'></i></button></span>"); 
		inputGroup.append(clearBtn);
	}
	$this.before(inputGroup);
	options.toolbar=inputGroup;
	var dropdownMenuWidth = "";//table宽度自定义
	if(options.width){
		dropdownMenuWidth = 'width:'+options.width+'px;';
	}
	var dropdownMenuHeight = "";//table高度自定义
	if(options.height){
		dropdownMenuHeight = 'max-height:'+options.height+'px;overflow-y:auto;';
		delete options.height;
	}
	//$this增加属性
	$this.addClass("dropdown-toggle");
	$this.attr("data-toggle","dropdown");
	$this.attr("readonly",options.readonly);
	var dropdownToggle = $('<div class="input-group"></div>');
	dropdownToggle.append('<span class="input-group-addon"><i class="fa fa-table"></i> '+options.name+'</span>');
	$this.before(dropdownToggle);
	dropdownToggle.append($this);
	
	//增加table
	var dropdownMenu = $('<div class="dropdown-menu" style="'+dropdownMenuWidth+dropdownMenuHeight+'"><table id="'+boxId+'_ST" ></table></div>');
	$this.before(dropdownMenu);
	var table = $("#"+boxId+"_ST");
	var keyInput = $("#"+boxId+"_ST_key");
	var clearBtn = $("#"+boxId+"_ST_clear");

	options.onClickRow = function(row,$el){
		options.onSelect.call(this,row);
	};
	
	options.onLoadSuccess = function(data){
		keyInput.focus();
		$this.parent().find(".dropdown-menu").find(".bootstrap-table1").removeClass("box");
		$this.parent().find(".dropdown-menu").find(".box-header").removeClass("box-header");
	}
	
	if(options.treeView){
		options.selectType = "dblclick";
	}
	table.bootstrapTable(options);
	h.log(options);
	
	//事件
	if(options.clearBtn){
		clearBtn.on('click',function(e){//清空
			cancelBubble(e);
			$this.val("");
			keyInput.val("");
			clearTimeout(inputtimeout);
			table.bootstrapTable("refreshOptions",{
		 		queryParams:function(params){
		 			//params = options.queryParams(params);
					return params;
				}
			});
			options.onClear.call(this);
		});
	}
	if(options.addBtn){
		addBtn.on('click',function(e){//弹出新增窗口
			cancelBubble(e);
			if(options.onAdd){
				options.onAdd.call(this);
			}else{
				//弹窗增加
				top.bootbox.dialog({
				  message: options.addUrl,
				  title: options.addTitle,
				  width:options.addWidth,
				  height:options.addHeight,
				  table:table,
				  buttons: {
				    "取消": {}, 
				    success: {   
				      label: "保存",
				      callback: function() {
				    	  h.iframe(this).submitAjax();
				    	 return false;
				      }
				    }
				  }
				});
			}
		});
	}
	$this.on('click',function(){//查询输入框焦点
		window.setTimeout(function(){
			keyInput.focus();
		}, 100);	
	}); 
	var qp = options.queryParams;
	keyInput.hOninput(function(){//输入查询
		clearTimeout(inputtimeout);
		inputtimeout = setTimeout(function(){
			table.bootstrapTable("refreshOptions",{
		 		queryParams:function(params){
		 			if(typeof(qp) == "function"){
    	 				params = qp(params);
    	 			}
		 			if(options.flkey != null){
		 				var hasChinese = (/[\u4E00-\u9FA5]/g.test(keyInput.val()));
		 				if(!hasChinese){
			 				//简拼搜索
			 				params[options.flkey] = keyInput.val();
			 				return params;
			 			}
		 			}
	 				//中文搜索
		 			params[options.key] = keyInput.val();
					return params;
				}
			});
		}, options.delay);
	}); 
	
} 

/**
 * 获得控件定位
 */
h.getpos = function (obj) {  
	var e = obj[0];
    var t=e.offsetTop;  
    var l=e.offsetLeft;  
    var height=e.offsetHeight; 
    var width = e.offsetWidth;
    while(e=e.offsetParent) {  
        t+=e.offsetTop;  
        l+=e.offsetLeft;  
    }  
    var s = new Object();
    s.t = t;
    s.l = l;
    s.w= width;
    s.h = height;
    return s;
}  

/**
 * 绑定oninput事件
 * $("#id").hOninput(func);
 */
$.fn.hOninput = function(func){
	var obj = this[0];
	if('oninput' in obj){
		obj.addEventListener("input",func,false);  
	}else{  
		obj.onpropertychange = func;  
	} 
}



/**
 * 文件上传
 * $("#file").hFile();
 * 
 * 注：须在$(function(){})外面初始化
 * 
 */
h.fileDefaults = {
	showPreview:false,
	showUpload:false,
	showCancel:false,
	language: 'zh'
}

$.fn.hFile = function(options){
	options = $.extend({},h.fileDefaults, options || {});
	this.fileinput(options);
}

/**
 * form栏位验证
 * $("#id").valid();
 */
$.fn.valid = function(){
	if(this.data("bv-field") && this.parents("form").length>0){
		this.parents("form").data('bootstrapValidator').updateStatus(this.attr("name"), 'NOT_VALIDATED').validateField(this.attr("name")); 
	}
}

/**
 * form验证非空初始化
 * notEmptyName 可接收单个栏位 或者 数组栏位
 * options 其他更多验证
 * $('#form1').initValid("empId");
 * $('#form1').initValid(["empId","name"]);
 * $('#form1').initValid(["empId","name"],options);
 * 
 */
$.fn.initValid = function(notEmptyName,options){
	var notEmpty = {validators : {notEmpty : {}}};
	options = options || {fields:{}};
	if(typeof notEmptyName == "string"){
		options.fields[notEmptyName] = notEmpty;
	}else{
		for ( var i in notEmptyName) {
			options.fields[notEmptyName[i]] = notEmpty;
		}
	} 
	this.bootstrapValidator(options);
}

/**
 * 日期时间控件
 * $("#file").datetimePicker();
 * 
 * 
 */
h.datetimePicker = {
	"singleDatePicker": true,
	"timePicker": true, 
	"timePickerSeconds": false,
	"timePicker24Hour": false,
    "showDropdowns": true
}
$.fn.datetimePicker = function(options){
	options = $.extend({},h.datetimePicker, options || {});
	this.daterangepicker(options)
}
/**
 * 时间控件
 * $("#date").hTimePicker();
 * 
 * 
 */
h.timepickerDefaults = {
	name:"",
	defaultTime: 'current',
    disableFocus: false,
    isOpen: false,
    minuteStep: 5,
    secondStep: 15,
    showSeconds: true,
    showInputs: true,
    showMeridian: false,
    template: 'dropdown',
    appendWidgetTo: '.hTimepicker',
}
$.fn.hTimepicker = function(options,opts){
	var $this = this;
	if(typeof options == "string"){
		if(opts == null){
			$this.timepicker(options);
		}else{
			$this.timepicker(options,opts);
		}
		return;
	}
	
	
	var $parent = $this.parent();
	options = $.extend({},h.timepickerDefaults, options || {});

	var inputGroup = $("<div class='input-group hTimepicker'  style='width:100%'></div>");
	inputGroup.append('<span class="input-group-addon"><i class="fa fa-clock-o"></i> '+options.name+'</span>');
	inputGroup.append($this);
	$parent.append(inputGroup);
	
	$this.timepicker(options);
}

/**
 * 按照年份推算日期
 * date:支持 new date() 或者 "2018-01-01"
 * num:加减的年份，支持正负
 * fmt:返回的日期格式，默认 yyyy-MM-dd
 * 
 */
h.addYear = function(date,num,fmt){
	var d = new Date(date);
	d.setFullYear(d.getFullYear()+num);
	var f = fmt?fmt:"yyyy-MM-dd";
	return d.Format(f);
}
/**
 * 按照月份推算日期
 * 
 */
h.addMonth = function(date,num,fmt){
	var d = new Date(date);
	d.setMonth(d.getMonth()+num);
	var f = fmt?fmt:"yyyy-MM-dd";
	return d.Format(f);
}
/**
 * 按照天数推算日期
 * 
 */
h.addDate = function(date,num,fmt){
	var d = new Date(date);
	d.setDate(d.getDate()+num);
	var f = fmt?fmt:"yyyy-MM-dd";
	return d.Format(f);
}

//阻止冒泡
function cancelBubble(event1){
	try{
		event.cancelBubble=true;//阻止冒泡
	}catch (e) {
		event1.stopPropagation();//阻止冒泡
	}
}

})(jQuery);

