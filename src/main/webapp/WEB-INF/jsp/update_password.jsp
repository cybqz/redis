<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp"%>

<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/noticejs.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/styles.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/my/add.css">
 	<script src="${ctx}/js/jquery.js"></script>
    <script src="${ctx}/js/pintuer.js"></script>
    <script src="${ctx}/js/cookie_util.js"></script>
    <script src="${ctx}/js/notice.js"></script>
    <script src="${ctx}/my/Request.js"></script>
    <style>
        .userWrap{
            padding: 60px;
            width: 60%;
            margin: 60px auto;
            border-radius: 3px;
            box-shadow: 0 0 3px #909090;
        }
        .user_div{
            height: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
            width: 100%;
            padding: 10px;
            margin-bottom: 60px;
        }
        .userInput{
            flex: 1;
            height: 40px;
            flex: 1;
            border:gainsboro 1px solid;
            border-radius:  3px 0 0 3px;
            padding: 15px 5px;
        }
        .user_info{
            width: 150px;
            display: flex;
            justify-content: flex-end;
            align-items: center;
            margin-right: 20px;
        }
        .userBtn{
            display: flex;
            width: 100px;
            height: 40px;
            align-items: center;
            justify-content: center;
            margin: 30px;
            border-radius: 4px;
            border: #0F5F7E 1px solid;
            cursor: pointer;
        }
        .userConfirm{
            background: #0F5F7E;
            color: white;
        }
    </style>
    <script type="text/javascript">

        $(document).ready(function () {
            new Rquest(ctx, "/user/getUser", null, false,
                function (data) {
                    if(data != null && data.userName){
                        $("#user_name").val(data.userName);
                    }
                }, function (xhr, textStatus, errorThrown) {
                    console.log(xhr);
                    console.log(textStatus);
                    console.log(errorThrown);
                }).ajaxpost();
        });
        function doupdate() {

            const confirm_password = $("#confirm_password").val();
            const old_password = $("#old_password").val();
            const password = $("#password").val();
            if(!old_password || old_password == null || old_password === ""){
                tips("旧密码不能为空");
                return;
            }
            if(!password || password == null || password === ""){
                tips("新密码不能为空");
                return;
            }
            if(!confirm_password || confirm_password == null || confirm_password === ""){
                tips("确认密码不能为空");
                return;
            }
            if(password != confirm_password){
                tips("两次输入的密码不一致");
                return;
            }
            var param = {
                "password": password,
                "oldPassword": old_password
            };
            var request = new Rquest(ctx, "/user/doUpdatePassword", param, false,
                function (data) {
                    if(data && data.validate){
                        window.location.href = ctx + "/";
                    }
                }, function (xhr, textStatus, errorThrown) {
                    console.log(xhr);
                    console.log(textStatus);
                    console.log(errorThrown);
                });
            request.ajaxpost();
        }
        
        function cancel() {
            window.location.href = ctx + "/stock/";
        }
    </script>
</head>
<body>
<!-- 引入头部 -->
<%@include file="../common/header.jsp"%>

<div class="userWrap">
    <div class="user_div">
        <span class="user_info">用户名：</span>
        <input id="user_name" disabled class="userInput" type="text">
    </div>
    <div class="user_div">
        <span class="user_info">旧密码：</span>
        <input id="old_password" class="userInput" placeholder="请输入旧密码" type="text">
    </div>
    <div class="user_div">
        <span class="user_info">新密码：</span>
        <input id="password" class="userInput" placeholder="请输入新密码" type="text">
    </div>
    <div class="user_div">
        <span class="user_info">确认密码：</span>
        <input id="confirm_password" class="userInput" placeholder="请再次输入新密码" type="text">
    </div>
    <div class="user_div">
        <span class="userBtn" onclick="cancel();">取消</span>
        <span class="userBtn userConfirm" onclick="doupdate();">确认</span>
    </div>
</div>
</body>
</html>
