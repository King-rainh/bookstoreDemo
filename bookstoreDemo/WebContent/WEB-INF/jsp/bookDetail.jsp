<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图书详情</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
<link type="text/css" rel="stylesheet" href="<%=path%>/css/book_detail.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/tools.js"></script>
</head>
<body>
   <%@include file="/header.jsp"%>

   <div class="main"  style="width: 900px;">
      <!-- 图书大图片 -->
      <div class="show_pic">
         <img class="pic" src="<%=path%>/img/${book.coverImage}" />
      </div>

      <div class="show_info">
         <h2>${book.name }</h2>
         <ul class="intro">
            <li>图书编号：${book.id }</li>
            <li>价格：<div class="price">¥${book.price }</div></li>
            <li>作 者： ${book.author }</li>
            <li>出 版 社：${book.press }</li>
            <li>
                <span>出版日期：${book.publishDate }</span> 
                <span>ISBN：${book.isbn }</span>
            </li>
            <li>
                <span>页 数：${book.pageCount }</span>
                <span>字 数：${book.wordCount }</span> 
            </li>
         </ul>

         <div class="buy-area">
            <form action="<%=path%>/cart" method="post">
                <input type="hidden" name="action" value="addSubmit" />
                <input type="hidden" name="bookId" value="${book.id }" />
                <label>我要买：</label>
                <input type="text" name="count" value="1"  size="4"/> 本
                <input type="submit"  class="add-cart"  value=""/>
            </form>
         </div>
         
      </div>
      
      <div style="clear: both;"></div>
      
      <div class="pro_content" >
            <div class="section">
               <div class="tit">内容推荐</div>
               <div class="descrip"> ${book.description } </div>
            </div>
      </div>
      
   </div>

   <%@include file="/footer.jsp"%>
</body>
</html>