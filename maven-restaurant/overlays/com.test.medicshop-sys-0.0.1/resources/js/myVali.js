
//扩大easyui表单的验证
$.extend($.fn.validatebox.defaults.rules, {
  idcard : {// 验证身份证 
        validator : function(value) { 
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value); 
        }, 
        message : '身份证号码格式不正确' 
    },
      minLength: {
        validator: function(value, param){
        	
        	var totalCount = 0; 
            for(var i=0; i<value.length; i++){ 
                var c = value.charCodeAt(i); 
                if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)){ 
                     totalCount++; 
                } 
                else{      
                    totalCount+=2; 
                } 
             } 
           
            return totalCount >= param[0];
        },
        message: '请输入至少{0}个字节.'
    },
    maxLength: {
        validator: function(value, param){
        	
        	var totalCount = 0; 
            for(var i=0; i<value.length; i++){ 
                var c = value.charCodeAt(i); 
                if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)){ 
                     totalCount++; 
                } 
                else{      
                    totalCount+=2; 
                } 
             } 
           
            return totalCount <= param[0];
        },
        message: '请输入至多{0}个字节.'
    },
    length:{validator:function(value,param){ 
    	var totalCount = 0; 
        for(var i=0; i<value.length; i++){ 
            var c = value.charCodeAt(i); 
            if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)){ 
                 totalCount++; 
            } 
            else{      
                totalCount+=2; 
            } 
         } 
        return totalCount >= param[0]&&totalCount <= param[1];
        
        }, 
            message:"请输入{0}-{1}个字节." 
        }, 
    size:{validator:function(value,param){ 
        var len=$.trim(value).length; 
            return len==param[0]; 
        }, 
            message:"输入内容长度必须为{0}." 
    },
    phone : {// 验证电话号码 
        validator : function(value) { 
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value); 
        }, 
        message : '格式不正确,请使用下面格式:020-88888888' 
    }, 
    goodsNo : {// 验证电话号码 
        validator : function(value) { 
        	var len=$.trim(value).length; 
        	if(len>0){
        		return /^[A-Z0-9]+$/.test(value); 
        	} else {
        		return true;
        	}
        }, 
        message : '格式不正确,只能使用大写字母和数字' 
    }, 
    mobile : {// 验证手机号码 
    	 validator : function(value) { 
            return /^(13|15|18)\d{9}$/i.test(value); 
        }, 
        message : '手机号码格式不正确' 
    }, 
    mail : {// 验证邮箱
        validator : function(value) {
            return /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/i.test(value); 
            //return /^(13|15|18)\d{9}$/i.com.test(value);
        }, 
        message : '邮箱格式不正确' 
    },
    intOrFloat : {// 验证整数或小数 
        validator : function(value) { 
            return /^\d+(\.\d+)?$/i.test(value); 
        }, 
        message : '请输入数字，并确保格式正确' 
    }, 
    currency : {// 验证货币 
        validator : function(value) { 
            return /^\d+(\.\d+)?$/i.test(value); 
        }, 
        message : '货币格式不正确' 
    }, 
    qq : {// 验证QQ,从10000开始 
        validator : function(value) { 
            return /^[1-9]\d{4,9}$/i.test(value); 
        }, 
        message : 'QQ号码格式不正确' 
    }, 
    integer : {// 验证整数 
        validator : function(value) { 
            return /^[+]?[1-9]+\d*$/i.test(value); 
        }, 
        message : '请输入整数' 
    },  
    pinteger : {// 验证正整数 
        validator : function(value) { 
            return /^[1-9]\d*$/i.test(value); 
        }, 
        message : '请输入正整数' 
    }, 
    age : {// 验证年龄
        validator : function(value) { 
            return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value); 
        }, 
        message : '年龄必须是0到120之间的整数' 
    }, 
    
    chinese : {// 验证中文 
        validator : function(value) { 
            return /^[\Α-\￥]+$/i.test(value); 
        }, 
        message : '请输入中文' 
    }, 
    nonchinese : {// 验证非中文 
        validator : function(value) { 
            return !(/[\u4E00-\u9FA5]/g.test(value)); 
        }, 
        message : '不可输入中文字符' 
    },
    english : {// 验证英语 
        validator : function(value) { 
            return /^[A-Za-z]+$/i.test(value); 
        }, 
        message : '请输入英文' 
    }, 
    unnormal : {// 验证是否包含空格和非法字符 
        validator : function(value) { 
            return /.+/i.test(value); 
        }, 
        message : '输入值不能为空和包含其他非法字符' 
    }, 
    username : {// 验证用户名 
        validator : function(value) { 
            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value); 
        }, 
        message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）' 
    }, 
    faxno : {// 验证传真 
        validator : function(value) { 
//            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.com.test(value);
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value); 
        }, 
        message : '传真号码不正确' 
    }, 
    zip : {// 验证邮政编码 
        validator : function(value) { 
            return /^[1-9]\d{5}$/i.test(value); 
        }, 
        message : '邮政编码格式不正确' 
    }, 
    ip : {// 验证IP地址 
        validator : function(value) { 
            return /d+.d+.d+.d+/i.test(value); 
        }, 
        message : 'IP地址格式不正确' 
    }, 
    name : {// 验证姓名，可以是中文或英文 
            validator : function(value) { 
                return /^[\Α-\￥]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value); 
            }, 
            message : '请输入姓名' 
    },
    date : {// 验证姓名，可以是中文或英文 
        validator : function(value) { 
         //格式yyyy-MM-dd或yyyy-M-d
            return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value); 
        },
        message : '请输入合适的日期格式'
    },
    msn:{ 
        validator : function(value){ 
        return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value); 
    }, 
    message : '请输入有效的msn账号' 
    },
    same:{ 
        validator : function(value, param){ 
            if($(param[0]).textbox("getValue") != "" && value != ""){ 
                return $(param[0]).textbox("getValue") == value; 
            }else{ 
                return true; 
            } 
        }, 
        message : '两次输入的密码不一致！'    
    } ,
    dateSE:{ 
        validator : function(value, param){ 
        	if($(param[0]).datebox("getValue")>value)
        		return false;
        	else 
        		return true;
        }, 
        message : '结束日期不能 早于 开始日期！'    
    } 
});