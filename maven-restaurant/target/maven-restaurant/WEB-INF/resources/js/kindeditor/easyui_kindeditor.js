(function ($, K) {
    if (!K)
        throw "KindEditor未定义!";

    function create(target) {
        var opts = $.data(target, 'kindeditor').options;
        var editor = K.create(target, opts);
        $.data(target, 'kindeditor').options.editor = editor;
    }

    $.fn.kindeditor = function (options, param) {
        if (typeof options == 'string') {
            var method = $.fn.kindeditor.methods[options];
            if (method) {
                return method(this, param);
            }
        }
        options = options || {};
        return this.each(function () {
            var state = $.data(this, 'kindeditor');
            if (state) {
                $.extend(state.options, options);
            } else {
                state = $.data(this, 'kindeditor', {
                        options : $.extend({}, $.fn.kindeditor.defaults, $.fn.kindeditor.parseOptions(this), options)
                    });
            }
            create(this);
        });
    };

    $.fn.kindeditor.parseOptions = function (target) {
        return $.extend({}, $.parser.parseOptions(target, []));
    };

    $.fn.kindeditor.methods = {
        editor : function (jq) {
            return $.data(jq[0], 'kindeditor').options.editor;
        }
    };

    $.fn.kindeditor.defaults = {
        resizeType : 1,
        allowPreviewEmoticons : false,
        allowImageUpload : false,
        uploadJson : '${ctx}/wxMessage/uploadImg.yhtml',
        fileManagerJson : '${ctx}/wxMessage/uploadImg.yhtml',
        items : [
'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline',  
'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',  
'insertunorderedlist', '|', 'emoticons', 'image', 'link','fullscreen','wordpaste','source'],
        afterChange:function(){
            this.sync();//这个是必须的,如果你要覆盖afterChange事件的话,请记得最好把这句加上.
        }
    };
    $.parser.plugins.push("kindeditor");
})(jQuery, KindEditor);