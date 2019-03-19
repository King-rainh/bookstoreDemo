<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Refresh" content="5,url=<%=path%>/orders?action=pay">
<title>订单生成成功</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
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
         <a href="<%=path%>/orders?action=pay">订单生成成功，去支付</a>
         <span>5 </span>  秒后自动跳转到支付页面
      </div>
   </div>

   <%@include file="/footer.jsp"%>
</body>
</html>