/**
 * apply this plugin into your website
 * @param selector choose which where you put the log status bar
 * @param indicator true, then put it before selector, other
 */
function applyFiuUserLogger(selector,indicator){
	
	var loginHtml = getLoginHtml();
	var loginBtn = getLoginButton();
	
	if(indicator)
		$(selector).before(loginBtn);
	else
		$(selector).after(loginBtn);
	
	$(loginHtml).appendTo('body');
	
	initialLoginStatus();
	
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
	loginBtn = '<div class="login_status">' + 
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




function initialLoginStatus(){
	$.post('UserAliveServlet', function(data){
		if (data.result == 'true') {
			logedInStatus(data.nick_name);
		}
		else {
			logoutStatus();
		}
	}, 'json');
}


function logedInStatus(nick_name){
	$('.login_status #welcome_info').show();
	$('.login_status #user_info').text(nick_name);
	$('.login_status #login-logout-tab').text("注销");
}


