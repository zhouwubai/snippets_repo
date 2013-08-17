/**
 * Apply fiu logger plugin into your web
 * @param xPos apply right top-corner coordinate system to this logger div
 * @param yPos
 */
function applyFiuUserLogger(xPos,yPos,main_page){
	
	var loginHtml = getLoginHtml();
	var loginBtn = getLoginButton();
	
/**	if(indicator)
		$('body').before(loginBtn);
	else
		$(selector).after(loginBtn);

**/	$(loginBtn).appendTo('body');
	$(loginHtml).appendTo('body');
	$(".login_status").css("right",xPos);
	$(".login_status").css("top",yPos);
	
	initialLoginStatus(main_page);
	
	$('#login-logout-tab').click(function(){
		$.post('UserAliveServlet', function(data){
    		if (data.result == 'true') {
    			SignOut();
    		}
    		else {
    			SignIn();
    			_wt_click('signin');
    		}
    	}, 'json');
	});
}




function getLoginButton(){
	loginBtn = '<div class="login_status" style="position:absolute;right:0;top:0;z-index:10">' + 
		'<span id="welcome_info" style="color:red;display:none">welcome,</span>'+
		'<a id="user_info" href="#"></a>' + '<span id="welcome_info">&nbsp;&nbsp;</span>' + 
		'<a id="login-logout-tab" href="#">登录</a></div>';
	return loginBtn;
}


function getLoginHtml(){
	
	loginHtml = '<div id="sign-in-dialog" title="用户登录" style="display:none">' +
		'<p><div style="width:50px;">邮箱:</div>' + 
		' <input type="text" name="sign-in-username" id="sign-in-username" /></p>' + 
		'<p><div style="width:50px;">密码:</div>' +
		'<input type="password" name="sign-in-password" id="sign-in-password" style="margin-bottom:20px"/></p>' +
		'<div style="margin:5px 0px 5px 150px">' +
		'<p style="display:inline;padding-left:5px;">' +
		'<a href="signup.html" style="color:red;text-decoration:none;" target="_blank"> 注册</a></div></p>' +
		'</div>' + 
		'</div>';

	return loginHtml;
}


function SignIn() {
	$('#sign-in-diaglog').show();
	$('#sign-in-dialog').dialog({ minWidth: 410, minHeight: 130,
		open: function() { 
        	$(this).bind("keypress.ui-dialog", function(event) { 
	        	if (event.keyCode == $.ui.keyCode.ENTER) { 
	        		$( this ).dialog( "close" );
					var password = $('#sign-in-password').val();
			    	var username = $('#sign-in-username').val();
			    	$.post('LoginValidateServlet', {'username':username, 'password':SHA1(password)}, function(data){
			    		if (data.result == 'true') {
			    			logedInStatus(data.nick_name);
			    		}
			    		else {
			    			alert('The email or password is wrong');
			    		}
			    	}, 'json');
	        	} 
        	});
        },
        
		buttons: [
		    {text:"Sign in", click:function() {
				$( this ).dialog( "close" );
				var password = $('#sign-in-password').val();
		    	var username = $('#sign-in-username').val();
		    	$.post('LoginValidateServlet', {'username':username, 'password':SHA1(password)}, function(data){
		    		if (data.result == 'true') {
		    			logedInStatus(data.nick_name);
		    		}
		    		else {
		    			alert('The email or password is wrong');
		    		}
		    	}, 'json');
				
			}},{
			text: "Cancel", click: function() {
				$('#sign-in-password').val("");
				$( this ).dialog( "close" );
			}}
		]
	});    	
}



function SignOut(){
	$.post('LogoutServlet',{},function(data){
		logoutStatus();
	});
}


function logoutStatus(){
	$('.login_status #welcome_info').hide();
	$('.login_status #user_info').text("");
	$('.login_status #login-logout-tab').text("登录");
}




function initialLoginStatus(main_page){
	$.post('UserAliveServlet', function(data){
		if (data.result == 'true') {
			logedInStatus(data.nick_name);
		}
		else {
			if(window.location.href.indexOf(main_page) == -1){
				alert("对不起，请选登录。");
				window.location.href = main_page;
			}
			logoutStatus();
		}
	}, 'json');
}


function logedInStatus(nick_name){
	$('.login_status #welcome_info').show();
	$('.login_status #user_info').text(nick_name);
	$('.login_status #login-logout-tab').text("注销");
}




