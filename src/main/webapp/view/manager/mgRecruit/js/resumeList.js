$(function(){
	setMenuHighlight(2171);
	loadList();
	
	// 全选
	$('#check_all').click(function(){
		var checked = $(this).prop('checked');
		$('#dataView :checkbox').each(function(){
			$(this).prop('checked', checked);
		});
	});
	//新增
	$('.add_pro').click(function(){
		location.href = 'links_add.html';
	});
	//删除
	$('.delete').click(function(){
		var ids = [];
		$('#dataView dd :checkbox:checked').each(function(i, v){
			var id = $(':hidden[name=linksId]',$(this).closest('dd')).val();
			ids.push({name:'linksIds',value:id});
		});
		if(ids.length==0){
			dynamicAlert('请选择要删除的链接');
			return;
		}
		dynamicConfirm("您要删除所选吗？","提示",function(res){
			if(res){
				$.ajax({
					url: '/lgcpsp.web/mvc/links/delete.json',
					type:'POST',
					data: ids,
					dataType:'json',
					success:function(data){
						if (data.result.success) 
						{
							var params = {
									content : data.result.msg,
									title : '提示',
									isShowCancel:false
							};
							dynamicHighConfirm(params, function(c) {
								loadList();
							});
						} else 
						{
							dynamicAlert(data.result.msg);
						}
					}
				});
			}
		});
	});
});
/**
 * 加载链接列表
 */
function loadList(pageIndex,pageSize){
	pageIndex = pageIndex ? pageIndex : 1;
	pageSize = pageSize ? pageSize : 10;
	$('input[name=page]','#searchForm').val(pageIndex);
	$('input[name=rows]','#searchForm').val(pageSize);
	var params = $('#searchForm').serializeArray();
	//显示遮罩
	//$(".data3").showLoading();
	$.ajax({
			url :'/zpApp/job/queryJobList.json',
			data: params,
			dataType:'json',
			async: false,
			type:'POST',
			success:function(rst){
				//去遮罩
				//$('.data3').hideLoading();
				$('#dataView dd').remove();
				$('#pagination').empty();
			
		    var ddHtml = '<dd><span style="width:98%;text-align:center">没有符合条件的查询结果</span></dd>';
			if(rst && rst.total>0){
				$.each(rst.data,function(i, v){
					ddHtml = '<dd>'
						+ '<input type="hidden" name="id" value="'+ v.id +'"/>'
						+ '<input type="hidden" name="sortTime" value="'+ formatTime(v.sortTime,'yyyy-MM-dd hh:mm:ss') + '"/>'
						+ '<span class="t_c info5_1" style="width:2%;"><input type="checkbox" class="f_r m_t_20" /></span>'
						+ '<span class="t_c info5_2" style="width:5%;">' + (i + 1) + '</span>'
						+ '<div class="t_l info5_3 title" style="text-align:center;float:left; width:17%;height:40px;">' + v.jobTitle + '</div>'
						+ '<div class="t_c info5_4 title" style="text-align:center;float:left; width:8%;height:40px;">' +v.jobTypeId+ '</div>'
						+ '<div class="t_c info5_4 title" style="text-align:center;float:left; width:10%;height:40px;">' +v.cityId+ '</div>'
						+ '<div class="t_c info5_4 title" style="text-align:center;float:left; width:10%;height:40px;">' +formatTime(v.publishTime,'yyyy-MM-dd')+ '</div>'
						+ '<div class="t_c info5_4 title" style="text-align:center;float:left; width:12%;height:40px;">张三</div>'
						+ '<div class="t_c info5_4 title" style="text-align:center;float:left; width:5%;height:40px;">' +v.pageViews+ '</div>'
						+ '<div class="t_c info5_4 title" style="text-align:center;float:left; width:8%;height:40px;"><a href="">11</a></div>'
						+ '<span class="t_c info5_13" style="width:15%;" ><ul>'
						+ '<li style="float:left; width:38px;"><a class="view" href="javascript:;"onclick="view(this);">查看</a></li>'
						+ '<li style="float:left; width:26px;"><a class="del" href="javascript:;"onclick="del(this);">删除</a></li>'
						+ '<li style="float:left; width:50px;"><a class="del" href="javascript:;"onclick="del(this);">取消展示</a></li>'
						+ '</ul></span>'
						+'</dd>'
						$('#dataView').append($(ddHtml));
					
				});
				$("#pagination").pagination(rst.total,{
					page_index : pageIndex - 1,
					page_size : pageSize,
					stat_show_always: true,
					pageSize_show_always:false,
					callback:function(pIndex,pSize,jq){
						loadList(pIndex + 1,pSize);
					}
				});
				$('.title',$('#dataView')).each(function(){
					$(this).attr('title', $(this).text());
				});
			}else{
				$('#dataView').append(ddHtml);
			}
			//ifrheight();
			$('.check_all input').prop('checked',false);
		},
		error:function(){
			//去除遮罩
			$(".data3").hideLoading();
		}
	});
}

/**
 * 修改
 * @param 
 */
function update(ele){
	var id = $(ele).closest('dd').find('input[name=linksId]').val();
	location.href = 'links_update.html?id='+id;
}

/**
 * 置顶
 * @param 
 */
function stick(ele){
	var linksId = $(ele).closest('dd').find('input[name=linksId]').val();
	$.ajax({
		url :'/lgcpsp.web/mvc/links/stick.json',
		Type:'POST',
		data:{
			linksId:linksId
		},
		dataType:'json',
		success:function(data){
			loadList();
		}
	});
}

/**
 * 下移
 * @param ele
 */
function down(ele){
	var linksId = $(ele).closest('dd').find('input[name=linksId]').val();
	var linksIdNext = $(ele).closest('dd').next().find('input[name=linksId]').val();
	var sortTimeBefore = $(ele).closest('dd').find('input[name=sortTime]').val();
	var sortTimeNext = $(ele).closest('dd').next().find('input[name=sortTime]').val();
	
	//显示遮罩
	$("html").showLoading();
	$.ajax({
		url: '/lgcpsp.web/mvc/links/change.json',
		type: 'POST',
		data: 
		{
			sortTime:sortTimeNext,
			linksId:linksId
		},
		dataType: 'json',
		success: function(data) 
		{
			if (data.success) 
			{
				$.ajax({
					url: '/lgcpsp.web/mvc/links/change.json',
					type: 'POST',
					data: 
					{
						sortTime:sortTimeBefore,
						linksId:linksIdNext,
					},
					dataType: 'json',
					success: function(data) 
					{
						//去除遮罩 
						$("html").hideLoading();
						loadList($('input[name=page]', '#searchForm').val(),$('input[name=rows]', '#searchForm').val());
					}
				});
			} 
		}
	});
}

/**
 * 上移
 * @param ele
 */
function up(ele){
	var linksId = $(ele).closest('dd').find('input[name=linksId]').val();
	var linksIdPrev = $(ele).closest('dd').prev().find('input[name=linksId]').val();
	var sortTime = $(ele).closest('dd').find('input[name=sortTime]').val();
	var sortTimePrev = $(ele).closest('dd').prev().find('input[name=sortTime]').val();
	//显示遮罩 
	$("html").showLoading();
	$.ajax({
		url: '/lgcpsp.web/mvc/links/change.json',
		type: 'POST',
		data: 
		{
			sortTime:sortTimePrev,
			linksId:linksId
		},
		dataType: 'json',
		success: function(data) 
		{
			if (data.success) 
			{
				$.ajax({
					url: '/lgcpsp.web/mvc/links/change.json',
					type: 'POST',
					data: 
					{
						sortTime:sortTime,
						linksId:linksIdPrev,
					},
					dataType: 'json',
					success: function(data) 
					{
						//去除遮罩 
						$("html").hideLoading();
						loadList($('input[name=page]', '#searchForm').val(),$('input[name=rows]', '#searchForm').val());
					}
				});
			} 
		}
	});
}
