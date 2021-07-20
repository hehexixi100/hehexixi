<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            //给"创建"按钮添加单击事件
            $("#createDicTypeBtn").click(function () {
                //发送请求
                window.location.href = "settings/dictionary/type/toSave.do";
            });

            //实现"全选"和"取消全选"
            //给"全选"按钮添加单击事件
            $("#chkedAll").click(function () {
                //让列表中的checkbox的checked属性值和全选按钮的checked属性值保持一致
                $("#tBody input[type='checkbox']").prop("checked", $("#chkedAll").prop("checked"));
            });

            //给列表中所有的checkbox添加单击事件
            $("#tBody input[type='checkbox']").click(function () {
                //获取列表中所有的checkbox
                //获取列表中所有被选中的checkbox
                //比较两个选择选中的元素的个数：相等--列表中所有的checkbox都选中了，否则，至少有一个没选中
                //得到一个jquery对象，对象中保存着一个由所有选中元素的dom对象组成数组，size()返回数组的长度。
                if ($("#tBody input[type='checkbox']").size() == $("#tBody input[type='checkbox']:checked").size()) {
                    $("#chkedAll").prop("checked", true);
                } else {
                    $("#chkedAll").prop("checked", false);
                }
            });



            $("#editDicTypeBtn").on("click",function () {

               // alert("ok");
                //alert($("#tBody input[type='checkbox']:checked").size());
               var checkboxSize=$("#tBody input[type='checkbox']:checked").size();

               if(checkboxSize==0){
                   alert("请选择需要编辑的对象！")
                   return;
               }

               if(checkboxSize>1){
                   alert("不能选择多个！")
                   return;
               }
               var code = $("#tBody input[type='checkbox']:checked").val();
               //alert(code);
                window.location.href="${pageContext.request.contextPath}/settings/dictionary/type/toEdit.do?code="+code;

            })

            $("#deleteDicTypeBtn").on("click",function () {
               // alert("ok");
                var checkedsize=$("#tBody input[type='checkbox']:checked").size();
              //  alert(checkedsize);
                if(checkedsize==0){
                    alert("请选择需要删除的对象！")
                }
               var checkedboxs=$("#tBody input[type='checkbox']:checked");
                var str="";
                $.each(checkedboxs,function (index,item) {
                   // alert(item.value);
                    str+="code="+item.value+"&";
                })
              // alert(str);
                str=str.substr(0,str.length-1);
               // alert(str);
                if(confirm("确定要删除吗？")){
                    $.ajax({
                        url:"${pageContext.request.contextPath}/settings/dictionary/type/toDelete.do",
                        data:str,
                        type:"post",
                        dataType:"json",
                        success:function (data) {
                            if(data.code==0){
                                alert(data.message);
                            }else {
                                window.location.href="${pageContext.request.contextPath}/settings/dictionary/type/index.do";
                            }

                        }
                    });

                }

            })

            //全选
            $("#checkboxAll").on("click",function () {
                $("#tBody input[type='checkbox']").prop("checked",$("#checkboxAll").prop("checked"));
            })

            //反选
            $("#tBody input[type='checkbox']").on("click",function () {
                if( $("#tBody input[type='checkbox']").size()== $("#tBody input[type='checkbox']:checked").size()){
                    $("#checkboxAll").prop("checked",true);
                }else {
                    $("#checkboxAll").prop("checked",false);
                }

            })





        });
    </script>
</head>
<body>

<div>
    <div style="position: relative; left: 30px; top: -10px;">
        <div class="page-header">
            <h3>字典类型列表</h3>
        </div>
    </div>
</div>
<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
    <div class="btn-group" style="position: relative; top: 18%;">
        <button id="createDicTypeBtn" type="button" class="btn btn-primary"><span
                class="glyphicon glyphicon-plus"></span> 创建
        </button>
        <button id="editDicTypeBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-edit"></span>
            编辑
        </button>
        <button id="deleteDicTypeBtn" type="button" class="btn btn-danger"><span
                class="glyphicon glyphicon-minus"></span> 删除
        </button>
    </div>
</div>
<div style="position: relative; left: 30px; top: 20px;">
    <table class="table table-hover">
        <thead>
        <tr>
            <th><input type="checkbox" id="checkboxAll"/></th>
            <th>序号</th>
            <th>编码</th>
            <th>名称</th>
            <th>描述</th>
        </tr>
        </thead>


        <tbody id="tBody">
        <c:forEach items="${dicTypeList}" var="dt" varStatus="vs">
            <c:if test="${vs.count%2 != 0}">
                <tr class="active">
                    <td><input type="checkbox" value="${dt.code}"/></td>
                    <td>${vs.count}</td>
                    <td>${dt.code}</td>
                    <td>${dt.name}</td>
                    <td>${dt.description}</td>
                </tr>
            </c:if>
            <c:if test="${vs.count%2 == 0}">
                <tr >
                    <td><input type="checkbox" value="${dt.code}"/></td>
                    <td>${vs.count}</td>
                    <td>${dt.code}</td>
                    <td>${dt.name}</td>
                    <td>${dt.description}</td>
                </tr>
            </c:if>

        </c:forEach>
        </tbody>

    </table>
</div>

</body>
</html>