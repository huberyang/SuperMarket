<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>登录超级市场</title>
    <link type="text/css" rel="stylesheet" href="/sso/css/login.css"/>
    <script type="text/javascript" src="/sso/js/jquery-1.6.4.js"></script>
    <script type="text/javascript" src="/sso/js/jquery.cookie.js"></script>
</head>
<body>
<div class="w">
    <div id="logo">
    	<a href="http://www.supermarket.com" clstag="passport|keycount|login|01">
    		<img src="/sso/images/supermarket-logo.gif" alt="超级市场" width="170" height="60"/>
    	</a><b></b>
   	</div>
</div>
<form id="formlogin" method="post" onsubmit="return false;">
    <div class=" w1" id="entry">
        <div class="mc " id="bgDiv">
            <div id="entry-bg" clstag="passport|keycount|login|02" style="width: 511px; height: 455px; position: absolute; left: -44px; top: -44px; background: url(/sso/images/544a11d3Na5a3d566.png) 0px 0px no-repeat;">
			</div>
            <div class="form ">
                <div class="item fore1">
                    <span>用户名</span>
                    <div class="item-ifo">
                        <input type="text" id="loginname" name="username" class="text"  tabindex="1" autocomplete="off"/>
                        <div class="i-name ico"></div>
                        <label id="loginname_succeed" class="blank invisible"></label>
                        <label id="loginname_error" class="hide"><b></b></label>
                    </div>
                </div>
                <script type="text/javascript">
                    setTimeout(function () {
                        if (!$("#loginname").val()) {
                            $("#loginname").get(0).focus();
                        }
                    }, 0);
                </script>
                <div id="capslock"><i></i><s></s>键盘大写锁定已打开，请注意大小写</div>
                <div class="item fore2">
                    <span>密码</span>
                    <div class="item-ifo">
                        <input type="password" id="nloginpwd" name="password" class="text" tabindex="2" autocomplete="off"/>
                        <div class="i-pass ico"></div>
                        <label id="loginpwd_succeed" class="blank invisible"></label>
                        <label id="loginpwd_error" class="hide"></label>
                    </div>
                </div>
                <div class="item login-btn2013">
                    <input type="button" class="btn-img btn-entry" id="loginsubmit" value="登录" tabindex="8" clstag="passport|keycount|login|06"/>
                </div>
            </div>
        </div>
        <div class="free-regist">
            <span><a href="/sso/page/register" clstag="passport|keycount|login|08">免费注册&gt;&gt;</a></span>
        </div>
    </div>
</form>
<script type="text/javascript">
	var redirectUrl = "${redirect}";
	var LOGIN = {
			checkInput:function() {
				if ($("#loginname").val() == "") {
					alert("username can't be null");
					$("#loginname").focus();
					return false;
				}
				if ($("#nloginpwd").val() == "") {
					alert("password can't be null");
					$("#nloginpwd").focus();
					return false;
				}
				return true;
			},
			/* doLogin:function() {
				$.post("/sso/user/login", $("#formlogin").serialize(),function(data){
					if (data.status == 200) {
						alert("login success!");
						if (redirectUrl == "") {
							location.href = "http://www.supermarket.com";
						} else {
							location.href = redirectUrl;
						}
					} else {
						alert("login failed,the reason was：" + data.msg);
						$("#loginname").select();
					}
				});
			}, */
			
			doLogin:function() {
				$.post("/sso/user/login", $("#formlogin").serialize(),function(data){
					if (data.status == 200) {
						alert("login success!");
						
						//request to transfer cart cookie info to the login user's redis region
						var cartCookie=$.cookie("SM_CART");
						
						if(!cartCookie){
							if (redirectUrl == "") {
								location.href = "http://www.supermarket.com";
							} else {
								location.href = redirectUrl;
							}
						}else{
							$.ajax({
								url : "http://www.supermarket.com/cart/transfer/" + data.data+".action",
								dataType : "jsonp",
								type : "POST",
								data:cartCookie, //change the javascript obejct to a string (use with backend data intercahnge)
				                contentType:"application/json;charset=utf-8", //declare contentType
				                dataType: "json",//declare the return data format
								success:function(data){
									if(data.status==200){
										if (redirectUrl == "") {
											location.href = "http://www.supermarket.com";
										} else {
											location.href = redirectUrl;
										}
									}
								}
							});
						}
					} else {
						alert("login failed,the reason was：" + data.msg);
						$("#loginname").select();
					}
				});
			},
			
			login:function() {
				if (this.checkInput()) {
					this.doLogin();
				}
			}
	};
	$(function(){
		$("#loginsubmit").click(function(){
			LOGIN.login();
		});
	});
</script>
</body>
</html>