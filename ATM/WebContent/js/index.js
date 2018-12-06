var num = 0; //使用数字键输入的数字

// ATM对象
var atm = {status:0}; // 0:空闲 1:关闭 2:处理中
atm.refresh = function refresh(status){
	this.status = status;
}

// 屏幕对象
var display = {text:""};
display.refresh = function refresh(text){
	this.text = text;
	$("#display").html(this.text);
}
display.show = function show(text){
	$("#display").html(text);
}

//打印机对象
var area = {text:""};
area.refresh = function refresh(text){
	this.text = text;
	$("#area").val(this.text);
}

//管理员按钮对象
var identityButton = {isAppear:false};
identityButton.refresh = function refresh(isAppear){
	this.isAppear = isAppear;
	if(isAppear == true){
		$('.identity').removeAttr("style")
	}else{
		$(".identity").css("display","none")
	}
}

// 按钮对象
var switchButton = {text:"",disable:true}; 
switchButton.refresh = function refresh(text,disable){
	this.text = text;
	this.disable = disable;
	$("#switch").html(text);
	$("#switch").unbind("click");
	if(atm.status == 0){
		$("#switch").click(turnoff);		
	}
	else if(atm.status == 1){
		$("#switch").click(turnon);	
	}
	$("#switch").attr('disabled',disable);
}

// 插卡孔
var cardSlot = {text:"",inserted:false};
cardSlot.refresh = function refresh(text,inserted){
	this.text = text;
	this.inserted = inserted;
	$("#card").html(text);
	$("#card").attr('disable',inserted);
}

var moneySlot = {insertMoney:false,depositMoney:false};
moneySlot.refresh = function refresh(insertMoney,depositMoney){
	if(insertMoney){
		$(".insertMoney").attr('onclick',"insertMoney();","");
	}else{
		$(".insertMoney").removeAttr('onclick');
	}
	
	if(depositMoney){
		$(".money").show();
		$(".gaizi").hide();
	}else{
		$(".gaizi").show();
		$(".money").hide();
	}
}
// 数字按钮
var digitButton = {state:2,visibility:0,servletName:""};
digitButton.refresh = function refresh(state,visibility,servletName){
	this.state = state;
	this.visibility = visibility;
	this.servletName = servletName;
}
	
function readNum(obj){
	var digit = Number(obj.value);
	if(digitButton.state == 0){
		submitNum(digit);
	}
	else if(digitButton.state == 1){
		num = 10*num + digit;
		var str = display.text+"<br/>";
		if(digitButton.visibility == 1){
			var number=""+num;
			var encrypt = "";
			for(var i=1;i<=number.length;i++){
				encrypt += "*";
			}
			str+=encrypt;
		}else{
			str+=num;
		}
		
		display.show(str);
	}
	else if(digitButton.state == 2){
	}
}

//终于找到  程序入口
$(document).ready(function() {
	$("#card").click(insertCard);
	$("#identity").click(insertAdminCard);
	getStatus();
});

function insertMoney(){
	jPrompt('请放入面额为100的纸币','',function(event,val){
		if(val%100==0 && val<=5000){
		 var str="您已放入了"+val+"元,请按确认按钮将钱存入。";
		 num = val;
		 display.show(str);
		}
	},"存款");
}
function refresh(resp){
	atm.refresh(resp.ATM.state);
	display.refresh(resp.display.text);
	switchButton.refresh(resp.switchbutton.text,resp.switchbutton.disable);
	cardSlot.refresh(resp.cardslot.text,resp.cardslot.inserted);
	digitButton.refresh(resp.digitbutton.state,resp.digitbutton.visibility,resp.digitbutton.servletName);
	area.refresh(resp.print.text);
	identityButton.refresh(resp.identity_button.isAppear);
}

function getStatus(){
	$.post('/ATM/GetStatusServlet', function(responseText) {
		console.log(responseText);
		refresh(responseText);
	});
}

function turnon(){
	$.post('/ATM/TurnOnServlet', function(responseText) {
		refresh(responseText);
	});
}

function turnoff(){
	$.post('/ATM/TurnOffServlet', function(responseText) {
		refresh(responseText);
	});
}

function insertCard(){
	jPrompt('请输入账号','',function(event,val){
		var cardNo = val;
		 $.post('/ATM/CardInsertedServlet','cardNo='+cardNo, function(responseText) {
				refresh(responseText);
			});	
	},"账号验证");
}

function insertAdminCard(){
	jPrompt('请输入账号','',function(event,val){
		var cardNo = val;
		 $.post('/ATM/CardInsertedServlet','cardNo='+cardNo, function(responseText) {
				refresh(responseText);
			});	
	},"账号验证");
}

function submitNum(number){
	$.post('/ATM/'+digitButton.servletName,'num='+number, function(responseText) {
		refresh(responseText);
		num = 0;
	});	
}

function cancel(){
	num = 0;
	var str = display.text+"<br/>";
	display.show(str);
}

function submit(){
	//console.log("num="+num);
	//console.log("servlet=" + digitButton.servletName);
	submitNum(num);
}