var opBtns = [], okHtml = '<a class="m_r_10 reg_cal" href="javascript:;">取消</a><a class="reg_ok" href="javascript:;">确定</a>';
var typeId;
$(function() {
	setMenuHighlight(6988);
	loadList(0);
	opBtns.push('<a class="m_r_10 reg_del" href="javascript:;">删除</a>');
	opBtns.push('<a class="reg_edit" href="javascript:;">编辑</a>');
	// 添加类别
	$('.reg_add').click(function(){
		var ddIdx = $('#opDd').siblings().size() == 0 ? 1 : $('#opDd').siblings().size(),
			ddHtml = '<dd><span class="reg_span1 t_c">' + ddIdx + '</span><span class="reg_span2">'
			    + '<input class="input1" type="text" maxlength="50" />'
				+ '</span><span class="reg_span3" name="">' + okHtml + '</span></dd>';
		$('#opDd').before(ddHtml);
		setPageHeight();
	});
	// 确认
	$(".reg1_type").on('click', '.reg_ok', function(){
		var $this = $(this), $dd = $this.closest('dd'), name = $.trim($dd.find(':text').val());
		if (!$.trim(name)) {
			dynamicAlert('类型不能为空');
		} else {
			var isAdd = true, url = 'add.json', params = { typeName: name };
			if ($this.parent().attr('id')) {
				var oldName = $this.parent().attr('name');
				if (name == oldName) {
					$dd.find(':text').addClass('b0').prop('readonly', true);
					$this.parent().html(opBtns.join(''));
					return;
				}
				isAdd = false;
				url = 'update.json';
				params.typeId = $this.parent().attr('id');
			}
			params.classification = $('#classification').val();
			$.ajax({
				url: '/zpApp/jobType/' + url,
				type: 'POST',
				data: params,
				dataType: 'json',
				success: function(data){
					if (data.result.success) {
						if (isAdd) {
							$this.parent().attr('id',data.result.content);
						}
						$this.parent().attr('name', name);
						$dd.find(':text').addClass('b0').prop('readonly', true);
						$this.parent().html(opBtns.join(''));
					} else {
						dynamicAlert(data.result.msg);
					}
				}
			}); 
		}
	});
	// 取消
	$('.reg1_type').on('click', '.reg_cal', function(){
		if ($(this).parent().attr('id')) {
			$(this).closest('dd').find(':text').val($(this).parent().attr('name'));
			$(this).closest('dd').find(':text').addClass('b0').prop('readonly', true);
			$(this).parent().html(opBtns.join(''));
		} else {
			$(this).closest('dd').remove();
			resetNo();
			setPageHeight();
		}
	});
	// 删除
	$('.reg1_type').on('click', '.reg_del', function(){
		var $this = $(this), id = $this.parent().attr('id');
		dynamicConfirm("您要删除专家类别吗？", "提示", function(res){
			if (res) {
				$.ajax({
					url: '/zpApp/jobType/delete.json',
					type: 'POST',
					data: { typeId: id},
					dataType: 'json',
					success: function(data){
						if (data.result.success) {
							$this.closest('dd').remove();
							resetNo();
							setPageHeight();
							dynamicAlert(data.result.msg);
						} else {
							dynamicAlert(data.result.msg);
						}
					}
				}); 
			}
     	});
	});
	// 编辑
	$('.reg1_type').on('click', '.reg_edit', function(){
		$(this).closest('dd').find(':text').removeClass('b0').prop('readonly', false);
		$(this).parent().html(okHtml);
	});
});

/**
 * 设置页面高度
 */
function setPageHeight() {
	ifrHeight();
	$('.reginfo').height(document.body.scrollHeight);
}

/**
 * 重置序号
 */
function resetNo() {
	$('.reg1_type .reg_span1:not(:first)').each(function(i){
		$(this).text(i + 1);
	});
}

/**
 * 加载列表
 */
function loadList(classification){
	if(classification==1)
	{
		$('#putong').removeClass("active");
		$('#qiangan').addClass("active");
	}else
		{
			$('#qiangan').removeClass("active");
			$('#putong').addClass("active");
		}
	$('#classification').val(classification)
	$.ajax({
		url: '/zpApp/jobType/list.json',
		dataType: 'json',
		data:{classification:classification},
		success: function(data){
			$('#opDd').prevAll('dd').remove();
			var ddHtml = '<dd><span style="width:98%;text-align:center">没有符合条件的查询结果</span></dd>';
			if (data) {
				$.each(data, function(i, v){
					ddHtml = '<dd>'
						+ '<span class="reg_span1 t_c">' + (i + 1) + '</span>'
						+ '<span class="reg_span2">'
						+ '<input type="text" class="input1 b0" readonly maxlength="50" oldvalue="'+v.typeName+'" value="' + v.typeName + '" />' 
						+ '</span>'
						+ '<span class="reg_span3" id="' + v.typeId + '" name="' + v.typeName + '">'
						+ opBtns.join('')
						+ '</span></dd>';
					$('#opDd').before(ddHtml);
				});
			} else {
				$('#opDd').before(ddHtml);
			}
			//permissionFilter();
			setPageHeight();
		}
	}); 
}

/*function permissionFilter(){
	var items = new Array();
	$.permissionFilter([
	    {permission:"com.lgcpsp.expert.expertType:select",display:function(status){
	       if(status){
	    	   //loadList();
	       }
	    }},
	    {permission:"com.lgcpsp.expert.expertType:delete",display:function(status){
	       if(!status){
	    	   $('.reg_del').css({"display":"none"});
	       }
		}},
		{permission:"com.lgcpsp.expert.expertType:update",display:function(status){
		       if(!status){
		    	   $('.reg_edit').css({"display":"none"});
		       }
		}},
		{permission:"com.lgcpsp.expert.expertType:add",display:function(status){
			    if(!status){
			    	$('.reg_add').css({"display":"none"});
			    }
		}}
    ],"/lgcpsp.web/mvc/permission/getPermissions.json");
	return items;
}
*/