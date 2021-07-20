<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/6/29 0029
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>

    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>



<html>
<head>
    <title>Title</title>
</head>
<body>
heheeh
<input type="text" id="birthday" />
</body>
<script type="text/javascript">
   $(function () {
       $("#birthday").datetimepicker({
           language:'zh-CN',
           format:'yyyy-mm-dd',
           minView:'month',
           autoclose:true,
           todayBtn:true,
           clearBtn:true

       });
   })


</script>
</html>
