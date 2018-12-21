/**
 * API
 */
var z = new Object();

(function($){
    z.iframe = function(obj){
        return  $(obj).find("iframe")[0].contentWindow;
    }




})(jQuery);
