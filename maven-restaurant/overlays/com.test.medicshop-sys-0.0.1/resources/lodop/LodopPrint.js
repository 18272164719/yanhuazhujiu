
var LODOP; //声明为全局变量
function preview(printSet, map, url) {
    printPage(printSet, map, url);
    if(LODOP){
        LODOP.PREVIEW();
    }
}
function design(printSet, map, url) {  
	printPage(printSet, map, url); 
    if(LODOP){
    	LODOP.PRINT_DESIGN();	
    }
}
function printPage(printSet, map, url) {
	var deviateWidth = 0;
	var deviateHeight = 0;
	var width = 800;
	var height = 600;
	if(printSet.deviateWidth){
		deviateWidth = printSet.deviateWidth;
	}
	if(printSet.deviateHeight){
		deviateHeight = printSet.deviateHeight;
	}
	if(printSet.width){
		width = printSet.width;
	}
	if(printSet.height){
		height = printSet.height;
	}
    LODOP=getLodop(url);
    if(!LODOP){
    	return;
    }
	LODOP.PRINT_INITA(deviateHeight,deviateWidth,width,height,"");

    for(var i=0;i<printSet.printSetModules.length;i++){
        printSet.printSetModules[i].action = printSet.printSetModules[i].action.replaceAll("&quot;","\"");
    }

    if(printSet.templateTypeValue == "1"){
        printPage1(printSet, map);
    }else if(printSet.templateTypeValue == "2"){
        printPage2(printSet, map);
    }else if(printSet.templateTypeValue == "3"){
        printPage3(printSet, map);
    }
}
function printPage1(printSet, map){
    var rows = map[printSet.printSetModules[0].printTemplate.id+""];

    var num = printSet.row*printSet.cell;
    var a = parseInt(rows.length/num);
    var b = rows.length%num;
    var n = 0;
    for(var i=0;i<a;i++){
        for(var j=0;j<num;j++){
            for(var k=0;k<printSet.printSetModules.length;k++){
                var action = printSet.printSetModules[k].action;
                print1(LODOP, action, rows[n], j, printSet);
            }
            n++;
        }
        LODOP.NEWPAGE();
    }
    for(var j=0;j<b;j++){
        for(var k=0;k<printSet.printSetModules.length;k++){
            var action = printSet.printSetModules[k].action;
            print1(LODOP, action, rows[n], j, printSet);
        }
        n++;
    }
}
function print1(LODOP, action, objNew, index, printSet){
    var x = parseInt(index/printSet.cell)*printSet.unitHeight;
    var y = (index%printSet.cell)*printSet.unitWidth;
    action = "obj=objNew; x = " + x + ";" + " y = " + y + ";" + action;
    eval(action);
}

function printPage2(printSet, map){
    var height = 0;
    for(var i=0;i<printSet.printSetModules.length;i++){
        var printSetModule = printSet.printSetModules[i];
        var action = printSetModule.action;
        var rows = map[printSetModule.printTemplate.id+""];
        if(height > printSet.unitHeight){
            LODOP.NEWPAGE();
            height = 0;
        }
        for(var j=0;j<rows.length;j++){
            if(printSetModule.loopType == 1) {
                if(height > printSet.unitHeight){
                    LODOP.NEWPAGE();
                    height = 0;
                }
                print2(LODOP, action, rows[j], printSetModule, height);
                height = height + printSetModule.moduleHeight;
                height = height + printSetModule.spacing;
            }else{
                print2(LODOP, action, rows[j], printSetModule, height);
                height = height + printSetModule.moduleHeight;
                height = height + printSetModule.spacing;
                break;
            }
        }
    }
}

function print2(LODOP, action, objNew, printSetModule, height){
    action = "obj=objNew; x = "+height+";" + " y = 0;" + action;
    eval(action);
}

function printPage3(printSet, map){

    var list;
    var printSetModule;

    for(var i=0;i<printSet.printSetModules.length;i++){
        if(printSet.printSetModules[i].pageType == 1||printSet.printSetModules[i].pageType == 2){
            //找到分页对象
            list = map[printSet.printSetModules[i].printTemplate.id+""];
            printSetModule = printSet.printSetModules[i];
        }
    }

    var a = parseInt(list.length/printSetModule.loopNum);
    for(var i=0;i<a;i++){
        var pageMap = map["pageMap_"+i]||{};
        for(var k=0;k<printSet.printSetModules.length;k++){
            var action = printSet.printSetModules[k].action;
            var rows = map[printSet.printSetModules[k].printTemplate.id+""];

            if(printSet.printSetModules[k].pageType == 0) {
                var row = rows[0];
                for (var key in pageMap) {
                    row[key] = pageMap[key];
                }
                print3(LODOP, action, row, 0, printSet.printSetModules[k]);
            }else{
                for(var j=i*printSetModule.loopNum,n=0;j<(i+1)*printSetModule.loopNum;j++,n++){
                    print3(LODOP, action, rows[j], n, printSetModule);
                }
            }
        }

        LODOP.NEWPAGE();
    }
    var pageMap = map["pageMap_"+a]||{};
    for(var k=0;k<printSet.printSetModules.length;k++){
        var action = printSet.printSetModules[k].action;
        var rows = map[printSet.printSetModules[k].printTemplate.id+""];
        if(printSet.printSetModules[k].pageType == 0){
            var row = rows[0];
            for (var key in pageMap) {
                row[key] = pageMap[key];
            }
            print3(LODOP, action, row, 0, printSet.printSetModules[k]);
        }else{
            for(var j=a*printSetModule.loopNum,n=0;j<list.length;j++,n++){
                print3(LODOP, action, rows[j], n, printSetModule);
            }
        }
    }
}
function print3(LODOP, action, objNew, index, printSetModule){
    var x = 0;
    var y = 0;
    if(printSetModule.loopType == 1){
        x = index*printSetModule.spacing;
    }else{
        y = index*printSetModule.spacing;
    }

    action = "obj=objNew; x = " + x + ";" + " y = " + y + ";" + action;
    eval(action);
}


String.prototype.replaceAll = function(s1,s2){
    return this.replace(new RegExp(s1,"gm"),s2);
}