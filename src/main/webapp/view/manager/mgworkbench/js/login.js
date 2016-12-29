$(function(){
	//绑定刷新验证码事件
	$('#refreshCode').click(function(){
		$(this).attr('src','/zpApp/adminUser/genVerifyCode.json?random='+new Date().getTime());
	});
	//回车事件
	$('#loginForm').keydown(function(e){  
		if(e.keyCode == 13){  
			$('#loginBtn').click();
		}
	});
	$('#loginBtn').click(function(){
		$('#loginBtn').prop('disabled',true);
		$('#loginBtn').val('登录中');
		var username = $("#username").val();
		var password = $.md5($("#password").val());
		var code = $("#code").val();
		$.ajax({
            url: '/zpApp/adminUser/login.json',
            data: {
            	username:username,
            	password:password,
            	code:code
            },
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if(data.result.success){
                	window.parent.location = '/zpApp/view/manager/mgworkbench/index.html';
                }else{
                	$("#error_tips").show();
                	$("#err_m").html(data.result.content);
                	$('#refreshCode').attr('src','/zpApp/adminUser/genVerifyCode.json?random='+new Date().getTime());
                	$('#loginBtn').prop('disabled',false);
                	$('#loginBtn').val('登录');
                }
            }
        });
	});
});