<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript"  src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
<base href="<%=basePath%>">


<head>
    <script>
        $(function () {
            var name2id={};
          //  alert("ok");
            $("#customerName").typeahead({
               // source:["动力节点","国庆节","端午节"]
                source:function (query,process) {
                    $.ajax({
                        url:"workbench/controller/hehe.do",
                        data:{"name":query},
                        type:"post",
                        dataType:"json",
                        success:function (data) {
                       //     alert("ok");
                        //    alert(data.length)
                            var customerArr=[];
                            $.each(data,function (index,obj) {
                                customerArr.push(obj.name);
                                name2id[obj.name]=obj.id;

                            })
                           // alert(customerArr.length)


                            process(customerArr);
                        }
                    });



                },
                afterSelect:function (item) {
                    //alert(item);
                    alert(name2id[item]);

                }


            })

        })



    </script>

    <title>Title</title>
</head>
<body>
<input type="text" id="customerName"/>


</body>
</html>
