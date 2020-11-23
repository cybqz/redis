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

            var request = new Rquest(ctx, "/stock/detail", {id:opreationId}, false,
                function (data) {
                    if(data && data.validate && data.data){
                        $("#supplierName").val(data.data['supplierName']);
                        $("#supplierPhone").val(data.data['supplierPhone']);
                        $("#goodsName").val(data.data['goodsName']);
                        $("#goodsCategory").val(data.data['goodsCategory']);
                        $("#stockInCount").val(data.data['stockInCount']);
                        $("#carNo").val(data.data['carNo']);
                    }
                }, function () {
                    console.log("error");
                });
            request.ajaxpost();
        });
        function doupdate() {
            var param = {
                id: opreationId,
                supplierName: $("#supplierName").val(),
                supplierPhone: $("#supplierPhone").val(),
                goodsName: $("#goodsName").val(),
                goodsCategory: $("#goodsCategory").val(),
                stockInCount: $("#stockInCount").val(),
                carNo: $("#carNo").val()
            };
            var request = new Rquest(ctx, "/stock/doupdate", param, false,
                function (data) {
                    if(data && data.validate){
                        window.location.href = ctx + "/stock/";
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
