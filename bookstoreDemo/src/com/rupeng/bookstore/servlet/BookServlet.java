package com.rupeng.bookstore.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.rupeng.bookstore.entity.Book;
import com.rupeng.bookstore.service.BookService;
import com.rupeng.bookstore.utils.AjaxResult;
import com.rupeng.bookstore.utils.Page;
import com.rupeng.bookstore.utils.PageAjaxResult;
import com.rupeng.bookstore.utils.Utils;

@WebServlet("/book")
@MultipartConfig
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            processList(request, response);

        } else if ("detail".equals(action)) {
            processDetail(request, response);

        } else if ("managerList".equals(action)) {
            processManagerList(request, response);

        } else if ("managerListJson".equals(action)) {
            processManagerListJson(request, response);

        } else if ("managerAdd".equals(action)) {
            processManagerAdd(request, response);

        } else if ("managerAddJson".equals(action)) {
            processManagerAddJson(request, response);

        } else if ("managerDeleteJson".equals(action)) {
            processManagerDeleteJson(request, response);

        } else if ("managerUpdate".equals(action)) {
            processManagerUpdate(request, response);

        } else if ("managerUpdateJson".equals(action)) {
            processManagerUpdateJson(request, response);

        }

    }

    private void processManagerAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/manager/bookAdd.jsp").forward(request, response);
    }

    private void processManagerList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/manager/bookList.jsp").forward(request, response);

    }

    private void processManagerUpdateJson(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // 获取请求参数
        int id = Integer.parseInt(request.getParameter("id"));
        String categoryIdStr = request.getParameter("categoryId");
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String author = request.getParameter("author");
        String press = request.getParameter("press");
        String publishDateStr = request.getParameter("publishDate");
        String wordCountStr = request.getParameter("wordCount");
        String pageCountStr = request.getParameter("pageCount");
        String isbn = request.getParameter("isbn");
        String description = request.getParameter("description");
        String coverImage = null;// 需要处理文件上传后才可得到其值

        // 类型转换和有效性检查
        int categoryId = 0;
        try {
            categoryId = Integer.parseInt(categoryIdStr);
        } catch (Exception e) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "图书分类不能为空，请使用下拉选项选择图书分类"));
            return;
        }
        if (Utils.isEmpty(name)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "图书名称不能为空"));
            return;
        }
        double price = 0;
        try {
            price = Double.parseDouble(priceStr);
        } catch (Exception e) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "图书价格不能为空或只能为数字"));
            return;
        }
        if (price < 0) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "图书价格不能小于0"));
            return;
        }
        if (Utils.isEmpty(author)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "作者不能为空"));
            return;
        }
        if (Utils.isEmpty(press)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "出版社不能为空"));
            return;
        }
        Date publishDate = null;
        try {
            publishDate = Utils.convertDate(publishDateStr, "yyyy-MM-dd");
        } catch (Exception e) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "出版日期不能为空或者格式不正确"));
            return;
        }
        int wordCount = 0;
        try {
            wordCount = Integer.parseInt(wordCountStr);
        } catch (Exception e) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "字数不能为空或者必须输入数字"));
            return;
        }
        if (wordCount < 1) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "字数不能小于1"));
            return;
        }
        int pageCount = 0;
        try {
            pageCount = Integer.parseInt(pageCountStr);
        } catch (Exception e) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "页数不能为空或者必须输入数字"));
            return;
        }
        if (pageCount < 1) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "页数不能小于1"));
            return;
        }
        if (Utils.isEmpty(isbn)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "ISBN不能为空"));
            return;
        }
        if (Utils.isEmpty(description)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "图书描述不能为空"));
            return;
        }

        // 处理图书封面图片上传
        Part part = request.getPart("coverImage");
        if (part != null && part.getSize() > 0) {
            coverImage = Utils.fileupload(part, getServletContext().getInitParameter("fileuploadRootPath"));
        }

        // 封装实体类
        Book book = bookService.findById(id);
        book.setAuthor(author);
        book.setCategoryId(categoryId);
        // 如果coverImage为空，就说明用户没有修改封面图片
        if (!Utils.isEmpty(coverImage)) {
            book.setCoverImage(coverImage);
        }
        book.setDescription(description);
        book.setIsbn(isbn);
        book.setName(name);
        book.setPageCount(pageCount);
        book.setPress(press);
        book.setPrice(price);
        book.setPublishDate(publishDate);
        book.setWordCount(wordCount);

        // 执行update的业务逻辑
        bookService.update(book);

        // 返回json响应
        Utils.sendAjaxResponse(response, new AjaxResult("success", "修改图书成功"));

    }

    private void processManagerUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book book = bookService.findById(id);
        request.setAttribute("book", book);
        request.getRequestDispatcher("/WEB-INF/jsp/manager/bookUpdate.jsp").forward(request, response);
    }

    private void processManagerDeleteJson(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        bookService.deleteById(id);
        Utils.sendAjaxResponse(response, new AjaxResult("success", "删除图书成功"));

    }

    private void processManagerAddJson(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // 获取请求参数
        String categoryIdStr = request.getParameter("categoryId");
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String author = request.getParameter("author");
        String press = request.getParameter("press");
        String publishDateStr = request.getParameter("publishDate");
        String wordCountStr = request.getParameter("wordCount");
        String pageCountStr = request.getParameter("pageCount");
        String isbn = request.getParameter("isbn");
        String coverImage = null;// 需要处理文件上传后才可得到其值
        String description = request.getParameter("description");

        // 类型转换和数据验证
        int categoryId = 0;
        try {
            categoryId = Integer.parseInt(categoryIdStr);
        } catch (Exception e) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "请选择图书类别"));
            return;
        }
        if (Utils.isEmpty(name)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "图书名称不能为空"));
            return;
        }
        double price = 0;
        try {
            price = Double.parseDouble(priceStr);
        } catch (Exception e) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "价格不能为空"));
            return;
        }
        if (price <= 0) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "价格不能为0或者不能为负"));
            return;
        }
        if (Utils.isEmpty(author)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "作者不能为空"));
            return;
        }
        if (Utils.isEmpty(press)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "出版社不能为空"));
            return;
        }
        Date publishDate = null;
        try {
            publishDate = Utils.convertDate(publishDateStr, "yyyy-MM-dd");
        } catch (Exception e) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "请填写正确的日期格式，如2016-07-19"));
            return;
        }
        int wordCount = 0;
        try {
            wordCount = Integer.parseInt(wordCountStr);
        } catch (Exception e) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "字数不能为空"));
            return;
        }
        if (wordCount < 1) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "字数不能小于1"));
            return;
        }

        int pageCount = 0;
        try {
            pageCount = Integer.parseInt(pageCountStr);
        } catch (Exception e) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "页数不能为空"));
            return;
        }
        if (pageCount < 1) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "页数不能小于1"));
            return;
        }
        if (Utils.isEmpty(isbn)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "ISBN不能为空"));
            return;
        }
        if (Utils.isEmpty(description)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "图书描述不能为空"));
            return;
        }
        // 处理文件上传
        Part part = request.getPart("coverImage");

        // 得到上传文件需要保持的目录
        // 在tomcat中配置的"/bookstore/img"虚拟路径的绝对路径

        coverImage = Utils.fileupload(part, getServletContext().getInitParameter("fileuploadRootPath"));

        // 封装实体类
        Book book = new Book();
        book.setAuthor(author);
        book.setCategoryId(categoryId);
        book.setCoverImage(coverImage);
        book.setDescription(description);
        book.setIsbn(isbn);
        book.setName(name);
        book.setPageCount(pageCount);
        book.setPress(press);
        book.setPrice(price);
        book.setPublishDate(publishDate);
        book.setWordCount(wordCount);

        // 执行业务逻辑
        bookService.add(book);

        Utils.sendAjaxResponse(response, new AjaxResult("success", "添加图书成功"));

    }

    private void processManagerListJson(HttpServletRequest request, HttpServletResponse response) {
        int page = 1;// 相当于targetPage目标页
        int rows = 10;// 每页多少条数据
        String searchText = request.getParameter("searchText");
        // 参数中有则转换，没有则使用默认的
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
        }
        try {
            rows = Integer.parseInt(request.getParameter("rows"));
        } catch (Exception e) {
        }

        PageAjaxResult pageAjaxResult = new PageAjaxResult(rows, page);
        if (Utils.isEmpty(searchText)) {
            bookService.list(pageAjaxResult);
        } else {
            bookService.list(pageAjaxResult, "%" + searchText + "%");
        }
        // 发送json响应
        Utils.sendAjaxResponse(response, pageAjaxResult);

    }

    private void processDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book book = bookService.findById(id);

        request.setAttribute("book", book);
        request.getRequestDispatcher("/WEB-INF/jsp/bookDetail.jsp").forward(request, response);
    }

    private void processList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int targetPage = 1;// 目标页
        int size = 6;// 每页的个数

        Integer categoryId = null;// 图书类别

        String searchText = request.getParameter("searchText");// 按照书名或者作者模糊查询

        try {
            targetPage = Integer.parseInt(request.getParameter("targetPage"));
        } catch (Exception e) {
        }
        try {
            // 如果用户没有按照类别查询，此处就会抛异常，需要捕获该异常，并不做任何处理
            categoryId = Integer.parseInt(request.getParameter("categoryId"));
        } catch (Exception e) {
        }

        Page<Book> page = new Page<Book>(size, targetPage);

        // 由于最终的效果支持分页、条件查询，所以先把最简单的没有分页和条件查询的list做出来，然后一步步丰富功能
        // 条件查询和类别查询是互斥的，并且条件查询的优先级高
        if (!Utils.isEmpty(searchText)) {
            bookService.list(page, "%" + searchText + "%");
        } else if (categoryId != null) {
            bookService.list(page, categoryId);
        } else {
            bookService.list(page);
        }

        request.setAttribute("page", page);

        request.getRequestDispatcher("/WEB-INF/jsp/bookList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
