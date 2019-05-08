<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<!-- 根据商品的id，查询商品规格参数并且完成数据在页面的展示-->
<ul class="paramItemPram">
</ul>
<form id="itemeEditForm" class="itemForm" method="post">
		<input type="hidden" name="id"/>
	    <table cellpadding="5">
	        <tr class="params hide">
				<td>商品规格:</td>
				<td></td>
			</tr>
		</table>
</form>
<script type="text/javascript">
	//$.messager.alert('提示', '商品规格信息查询成功');
	$(function() {
		$.post("/item/page/params/155620608526913", function(data) {
			if (data.status == 200) {
				//解析json数据
				var paramsData = JSON.parse(data.data);
				var html = "<ul>";
				for ( var i in paramsData) {
					//动态页面的生成
					var group = paramsData[i].group;
					 html+="<li><table>";
					 html+="<tr><td colspan=\"2\" class=\"group\">"+group+"</td></tr>";
					for (var j in paramsData[i].params) {
						var key = paramsData[i].params[j].k;
						var value = paramsData[i].params[j].v;
						html+="<tr><td class=\"param\" style=\"width:80px;text-align:right\"><span>"+key+"</span>: </td><td><input autocomplete=\"off\" type=\"text\" value=\""+value+"\"/></td></tr>";
					}
					html+="</li></table>";
				}
				html+= "</ul>";
				//$(".paramItemPram").html(html);
				$(".params td").eq(1).html(html);
			}

		});
	});
</script>