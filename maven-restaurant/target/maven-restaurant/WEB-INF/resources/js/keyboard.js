

/**
 * args.rowSize：每行几列，用于计算上下跳转
 * args.enterPressHandler:回车键处理方式                              --- all(全部),last(最后一个文本),id(指定ID),data-id（table的data-id通配）
 * args.enterPressKey:enterPressHandler为ID或者data-id时候的匹配关键字  --- 返回方法
 * args.enterPressFunc:回执方法                                       --- 返回方法
 *
 */
$.fn.keyHandler = function (args) {
    var win = $(this);
    win.rowSize = args.rowSize||0;
    win.enterPressHandler = args.enterPressHandler||true;//all
    win.enterPressKey = args.enterPressKey||"";
    win.enterPressFunc = args.enterPressFunc||function() {};
    win.allowRadio = args.allowRadio||false;
    win.focusIndex = 0;
    win.contextmenu(function(event) {return event.returnValue = false});
    win.loadTexts = false;
    win.keydown(function(event) {
        if (event == undefined) return;
        if (event == null) return;
        win.initTexts();
        if (event.which == 37) {
            win.cursorPosition(event);
        } else if (event.which == 38) {
            win.cursorPosition(event);
        } else if (event.which == 39) {
            win.cursorPosition(event);
        } else if (event.which == 40) {
            win.cursorPosition(event);
        } else if (event.which == 13) {
            win.pressEnter(event);
        }
    });
    var str = "select:visible,input[type='text']:visible,.form-control:visible,textarea:visible";

    win.on("focus",str,function(){
        win.initTexts();
        win.focusIndex = Number($(this).attr("focusId"));
    });
    win.initTexts = function(){
        if(!win.loadTexts) {
            win.texts = $(this).find(str);
            win.texts.each(function(i,n) {
                $(this).attr("focusId",i);
            });
            win.loadTexts = true;
        }
    }
    win.pressEnter = function (event) {
        var f;
        if(win.enterPressHandler == "all") {
            f = win.enterPressFunc();
        } else if(win.enterPressHandler == "last") {
            if (win.focusIndex+1 == win.texts.length) {
                f = win.enterPressFunc();
            }
        } else {
            for (var i=0;i<event.target.attributes.length;i++) {
                var target = event.target.attributes[i];
                if(win.enterPressHandler == "id" && target.name == "id") {
                    if (target.value == win.enterPressKey) {
                        f = win.enterPressFunc($("#"+target.value));
                        break;
                    }
                } else if (win.enterPressHandler == "data-id" && target.name == "data-id") {
                    if (target.value.indexOf(win.enterPressKey) == 0) {
                        f = win.enterPressFunc($("[data-id='"+target.value+"']"));
                        break;
                    }
                }
            }
        }

        if (f != false && win.focusIndex+1 < win.texts.length) {
            win.texts[win.focusIndex+1].focus();
        }
        event.stopPropagation();
    }
    win.cursorPosition = function(event){
        var input = null;
        for (var i=0;i<event.target.attributes.length;i++) {
            var target = event.target.attributes[i];
            if(target.name == "id") {
                input = $("#"+target.value)[0];
                break;
            } else if (target.name == "data-id") {
                input = $("[data-id='"+target.value+"'")[0];
                break;
            }
        }
        if (input == null) return;

        var cursurPosition=-1;
        if(document.selection) {
            var range = document.selection.createRange();
            range.moveStart("character",-input.value.length);
            cursurPosition = range.text.length;
        } if(input.selectionStart){
            cursurPosition= input.selectionStart;
        }
        if (event.which ==37 && cursurPosition == -1 && win.focusIndex>0) {
            focusInput(win.focusIndex-1);
        } else if (event.which ==38 && win.rowSize!=0 && (win.focusIndex+1)>=win.rowSize) {
            focusInput(win.focusIndex-win.rowSize);
        } else if (event.which ==39 && cursurPosition == input.value.length && (win.focusIndex+1)<=win.texts.length) {
            focusInput(win.focusIndex+1);
        } else if (event.which ==40 && win.rowSize!=0 && (win.focusIndex+win.rowSize)<=win.texts.length) {
            focusInput(win.focusIndex+win.rowSize);
        }
    }
    function focusInput(i) {
        var ninput = $(win.texts[i]);
        if($(ninput).is("input")) {
            var val = ninput.val();
            ninput.val("").focus().val(val);
        } else {
            ninput.focus()
        }
    }
}
$.extend({
    bindKeyboard:function(args) {
        var args = args||{};
        var keyMap = {
            "BACKSPACE": 8,
            "TAB": 9,
            "RETURN": 10,
            "ENTER": [13,108],
            "SHIFT": 16,
            "CTRL": 17,
            "ALT": 18,
            "PAUSE": 19,
            "CAPSLOCK": 20,
            "ESC": 27,
            "SPACE": 32,
            "PAGEUP": 33,
            "PAGEDOWN": 34,
            "END": 35,
            "HOME": 36,
            "LEFT": 37,
            "UP": 38,
            "RIGHT": 39,
            "DOWN": 40,
            "INSERT": 45,
            "DEL": 46,
            ";": 59,
            "=": 61,
            "0": [48,96],
            "1": [49,97],
            "2": [50,98],
            "3": [51,99],
            "4": [52,100],
            "5": [53,101],
            "6": [54,102],
            "7": [55,103],
            "8": [56,104],
            "9": [57,105],
            "*": 106,
            "+": [107,187],
            ".": [110,190],
            "/": [111,191],
            "F1": 112,
            "F2": 113,
            "F3": 114,
            "F4": 115,
            "F5": 116,
            "F6": 117,
            "F7": 118,
            "F8": 119,
            "F9": 120,
            "F10": 121,
            "F11": 122,
            "F12": 123,
            "NUMLOCK": 144,
            "SCROLL": 145,
            "-": [109,173,189],
            ";": 186,
            "=": 187,
            ",": 188,
            "`": 192,
            "[": 219,
            "\\": 220,
            "]": 221,
            "'": 222
        };
        var keyFunc = {};
        for(var k in args) {
            var CTRL = (k.indexOf("CTRL") == 0);
            var ALT = (k.indexOf("ALT") == 0);
            var id = k;
            if (CTRL || ALT) {
                id = id.substring(id.lastIndexOf("+")+1, id.length);
            }
            if (typeof keyMap[id] == "array"||typeof keyMap[id] == "object") {
                $(keyMap[id]).each(function() {
                    keyFunc[this+"_"+CTRL+"_"+ALT] = args[k]||function(){};
                });
            } else {
                console.log((typeof keyMap[id])+keyMap[id]+"_"+CTRL+"_"+ALT);
                keyFunc[keyMap[id]+"_"+CTRL+"_"+ALT] = args[k]||function(){};
            }
        }
        console.log(args);
        console.log(keyFunc);
        $(top).keydown(function(event) {
            console.log(event.which+"_"+event.ctrlKey+"_"+event.altKey);
            if (event!=null && keyFunc[event.which+"_"+event.ctrlKey+"_"+event.altKey] != undefined) {
                keyFunc[event.which+"_"+event.ctrlKey+"_"+event.altKey]();
                cancelBubble(event);
                console.log(keyFunc[event.which+"_"+event.ctrlKey+"_"+event.altKey]);
            }
        });
        $(window).keydown(function(event) {
            console.log(event.which+"_"+event.ctrlKey+"_"+event.altKey);
            if (event!=null && keyFunc[event.which+"_"+event.ctrlKey+"_"+event.altKey] != undefined) {
                var f = keyFunc[event.which+"_"+event.ctrlKey+"_"+event.altKey]();
                if (f == false) {
                    return f;
                }
                cancelBubble(event);
                console.log(keyFunc[event.which+"_"+event.ctrlKey+"_"+event.altKey]);
            }
        });
        $("input").keydown(function(event) {
            console.log(event.which+"_"+event.ctrlKey+"_"+event.altKey);
            if (event!=null && keyFunc[event.which+"_"+event.ctrlKey+"_"+event.altKey] != undefined) {
                var f = keyFunc[event.which+"_"+event.ctrlKey+"_"+event.altKey]();
                if (f == false) {
                    return f;
                }
                cancelBubble(event);
                console.log(keyFunc[event.which+"_"+event.ctrlKey+"_"+event.altKey]);
            }
        });
        window.onupdate = function() {
            $(top).off("keydown");
            $(window).off("keydown");
            $("input").off("keydown");
        }
        $("body").click();
    }
});