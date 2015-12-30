<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Ztree demo</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" href="./css/demo.css">
<link rel="stylesheet" type="text/css"
	href="./css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="./js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="./js/jquery.ztree.core-3.5.js"></script>


<script type="text/javascript">
	var setting = {
		data : {
			simpleData : {
				enable : true
			}
		},
		async : {
			enable : true,
			url : "tree.action",
			autoParam : [ "id=pid" ],
			dataFilter : filter
		//异步返回后经过Filter 
		},
		callback : {
			asyncSuccess : zTreeOnAsyncSuccess,//异步加载成功
			asyncError : zTreeOnAsyncError, //加载错误 
			//beforeClick : beforeClick,
			onClick : zTreeOnClick
		//捕获单击节点之前的事件回调函数 
		}
	};

	function zTreeOnClick(event, treeId, treeNode) {
		if (!treeNode.isParent) {
			alert(treeNode.tId + ", " + treeNode.name);
		}
	};

	function filter(treeId, parentNode, childNodes) {

		if (!childNodes)
			return null;
		for (var i = 0, l = childNodes.length; i < l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}
	function beforeClick(treeId, treeNode) {
		if (!treeNode.isParent) {
			alert("请选择父节点");
			return false;
		} else {
			zTree.expandNode(treeNode);
			return true;
		}
	}
	function zTreeOnAsyncError(event, treeId, treeNode) {
		alert("异步加载失败!");
	}
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {

	}

	var zNodes = [];

	$(document).ready(function() {
		$.fn.zTree.init($("#tree"), setting, zNodes);
	});
</script>
<body>
	<ul id="tree" class="ztree"></ul>
</body>
</html>
