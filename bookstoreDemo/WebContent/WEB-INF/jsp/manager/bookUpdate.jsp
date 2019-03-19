<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>
<div style="padding: 20px;">
    <form id="bookUpdateForm" class="easyui-form" data-options="url:'<%=path%>/book'"  method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="managerUpdateJson" />
        <input type="hidden" name="id" value="${book.id }" />
    
        <img src="<%=path%>/img/${book.coverImage}"  style="width:100px;" /><br/>
        <label>图书封面：</label>
        <input  name="coverImage"  class="easyui-filebox"  data-options="buttonText: '图片',accept: 'image/*'"/><span>(如果不需要修改封面，则不用选择新图片)</span><br/>
        
        <label>图书名称：</label>
        <input value="${book.name }" name="name"  class="easyui-textbox" data-options="required:true"/><br/>
        
        <label>作者：</label>
        <input value="${book.author }"  name="author"  class="easyui-textbox" data-options="required:true"/><br/>
        
        <label>价格：</label>
        <input value="${book.price }"  name="price"  class="easyui-numberbox" data-options="required:true,min:0"/><br/>
        
        <label>图书分类：</label>
        <input value="${book.categoryId }"  name="categoryId"  class="easyui-combobox"    data-options="required:true,valueField:'id',textField:'name',url:'<%=path%>/category?action=listJson'" /><br/>
      
        <label>出版社：</label>
        <input value="${book.press }"  name="press" class="easyui-textbox" data-options="required:true"/><br/>
        
        <label>出版日期：</label>
        <input value="${book.publishDate }"  name="publishDate" class="easyui-datebox"  data-options="required:true"/><br/>
        
        <label>页数：</label>
        <input  value="${book.pageCount }"  name="pageCount"  class="easyui-numberbox" data-options="required:true,min:0"/><br/>
        
        <label>字数：</label>
        <input value="${book.wordCount }"  name="wordCount"  class="easyui-numberbox" data-options="required:true,min:0"/><br/>
        
        <label>ISBN：</label>
        <input value="${book.isbn }"   name="isbn"  class="easyui-textbox" data-options="required:true"/><br/>
        
        <label>图书描述：</label>
        <input  value="${book.description }"   name="description"  class="easyui-textbox" data-options="required:true,multiline:true,height:100"/><br/>
        
        <a onclick="submitForm('bookUpdateForm',false,'bookDatagrid')" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" >修改</a>
    </form>

</div>
