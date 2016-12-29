$(function(){
	loadList();
	$('#search').click(function(){
		loadList();
	});
	$('#searchForm').keydown(function(e){  
		if(e.keyCode == 13){  
			loadList();
			return false;
		}
	});
});

/**
 * 加载列表
 */
function loadList(pageIndex, pageSize) {
	pageIndex = pageIndex ? pageIndex : 1;
	pageSize = pageSize ? pageSize : 10;
	$('input[name=page]', '#searchForm').val(pageIndex);
	$('input[name=rows]', '#searchForm').val(pageSize);
	
	var params = $('#searchForm').serializeArray();
	$.ajax({
		url: '/zpApp/sysConfig/list.json',
		data: params,
		dataType: 'json',
		success: function(rst) {
			$('#dataView tbody').remove();
			$("#pagination").empty();
			
			var trHtml = '<tr><td colspan="6" style="text-align: center;">没有符合条件的查询结果</td></tr>';
			if (rst && rst.total > 0) {
				$.each(rst.data, function(i, v){
					trHtml = '<tr>'
						+ '<input type="hidden" name="keyId" value="' + v.keyId + '" />'
						+ '<input type="hidden" name="keyType" value="' + v.keyType + '" />'
						+ '<td align="center">' + (i + 1) + '</td>'
						+ '<td class="title">' + v.keyName + '</td>'
						+ '<td class="title">' + formatterValue(v.keyType,v.keyValue1,v.keyValue2,v.keyValue3,v.keyValue4) + '</td>'
						+ '<td class="title">' + v.keyDesc + '</td>'
						+ '<td align="left">' + v.edtTime + '</td>'
						+ '<td align="left"><a href="javascript:;" class="upa" onclick="edit(this);">修改</a></td>'
						+ '</tr>';
					$('#dataView').append(trHtml);
				});
				$("#pagination").pagination(rst.total, {
					page_index: pageIndex - 1,
					page_size: pageSize,
					stat_show_always: true,
					pagesize_show_always: false,
					callback: function(pIndex, pSize, jq){
						loadList(pIndex + 1, pSize); 
					}
				});
				$('#dataView .title').each(function(){
					$(this).attr('title', $(this).text());
				});
			} else {
				$('#dataView').append(trHtml);
			}
			ifrHeight();
		}
	});
}

/**
 * 修改
 * @param ele
 */
function edit(ele) {
	var keyId = $(ele).closest('tr').find('input[name=keyId]').val(),
		keyType = $(ele).closest('tr').find('input[name=keyType]').val(),
		keyName = $(ele).closest('tr').find('td:eq(1)').text(),
		keyValue = $(ele).closest('tr').find('td:eq(2)').text(),
		keyDesc = $(ele).closest('tr').find('td:eq(3)').text(),
		content = '<div class="controlScore"><ul><li><label style="text-align: right;">配置项名称：</label>'
			+ keyName + '</li><li><label style="text-align: right;">配置项值：</label>'
			+ '<input type="text" class="txt" name="keyValue1" style="width: 250px;" maxlength="200"/>'
			+ '<input type="text" class="txt" name="keyValue2" style="width: 250px; display:none;" maxlength="10"/>'
			+ '<input type="text" class="txt Wdate" name="keyValue3" style="width: 218px; display:none;" onfocus="WdatePicker({skin:\'whyGreen\', readOnly:true});"/>'
			+ '<textarea name="keyValue4" style="vertical-align: top; display:none;"></textarea>'
			+ '<label id="valTip"></label></li>'
			+ '<li><label style="text-align: right;">描述：</label><textarea name="keyDesc" maxlength="100" style="vertical-align: top;">' + keyDesc + '</textarea>'
			+ '<label id="descTip" style="margin-top: 2px; left: 100px;"></label></li></ul></div>',
	 	setting = {
			id: 'modify-pass',
			title: "修改系统配置项",
			key: 'keyId',
			css: {height: '300', width: '450'},
			content: $(content)
		},
		obj = dynamicPopDiv(setting),
		mark = obj.dialog.attr("mark"),
		$keyVal1 = $('input[name=keyValue1]', obj.form),
		$keyVal2 = $('input[name=keyValue2]', obj.form),
		$keyVal3 = $('input[name=keyValue3]', obj.form),
		$keyVal4 = $('textarea[name=keyValue4]', obj.form),
		$keyValObj = $keyVal1;
	
	switch (keyType) {
	case '1':
		$keyVal1.val(keyValue);
		$keyValObj = $keyVal1;
		break;
	case '2':
		$keyVal1.hide();
		$keyVal2.show().val(keyValue);
		$keyValObj = $keyVal2;
		break;
	case '3':
		$keyVal1.hide();
		$keyVal3.show().val(keyValue);
		$keyValObj = $keyVal3;
		break;
	case '4':
		$keyVal1.hide();
		$keyVal4.show().val(keyValue);
		$keyValObj = $keyVal4;
		obj.dialog.height('380');
		break;
	default:
		$keyVal1.prop('disabled', true);
		break;
	}
	
	obj.btnOk.click(function(){
		$('#valTip', obj.form).removeClass().empty();
		$('#descTip', obj.form).removeClass().empty();
		
		trimText(obj.form);
		var isValid = true,
			keyVal = $keyValObj.val(),
			desc = obj.form.find('textarea[name=keyDesc]').val();
		if (!keyVal) {
			isValid = false;
			$('#valTip', obj.form).addClass('error').text('请输入配置项值');
		}
		if (desc && desc.length > 100) {
			isValid = false;
			$('#descTip', obj.form).addClass('error').text('最多100位字符').show();
		}
		if (isValid) {
			obj.key.val(keyId);
			var params = obj.form.serializeArray();
			$.ajax({
				url: '/lgcpsp.web/mvc/sysConfig/update.json',
				type: 'POST',
				data: params,
				dataType: 'json',
				success: function(data) {
					if (data.result.success) {
						loadList();
					} else {
						dynamicAlert(data.result.msg);
					}
				}
			});
			closeCoverDiv($('div[mark="' + mark + '"]', top.document));
		}
	});
	obj.btnCancel.click(function(){
		closeCoverDiv($('div[mark="' + mark + '"]', top.document));
	});
}

/**
 * 返回配置項值
 * @param keyType配置值（1:字符串 2：数字 3：时间 4：大字段）,keyValue1,keyValue2,keyValue3,keyValue4
 */
function formatterValue(keyType,keyValue1,keyValue2,keyValue3,keyValue4) {
	var rst = '';
	switch (keyType) {
		case "1":
			rst = keyValue1;
			break;
		case "2":
			rst = keyValue2;
			break;
		case "3":
			rst = keyValue3;
			break;
		case "4":
			rst = keyValue4;
			break;
		default:
			break;
	}
	return rst;
}
