<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/tools.js"></script>
<script type="text/javascript">
    $(function(){
        //失去焦点事件
        $("#email").blur(function(){
            checkEmail("email", "emailTip");
        });
        $("#password").blur(function(){
            checkLength("password", "passwordTip", 6);
            checkRepassword("password", "repassword", "repasswordTip");
        });
        $("#repassword").blur(function(){
            checkRepassword("password", "repassword", "repasswordTip");
        });
        $("#imageCode").blur(function(){
            checkNotEmpty("imageCode", "imageCodeTip");
        });
        
        //表单提示前做一次综合检查
        $("form").submit(function(){
            //对每个表单项的验证结果
            var r1 = checkEmail("email", "emailTip");
            var r2 = checkLength("password", "passwordTip", 6);
            var r3 = checkRepassword("password", "repassword", "repasswordTip");
            var r4 = checkNotEmpty("imageCode", "imageCodeTip");
            
            //只要有一个验证失败位false，则整体失败，取消表单提交
            return r1 && r2 && r3 && r4;
        });
    });
</script>
</head>
<body>
   <%@include file="/header.jsp"%>

   <div class="main"  style="width: 400px;">
        <div class="message">
            <p>注册页面</p>
            <span>${message }</span>
        </div>
       <form  action="<%=path%>/user" method="post">
           <input type="hidden" name="action" value="registerSubmit" />
              <!-- 邮箱 -->
              <div class="text-input">
                 <label for="email">邮箱 *</label>
                 <input id="email" name="email"  type="text"  value="${param.email }" />
                 <span id="emailTip" ></span>
              </div>
        
        
              <!-- 密码 -->
              <div class="text-input">
                 <label for="password">密码 *</label>
                 <input id="password" name="password" type="password"   value="${param.password }" />
                 <span id="passwordTip"></span>
              </div>

              <!-- 重复密码 -->
              <div class="text-input">
                 <label for="repassword">重复密码 *</label>
                 <input id="repassword"  type="password"   value="${param.password }" />
                 <span id="repasswordTip"></span>                 
              </div>
        
              <!-- 验证码 -->
             <div class="text-input  image-code">
                <label for="imageCode">验证码 *</label>
                <input id="imageCode" name="imageCode"  type="text"  size="6"/>&nbsp;&nbsp;&nbsp;&nbsp;
                <img src="<%=path%>/imageCode?t=" <%=System.currentTimeMillis() %> onclick="this.src='<%=path%>/imageCode?t='+new Date().getTime()" />
                <span style="line-height: 10px;" id="imageCodeTip"></span>
             </div>
        
              <!-- 注册  -->
              <br/><br/>
              <input type="submit"  value="   注   册   " />
      </form>
   </div>

   <%@include file="/footer.jsp"%>
</body>
</html>