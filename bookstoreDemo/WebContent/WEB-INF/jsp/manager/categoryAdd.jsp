<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>
    <!-- 添加区域 -->
<div style="padding: 20px;">
   
    <form id="categoryAddForm" class="easyui-form" data-options="url:'<%=path%>/category'"  method="post" >
    
        <input type="hidden" name="action" value="managerAddJson" />
        <label>图书名称名称：</label>
        <input name="name"  class="easyui-textbox" data-options="required:true"/><br/>
        
        <a href="#" onclick="submitForm('categoryAddForm',true,'categoryDatagrid')" class="easyui-linkbutton" data-options="iconCls:'icon-add'" >添加新图书分类</a>
    </form>
   
</div>
