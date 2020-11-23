<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>

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
    <script type="text/javascript">
        $(document).ready(function () {

            var request = new Rquest(ctx, "/sale/detail", {id:opreationId}, false,
                function (data) {
                    if(data && data.validate && data.data){
                        $("#buyingPeopleName").val(data.data['buyingPeopleName']);
                        $("#phone").val(data.data['phone']);
                        $("#description").val(data.data['description']);
                        $("#buyName").val(data.data['buyName']);
                        $("#price").val(data.data['price']);
                        $("#category").val(data.data['category']);
                        $("#profit").val(data.data['profit']);
                        $("#staffNo").val(data.data['staffNo']);
                    }
                }, function () {
                    console.log("error");
                });
            request.ajaxpost();
        });
        function doupdate() {
            var param = {
                id: opreationId,
                buyingPeopleName: $("#buyingPeopleName").val(),
                phone: $("#phone").val(),
                description: $("#description").val(),
                buyName: $("#buyName").val(),
                price: $("#price").val(),
                category: $("#category").val(),
                profit: $("#profit").val(),
                staffNo: $("#staffNo").val()
            };
            var request = new Rquest(ctx, "/sale/doupdate", param, false,
                function (data) {
                    if(data && data.validate){
                        window.location.href = ctx + "/sale/";
                    }
                }, function () {
                    console.log("error");
                });
            request.ajaxpost();
        }
        
        function cancel() {
            window.location.href = ctx + "/sale/";
        }
    </script>
</head>
<body>
<!-- 引入头部 -->
<%@include file="../../common/header.jsp"%>

<div class="wrap">

    <%@include file="common_content.jsp"%>

    <div class="button_wrap">
        <span class="button_text cancel" onclick="cancel();">取消</span>
        <span class="button_text save" onclick="doupdate();">保存</span>
    </div>
</div>
</body>
</html>
