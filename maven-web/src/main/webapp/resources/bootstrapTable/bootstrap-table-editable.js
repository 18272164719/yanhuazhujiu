/**
 * @author zhixin wen <wenzhixin2010@gmail.com>
 * extensions: https://github.com/vitalets/x-editable
 */

(function($) {

    'use strict';

    $.extend($.fn.bootstrapTable.defaults, {
        editable: true,
        onEditableInit: function() {
            return false;
        },
        onEditableSave: function(field, row, oldValue, $el) {
            return false;
        },
        onEditableShown: function(field, row, $el, editable) {
            return false;
        },
        onEditableHidden: function(field, row, $el, reason) {
            return false;
        }
    });
    $.extend($.fn.bootstrapTable.columnDefaults, {
    	selectCache:true
    });

    $.extend($.fn.bootstrapTable.Constructor.EVENTS, {
        'editable-init.bs.table': 'onEditableInit',
        'editable-save.bs.table': 'onEditableSave',
        'editable-shown.bs.table': 'onEditableShown',
        'editable-hidden.bs.table': 'onEditableHidden'
    });

    var BootstrapTable = $.fn.bootstrapTable.Constructor,
        _initTable = BootstrapTable.prototype.initTable,
        _initBody = BootstrapTable.prototype.initBody;

    BootstrapTable.prototype.initTable = function() {
        var that = this;
        _initTable.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.editable) {
            return;
        }

        $.each(this.columns, function(i, column) {
            if (!column.editable) {
                return;
            }
            var opt = column.editable;
            if(opt.type=="text" || opt.type=="date"){
	            column.formatter = column.formatter || function(value, row, index) {
	            	var align = "left";
	            	if(column.moneyFmt){
	            		align = "right";
	            	}
	                return "<input class='form-control' style='width:100%;text-align:"+align+"' data-index='"+row.id+"' id='"+column.field +"_"+row.id+"' data-id='"+column.field +"_"+row.id+"' "+opt.readonly+" />";
	            };
	            column.cellStyle = column.cellStyle || function(row, index) {
	                return {
	                    css: {"padding":"1px 1px"}
	                  };
	            };
            }else if(opt.type=="select"){
	            column.formatter = column.formatter || function(value, row, index) {
	                return "<select data-index='"+row.id+"'  id='"+column.field +"_"+row.id+"' data-id='"+column.field +"_"+row.id+"' ></select> ";
	            };
	            column.cellStyle = column.cellStyle || function(row, index) {
	                return {
	                    css: {"padding":"1px 1px"}
	                  };
	            };
	           
            }
            return "12";
            var editableOptions = {},
                editableDataMarkup = [],
                editableDataPrefix = 'editable-';

            var processDataOptions = function(key, value) {
                // Replace camel case with dashes.
                var dashKey = key.replace(/([A-Z])/g, function($1) {
                    return "-" + $1.toLowerCase();
                });
                if (dashKey.slice(0, editableDataPrefix.length) == editableDataPrefix) {
                    var dataKey = dashKey.replace(editableDataPrefix, 'data-');
                    editableOptions[dataKey] = value;
                }
            };

            $.each(that.options, processDataOptions);

            column.formatter = column.formatter || function(value, row, index) {
                return value;
            };
            column._formatter = column._formatter ? column._formatter : column.formatter;
            column.formatter = function(value, row, index) {
                var result = column._formatter ? column._formatter(value, row, index) : value;

                $.each(column, processDataOptions);

                $.each(editableOptions, function(key, value) {
                    editableDataMarkup.push(' ' + key + '="' + value + '"');
                });

                var _dont_edit_formatter = false;
                if (column.editable.hasOwnProperty('noeditFormatter')) {
                    _dont_edit_formatter = column.editable.noeditFormatter(value, row, index);
                }

                if (_dont_edit_formatter === false) {
                    return ['<a href="javascript:void(0)"',
                        ' data-name="' + column.field + '"',
                        ' data-pk="' + row[that.options.idField] + '"',
                        ' data-value="' + result + '"',
                        editableDataMarkup.join(''),
                        '>' + '</a>'
                    ].join('');
                } else {
                    return _dont_edit_formatter;
                }

            };
        });
    };

    BootstrapTable.prototype.initBody = function() {
        var that = this;
        _initBody.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.editable) {
            return;
        }
       
        $.each(this.columns, function(i, column) {
            if (!column.editable) {
                return;
            }
            
            var opt = column.editable;
            
            if(opt.type=="text" || opt.type=="date"){
            	$.each(that.getData(),function(index){
            		var columnField = (column.field).replace(/\./g,"\\\.")+'_'+this.id;
            		var obj = that.$body.find('#'+columnField);
					try{         
						if(opt.value){
							obj.val(opt.value);
						}else{
							obj.val(eval("this."+column.field));
						}
					}catch(e){}
            		obj.bind("click",function(event){
            			that.check_(true, index);
            			cancelBubble(event);
            		});
            		if(opt.type=="date"){
            			obj.datepicker(opt.options);  
            		}
            	}); 
            }else if(opt.type=="select"){ 
            	$.each(that.getData(),function(index){
            		var row = this; 
            		var rowId = this.id;
            		var columnField = (column.field).replace(/\./g,"\\\.")+'_'+this.id;
            		var obj = that.$body.find('#'+columnField);
            		var _options = $.extend({},opt.options);
            		_options.width = "100%";
            		 //row.开头的val处理 
            		var newParams = new Object();
            		for ( var key in _options.params) {
						var v = _options.params[key]+"";
						if(v.indexOf("row.") == 0){
							newParams[key] = eval(v);
						}else{
							newParams[key] = v;
						}
					}
            		_options.params = newParams;
            		_options.onLoadSuccess = function(data){
            			if(column.selectCache){
            				opt.options.data = data;
            			}
            		};
            		
            		obj.select2(_options);
            		if(opt.onChange){
            			obj.on("change",function(){
                			opt.onChange(obj.val(),rowId);
                		})
            		}
            		
            		obj.next("span").bind("click",function(event){
            			that.check_(true, index);
            			cancelBubble(event);
            		});
            		try{  
            			if(typeof(eval("this."+column.field)) != "undefined" && eval("this."+column.field) != null){
            				obj.val(eval("this."+column.field)+"").trigger("change");
            			}else{
            				obj.trigger("change");
            			}
					}catch(e){}
            	});
            }
            
            return;
            that.$body.find('a[data-name="' + column.field + '"]').editable(column.editable)
                .off('save').on('save', function(e, params) {
                    var data = that.getData(),
                        index = $(this).parents('tr[data-index]').data('index'),
                        row = data[index],
                        oldValue = row[column.field];

                    $(this).data('value', params.submitValue);
                    row[column.field] = params.submitValue;
                    that.trigger('editable-save', column.field, row, oldValue, $(this));
                    that.resetFooter();
                });
            that.$body.find('a[data-name="' + column.field + '"]').editable(column.editable)
                .off('shown').on('shown', function(e, editable) {
                    var data = that.getData(),
                        index = $(this).parents('tr[data-index]').data('index'),
                        row = data[index];

                    that.trigger('editable-shown', column.field, row, $(this), editable);
                });
            that.$body.find('a[data-name="' + column.field + '"]').editable(column.editable)
                .off('hidden').on('hidden', function(e, reason) {
                    var data = that.getData(),
                        index = $(this).parents('tr[data-index]').data('index'),
                        row = data[index];
                    that.trigger('editable-hidden', column.field, row, $(this), reason);
                });
        });
        
        this.trigger('editable-init');
    };
    //阻止冒泡
    function cancelBubble(event1){
    	try{
    		event.cancelBubble=true;//阻止冒泡
    	}catch (e) {
    		event1.stopPropagation();//阻止冒泡
		}
    }
})(jQuery);
