$(function(){
	var result = getAccountInfo();
	if(result){		
		$("#uid").val(result.id);
		$("#adminname").text(result.username);
	}else{
		window.parent.location = '/zpApp/view/manager/mgworkbench/login.html';
	}
    //获取权限数据,加载菜单
	loadMenu();
	var sendUrl=$.getUrlParam('sendUrl');
	var objId = $.getUrlParam('objId');
	if(sendUrl){
		window.frames["main_iframe"].location = sendUrl;
		setMenuHighlight(objId);
	}
});

//加载菜单
function loadMenu(){
	 $("#menuList li a").live("click",function(){
		   if($(this).siblings("ul").first().css("display")!=undefined)
		   {
			   if($(this).siblings("ul").first().css("display")=="none")
				   {
  				   $(this).find("span").removeClass();
      			   $(this).find("span").attr("class","showChange");
      			   $(this).find("span").siblings("em").removeClass();
      			   $(this).find("span").siblings("em").attr("class","emChange");
      			   $(this).siblings("ul").show();
				   }else{
  					   $(this).find("span").removeClass();
  					   $(this).find("span").attr("class","showChildren");
  					   $(this).find("span").siblings("em").removeClass();
  					   $(this).find("span").siblings("em").attr("class","em");
  					   $(this).siblings("ul").hide();
				   }
		   }
		   ifrHeight();
	   });
	  app.init();
};

function loadUser(uid){
	var data;
	if(uid){
		//查询用户信息
		$.ajax({
			url:'/sc/mvc/user/api/user/getUser.json',
			data:{"id":uid},
			type:'post',
			dataType:'json',
			async : false,
			success:function(r){
				if(r.worker!=null){
					if(r.worker.extInfo['qiangan']==1){
						$('.top').css({"background": "#060840"});
						$('#logo').attr("src","../../resources/images/manager/admin_logo_qg.png");
						$('.top_bg').css({"background": "url(../../resources/images/manager/Banner.jpg) no-repeat"});
						$('#ishang').val('1');
					}else{
						$('#ishang').val('0');
					}
				}else{
					$('#ishang').val('0');
				}
				data = r;
			}
		});
	}
	return data;
};


/**
 * 退出系统
 */
function loginOut(){
	$.ajax({
		url:'/zpApp/adminUser/logout.json',
		type:'post',
		dataType:'json',
		cache : false,
		success:function(r){
			window.parent.location = '/zpApp/view/manager/mgworkbench/login.html';
		},
		error:function(){
			dynamicAlert('系统退出异常',"提示");
		 }
	});
};

/*function nTabs(thisObj,Num,type,struId,structureID){
	loadStruUserDataOrg(type,struId,structureID); 
	return;
	if(thisObj.className == "active")return;
	var tabObj = thisObj.parentNode.id;
	var tabList = document.getElementById(tabObj).getElementsByTagName("dd");
	for(i=0; i <tabList.length; i++)
	{
	  if (i == Num)
	  {
	   $('#active'+i).attr('class','active'); 
	   //document.getElementById(tabObj+"_Content"+i).style.display = "block";
	  }else{
		  $('#active'+i).attr('class','normal');; 
	   //document.getElementById(tabObj+"_Content"+i).style.display = "none";
	  }
	} 
}*/


/*function nTabs1(thisObj,Num,type,struId){
	loadStruUserData(type,struId);
	return;
	if(thisObj.className == "active")return;
	var tabObj = thisObj.parentNode.id;
	var tabList = document.getElementById(tabObj).getElementsByTagName("dd");
	for(i=0; i <tabList.length; i++)
	{
	  if (i == Num)
	  {
	   $('#active'+i).attr('class','active'); 
	   //document.getElementById(tabObj+"_Content"+i).style.display = "block";
	  }else{
		  $('#active'+i).attr('class','normal');; 
	   //document.getElementById(tabObj+"_Content"+i).style.display = "none";
	  }
	} 
}*/





