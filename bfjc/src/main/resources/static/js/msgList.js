var ccateMap={};
layui.use(['form','table'], function() {
	var table = layui.table;
	var form = layui.form;
	var $ = layui.$;


	ccateMap['msgType']={'ACDMDSResponse':'航班消息','ACDMDSStatusResponse':'心跳消息'};
	ccateMap['status']={'0':'待解析','1':'已解析','2':'状态信息','9':'解析失败'};
	
	 for(var key in ccateMap){
		var codesMap=ccateMap[key];
		for(var code in codesMap){			
			$("select[name='"+key+"']").append("<option value='"+code+"'>"+codesMap[code]+"</option>");
			
		}	
	 } 
	 
	form.render("select"); 
	var tableMsg = table.render({
		elem : '#msgList',
		url : '../msg/msgStoreList',
		response : {
			statusCode : 200, // 成功的状态码，默认：0
			msgName : 'message' // 状态信息的字段名称，默认：msg
		},
		cols : [ [ {
			field : 'id',
			title : '消息ID',
			width : 80
		}, {
			field : 'msgType',
			title : '消息类型',
			width : 80,
			templet: '#msgTypeCode'
		}, {
			field : 'msgTime',
			title : '消息源时间',
			width : 150
		}, {
			field : 'receiveTime',
			title : '接收时间',
			width : 150
		}, {
			field : 'parseTime',
			title : '解析时间',
			width : 150
		}, {
			field : 'status',
			title : '消息状态',
			width : 80,
			templet: '#statusCode'
		}, {
			field : 'sender',
			title : '消息来源',
			width : 80
		}, {
			field : 'seqNum',
			title : '消息序列号',
			width : 100
		}, {
			field : 'flightIdentity',
			title : '航班号',
			width : 100
		}, {
			field : 'flightDirection',
			title : '航向',
			width : 80
		}, {
			field : 'scheduledDate',
			title : '计划日期',
			width : 100,
		}, {
			field : 'flightRepeatCount',
			title : '航班重复号',
			width : 100
		}, {
			fixed : 'right',
			width : 100,
			title : '操作',
			align : 'center',
			toolbar : '#recToolbar'
		} ] ],
		// initSort: {field:'id', type:'desc'},
		// cellMinWidth: 120,
		// height : 'full',
		page : true,
		limit : 10,
		skin : 'row',
		even : true,
		size : 'sm'
	});

	table.on('tool(lay_msgList)', function(obj) {
		var data = obj.data;

		if (obj.event == 'view') {// 编辑

			layer.open({				
				title : '查看ID为[' + data.id + ']的信息内容',
				content : "<textarea readonly  class='layui-textarea-sm' style='width:560px;height:400px'>"+data.msgContent+"</textarea>",
				 area:['600px','540px'],
				 shade: [0.8, '#393D49'],
				 maxmin: true,         
				 shadeClose:true,
				 zIndex: layer.zIndex //重点1
			});

		}else{
			if (obj.event == 'parse') {// 编辑

				$.getJSON('../msg/panel/dsParse/'+data.id, function(rs){
					if(rs.code=="200"){
						layer.alert(rs.message, {title: '解析操作'});
					}else{
						layer.alert('失败', {title: '解析操作'}); 
					}
					   
				});	
				
			} 
		} 
	});

	var $ = layui.$, active = {
		requery : function() {
			// 执行重载
			tableMsg.reload({
				page : {
					curr : 1
				// 重新从第 1 页开始
				},
				where : {
					id : $("input[name='id']").val(),
					msgType : $("select[name='msgType']").val(),
					status : $("select[name='status']").val()

				}
			});
		}

	};

	$("button[name='requery']").on('click', function() {
		var type = $(this).data('type');
//		console.log(type);
		active[type] ? active[type].call(this) : '';
	});

});