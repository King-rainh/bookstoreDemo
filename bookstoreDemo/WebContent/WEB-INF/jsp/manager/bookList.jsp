<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>

<div style="padding: 50px;">

    <!-- 列表区域 -->
    <script type="text/javascript">

        //bookDatagrid加载数据成功后，在显示到表格中之前，为加载的数据中每行新增一个修改链接和删除链接
        function loadFilter(data){
            var rows = data.rows;
            for(var i=0;i<rows.length;i++){
                var row = rows[i];
                row.operate="<a href='#' onclick='openTab(\"<%=path%>/book?action=managerUpdate&id="+row.id+"\",\"图书修改/详情\")'>修改/详情</a>";
                row.operate=row.operate+" <a href='#' onclick='deleteEntity(\"<%=path%>/book\","+row.id+",\"bookDatagrid\")'>删除(下架)</a>";
            }
            return data;
        }
        //查询图书
        function bookSearch(){
            var searchText = $("#bookSearchInput").val();
            var params = {"searchText":searchText};
            $("#bookDatagrid").datagrid("reload",params);
            //reload方法执行后params会被datagrid记住，随后的下一页等操作发出的请求也会携带params数据
        }
    </script>
    
    <!-- 工具栏 -->
    <div style="margin-bottom: 10px;">
        <input id="bookSearchInput" class="easyui-textbox" data-options="
                                        buttonIcon:'icon-search',
                                        buttonText:'书名/作者模糊查询',
                                        onClickButton:bookSearch,
                                        width:300"/>
        &nbsp;&nbsp;&nbsp;&nbsp;                                        
        <a class="easyui-linkbutton" data-options=" iconCls:'icon-add'"  onclick="openTab('<%=path%>/book?action=managerAdd','添加图书')"  >添加图书</a>                                        
    </div>
    
    <table id="bookDatagrid" class="easyui-datagrid" data-options="
                    url:'<%=path%>/book?action=managerListJson',
                    pagination:true,
                    fitColumns:true,
                    loadFilter:loadFilter">
        <thead>
            <tr>
                <th data-options="field:'name',width:100">图书名称</th>
                <th data-options="field:'price',width:100">价格</th>
                <th data-options="field:'author',width:100">作者</th>
                <th data-options="field:'publishDate',width:100">出版日期</th>
                <th data-options="field:'operate',width:100">操作</th>
            </tr>
        </thead>
    </table>

</div>
