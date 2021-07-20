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
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <!--  PAGINATION plugin -->
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
    <script type="text/javascript">

        $(function () {


            /* $("#heeh").bs_pagination({
                 currentPage: 1,
                 rowsPerPage: 2,
                 totalRows: 7,
                 totalPages: 11,
                 visiblePageLinks: 5,
                 showGoToPage: true,


                 showRowsInfo: false,
                 showRowsPerPage: false,
                 onChangePage: function (e, pageObj) {
                     selectActivityForPageByConditionll(pageObj.currentPage, pageObj.rowsPerPage);

                 }

             })*/


            $("#createActivityBtn").on("click",function () {
                $("#createActivityModal").modal("show");

            });

            //给"保存"按钮添加单击事件
            $("#saveCreateActivityBtn").click(function () {
                //收集参数
                var owner = $("#create-marketActivityOwner").val();
                var name = $.trim($("#create-marketActivityName").val());
                var startDate = $("#create-startDate").val();
                var endDate = $("#create-endDate").val();
                var cost = $.trim($("#create-cost").val());
                var description = $.trim($("#create-description").val());
                //表单验证
                if (owner == "") {
                    alert("所有者不能为空");
                    return;
                }
                if (name == "") {
                    alert("名称不能为空");
                    return;
                }
                if (startDate != "" && endDate != "") {
                    //验证开始日期和结束日期
                    if (startDate > endDate) {
                        alert("结束日期不能比开始日期小");
                        return;
                    }
                }

                var regExp = /^(([1-9]\d*)|0)$/;
                if (!regExp.test(cost)) {
                    alert("成本只能是非负整数");
                    return;
                }

                //发送请求
                $.ajax({
                    url: 'workbench/activity/saveCreateActivity.do',
                    data: {
                        owner: owner,
                        name: name,
                        startDate: startDate,
                        endDate: endDate,
                        cost: cost,
                        description: description
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        if (data.code == "1") {
                            //关闭模态窗口
                          //  $("#createActivityModal").modal("hide");
                            $("#createActivityModal").modal("hide");
                            //刷新市场活动列表
                            //alert("创建工程，局部刷新市场活动列表");
                          //  queryActivityForPageByCondition(1,$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
                              queryActivityForPageByCondition(1,$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
                        } else {
                            //提示信息
                            alert(data.message);
                            //模态窗口不关闭
                            $("#createActivityModal").modal("show");//只要去掉了data-dismiss属性，此行代码可以不写，但是建议写上。
                        }
                    }
                });
            });

            //当容器加载完成，对容器调用工具函数
            // alert("ok");
            $(".mydate").datetimepicker({

                language: 'zh-CN',//语言
                format: 'yyyy-mm-dd',//日期格式
                minView: 'month',//日期选择器上最小能选择的日期的视图
                initialDate: new Date(),// 日历的初始化显示日期，此处默认初始化当前系统时间
                autoclose: true,//选择日期之后，是否自动关闭日历
                todayBtn: true,//是否显示当前日期的按钮
                clearBtn: true,//是否显示清空按钮
                container: '#createActivityModal'//在创建的模态窗口上显示日历
            });

            $("input[name='edit']").datetimepicker({

                language: 'zh-CN',//语言
                format: 'yyyy-mm-dd',//日期格式
                minView: 'month',//日期选择器上最小能选择的日期的视图
                initialDate: new Date(),// 日历的初始化显示日期，此处默认初始化当前系统时间
                autoclose: true,//选择日期之后，是否自动关闭日历
                todayBtn: true,//是否显示当前日期的按钮
                clearBtn: true,//是否显示清空按钮
                container: '#editActivityModal'//在创建的模态窗口上显示日历
            });


            $("input[name='ok']").datetimepicker({

                language: 'zh-CN',//语言
                format: 'yyyy-mm-dd',//日期格式
                minView: 'month',//日期选择器上最小能选择的日期的视图
                initialDate: new Date(),// 日历的初始化显示日期，此处默认初始化当前系统时间
                autoclose: true,//选择日期之后，是否自动关闭日历
                todayBtn: true,//是否显示当前日期的按钮
                clearBtn: true,//是否显示清空按钮
                // container: '#createActivityModal'//在创建的模态窗口上显示日历
            });


            //当页面加载完成之后，显示所有数据的第一页，默认每页显示10条
            queryActivityForPageByCondition(1, 2);

            //给"查询"按钮添加单击事件
            $("#queryActivityBtn").click(function () {
               // alert("ok");
                //显示所有符合条件的数据的第一页，默认每页显示10条
                queryActivityForPageByCondition(1, $("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
            });

            //给"修改"按钮添加单击事件
            $("#editActivityBtn").click(function () {
                //收集参数
                var chkedIds = $("#tBody input[type='checkbox']:checked");
                //表单验证
                if (chkedIds.size() == 0) {
                    alert("请选择要修改的记录");
                    return;
                }
                if (chkedIds.size() > 1) {
                    alert("一次只能修改一条记录");
                    return;
                }
                var id = chkedIds.get(0).value;
                //发送请求
                $.ajax({
                    url: 'workbench/activity/editActivity.do',
                    data: {
                        id: id
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        //把市场活动的信息设置到form表单中
                        $("#edit-id").val(data.id);
                        $("#edit-marketActivityOwner").val(data.owner);
                        $("#edit-marketActivityName").val(data.name);
                        $("#edit-startDate").val(data.startDate);
                        $("#edit-endDate").val(data.endDate);
                        $("#edit-cost").val(data.cost);
                        $("#edit-description").val(data.description);
                        //显示修改市场活动的模态窗口
                        $("#editActivityModal").modal("show");
                    }
                });
            });

            //给"更新"按钮添加单击事件
            $("#saveEditActivityBtn").click(function () {
                //收集参数
                var id = $("#edit-id").val();
                var owner = $("#edit-marketActivityOwner").val();
                var name = $.trim($("#edit-marketActivityName").val());
                var startDate = $("#edit-startDate").val();
                var endDate = $("#edit-endDate").val();
                var cost = $.trim($("#edit-cost").val());
                var description = $.trim($("#edit-description").val());
                //表单验证(作业)
                if(name==""){
                    alert("活动名称不能为空！")
                    return

                }
                if(startDate>endDate){
                    alert("开始日期时间不能超过结束时间！")
                    return;

                }
                var regExp = /^(([1-9]\d*)|0)$/;
                if(!regExp.test(cost)){
                    alert("应为非负整数！")
                    return;

                }
                //发送请求
                $.ajax({
                    url: 'workbench/activity/saveEditActivity.do',
                    data: {
                        id: id,
                        owner: owner,
                        name: name,
                        startDate: startDate,
                        endDate: endDate,
                        cost: cost,
                        description: description
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        if (data.code == "1") {
                            //关闭模态窗口
                            $("#editActivityModal").modal("hide");
                            //刷新市场活动列表，保持页号和每页显示条数都不变
                            queryActivityForPageByCondition($("#demo_pag1").bs_pagination('getOption', 'currentPage'), $("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
                        } else {
                            //提示信息
                            alert(data.message);
                            //模态窗口不关闭
                            $("#editActivityModal").modal("show");
                        }
                    }
                });
            });

            //给"删除'按钮添加单击事件
            $("#deleteActivityBtn").click(function () {
                //收集参数
                var chkedIds = $("#tBody input[type='checkbox']:checked");
                //表单验证
                if (chkedIds.size() == 0) {
                    alert("请选择要删除的记录");
                    return;
                }
                //遍历chkedIds，生成id=xx&id=xx&.....&id=xx
                var ids = "";
                $.each(chkedIds, function () {
                    ids += "id=" + this.value + "&";
                });
                //ids:id=xx&id=xx&.....&id=xx&
                ids = ids.substr(0, ids.length - 1);

                if (window.confirm("确定删除吗？")) {
                    //发送请求
                    $.ajax({
                        url: 'workbench/activity/deleteActivityByIds.do',
                        data: ids,
                        type: 'post',
                        dataType: 'json',
                        success: function (data) {
                            if (data.code == "1") {
                                //刷新市场活动列表，显示第一页数据，保持每页显示条数不变
                                queryActivityForPageByCondition(1, $("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
                            } else {
                                //提示信息
                                alert(data.message);
                            }
                        }
                    });
                }
            });

            //实现全选和取消全选(作业)
            $("#chked_all").click(function () {
                $("#tBody input[type='checkbox']").prop("checked", this.checked);
            });

            /*$("#tBody input[type='checkbox']").click(function () {
                if ( $("#tBody input[type='checkbox']").size()==$("#tBody input[type='checkbox']:checked").size()){
                    $("#chked_all").prop("checked",true);
                }else{
                    $("#chked_all").prop("checked",false);
                }
            });*/

            $("#tBody").on("click", "input[type='checkbox']", function () {
                if ($("#tBody input[type='checkbox']").size() == $("#tBody input[type='checkbox']:checked").size()) {
                    $("#chked_all").prop("checked", true);
                } else {
                    $("#chked_all").prop("checked", false);
                }
            });

            //给"批量导出"按钮添加单击事件
            $("#exportActivityAllBtn").click(function () {
                //发送同步请求
                window.location.href = "workbench/activity/exportAllActivity.do";
            });

            $("#exportActivityXzBtn").on("click",function () {
               // alert("ok");
               var checkedboxs= $("#tBody input[type='checkbox']:checked");
               if(checkedboxs.size()==0){
                   alert("请选择需要导出的信息");
               }
               var str="";
               // id=000&id=111&id=222&
               $.each(checkedboxs,function (index,item) {
                   str+="id="+item.value+"&";

               })
                // id=000&id=111&id=222
               str= str.substring(0,str.length-1);
              // alert(str);
                window.location.href="${pageContext.request.contextPath}/workbench/activity/exportAllActivity.do?"+str;

            })

            //给"上传列表数据(导入)"按钮添加单击事件
            $("#importActivityListBtn").click(function () {
                $("#importActivityModal").modal("show");
            });

            $("#importActivityBtn").on("click",function () {
            // alert($("#activityFile").val());
             //判断是否是Excel文件，.xls结尾
                var fileName = $("#activityFile").val();
               var fileNameSuffix= fileName.substr(fileName.lastIndexOf(".")+1).toUpperCase()

               // alert(fileName.substr(fileName.lastIndexOf(".")+1).toUpperCase())
                if(fileNameSuffix != "XLS"){
                    alert("操作仅针对Excel，仅支持后缀名为XLS的文件！");

                }
                //alert($("#activityFile")[0].files[0].size);
                var fileSize=$("#activityFile")[0].files[0].size;
                if(fileSize>8*1024*1024){
                    alert("请确认您的文件大小不超过5MB！")
                }
                var myFile=$("#activityFile")[0].files[0];
                var  formData = new FormData;
                formData.append("myFile",myFile);
                formData.append("userName","tom");

                $.ajax({
                    url:"${pageContext.request.contextPath}/workbench/activity/Upfile.do",
                    data:formData,
                    type:"post",
                    dataType:"json",
                    processData:false,
                    contentType:false,
                    success:function (data) {
                        alert("ok");

                    }
                })



            })



        });

        /*function selectActivityForPageByConditionll(currentPage, rowsPerPage) {
            $.ajax({
                url: "${pageContext.request.contextPath}/workbench/controller/toPaging.do",
                data: {"currentPage": currentPage, "rowsPerPage": rowsPerPage},
                type: "post",
                dataType: "json",
                success: function (data) {
                    var str = "";
                    $.each(data, function (index, obj) {
                        str += "<tr class=\"active\">";
                        //   str += "<td><input type=\"checkbox\"  value=\"" + obj.id + "\"/></td>";
                        str += "<td><input type=\"checkbox\" value=\"" + obj.id + "\" /></td>";
                        str += "<td>" + obj.name + "</td>";
                        str += "<td>" + obj.owner + "</td>";
                        str += "<td>" + obj.startDate + "</td>";
                        str += "<td>" + obj.endDate + "</td>";
                        str += "</tr>";

                    });
                    // $.each(data, function (index, obj) {
                    //     str += "<tr class=\"active\">";
                    //   str += "<td><input type=\"checkbox\"  value=\"" + obj.id + "\"/></td>";
                    //   //  htmlStr += "<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detailActivity.do?id=" + obj.id + "'\">" + obj.name + "</a></td>";
                    //     str += "<td>" + obj.owner + "</td>";
                    //     str += "<td>" + obj.startDate + "</td>";
                    //     str += "<td>" + obj.endDate + "</td>";
                    //     str += "</tr>";
                    // });


                    $("#tBody").html(str);


                }
            })

        }*/


        function queryActivityForPageByCondition(pageNo, pageSize) {
            //alert("ok");
            var name = $.trim($("#query-name").val());
            var owner = $.trim($("#query-owner").val());
            var startDate = $("#query-startDate").val();
            var endDate = $("#query-endDate").val();

            $.ajax({
                url:"${pageContext.request.contextPath}/workbench/controller/toPaging.do",
                data:{
                    "pageSize":pageSize,
                    "pageNo":pageNo,
                    "name":name,
                    "owner":owner,
                    "startDate":startDate,
                    "endDate":endDate
                },
                type:"post",
                success:function (data) {
                  //  alert("ok");
                  //表格拼接
                  var htmlStr="";
                  $.each(data.activityList,function (index,obj) {
                      htmlStr+="<tr>";
                      htmlStr+="<td><input type=\"checkbox\" value=\""+obj.id +"\"/></td>";
//"<td><a  onclick=\"window.location.href='workbench/activity/detailActivity.do?id=" + obj.id + "'\">" + obj.name + "</a></td>";
                      //"<td><a href=\"workbench/controller/todetail.do?id=\"" +obj.id+ ">"
                      htmlStr+="<td><a  onclick=\"window.location.href='workbench/controller/todetail.do?id=" + obj.id + "'\">"+obj.name+"</a></td>";
                      htmlStr+="<td>"+obj.owner +"</td>";
                      htmlStr+="<td>"+obj.startDate+"</td>";
                      htmlStr+=" <td>"+obj.endDate +"</td>";
                      htmlStr+="</tr>";
          //


                  });
                  //异步刷新
                  $("#tBody").html(htmlStr);

                  var totalPages = 1;
                  //计算总页数
                  if(data.totals % pageSize == 0){
                      totalPages=data.totals / pageSize;
                  }else {
                      totalPages=parseInt(data.totals / pageSize)+1;
                  }

                    //分页
                    $("#demo_pag1").bs_pagination({
                        currentPage:pageNo,
                        rowsPerPage:pageSize,
                        totalRows:data.totals,
                        totalPages:totalPages,
                        visiblePageLinks:5,
                        showGoToPage:true,
                        onChangePage:function (e,pageObj) {
                            queryActivityForPageByCondition(pageObj.currentPage, pageObj.rowsPerPage)

                        }
                    })

                }


            })





        }
    </script>
</head>
<body>

<%--============================--%>
<%--<tr>--%>
<%--    <td><input type="checkbox" value="编号"/></td>--%>
<%--    <td>名称</td>--%>
<%--    <td>所有者</td>--%>
<%--    <td>开始日期</td>--%>
<%--    <td>结束日期</td>--%>
<%--</tr>--%>
<%--============================--%>


<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form" id="activityForm">

                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-marketActivityOwner">
                                <c:forEach items="${userList}" var="u">
                                    <option value="${u.id}">${u.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-marketActivityName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control mydate" id="create-startDate" readonly="true">
                        </div>
                        <label for="create-endDate" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control mydate" id="create-endDate" readonly="true">
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-description" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-description"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="saveCreateActivityBtn" type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">
                    <input type="hidden" id="edit-id">
                    <div class="form-group">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-marketActivityOwner">
                                <c:forEach items="${userList}" var="u">
                                    <option value="${u.id}">${u.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startDate" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-startDate" value="2020-10-10" name="edit">
                        </div>
                        <label for="edit-endDate" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-endDate" value="2020-10-20" name="edit">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost" value="5,000">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-description" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="edit-description">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="saveEditActivityBtn" type="button" class="btn btn-primary">更新</button>
            </div>
        </div>
    </div>
</div>

<!-- 导入市场活动的模态窗口 -->
<div class="modal fade" id="importActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
            </div>
            <div class="modal-body" style="height: 350px;">
                <div style="position: relative;top: 20px; left: 50px;">
                    请选择要上传的文件：<small style="color: gray;">[仅支持.xls格式]</small>
                </div>
                <div style="position: relative;top: 40px; left: 50px;">
                    <input type="file" id="activityFile">
                </div>
                <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;">
                    <h3>重要提示</h3>
                    <ul>
                        <li>操作仅针对Excel，仅支持后缀名为XLS的文件。</li>
                        <li>给定文件的第一行将视为字段名。</li>
                        <li>请确认您的文件大小不超过5MB。</li>
                        <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                        <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                        <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                        <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                    </ul>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text" id="query-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text" id="query-owner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control" type="text" id="query-startDate" name="ok"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control" type="text" id="query-endDate" name="ok"/>
                    </div>
                </div>

                <button id="queryActivityBtn" type="button" class="btn btn-default">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <button id="createActivityBtn" type="button" class="btn btn-primary"><span
                        class="glyphicon glyphicon-plus"></span> 创建
                </button>
                <button id="editActivityBtn" type="button" class="btn btn-default"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button id="deleteActivityBtn" type="button" class="btn btn-danger"><span
                        class="glyphicon glyphicon-minus"></span> 删除
                </button>
            </div>
            <div class="btn-group" style="position: relative; top: 18%;">
                <button id="importActivityListBtn" type="button" class="btn btn-default"><span
                        class="glyphicon glyphicon-import"></span> 上传列表数据（导入）
                </button>
                <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span
                        class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）
                </button>
                <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span
                        class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）
                </button>
            </div>
        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="chked_all"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody id="tBody">

<%--                <c:forEach items="${activityList}" var="activity" varStatus="vs">--%>
<%--                   --%>
<%--                    <tr class="active">--%>
<%--                    <td><input type="checkbox" value="${activity.id}"/></td>--%>
<%--                    <td>${activity.name}</td>--%>
<%--                    <td>${activity.owner}</td>--%>
<%--                    <td>${activity.startDate}</td>--%>
<%--                    <td>${activity.endDate}</td>--%>
<%--                    </tr>--%>
<%--                </c:forEach>--%>

                <%--<tr class="active">
                    <td><input type="checkbox" /></td>
                    <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                    <td>zhangsan</td>
                    <td>2020-10-10</td>
                    <td>2020-10-20</td>
                </tr>
                <tr class="active">
                    <td><input type="checkbox" /></td>
                    <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                    <td>zhangsan</td>
                    <td>2020-10-10</td>
                    <td>2020-10-20</td>
                </tr>--%>
                </tbody>
            </table>

            <!--创建容器-->
            <div id="demo_pag1"></div>
        </div>
        <%--<div style="height: 50px; position: relative;top: 30px;">
            <div>
                <button type="button" class="btn btn-default" style="cursor: default;">共<b id="totalRowsB">50</b>条记录</button>
            </div>
            <div class="btn-group" style="position: relative;top: -34px; left: 110px;">
                <button type="button" class="btn btn-default" style="cursor: default;">显示</button>
                <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                        10
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#">20</a></li>
                        <li><a href="#">30</a></li>
                    </ul>
                </div>
                <button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
            </div>
            <div style="position: relative;top: -88px; left: 285px;">
                <nav>
                    <ul class="pagination">
                        <li class="disabled"><a href="#">首页</a></li>
                        <li class="disabled"><a href="#">上一页</a></li>
                        <li class="active"><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">下一页</a></li>
                        <li class="disabled"><a href="#">末页</a></li>
                    </ul>
                </nav>
            </div>
        </div>--%>

    </div>

</div>
</body>
</html>