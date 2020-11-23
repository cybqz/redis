<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"   uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fn"  uri="/WEB-INF/tlds/fn.tld"  %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld"  %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="${pageContext.request.getAttribute('title')}"/>
<c:set var="model" value="${pageContext.request.getAttribute('model')}"/>
<c:set var="opreationId" value="${pageContext.request.getAttribute('opreationId')}"/>
<script type="text/javascript">
    var ctx = "${ctx}", model = "${model}", title = "${title}", opreationId = "${opreationId}";

    document.onreadystatechange = function() {
        if(document.readyState == "complete"){
            //登陆状态全局校验
            loginedCheck();
        }
    }

    function loginedCheck(){
        new Rquest(ctx, "/user/getUser", null, false,
            function (data) {
                if(data != null && data.userName){
                    $("#login_info").text("欢迎：" + data.userName);
                }
            }, function (xhr, textStatus, errorThrown) {
                console.log(xhr);
                console.log(textStatus);
                console.log(errorThrown);
                //window.location.href = ctx
            }).ajaxpost();
    }

    //全局提示
    function tips(msg) {
        new NoticeJs({
            text: msg,
            position: 'topCenter',
            animation: {
                open: 'animated bounceIn',
                close: 'animated bounceOut'
            }
        }).show();
    }
</script>