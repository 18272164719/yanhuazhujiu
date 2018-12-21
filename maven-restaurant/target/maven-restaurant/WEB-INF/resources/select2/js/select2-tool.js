

(function ($) {
    var _select2Tool = $.fn.select2;
    $.fn.select2 = function (options) {
    	if(options == "text"){
    		 return this.find(':selected').text();
    	}
	    if(options == "close" ||typeof options == 'string'|| !options.data){
			_select2Tool.call(this,options);
			return;
		}
    	
    	if(typeof options.data == 'object'){
    		options = $.extend({},$.fn.select2.defaults, options || {});
    		_select2Tool.call(this,options);
    		return;
    	}
    	
    	options = $.extend({},$.fn.select2.defaults, options || {});
		var d = new Array();
		$.ajax({
			url : options.data,
			data : options.params,
			dataType : "json",
			type : "POST",
			cache : false,
			async: false,
			success : function(data) {
				 d = new Array();
				 $.each(data, function (i, item) {
                	var o = new Object();
                	o.id = item[options.valueField];
                	//o.text = item[options.textField];
                	o.text = eval("item."+options.textField);
                	d.push(o);
                });
			}
		});
		
        options.data = d;
        
        //调用原方法
        this.empty();   
        _select2Tool.call(this,options);
        //this.val(2).trigger("change");

        //初始值
        if(options.value==""){
        	this.val(null).trigger("change");
        }else if(options.value){
        	this.val(options.value).trigger("change");
        }

        options.onLoadSuccess(options.data);
        
        if(options.onChange){
        	this.unbind("change"); 
        	this.on("select2:select",function(e){
        		options.onChange(e);
        	})
        }
    };
    
    $.fn.select2.defaults = {
		params: {},
        valueField: 'id',
        textField: 'text',
        placeholder: '请选择',
        value:null,
        width:100,
        minimumResultsForSearch : Infinity,
        onChange:null,
        onLoadSuccess:function(data){},
        allowClear:false
    };
    
  
})(jQuery);