<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="common/taglibs.jsp"%>

<!doctype html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>${title}</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/animate.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/noticejs.css" />
 	<script src="${ctx}/js/jquery.js"></script>
    <script src="${ctx}/js/pintuer.js"></script>
    <script src="${ctx}/js/cookie_util.js"></script>
	<script src="${ctx}/js/notice.js"></script>
	<script src="${ctx}/my/Request.js"></script>
</head>
<body>
	<div class="jq22-container" style="padding-top:10px">
		<div class="login-wrap">
			<div class="login-html">
				<input id="tab-1" type="radio" name="tab" class="sign-in" checked><label for="tab-1" class="tab">登录</label>
				<input id="tab-2" type="radio" name="tab" class="sign-up"><label for="tab-2" class="tab">注册</label>
				<div class="login-form">
					<div class="sign-in-htm">
						<div class="group">
							<label for="user" class="label">Username</label>
							<input id="user" type="text" class="input">
						</div>
						<div class="group">
							<label for="pass" class="label">Password</label>
							<input id="pass" type="password" class="input" data-type="password">
						</div>
						<div class="group">
							<input id="login" type="button" class="button" value="登录">
						</div>
						<div class="hr"></div>
					</div>
					<div class="sign-up-htm">
						<div class="group">
							<label for="user" class="label">Username</label>
							<input id="user1" name="user1" type="text" class="input">
						</div>
						<div class="group">
							<label for="pass" class="label">Password</label>
							<input id="pass1" name="pass1" type="password" class="input" data-type="password"/>
						</div>
						<div class="group">
							<label for="pass" class="label">Repeat Password</label>
							<input id="rep_pass1" type="password" class="input" data-type="password">
						</div>
						<div class="group">
							<input id="register" type="button" class="button" value="注册"/>
						</div>
						<div class="hr"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">

		$(document).ready(function () {

			new Rquest(ctx, "/user/getUser", null, false,
					function (data) {
						toCustomerinfo(data);
					}, null).ajaxpost();

			/**
			 * 注册
			 */
			$("#register").click(function () {

				//获取请求参数
				var user1 = $("#user1").val();
				if(user1==null || user1==""){
					tips("请输入用户名");
					return false;
				}
				var pass1 = $("#pass1").val();
				if(pass1==null || pass1==""){
					tips("请输入密码");
					return false;
				}
				var rep_pass1 =$("#rep_pass1").val();
				if(rep_pass1==null || rep_pass1==""){
					tips("请确认密码");
					return false;
				}
				if(pass1!=rep_pass1){
					tips("密码输入不一致");
					return false;
				}
				new Rquest(ctx, "/register/register", {"userName":user1,"password":pass1}, false,
						function (data) {
							toCustomerinfo(data);
						}, function () {
							console.log("error");
						}).ajaxpost();
			});

			/**
			 * 登陆
			 */
			$("#login").click(function () {
				//获取请求参数
				var user =$("#user").val();
				if(user==null || user==""){
					tips("请输入用户名");
					return false;
				}
				var pass = $("#pass").val();
				if(pass==null || pass==""){
					tips("请输入密码");
					return false;
				}
				//检查参数格式
				var check=true;//true通过检测，false未通过
				//发送Ajax请求
				if(check){
					new Rquest(ctx, "/login/login", {"userName":user,"password":pass}, false,
							function (data) {
								toCustomerinfo(data);
							}, function () {
								console.log("error");
							}).ajaxpost();
				}
			});
		});

		function toCustomerinfo(data) {
			if(data && data.validate){
				window.location.href = ctx + "/customerinfo/";
			}
		}
	</script>
</body>
</html>