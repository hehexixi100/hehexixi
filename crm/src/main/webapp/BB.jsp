<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <!--  JQUERY -->
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>

    <!--  BOOTSTRAP -->
    <link rel="stylesheet" type="text/css" href="jquery/bootstrap_3.3.0/css/bootstrap.min.css">
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

<%--    pagination--%>
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
    <title>Title</title>
</head>
<body>
<div id="demo_pag1"></div>

</body>
<script type="text/javascript">
    $(function () {
        $("#demo_pag1").bs_pagination({
            currentPage:2,
            rowsPerPage:3,
            totalRows:50,
            totalPages:44,
            visiblePageLinks:6,
            showGoToPage:false,
            showRowsPerPage:false,
            showRowsInfo:false,
            onChangePage:function (e,pageObj) {
                alert(pageObj.currentPage);
                alert(pageObj.rowsPerPage);

            }

        })

    })
</script>
</html>
