<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>

<div style="padding: 50px;">

    <!-- 列表区域 -->
    <script type="text/javascript">

        //categoryDatagrid加载数据成功后，在显示到表格中之前，为加载的数据中每行新增一个修改链接和删除链接
        function loadFilter(data){
            var rows = data.rows;
            for(var i=0;i<rows.length;i++){
                var row = rows[i];
                row.operate="<a href='#' onclick='openTab(\"<%=path%>/category?action=managerUpdate&id="+row.id+"\",\"修改图书分类\")'>修改</a>";
                row.operate=row.operate+" <a href='#' onclick='deleteEntity(\"<%=path%>/category\","+row.id+",\"categoryDatagrid\")'>删除</a>";
            }
            return data;
        }
        //查询
        function categorySearch(){
            var searchText = $("#categorySearchInput").val();
            var params = {"searchText":searchText};
            $("#categoryDatagrid").datagrid("reload",params);
        }
    </script>
    
    <!-- 工具栏 -->
    <div style="margin-bottom: 10px;">
        <input id="categorySearchInput" class="easyui-textbox" data-options="
                                        buttonIcon:'icon-search',
                                        buttonText:'分类名称模糊查询',
                                        onClickButton:categorySearch,
                                        width:300"/>
        &nbsp;&nbsp;&nbsp;&nbsp;                                        
        <a class="easyui-linkbutton" data-options=" iconCls:'icon-add'"  onclick="openTab('<%=path%>/category?action=managerAdd','添加图书分类')"  >添加图书分类</a>                                        
    </div>
    
    <table id="categoryDatagrid" class="easyui-datagrid" data-options="
                    url:'<%=path%>/category?action=managerListJson',
                    pagination:true,
                    fitColumns:true,
                    loadFilter:loadFilter">
        <thead>
            <tr>
                <th data-options="field:'name',width:100">图书分类名称</th>
                <th data-options="field:'operate',width:100">操作</th>
            </tr>
        </thead>
    </table>

</div>
