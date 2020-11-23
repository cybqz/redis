<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    #header{
        background: #333333;
        height: 70px;
        padding: 0 30px;
        font-size: 24px;
        color: white;
        line-height: 70px;
    }
    .header_right_wrap{
        position: absolute;
        right: 0;
        top:0;
    }
    .header_right{
        font-size: 16px;
        display: inline-block;
        margin-right: 30px;
        cursor: pointer;
    }
</style>
<script>
    function logout() {
        new Rquest(ctx, "/login/logout", null, false,
            function (data) {
                window.location.href = ctx;
            }, function () {
                console.log("error");
            }).ajaxpost();
    }

    function updatePassword() {
        window.location.href = ctx + "/user/updatePassword";
    }
</script>
<div id="header">
    汽车销售管理系统
    <span class="header_right_wrap">
        <span class="header_right" id="login_info">登陆信息</span>
        <span class="header_right" onclick="updatePassword();">修改密码</span>
        <span class="header_right" onclick="logout();">退出</span>
    </span>

</div>
