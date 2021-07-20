<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/7/9 0009
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="jquery/jquery-1.11.1-min.js"></script>
<script src="jquery/echarts/echarts.min.js"></script>
<html>
<script type="text/javascript">
    $(function () {
       var myChart = echarts.init(document.getElementById("main"));
      // alert("ok");
       var optionl={
           title:{
               text:'echarts入门'
           },
           tooltip:{},
           legend:{
               data:['销量']
           },
           xAxis: {
               data:['衬衫','裙子','裤子','袜子','鞋子']
           },
           yAxis:{},
           series:[{
               name:'销量',type:'bar',data:[5,12,15,20,10]
           }],

       }
       myChart.setOption(optionl);

    })
</script>
<head>
    <title>Title</title>
</head>
<body>
        <div id="main" style="width: 600px; height: 400px"></div>
</body>
</html>
