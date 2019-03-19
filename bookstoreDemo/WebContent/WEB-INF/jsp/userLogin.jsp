<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/tools.js"></script>
<script type="text/javascript">
   $(function(){
       //失去焦点事件
       //验证邮箱
       $("#email").blur(function(){
           checkEmail("email", "emailTip");
       });
       
       //验证密码
        $("#password").blur(function(){
            checkLength("password", "passwordTip", 6);
        });
       
       //验证验证码
        $("#imageCode").blur(function(){
            checkNotEmpty("imageCode", "imageCodeTip");
        });
       
       //提交表单前做一次综合验证
       $("form").submit(function(){
           var r1 = checkEmail("email", "emailTip");
           var r2 = checkLength("password", "passwordTip", 6);
           var r3 = checkNotEmpty("imageCode", "imageCodeTip");
           
           return r1 && r2 && r3;
       });
   });
</script>
</head>
<body>
   <%@include file="/header.jsp"%>

   <div class="main"  style="width: 400px;">

        <div class="message">
            <p>登录页面</p>
            <span>${message }</span>
        </div>
      <form action="<%=path%>/user" method="post">
          <input type="hidden" name="action" value="loginSubmit" /> 
          <!-- 用户名 -->
          <div class="text-input">
             <label for="email">邮箱</label>
             <input id="email" name="email" type="text" 
                  <c:if test="${not empty param.email }">value="${param.email }"</c:if>
                  <c:if test="${empty param.email }">value="${cookie.email.value }"</c:if>
              />
             <span id="emailTip"></span>
          </div>
    
          <!-- 密码 -->
          <div class="text-input">
             <label for="password">密码</label>
             <input id="password" name="password" type="password"  
                  <c:if test="${not empty param.password }">value="${param.password }"</c:if>
                  <c:if test="${empty param.password }">value="${cookie.password.value }"</c:if>
             />
             <span id="passwordTip"></span>
          </div>
    
          <!-- 验证码 -->
          <div class="text-input image-code">
                <label for="imageCode">验证码</label>
                <input id="imageCode" name="imageCode" type="text" size="6"/>
                <img  src="<%=path%>/imageCode?t=<%=System.currentTimeMillis() %>" onclick="this.src='<%=path%>/imageCode?t='+new Date().getTime()" />
                &nbsp;&nbsp;&nbsp;
                <span id="imageCodeTip"></span>
          </div>
    
    
          <!-- 记住我  -->
          <p>
             <input  id="rememberMe" name="rememberMe" type="checkbox"  value="yes"
                <c:if test="${ (empty param.email ) && (cookie.rememberMe.value=='yes') }">checked="checked"</c:if>
                <c:if test="${ (not empty param.email) &&( param.rememberMe == 'yes' )}">checked="checked"</c:if>
             />
             
             <label for="rememberMe">记住我</label>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
             <a href="<%=path%>/user?action=passwordRetrieve">找回密码</a>
          </p>
          
          <br/><br/>
          <input type="submit"  value="   登   录   " />
      </form>
   </div>

   <%@include file="/footer.jsp"%>
</body>
</html>