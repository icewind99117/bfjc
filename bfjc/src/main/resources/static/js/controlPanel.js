layui.use(['form'], function(){
  var form = layui.form,layer = layui.layer,$ = layui.$;
  
  $.ajaxSettings.async = false;//同步执行
	$.getJSON('../msg/allApiTag', function(rs){
		if(rs.code=="200"){

			buildConstruction(rs.data);
			  
			  
		}else{
			layer.alert(rs.message, {  
			      title: '加载配置参数信息'  
			}) ; 
		}
		   
	});	
	refreshStatus();
	
	 setInterval(function(){refreshStatus();},30*1000);
	 
	 function refreshStatus(){
			$.getJSON('../msg/panel/dsStatus', function(rs){
				if(rs.code=="200"){

					console.log("dsStatus:"+rs.data);
					$("#apiStatus").html(rs.data);
					  
					  
				}else{
					layer.alert(rs.message, {  
					      title: '加载配置参数信息'  
					}) ; 
				}
				   
			});	
	 }

	form.render();
	
	
	function buildConstruction(data){
		var body="";
		body+="<div class='layui-form-item'>" +
					"<label class='layui-form-label'  style='width: 200px;'>接口状态</label>" +
					"<div class='layui-input-block'>" +
						"<div class='layui-input-inline' style='width: 200px;'>" +
							"<label class='layui-form-label' id='apiStatus'></label>" +
						"</div>" +
						"<div class='layui-input-inline'>" +
						"<button name='subscribeBtn' class='layui-btn layui-btn-sm' type='button' data-type='start'>开始订阅</button>" +
						"<button name='subscribeBtn' class='layui-btn layui-btn-sm' type='button' data-type='stop'>取消订阅</button>" +
						"</div>" +
					"</div>" +
				"</div>";
		for(var i=0;i<data.length;i++){
			var row = data[i];
			body+="<div class='layui-form-item'>" +
					"<label class='layui-form-label'  style='width: 200px;'>"+row['name']+"</label>" +
					"<div class='layui-input-block'>" +
						"<div class='layui-input-inline' style='width: 200px;'>" +
							"<input type='text'  name='"+row['code']+"' class='layui-input' lay-verify='"+row['code']+"' value='"+row['params']+"'>" +
						"</div>" +
						"<div class='layui-input-inline' style='width: 800px;'>" +
							"<label class='layui-form-label-left' >"+row['description']+"</label>" +
						"</div>" +
					"</div>" +
				"</div>";
		
			
		}
	
		$("form.layui-form").prepend(body);
	}
  //自定义验证规则
  form.verify({
	snapshot: function(value){
		var val=value.replace("D","");
		if(isNaN(val) || value.indexOf('D')<-1){
			return '格式错误,单位为天【D】,格式应如:-1D';
		}else{
			if(parseInt(val)<-3 ||parseInt>0){
				return '数字应在-3~0之间';	
			}
		}
      
    },
    update: function(value){
    	var val=value.replace("M","");
		if(isNaN(val) || value.indexOf('M')<-1){
			return '格式错误，单位为分钟【M】,格式应如:-20M';
		}else{
			if(parseInt(val)<-1440 ||parseInt>0){
				return '数字应在-1440~0之间';	
			}
		}
    },
    maxOffline: function(value){
    	var val=value.replace("H","");
		if(isNaN(val) || value.indexOf('M')<-1){
			return '格式错误，单位为小时【H】,格式应如:1H';
		}else{
			if(parseInt(val)>72 ||parseInt<0){
				return '数字应在0~72之间';	
			}
		}
    },
    timestamp: function(value){
    	
        if(value!="" && !checkDateTime(value)){
          return '时间戳格式有误';
        }
    }

  });

  function checkDateTime(str){  
	  var reg = /^(\d+)-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;  
	  var r = str.match(reg);  
	  if(r==null)return false;  
	  r[2]=r[2]-1;  
	  var d= new Date(r[1], r[2],r[3], r[4],r[5], r[6]);  
	  if(d.getFullYear()!=r[1])return false;  
	  if(d.getMonth()!=r[2])return false;  
	  if(d.getDate()!=r[3])return false;  
	  if(d.getHours()!=r[4])return false;  
	  if(d.getMinutes()!=r[5])return false;  
	  if(d.getSeconds()!=r[6])return false;  
	  return true;  
	  }
  //监听提交
  form.on('submit(save)', function(data){
	  
    console.log("POST DATA:"+JSON.stringify(data.field));
    
    $.ajax({
		  type: 'POST',
		  url: '../msg/apiTag',
		  dataType: 'json',
 		  async:false,
		  data: data.field,
		  success: function(rs){
			  if(rs.code=="200"){
				  
				  layer.alert(rs.message, {title: '保存配置操作'}); 
			  }else{
				  layer.alert("失败", {title: '保存配置操作'}) ;
			  }
			  

		  }
		});

    return false;
  });
  
  var active = {
			start : function() {
				$.getJSON('../msg/panel/dsStart', function(rs){
					if(rs.code=="200"){
						layer.alert(rs.message, {title: '订阅操作'});
						refreshStatus();
					}else{
						layer.alert('失败', {title: '订阅操作'}); 
					}
					   
				});	
				
			},
			stop : function(deptCode) {
				$.getJSON('../msg/panel/dsStop', function(rs){
					if(rs.code=="200"){
						layer.alert(rs.message, {title: '订阅操作'});
						refreshStatus();
					}else{
						layer.alert('失败', {title: '订阅操作'}); 
					}
					   
				});	
				
			}
		};


  $("button[name='subscribeBtn']").on('click', function() {
		var type = $(this).data('type');
		console.log(type);		
		active[type] ? active[type].call(this) : '';

	});	
});