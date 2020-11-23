<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    #model_bar{
        height: 100%;
        background: #0F5F7E;
        color: white;
    }
    .tabText{
        height: 70px;
        padding: 0 30px;
        font-size: 18px;
        line-height: 70px;
        cursor: pointer;
        text-align: center;
        cursor: pointer;
    }
    .tabText:hover{
        color: #00a0e9;
        background: white;
    }
</style>
<div id="model_bar">
    <div class="tabText">
        <span onclick="toCustomerInfo();">客户管理</span>
    </div>
    <div class="tabText">
        <span onclick="toSale();">销售管理</span>
    </div>
    <div class="tabText">
        <span onclick="toStock();">库存管理</span>
    </div>
    <div class="tabText">
        <span onclick="toStaff();">员工管理</span>
    </div>
</div>
<script>
    function toCustomerInfo(){
        window.location.href = ctx + "/customerinfo/";
    }

    function toSale() {
        window.location.href = ctx + "/sale/";
    }
    function toStock(){
        window.location.href = ctx + "/stock/";
    }

    function toStaff() {
        window.location.href = ctx + "/staff/";
    }
</script>
