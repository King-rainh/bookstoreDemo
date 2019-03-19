<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Refresh" content="5,url=<%=path%>/user?action=login">
<title>找回密码成功</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/tools.js"></script>
<script type="text/javascript">
     $(function(){
         var remainTime = 4;
         setInterval(function(){
             $(".success span").html(remainTime);
             remainTime--;
         }, 1000);
     });
</script>
</head>
<body>
   <%@include file="/header.jsp"%>

   <div class="main" style="width: 900px;">
      <div class=success>
         <a href="<%=path%>/user?action=login">找回密码成功，去登录</a>
         <span>5 </span>  秒后自动跳转到登录页面
      </div>
   </div>

   <%@include file="/footer.jsp"%>
</body>
</html>