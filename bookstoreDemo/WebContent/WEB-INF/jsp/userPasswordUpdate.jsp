<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/tools.js"></script>
<script type="text/javascript">

    $(function(){
        
        //失去焦点事件
        $("#password").blur(function(){
            checkLength("password", "passwordTip", 6);
        });
        $("#newpassword").blur(function(){
            checkLength("newpassword", "newpasswordTip", 6);
            checkRepassword("newpassword", "renewpassword", "renewpasswordTip");
        });
        $("#renewpassword").blur(function(){
            checkRepassword("newpassword", "renewpassword", "renewpasswordTip");
        });
        $("#imageCode").blur(function(){
            checkNotEmpty("imageCode", "imageCodeTip");
        });
        
        //表单提示前做一次综合检查
        $("form").submit(function(){
            //对每个表单项的验证结果
            var r1 = checkLength("password", "passwordTip", 6);
            var r2 = checkLength("newpassword", "newpasswordTip", 6);
            var r3 = checkRepassword("newpassword", "renewpassword", "renewpasswordTip");
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
            <p>修改密码</p>
            <span>${message }</span>
        </div>
       <form  action="<%=path%>/user" method="post">
           <input type="hidden" name="action" value="passwordUpdateSubmit" />
        
              <!-- 原密码 -->
              <div class="text-input">
                 <label for="password">原密码 *</label>
                 <input id="password" name="password" type="password"   value="${param.password }" />
                 <span id="passwordTip"></span>
              </div>
              
              <!-- 新密码 -->
              <div class="text-input">
                 <label for="newpassword">新密码 *</label>
                 <input id="newpassword" name="newpassword" type="password"   value="${param.newpassword }" />
                 <span id="newpasswordTip"></span>
              </div>

              <!-- 重复新密码 -->
              <div class="text-input">
                 <label for="renewpassword">重复密码 *</label>
                 <input id="renewpassword"  type="password" />
                 <span id="renewpasswordTip"></span>                 
              </div>
        
              <!-- 验证码 -->
             <div class="text-input  image-code">
                <label for="imageCode">验证码 *</label>
                <input id="imageCode" name="imageCode"  type="text"  size="6"/>&nbsp;&nbsp;&nbsp;&nbsp;
                <img src="<%=path%>/imageCode?t=" <%=System.currentTimeMillis() %> onclick="this.src='<%=path%>/imageCode?t='+new Date().getTime()" />
                <span style="line-height: 10px;" id="imageCodeTip"></span>
             </div>
        
              <br/><br/>
              <input type="submit"  value="  使用新密码 " />
      </form>
   </div>

   <%@include file="/footer.jsp"%>
</body>
</html>