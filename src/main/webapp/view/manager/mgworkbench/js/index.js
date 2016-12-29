/**
 * Created by liurongqing on 15/1/4.
 */
var app = function () {

	function default_menu_first() {
		$(".container .main_left ul li a:first").addClass("menu_click");
	}

	function menu_animate() {
		$(".container .main_left ul li a").hover(function () {
			if($(this).has("ul").length > 0) return;
			if (!$(this).hasClass("menu_click")) {
				$(this).addClass("menu_over");
			}
		}, function () {
			$(this).removeClass("menu_over");
		});
		$(".container .main_left ul li a").click(function () {
			// 如果是点击状态，则不操作
			//if($(this).hasClass("menu_click")) return;
			// 点击之前，别的图标要恢复原状
			$(".container .main_left ul li a img").each(function(i){
				var imgSrc = $(this).attr("src");
				if(imgSrc.search("_hover") != -1){
					var imgOld = $(this).attr("src").replace("_hover","");
					$(this).attr("src",imgOld);
				}
			});
			if($(this).attr('href')=="javascript:void(0);"){
				// 效果显示
				//$(".container .main_left ul li a").removeClass("menu_click");
				$(this).removeClass().addClass("menu_over");
				return;
			}else{
				// 效果显示
				$(".container .main_left ul li a").removeClass("menu_click");
				$(this).removeClass("menu_over").addClass("menu_click");
			}
			// 图标修改
			var img_src = $(this).find("img").attr('src')
			if(typeof img_src != "undefined" && img_src.search(".png") != -1){
				img_src = img_src.replace('.png','_hover.png');
				$(this).find("img").attr('src',img_src);
			}
			// 内容更改
			var data_link = $(this).attr("data-link");
			if (typeof data_link != "undefined" && data_link) {
				$("#main_iframe").attr("src",data_link);
				$(document).scrollTop(0);
				//$("#main_iframe").attr("src", data_link);
			}else{
				//$("#main_iframe").attr("src","./tpl/error.html");
			}
		});
	}

	function iframe_auto(){
		$("#main_iframe").load(function(){
			$("html,body").animate({
				scrollTop:0
			},100);
			var iframeH = 0;
			if(navigator.userAgent.search("MSIE") != -1){
				iframeH = $(this).contents().height();
			}else{
				iframeH = $(this).contents().find("html").height();
			}
			iframeH = iframeH > 300 ? iframeH : 300;
			$(this).css("height",iframeH+"px");

			var leftH = parseInt($(".main_left").css("height"));
			iframeH = iframeH > leftH ? iframeH : leftH;

			$(this).parents(".container").css("height",iframeH+"px");
		});
	}
	
	function dialog_menu(){
		$("#dialog_seleuser .user1").on("click","div",function(){
			var iLen = $(this).index();
			$("#dialog_seleuser .user1 div").removeClass("active");
			$(this).addClass("active");
			if(iLen == 0){
				$(".user3_1,.user3_2").show();
				$(".user5").hide();
			}else{
				$(".user3_1,.user3_2").hide();
				$(".user5").show();
			}
		});
	}

	return {
		// 初始化...
		init: function () {
			//$("#main_iframe").attr("src","./tpl/index1.html");
			$("#main_iframe").attr("src","/zpApp/view/manager/mgRecruit/job_list.html");
			// 默认第一个选中
			default_menu_first();
			// 菜单效果
			menu_animate();
			// iframe
			iframe_auto();
			// 组织结构管理的菜单切换
			dialog_menu();
		}
	}
}();


function loadMenu(url){
	if (typeof url != "undefined" && url) {
		$("#main_iframe").attr("src", url);
		$(document).scrollTop(0);
	}else{
		$("#main_iframe").attr("src","./tpl/error.html");
	}
}
