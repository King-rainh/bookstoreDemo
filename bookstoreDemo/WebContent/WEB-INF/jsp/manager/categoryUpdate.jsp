<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>
<div style="padding: 20px;">
    <form id="categoryUpdateForm" class="easyui-form" data-options="url:'<%=path%>/category'"  method="post" >
        <input type="hidden" name="action" value="managerUpdateJson" />
        <input type="hidden" name="id" value="${category.id }" />
    
        <label>图书分类名称：</label>
        <input value="${category.name }" name="name"  class="easyui-textbox" data-options="required:true"/><br/>
        
        <a onclick="submitForm('categoryUpdateForm',false,'categoryDatagrid')" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" >修改</a>
    </form>

</div>
