<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>

<div style="padding: 20px;">
   
    <form id="userPasswordUpdateForm" class="easyui-form" data-options="url:'<%=path%>/user'"  method="post" >
    
        <input type="hidden" name="action" value="managerPasswordUpdateJson" />
        
        <label>原密码</label>
        <input name="password"  class="easyui-textbox" data-options="type:'password',required:true"/><br/>
        
        <label>新密码</label>
        <input name="newpassword"  class="easyui-textbox" data-options="type:'password',required:true"/><br/>
        
        <label>重复新密码</label>
        <input name="renewpassword"  class="easyui-textbox" data-options="type:'password',required:true"/><br/>
        
        <br/><br/>
        <a href="#" onclick="submitForm('userPasswordUpdateForm',false)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" >使用新密码</a>
    </form>
   
</div>