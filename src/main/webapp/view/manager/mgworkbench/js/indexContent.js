var userType = 0;
$(function(){
	$(".zx_a").click(function(event) {
		$(this).addClass('zx_a_on').siblings().removeClass('zx_a_on');     
    });
	
	//加载当前日期
	getNowDate();
	
	//加载省份城市
	findWeather();
	
	// 首页快捷跳转
	fasterForward();
	//var uid = $(window.parent.document).find('#uid').val();
	//loadUser(uid);
	
	//加载我的消息
	queryMyMessage();
	
});

/**
 * 首页快捷跳转
 */
function fasterForward() {
	var user = getAccountInfo();//获取当前登录人的信息
	// 通知公告
	$('#contentForward').click(function(){
		location.href = '/lgcpsp.web/view/mginformation/information.html';
	});
	// 企业诉求
	$('#companyForward').click(function(){
		location.href = '/lgcpsp.web/view/mgCompany/question_list.html';
	});
	// 产业政策
	$('#questionForward').click(function(){
		location.href = '/lgcpsp.web/view/mgPolicy/policy_list.html';
	});
	// 专家库
	$('#analysisForward').click(function(){
		location.href = '/lgcpsp.web/view/mgExpert/expert_list.html';
	});
}

function queryMyMessage(){
	$.ajax({
		type : 'post',
		url : '/lgcpsp.web/mvc/messageMgt/listAll.json',
		//async:false,
		dataType : 'json',
		beforeSend : function(xhr) {
			$('#mymessage').empty();
		},
		success : function(result) {
			if(!result || result.unreadTotal == 0){
				if(userType==0){					
					$('#mymessage').attr('class','desktop4_empty');
					$('<img class="f_l" alt="" src="/lgcpsp.web/images/portal/empty_info.png"><div class="f_r"> 您暂时没有未读信息 </div>').appendTo($('#mymessage'));
				}else{
					$('#mymessage_qg').attr('class','desktop4_empty').css({"margin-left":"226px"});
					$('<img class="f_l" alt="" src="/lgcpsp.web/images/portal/empty_info.png"><div class="f_r"> 您暂时没有未读信息 </div>').appendTo($('#mymessage_qg'));
				}
				return;
			}
			var data = result.data, ul = $('<ul/>');
			$.each(data, function(i, item) {
				var liObj = $('<li/>');
				liObj.append('<input type="hidden" name="msgId" value="' + item.msgId + '" />'
						+ '<input type="hidden" name="msgStatus" value="' + item.msgStatus + '" />'
						+ '<input type="hidden" name="linkUrl" value="' + item.linkUrl + '" />'
						+ '<input type="hidden" name="companyMsg" value="' + item.companyMsg + '" />'
						+ '<input type="hidden"  name="msgType" value="' + item.msgType + '" />')
					.append($('<span class="f_l desktop4_1 m_l_10">'+msgTypeFomart(item)+'</span>'))
					.append($('<span class="f_l m_l_10 t_w_h"><a href="javascript:;" onclick="viewMsg(this);"  title="'+item.msgContent+'">'+item.msgContent+'</a></span>'))
					.append($('<span class="f_r m_r_10"><a href="javascript:;">'+formatTime(item.recTime, '')+'</a></span>'))
					.appendTo(ul);
			});
			if(userType==0){		
				$('.index1_2_span2').text(result.unreadTotal);
				$('#mymessage').attr('class','desktop4');
				ul.appendTo($('#mymessage'));
			}else{
				$('#msg_qg_total').text(result.unreadTotal);
				$('#mymessage_qg').attr('class','desktop4');
				ul.appendTo($('#mymessage_qg'));
			}
			ifrHeight();
		},
		error : function() {
			if(userType==0){					
				$('#mymessage').attr('class','desktop4_empty');
				$('<img class="f_l" alt="" src="/lgcpsp.web/images/portal/empty_info.png"><div class="f_r"> 消息加载异常</div>').appendTo($('#mymessage'));
			}else{
				$('#mymessage_qg').attr('class','desktop4_empty').css({"margin-left":"226px"});
				$('<img class="f_l" alt="" src="/lgcpsp.web/images/portal/empty_info.png"><div class="f_r"> 消息加载异常</div>').appendTo($('#mymessage_qg'));
			}
			return;
		}
	});
}

/**
 * 1：企业诉求  2：通知公告  3：资金公告 4:产业政策 5:旧改政策 6:人才政策 7:专家库
 */
function msgTypeFomart(item){
	var msgType = '';
	if(item.msgType==1){
		msgType = '企业诉求';
	}else if(item.msgType==2){
		msgType = '通知公告';
	}else if(item.msgType==3){
		msgType = '资金公告';
	}else if(item.msgType==4){
		msgType = '产业政策';
	}else if(item.msgType==5){
		msgType = '旧改政策';
	}else if(item.msgType==6){
		msgType = '人才政策';
	}else if(item.msgType==7){
		msgType = '专家库';
	}
	return msgType;
}

/**
 * 查看消息
 * @param ele
 */
function viewMsg(ele) {
	var id = $(ele).closest('li').find('input[name=msgId]').val();
	var status = $(ele).closest('li').find('input[name=msgStatus]').val();
	var linkUrl = $(ele).closest('li').find('input[name=linkUrl]').val();
	//var msgType = $(ele).closest('li').find('input[name=msgType]').val();
	//未读
	if (status == 1) {
		var ids = [{name: 'msgIds', value: id}];
		read(ids, linkUrl,ele);
	} 
	//已读
	else if(status == 2 && linkUrl) {
		/*if (msgType == 11) {
			alertMsg(companyMsg);
			return false;
		}
		if (msgType == 1||msgType == 7) {
			window.open(linkUrl);
		}else{*/
			location.href = linkUrl;
		//}
	}
}

/**
 * 阅读消息
 * @param ids
 * @param linkUrl
 */
function read(ids, linkUrl,ele) {
	//var msgType = $(ele).closest('li').find('input[name=msgType]').val();
	//var companyMsg = $(ele).closest('li').find('input[name=companyMsg]').val();
	$.ajax({
		url: '/lgcpsp.web/mvc/messageMgt/read.json',
		type: 'POST',
		data: ids,
		dataType: 'json',
		success: function(data) {
		},
		complete: function(){
			/*if (msgType == 11) {
				dynamicAlert(companyMsg,'消息');
				return false;
			}*/
			if (linkUrl) {
				location.href = linkUrl;
			}
		}
	});
}

//获取新浪城市与天气信息
function findWeather() {
    var cityUrl = 'http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js';
    $.getScript(cityUrl, function(script, textStatus, jqXHR) {
        var citytq = remote_ip_info.city ;// 获取城市
        $('#provinceCity').html(citytq); 
        var url = "http://php.weather.sina.com.cn/iframe/index/w_cl.php?code=js&city=" + citytq + "&day=0&dfc=3";
        $.ajax({
            url : url,
            dataType : "script",
            scriptCharset : "gbk",
            success : function(data) {
                var _w = window.SWther.w[citytq][0];
                var weatherImg = judgingWeather(_w.s1||_w.s2);

                $('#weather').html(_w.s1);
                $('#weatherImg').attr("src","../../../images/manager/"+weatherImg+".png");
                $('#temperature').html(_w.t1 + "℃/" + _w.t2 + "℃  ");
            }
        });
    });
}

/**
 * 弹出企业消息
 * @param msg
 */
/*function alertMsg(msg){
	 var contents = '<div class="controlScore"><center>'
		 +'<textarea readonly="" style="border:0px;text-align: center;margin-top: 10px;'
		 +'line-height: 25px;" maxlength="100" rows="6" cols="45">'+msg+'</textarea></center></div>';
 

 var setting = {
		title: '消息通知',
		content: $(contents),
		css: {'height': '295px'}
	},
	obj = dynamicPopDiv(setting),
	dialog = obj.dialog,
	mark = obj.dialog.attr("mark");

obj.btnOk.click(function(){
	closeCoverDiv($('div[mark="' + mark + '"]', top.document));
});

obj.btnCancel.hide();
}*/
/*
function check(obj){
	if("1" == obj){//按季
		$("#selectQ").css('display','block');
		$("#selectM").css('display','none');
		$("#selectW").css('display','none');
	}else if("2" == obj){//按月
		$("#selectQ").css('display','none');
		$("#selectM").css('display','block');
		$("#selectW").css('display','none');
	}else if("3" == obj){//按周
		$("#selectQ").css('display','none');
		$("#selectM").css('display','none');
		$("#selectW").css('display','block');
	}else{
		$("#selectQ").css('display','none');
		$("#selectM").css('display','none');
		$("#selectW").css('display','none');
		typeStats();
		// 问题解决情况 
		streetQuestionStats();
	}
	ifrHeight();
}*/

/*function funcweek(){
	$dp.$('week').value=$dp.cal.getP('y')+$dp.cal.getP('W','WW');
	$('#weekHd').val($dp.cal.getP('W','WW'));
	$('#showWeek').html($dp.cal.getP('W','WW'));
}*/
/*
function queryStatis(obj){
	if("1" == obj){//按季
		var year=$('#year').val();
		var month=$('#month').val();
		if('' == year){
			dynamicAlert('请选择年份');
			return;
		}
		typeStats(obj,year,month);
		// 问题解决情况 
		streetQuestionStats(obj,year,month);
	}else if("2" == obj){//按月
		var date=$('#date').val();
		if('' == date){
			dynamicAlert('请选择年月');
			return;
		}
		var year=$.trim(date).substr(0,4);
		var month=$.trim(date).substr(4);
		typeStats(obj,year,month);
		// 问题解决情况 
		streetQuestionStats(obj,year,month);
	}else if("3" == obj){//按周
		var week=$('#week').val();
		if('' == week){
			dynamicAlert('请选择周期');
			return;
		}
		var year=$.trim(week).substr(0,4);
		var month=$('#weekHd').val();
		typeStats(obj,year,month);
		// 问题解决情况 
		streetQuestionStats(obj,year,month);
	}
}*/

/**
 * 问题类型比例分析
 */
/*var beginDate="";
var endDate="";
function typeStats(type,year,month){
	var legendData = [], seriesData = [];
	beginDate="";
	endDate="";
	$.ajax({
		type : 'post',
		url: '/epsp/mvc/questionstatistics/typeStats.json',
		data:{
			   type:type,
			   year:year,
			   month:month
		},
		dataType: 'json',
		success: function(data){
			if (data) {
				beginDate=data.streetQuestionStats.beginDate;
				endDate=data.streetQuestionStats.endDate;
				$.each(data, function(k, v){
					if(v){
						if(v.questionTotal){
							legendData.push(k);
							seriesData.push({value: v.questionTotal, name: v.questionTypeName});
						}
					}
				});
				// 路径配置
		        require.config({
		            paths: {
		                echarts: '/epsp/resources/echarts/build/dist'
		            }
		        });
		    	// 使用
		        require(
		        	[
		                'echarts',
		                'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
		            ],
		            function (ec) {
		        		var ecConfig = require('echarts/config');
		                // 基于准备好的dom，初始化echarts图表
		                var myChart = ec.init(document.getElementById('typeStats')),
		                	option = {
		               	    	title: {
		                	        text: '问题类型比例分析',
		                	        x:'center'
		                	    },
		                	    tooltip: {
		                	        trigger: 'item',
		                	        formatter: "{a}<br/>{b}: {c} ({d}%)"
		                	    },
		                	    legend: {
		                	      	y: 'bottom',
		                	      	selectedMode: false,
		                	        data: legendData
		                	    },
		                	    series: [
		                	        {
		                	            name: '问题类型',
		                	            type: 'pie',
		                	            radius: '55%',
		                	            center: ['50%', '60%'],
		                	            clickable: true,
		                	            data: seriesData
		                	        }
		                	    ]
		                	};
		                //为问题解决情况分析绑定点击事件
		                myChart.on(ecConfig.EVENT.CLICK, viewQuestionByType);
		                // 为echarts对象加载数据 
		                myChart.setOption(option); 
		                //window.onresize = myChart.resize;
		            }
		        );
			}
		}
		
		
	});
}*/

/**
 * 点击问题类型比例分析
 * @param param
 * @returns
 */
/*function viewQuestionByType(param) {
	var loca='/epsp/res/mgquestion/question_hang_list.html?typeName='+param.name;
	if(beginDate != null){
		loca+='&beginDate='+beginDate+'&endDate='+endDate;
	}
	window.location.href=loca;
}*/

/**
 * 问题解决情况
 */
/*function streetQuestionStats(type,year,month){
	var streetId = [] ,streetName = [] ,notSolve = [] , isSolve = [], ratio = [];
	$.ajax({
		type : 'post',
		url: '/epsp/mvc/questionstatistics/streetQuestionStats.json',
		data:{
			   type:type,
			   year:year,
			   month:month
		},
		dataType: 'json',
		success: function(data){
			if (data) {
				$.each(data, function(k, v){
					if(v){
						streetId.push(v.streetId);
						streetName.push(v.streetName);
						notSolve.push(v.notSolve);
						isSolve.push(v.isSolve);
						ratio.push(Math.round(v.ratio));
					}
				});
				// 路径配置
		        require.config({
		            paths: {
		                echarts: '/epsp/resources/echarts/build/dist'
		            }
		        });
		    	// 使用
		        require(
		        	[
		                'echarts',
		                'echarts/chart/line',
		                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
		            ],
		            function (ec) {
		        		var ecConfig = require('echarts/config');
		                // 基于准备好的dom，初始化echarts图表
		                var myChart = ec.init(document.getElementById('questionSolveStats')),
		                option = {
		                	title: {
	                	        text: '问题解决情况分析',
	                	        x:'center'
	                	    },
		                    tooltip : {
		                        trigger: 'axis',
		                        formatter: '{b}<br/>{a1}: {c1}<br/>{a2}: {c2}<br/>{a}: {c}%',
		                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		                        }
		                    },
		                    legend: {
		                    	y: 26,
	                	      	selectedMode: false,
		                        data: ['已解决', '未解决', '问题解决率']
		                    },
		                    dataZoom: {
		                    	orient: "horizontal", //水平显示
		                    	show: true, //显示滚动条
		                    	zoomLock: true,//数据缩放锁，当设置为true时选择区域不能伸缩，即(end - start)值保持不变，仅能做数据漫游。
		                    	start: 0, //起始值为0%
		                    	end: 20,  //结束值为50%
		                    	height: 20,
		                    	fillerColor: 'rgba(144,197,237,0.5)'
	                    	},
		                    xAxis : [
								{	//显示策略，可选为：true（显示） | false（隐藏），默认值为true  
								    //show: false,  
								    type : 'category',
								    axisLabel: {
								    	interval: 0
								    },
								    data : streetName
								}
		                    ],
		                    yAxis : [
								{
		                            type : 'value',
		                            name: '问题数量'
		                        },
		                        {
		                            type : 'value',
		                            name: '问题解决率',
		                            axisLabel : {
		                                formatter: '{value}%'
		                            }
		                        }
		                    ],
		                    series : [
		                        {
		                            name:'已解决',
		                            type:'bar',
		                            stack: '问题解决情况',
		                            clickable: true,
		                            data:isSolve
		                        },
		                        {
		                            name:'未解决',
		                            type:'bar',
		                            stack: '问题解决情况',
		                            clickable: true,
		                            data:notSolve
		                        },
		                        {
		                            name:'问题解决率',
		                            type:'line',
		                            stack: '问题解决情况',
		                            yAxisIndex: 1,
		                            smooth: true,
		                            clickable: true,
		                            data: ratio
		                        }
		                    ]
		                };
		                //为问题解决情况分析绑定点击事件
		                myChart.on(ecConfig.EVENT.CLICK, viewQuestionByName);
		                // 为echarts对象加载数据 
		                myChart.setOption(option);
		                //window.onresize = myChart.resize;
		            }
		        );
			}
		}
	});
}*/

/**
 * 点击问题解决情况分析
 * @param param
 * @returns
 */
/*function viewQuestionByName(param) {
	
	//window.location.href='/epsp/res/mgquestion/question_hang_list.html?streetName='+param.name;
	var loca='/epsp/res/mgquestion/question_hang_list.html?streetName='+param.name;
	if(beginDate != null){
		loca+='&beginDate='+beginDate+'&endDate='+endDate;
	}
	window.location.href=loca;
   
}*/

