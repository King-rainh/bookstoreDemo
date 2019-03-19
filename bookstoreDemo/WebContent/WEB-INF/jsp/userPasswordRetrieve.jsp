<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>找回密码</title>
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

           //验证验证码
           $("#checkCode").blur(function(){
               checkNotEmpty("checkCode", "checkCodeTip");
           });
           
           //验证新密码
           $("#newpassword").blur(function(){
               checkLength("newpassword", "newpasswordTip", 6);
           });
          
          //点击"把验证码发送到邮箱"按钮后
          $("#getCheckCodeBtn").click(function(){
              
              if(!checkEmail("email", "emailTip")){
                  return ;//如果验证失败，不会发送ajax请求
              }
              
              var remainTime=60;//发送请求60秒后可重试
              //先禁用按钮
              $("#getCheckCodeBtn").prop("disabled",true).val(remainTime+"秒后重试...");
              
              //发送ajax请求，让服务器发送验证码邮件
              $.post(
                      "<%=path%>/user",
                      {"action":"sendCheckCode","email":$("#email").val()},
                      function(result){
                          if(result.status=="success"){
                              //服务器发送邮箱验证码成功后，按钮60秒后可重新点击
                              var intervalId = setInterval(function(){
                                  remainTime--;
                                  if(remainTime==0){
                                      clearInterval(intervalId);
                                      $("#getCheckCodeBtn").prop("disabled",false);
                                      $("#getCheckCodeBtn").val("获取验证码");
                                  }else{
                                      $("#getCheckCodeBtn").val(remainTime+"秒后重试...");
                                  }
                              }, 1000);
                          }else{
                              $("#getCheckCodeBtn").prop("disabled",false);
                              $("#getCheckCodeBtn").val("获取验证码");
                              alert(result.data);
                          }
              },'json');

          });
          
          //提交表单前做一次综合验证
          $("form").submit(function(){
              var r1 = checkEmail("email", "emailTip");
              var r2 = checkNotEmpty("checkCode", "checkCodeTip")
              var r3 = checkLength("newpassword", "newpasswordTip", 6);
              
              return r1 && r2 && r3;
          });
        });
</script>
</head>
<body>
   <%@include file="/header.jsp"%>

   <div class="main"  style="width: 400px;">

        <div class="message">
            <p>找回密码</p>
            <span>${message }</span>
        </div>
      <form action="<%=path%>/user" method="post">
            <input type="hidden" name="action" value="passwordRetrieveSubmit" />
            
            <div class="text-input">
                <label for="email">邮箱*</label>
                <input id="email" name="email" type="text"  value="${param.email }"/>
                <span id="emailTip"></span>
            </div>
            
             <!-- 验证码 -->
             <div class="text-input">
                <label for="checkCode">验证码*</label>                
                <input id="checkCode" name="checkCode" type="text" size="6"/>&nbsp;
                <input id="getCheckCodeBtn" class="get-check-code-btn" type="button"  value="  获取验证码   "/>
                <span id="checkCodeTip"></span>
             </div>
             
            <!-- 新密码 -->
            <div class="text-input">
                <label for="newpassword">新密码*</label>
                <input id="newpassword" name="newpassword" type="password"   value="${param.newpassword }" />
                <span id="newpasswordTip"></span>
            </div>
             
          <br/><br/>
          <input type="submit"  value="  使用新密码 " />
      </form>
   </div>

   <%@include file="/footer.jsp"%>
</body>
</html>