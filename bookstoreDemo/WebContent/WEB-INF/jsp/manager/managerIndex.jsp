<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员主页</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/easyui/themes/icon.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path%>/js/tools.js"></script>
<script type="text/javascript">
    //后台系统使用，用来退出系统
    function logout() {
        $.post('<%=path%>/user', {
            "action" : "logout",
            "isManagerLogout" : "yes"
        }, function(result) {
            if (result.status = "success") {
                window.location.href = "";// 此处的""表示地址栏当前的地址，因为已经退出了，所以会跳转到登录页面
            } else {
                alert(result.data);
            }
        }, 'json');
    }

    /**
     * 后台系统使用，删除一行数据，删除前会询问用户是否确定要删除
     * 
     * @param url
     * @param entityId
     *         要删除的数据的id
     * @param datagridId
     *         数据表格id，删除成功后会重新加载此数据表格
     */
    function deleteEntity(url, entityId, datagridId) {
        var really = confirm("确定要删除此条数据吗？");
        if (really) {
            // 请求参数
            var params = {
                "action" : "managerDeleteJson",
                "id" : entityId
            };
            // 使用ajax发送删除的请求
            $.post(url, params, function(result) {
                if (result.status == "success") {
                    // 删除成功后就重新加载datagrid
                    $("#" + datagridId).datagrid("load");
                }
                alert(result.data);
            }, 'json');
        }
    }
    /**
     * 后台系统使用，提交表单
     * 
     * @param formId
     * @param clearForm
     *         指定处理成功后是否清空表单
     * @param datagridId
     *         如果存在，如果处理成功后，则重新加载此datagrid
     */
    function submitForm(formId, clearForm, datagridId) {
        $("#" + formId).form("submit", {
            onSubmit : function() {
                return $("#" + formId).form("validate");
            },// 提交前先校验
            success : function(result) {
                // 如果响应被处理成文本，就把文本转换为json对象
                if (!result.status) {
                    result = eval("(" + result + ")");
                }
                if (result.status == "success") {
                    if (clearForm) {
                        $("#" + formId).form("clear");// 清空表单
                    }
                    //调用时如果传入此参数，则重新加载此数据表格
                    if(datagridId){
                        $("#" + datagridId).datagrid("reload");// 重新加载相关的datagridId    
                    }
                    
                }
                alert(result.data);// 提示用户处理结果
            }
        });
    }
    // 后台系统使用，打开一个tab页显示url指定的页面，如果此tab页本来就存在，则获取焦点
    function openTab(url, title) {
        var $tabs = $("#mainTabs");
        if ($tabs.tabs("exists", title)) {
            $tabs.tabs("select", title);
        } else {
            $tabs.tabs("add", {
                "title" : title,
                "href" : url,
                'closable' : true
            });
        }
    }
</script>
</head>

<body class="easyui-layout" style="font-size: large">

    <!--页面头部-->
    <div data-options="region:'north'" style="height: 70px;">
        <h1 style="margin-left: 40%; margin-top: 10px; margin-bottom: 5px">图书商城后台管理系统</h1>
        <a href="#" onclick="logout()" style="float: right; margin-right: 20px">退出</a>
    </div>

    <!--导航栏-->
    <div data-options="region:'west',title:'导航栏',split:true" style="width: 200px;">
        <ul class="easyui-tree">
            <li>
                <span>网站内容管理</span>
                <ul>
                    <li><span><a  onclick="openTab('<%=path%>/book?action=managerList','图书管理')">图书管理</a></span></li>
                    <li><span><a  onclick="openTab('<%=path%>/category?action=managerList','图书分类管理')">图书分类管理</a></span></li>
                </ul>
            </li>
            <li>
                <span>系统管理</span>
                <ul>
                    <li><span><a  onclick="openTab('<%=path%>/user?action=managerPasswordUpdate','修改密码')">修改密码</a></span></li>
                </ul>
            </li>
        </ul>
    </div>

    <!--主题区域-->
    <div data-options="region:'center'" style="background: #eee;">
        <!--主题区域是个tabs-->
        <div id="mainTabs" class="easyui-tabs" data-options="fit:true,border:false">
            <div title="欢迎使用"></div>  <!-- 默认tab页，不可关闭 -->
        </div>
    </div>

</body>
</html>