/*
 * Bootstrap 3.3.6 IconPicker - jQuery plugin for Icon selection
 *
 * Copyright (c) 20013 A. K. M. Rezaul Karim<titosust@gmail.com>
 * Modifications (c) 20015 Paden Clayton<fasttracksites.com>
 *
 * Licensed under the MIT license:
 *   http://www.opensource.org/licenses/mit-license.php
 *
 * Project home:
 *   https://github.com/titosust/Bootstrap-icon-picker
 *
 * Version:  1.0.1
 *
 */

(function($) {

    $.fn.iconPicker = function( options ) {
        
        var mouseOver=false;
        var $popup=null;
        //var icons=new Array("adjust","alert","align-center","align-justify","align-left","align-right","apple","arrow-down","arrow-left","arrow-right","arrow-up","asterisk","baby-formula","backward","ban-circle","barcode","bed","bell","bishop","bitcoin","blackboard","bold","book","bookmark","briefcase","btc","bullhorn","calendar","camera","cd","certificate","check","chevron-down","chevron-left","chevron-right","chevron-up","circle-arrow-down","circle-arrow-left","circle-arrow-right","circle-arrow-up","cloud","cloud-download","cloud-upload","cog","collapse-down","collapse-up","comment","compressed","console","copy","copyright-mark","credit-card","cutlery","dashboard","download","download-alt","duplicate","earphone","edit","education","eject","envelope","equalizer","erase","eur","euro","exclamation-sign","expand","export","eye-close","eye-open","facetime-video","fast-backward","fast-forward","file","film","filter","fire","flag","flash","floppy-disk","floppy-open","floppy-remove","floppy-save","floppy-saved","folder-close","folder-open","font","forward","fullscreen","gbp","gift","glass","globe","grain","hand-down","hand-left","hand-right","hand-up","hd-video","hdd","header","headphones","heart","heart-empty","home","hourglass","ice-lolly","ice-lolly-tasted","import","inbox","indent-left","indent-right","info-sign","italic","jpy","king","knight","lamp","leaf","level-up","link","list","list-alt","lock","log-in","log-out","magnet","map-marker","menu-down","menu-hamburger","menu-left","menu-right","menu-up","minus","minus-sign","modal-window","move","music","new-window","object-align-bottom","object-align-horizontal","object-align-left","object-align-right","object-align-top","object-align-vertical","off","oil","ok","ok-circle","ok-sign","open","open-file","option-horizontal","option-vertical","paperclip","paste","pause","pawn","pencil","phone","phone-alt","picture","piggy-bank","plane","play","play-circle","plus","plus-sign","print","pushpin","qrcode","queen","question-sign","random","record","refresh","registration-mark","remove","remove-circle","remove-sign","repeat","resize-full","resize-horizontal","resize-small","resize-vertical","retweet","road","rub","ruble","save","save-file","saved","scale","scissors","screenshot","sd-video","search","send","share","share-alt","shopping-cart","signal","sort","sort-by-alphabet","sort-by-alphabet-alt","sort-by-attributes","sort-by-attributes-alt","sort-by-order","sort-by-order-alt","sound-5-1","sound-6-1","sound-7-1","sound-dolby","sound-stereo","star","star-empty","stats","step-backward","step-forward","stop","subscript","subtitles","sunglasses","superscript","tag","tags","tasks","tent","text-background","text-color","text-height","text-size","text-width","th","th-large","th-list","thumbs-down","thumbs-up","time","tint","tower","transfer","trash","tree-conifer","tree-deciduous","triangle-bottom","triangle-left","triangle-right","triangle-top","unchecked","upload","usd","user","volume-down","volume-off","volume-up","warning-sign","wrench","xbt","yen","zoom-in","zoom-out");
        var icons = new Array("glass","th-large","arrow-right","arrow-up","arrow-down","mail-forward","share","expand","compress","plus","minus","asterisk","th","exclamation-circle","gift","leaf","fire","eye","eye-slash","warning","exclamation-triangle","plane","calendar","th-list","random","comment","magnet","chevron-up","chevron-down","retweet","shopping-cart","folder","folder-open","arrows-v","check","arrows-h","bar-chart-o","bar-chart","twitter-square","facebook-square","camera-retro","key","gears","cogs","comments","remove","thumbs-o-up","thumbs-o-down","star-half","heart-o","sign-out","linkedin-square","thumb-tack","external-link","sign-in","trophy","close","github-square","upload","lemon-o","phone","square-o","bookmark-o","phone-square","twitter","facebook","github","times","unlock","credit-card","rss","hdd-o","bullhorn","bell","certificate","hand-o-right","hand-o-left","hand-o-up","search-plus","hand-o-down","arrow-circle-left","arrow-circle-right","arrow-circle-up","arrow-circle-down","globe","wrench","tasks","filter","briefcase","search-minus","arrows-alt","group","users","chain","link","cloud","flask","cut","scissors","copy","power-off","files-o","paperclip","save","floppy-o","square","navicon","reorder","bars","list-ul","list-ol"," fa-music","signal","strikethrough","underline","table","magic","truck","pinterest","pinterest-square","google-plus-square","google-plus","money","gear","caret-down","caret-up","caret-left","caret-right","columns","unsorted","sort","sort-down","sort-desc","sort-up","cog","sort-asc","envelope","linkedin","rotate-left","undo","legal","gavel","dashboard","tachometer","comment-o","trash-o","comments-o","flash","bolt","sitemap","umbrella","paste","clipboard","lightbulb-o","exchange","cloud-download","home","cloud-upload","user-md","stethoscope","suitcase","bell-o","coffee","cutlery","file-text-o","building-o","hospital-o","file-o","ambulance","medkit","fighter-jet","beer","h-square","plus-square","angle-double-left","angle-double-right","angle-double-up","angle-double-down","clock-o","angle-left","angle-right","angle-up","angle-down","desktop","laptop","tablet","mobile-phone","mobile","circle-o","road","quote-left","quote-right","spinner","circle","mail-reply","reply","github-alt","folder-o","folder-open-o","smile-o","download","frown-o","meh-o","gamepad","keyboard-o","flag-o","flag-checkered","terminal","code","mail-reply-all","reply-all","arrow-circle-o-down","star-half-empty","star-half-full","star-half-o","location-arrow","crop","code-fork","unlink","chain-broken","question","info","search","arrow-circle-o-up","exclamation","superscript","subscript","eraser","puzzle-piece","microphone","microphone-slash","shield","calendar-o","fire-extinguisher","inbox","rocket","maxcdn","chevron-circle-left","chevron-circle-right","chevron-circle-up","chevron-circle-down","html","css","anchor","unlock-alt","play-circle-o","bullseye","ellipsis-h","ellipsis-v","rss-square","play-circle","ticket","minus-square","minus-square-o","level-up","level-down","rotate-right","check-square","pencil-square","external-link-square","share-square","compass","toggle-down","caret-square-o-down","toggle-up","caret-square-o-up","toggle-right","repeat","caret-square-o-right","euro","eur","gbp","dollar","usd","rupee","inr","cny","rmb","refresh","yen","jpy","ruble","rouble","rub","won","krw","bitcoin","btc","file","list-alt","file-text","sort-alpha-asc","sort-alpha-desc","sort-amount-asc","sort-amount-desc","sort-numeric-asc","sort-numeric-desc","thumbs-up","thumbs-down","youtube-square","lock","youtube","xing","xing-square","youtube-play","dropbox","stack-overflow","instagram","flickr","adn","bitbucket","flag","bitbucket-square","tumblr","tumblr-square","long-arrow-down","long-arrow-up","long-arrow-left","long-arrow-right","apple","windows","android","headphones","linux","dribbble","skype","foursquare","trello","female","male","gittip","sun-o","moon-o"," fa-envelope-o","volume-off","archive","bug","vk","weibo","renren","pagelines","stack-exchange","arrow-circle-o-right","arrow-circle-o-left","toggle-left","volume-down","caret-square-o-left","dot-circle-o","wheelchair","vimeo-square","turkish-lira","try","plus-square-o","space-shuttle","slack","envelope-square","volume-up","wordpress","openid","institution","bank","university","mortar-board","graduation-cap","yahoo","google","reddit","qrcode","reddit-square","stumbleupon-circle","stumbleupon","delicious","digg","pied-piper","pied-piper-alt","drupal","joomla","language","barcode","fax","building","child","paw","spoon","cube","cubes","behance","behance-square","steam","tag","steam-square","recycle","automobile","car","cab","taxi","tree","spotify","deviantart","soundcloud","tags","database","file-pdf-o","file-word-o","file-excel-o","file-powerpoint-o","file-photo-o","file-picture-o","file-image-o","file-zip-o","file-archive-o","book","file-sound-o","file-audio-o","file-movie-o","file-video-o","file-code-o","vine","codepen","jsfiddle","life-bouy","life-buoy","bookmark","life-saver","support","life-ring","circle-o-notch","ra","rebel","ge","empire","git-square","git","print","hacker-news","tencent-weibo","qq","wechat","weixin","send","paper-plane","send-o","paper-plane-o","history"," fa-heart","camera","circle-thin","header","paragraph","sliders","share-alt","share-alt-square","bomb","soccer-ball-o","futbol-o","tty","font","binoculars","plug","slideshare","twitch","yelp","newspaper-o","wifi","calculator","paypal","google-wallet","bold","cc-visa","cc-mastercard","cc-discover","cc-amex","cc-paypal","cc-stripe","bell-slash","bell-slash-o","trash","copyright","italic","at","eyedropper","paint-brush","birthday-cake","area-chart","pie-chart","line-chart","lastfm","lastfm-square","toggle-off","text-height","toggle-on","bicycle","bus","ioxhost","angellist","cc","shekel","sheqel","ils","meanpath","text-width","align-left","align-center","align-right","align-justify"," fa-star","list","dedent","outdent","indent","video-camera","photo","image","picture-o","pencil","map-marker"," fa-star-o","adjust","tint","edit","pencil-square-o","share-square-o","check-square-o","arrows","step-backward","fast-backward","backward"," fa-user","play","pause","stop","forward","fast-forward","step-forward","eject","chevron-left","chevron-right","plus-circle"," fa-film","minus-circle","times-circle","check-circle","question-circle","info-circle","crosshairs","times-circle-o","check-circle-o","ban","arrow-left");
        var settings = $.extend({
        	
        }, options);
        return this.each( function() {
        	element=this;
            if(!settings.buttonOnly && $(this).data("iconPicker")==undefined ){
            	$this=$(this).addClass("form-control");

            	$wraper=$("<div></div>");
                $wraper.attr("class","input-group");
            	$this.wrap($wraper);

            	$button=$("<span class=\"input-group-addon pointer\"><i class=\"glyphicon  glyphicon-picture\"></i></span>");
            	$this.after($button);
            	(function(ele){
	            	$button.click(function(){
			       		createUI(ele);
			       		showList(ele,icons);
	            	});
	            })($this);

            	$(this).data("iconPicker",{attached:true});
            }
        
	        function createUI($element){
	        	$popup=$('<div></div>').addClass('icon-popup').css({
                    'top':$element.offset().top+$element.outerHeight()+6,
                    'left':$element.offset().left
                });

	        	$popup.html('<div class="ip-control"> \
						          <ul> \
						            <li><a href="javascript:;" class="btn" data-dir="-1"><span class="fa fa-fw fa-fast-backward"></span></a></li> \
						            <li><input type="text" class="ip-search fa fa-fw fa-search" placeholder="Search" /></li> \
						            <li><a href="javascript:;"  class="btn" data-dir="1"><span class="fa fa-fw fa-fast-forward"></span></a></li> \
						          </ul> \
						      </div> \
						     <div class="icon-list"> </div> \
					         ').appendTo("body");
	        	
	        	
	        	$popup.addClass('dropdown-menu').show();
				$popup.mouseenter(function() {  mouseOver=true;  }).mouseleave(function() { mouseOver=false;  });

	        	var lastVal="", start_index=0,per_page=30,end_index=start_index+per_page;
	        	$(".ip-control .btn",$popup).click(function(e){
	                e.stopPropagation();
	                var dir=$(this).attr("data-dir");
	                start_index=start_index+per_page*dir;
	                start_index=start_index<0?0:start_index;
	                if(start_index+per_page<=549){
	                  $.each($(".icon-list>ul li"),function(i){
	                      if(i>=start_index && i<start_index+per_page){
	                         $(this).show();
	                      }else{
	                        $(this).hide();
	                      }
	                  });
	                }else{
	                  start_index=540;
	                }
	            });
	        	
	        	$('.ip-control .ip-search',$popup).on("keyup",function(e){
	                if(lastVal!=$(this).val()){
	                    lastVal=$(this).val();
	                    if(lastVal==""){
	                    	showList(icons);
	                    }else{
	                    	showList($element, $(icons)
							        .map(function(i,v){ 
								            if(v.toLowerCase().indexOf(lastVal.toLowerCase())!=-1){return v} 
								        }).get());
						}
	                    
	                }
	            });  
	        	$(document).mouseup(function (e){
				    if (!$popup.is(e.target) && $popup.has(e.target).length === 0) {
				        removeInstance();
				    }
				});

	        }
	        function removeInstance(){
	        	$(".icon-popup").remove();
	        }
	        function showList($element,arrLis){
	        	$ul=$("<ul>");
	        	
	        	for (var i in arrLis) {
	        		$ul.append("<li><a href=\"#\" title="+arrLis[i]+"><span class=\"fa fa-fw fa-"+arrLis[i]+"\"></span></a></li>");
	        	};

	        	$(".icon-list",$popup).html($ul);
	        	$(".icon-list li a",$popup).click(function(e){
	        		e.preventDefault();
	        		var title=$(this).attr("title");
	        		$element.val("fa fa-fw fa-"+title);
	        		removeInstance();
	        	});
	        }

        });
    }

}(jQuery));
