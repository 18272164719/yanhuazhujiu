//禁浏览器返回
$(function() {
	if (window.history && window.history.pushState) {
		$(window).on('popstate', function () {
			window.history.pushState('forward', null, '#');
			window.history.forward(1);
		});
	}
	window.history.pushState('forward', null, '#'); //在IE中必须得有这两行
	window.history.forward(1);
})
//判断各种浏览器，找到正确的方法
function launchFullscreen() {
	var docElm = document.documentElement;
	//W3C  
	if (docElm.requestFullscreen) {  
	    docElm.requestFullscreen();  
	}
	//FireFox  
	else if (docElm.mozRequestFullScreen) {  
	    docElm.mozRequestFullScreen();  
	}
	//Chrome等  
	else if (docElm.webkitRequestFullScreen) {  
	    docElm.webkitRequestFullScreen();  
	}
	//IE11
	else if (docElm.msRequestFullscreen) {
		docElm.msRequestFullscreen();
	}else{
		return;
	}
	
	
	//退出全屏
	if (document.exitFullscreen) {  
	    document.exitFullscreen();  
	}  
	else if (document.mozCancelFullScreen) {  
	    document.mozCancelFullScreen();  
	}  
	else if (document.webkitCancelFullScreen) {  
	    document.webkitCancelFullScreen();  
	}
	else if (document.msExitFullscreen) {
	      document.msExitFullscreen();
	}
	 
	
}



//iframe高度自适应
function SetCwinHeight(){
	  var iframeid=$("#myTabs").tabs("getSelectedFrame")[0]; //iframe id
	  var minH = $(window).height()-90;
	  //iframeid.css("minHeight",h);
	  if (document.getElementById){
	   	if (iframeid && !window.opera){ 
	   		 if (iframeid.contentDocument && iframeid.contentDocument.body.offsetHeight){
	    	 	h = iframeid.contentDocument.body.offsetHeight;
	    	 }else if(iframeid.Document && iframeid.Document.body.scrollHeight){
	     		h = iframeid.Document.body.scrollHeight;
	    	 }
	   	}
	  }
	  if(h<minH) h = minH;
	  $(iframeid).css("height",h);
}

function transTreeData(items,parentId){
    if(items.length>0){
        var curPid= parentId //pid=-1，为最上层节点 ，即无父节点
        var parent=findChild(curPid,items);//数组
        return parent;
    }else{
        return [];
    }
}
function findChild (curPid,items){
    var _arr = [];
    var length=items.length;
    for(var i = 0; i < length; i++){
        if(items[i].parentId == curPid){
            var _obj = items[i];
            _obj.children = findChild(_obj.id,items);
            _arr.push(_obj);
        }
    }
    return _arr;
}


var toptable;
var toptable2;
var toptable3;
var topCallback;//回调方法自定义，用于弹出挑选框回调
var topCallback2;
var topCallback3;

var editrow;
function tfLoad(obj){
	 if(editrow != null){
		 $.each($(obj).contents().find("input,textarea"),function(index){
			 var v = eval("editrow."+$(this).attr("name"));
			 if(v == null) return;
			 if($(this).attr("type")== "radio"){
				 try{
					 if($(this).val() == eval("editrow."+$(this).attr("name"))){
						 $(this).attr("checked","checked");
					 }
				 }catch(e){}
			 }else if($(this).attr("type")== "checkbox"){
				 try{
					 if( eval("editrow."+$(this).attr("name")) == 1 ||  eval("editrow."+$(this).attr("name")) == true ){
						 $(this).attr("checked","checked");
					 }
					 $(this).val(eval("editrow."+$(this).attr("name")));
				 }catch(e){}
			 }else {
				 try{
					if(typeof($(this).attr("data-date"))!="undefined"){
						//$(this).datepicker("setDate",$.format.date(1489377260000,"yyyy-MM-dd"));
						//$(this).datepicker("setDate",$.format.date(v,"yyyy-MM-dd"));
						$(this).val($.format.date(v,"yyyy-MM-dd"));
					}else{
						$(this).val(v);
					}
				 }catch(e){}
			 }
		 });
		 
		 $.each($(obj).contents().find("select"),function(index){
			 var v = eval("editrow."+$(this).attr("name"));
			 if(v == null) return;
			 $(obj)[0].contentWindow.setSelectVal_indexFunc(this,v);
		 });
		 editrow = null;
		 //赋值回调
		 try{
			 $(obj)[0].contentWindow.editrowSuccess();
		 }catch(e){}
			
	 }
}

//消息提示
function showMsg(text){
	  $.bootstrapGrowl(text, { type: 'success' });
}
function showInfo(text){
	  $.bootstrapGrowl(text);
}
function showErr(text){
	  $.bootstrapGrowl(text, { type: 'danger' });
}


function treeNodeFunc(type,id,name,url,icon,parentId,obj){
	//active 0三级菜单  1二级菜单
	var ol = "";
	if(type==0){
		$("#tree").children("li.active").removeClass("active");
		$(".treeview-menu").find("li.active").removeClass("active");
		$("#treeMenuLi"+id).addClass("active");
		$("#treeLi"+parentId).addClass("active");
		
		//组OL
		//三级菜单名称
		var threeOL = $("#treeMenu_"+id).text();
		var twoOL = $("#treeMenu_"+id).parents(".second-menu-item").find(".second-menu-title").text();
		var oneOL = $("#treeMenu_"+id).parents(".treeview").children("a").children("span:first").text();
		var icon = $("#treeMenu_"+id).parents(".treeview").children("a").children("i").attr("class");
		ol = icon+","+oneOL+","+twoOL+","+threeOL
	}
	else if(type==1){
		$("#tree").children("li.active").removeClass("active");
		$(".treeview-menu").find("li.active").removeClass("active");
		$("#treeLi"+parentId).addClass("active");
		//组OL
		//二级菜单名称
		var twoOL = $("#treeMenu_"+id).text();
		var oneOL = $("#treeMenu_"+id).parents(".treeview").children("a").children("span:first").text();
		var icon = $("#treeMenu_"+id).parents(".treeview").children("a").children("i").attr("class");
		ol = icon+","+oneOL+","+twoOL
	}
	//打开窗口
	addTab(name,url,icon, true,ol);
}

function setOL(ol){
	var urlOL = $("#urlOL");
	urlOL.empty();
	var arr = ol.split(",");
	var icon = "";
	var active="";
	for(var i=1;i<arr.length;i++){
		if(i==arr.length-1){
			active = 'class="active"';
		}
		if(i==1){
			icon = '<i class="'+arr[0]+'"></i>';
			urlOL.append($('<li '+active+' ><i class="'+arr[0]+'"></i> '+arr[i]+'</li>'));
		}else{
			urlOL.append($('<li '+active+' > '+arr[i]+'</li>'));
		}
		
		
	}
     
}


