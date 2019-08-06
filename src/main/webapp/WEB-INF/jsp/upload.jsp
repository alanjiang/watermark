<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path=request.getContextPath();
  String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
  request.setAttribute("ctxPath",basePath);
%>

<html>
<head>
<title>
word to pdf demo
</title>
  <script src="${ctxPath}/js/jquery.min.js"></script>
  <script src="${ctxPath}/js/jquery.form.js" type="text/javascript"></script> 
</head>


<body>
<form id="jvForm"  method="post" enctype="multipart/form-data">  

<table>  

<tbody>  

<tr>  

<td width="20%">  

<span>*</span>  

             上传WORD转PDF示例:</td>  

<td width="80%">  

                        仅支持WORD附件转PDF

</td>  

</tr>  

<tr>  

<td width="20%"></td>  

<td width="80%">  

<input name="attach" type="file" onchange="uploadPic()">  



</td>  

</tr>  

<tr>

<td>水印内容</td>

<td>
<input name="watermarkContent" type="text" value="Watermark Demo">
</td>
</tr>

<tr>

<td>字体大小</td>

<td>
<input name="fontSize" type="text" value="120">
</td>
</tr>


</tbody>  
</table>  
</form>  

</body>


<script>

function uploadPic() {  

	 
    // 上传设置  

    var options = {  

              // 规定把请求发送到那个URL  

              url: "${ctxPath}/upload/convert",  

              // 请求方式  

              type: "post",  

              // 服务器响应的数据类型  

              dataType: "json",  

              // 请求成功时执行的回调函数  

              success: function(data, status, xhr) {  
                  
                  // 图片显示地址  
                  
                   alert(JSON.stringify(data));

                  //$("#allUrl").attr("src", data.path);  

              }  

      };  

      $("#jvForm").ajaxSubmit(options);  

  }  
</script>



</script>
</html>
