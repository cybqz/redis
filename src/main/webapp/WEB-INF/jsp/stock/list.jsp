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
    <link rel="stylesheet" type="text/css" href="${ctx}/my/Table.css">
 	<script src="${ctx}/js/jquery.js"></script>
    <script src="${ctx}/js/pintuer.js"></script>
    <script src="${ctx}/js/cookie_util.js"></script>
    <script src="${ctx}/js/notice.js"></script>
    <script src="${ctx}/my/Request.js"></script>
    <script src="${ctx}/my/Table.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            //加载列表数据并渲染
            load({stock: {},pagination:{limit:50}});

            //获取总条数
            new Rquest(ctx, "/stock/count", null, false,
                function (data) {
                    if(data && data.validate && data.data){
                        $("#count").html(data.data);
                    }
                }, function () {
                    console.log("error");
                }).ajaxpost();

            //搜索事件
            $(".searchBtn").click(function () {
                const supplierNameParam = $(".searchInput").val();
                if(supplierNameParam == null || supplierNameParam === ''){
                    tips("搜索条件不能为空");
                    return;
                }
                load({stock: {supplierName: supplierNameParam},pagination:{limit:50}});
            });
        })
        
        function add() {
            window.location.href = ctx + "/stock/add"
        }

        function load(param) {
            var column = [{supplierName:'供应商名称'},{supplierPhone:'供应商电话'},{goodsName:'商品名称'},{goodsCategory:'商品类别'},{stockInCount:'库存数量'},{carNo:'汽车编号'}];
            new Table('#list', column, param, ctx, "/stock/page").renderingTable();
        }
        function update(id){

            window.location.href = ctx + "/stock/update?id=" + id;
        }
        function remove(id){

            var request = new Rquest(ctx, "/stock/delete", {id:id}, false,
                function (data) {
                    tips(data.msg);
                    load({stock: {},pagination:{limit:50}});
                }, function () {
                    console.log("error");
                });
            request.ajaxpost();
        }
        
        function pagenation_to_first() {

        }
    </script>
</head>
<body>
<!-- 引入头部 -->
<%@include file="../../common/header.jsp"%>

<div class="wrap">
    <div  id="tab_wrap">
        <!-- 引入模块导航栏 -->
        <%@include file="../../common/model_bar.jsp"%>
    </div>
    <div  id="content">
        <!-- 表格的公共信息展示 -->
        <div id="top_text">
             <span class="message_wrap">
               ${model}共 <span id="count" class="color_blue">0</span> 条
            </span>
            <span class="search_wrap">
                <input class="searchInput" placeholder="输入供应商名称" type="text"/>
                <span class="searchBtn">搜索</span>
            </span>
            <span class="add_wrap">
                 <span id="add" class="add" onclick="add()">
                    添加
                 </span>
            </span>
        </div>
        <!-- 表格内容展示-->
        <div id="list"></div>

    </div>

</div>
</body>
<script>
    window.onload=function modify(){
        var s=document.body.clientHeight-70;
        document.getElementById("tab_wrap").style.height=s;
    }
</script>
</html>
