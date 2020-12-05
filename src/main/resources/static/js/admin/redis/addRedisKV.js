$(document).ready(function() {
$('#addModal').on('show.bs.modal', function(event) {
			let button = $(event.relatedTarget);
			let operator = button.data('whatever'); 
			if(operator == 'add') {
				$("#addModalLabel").text("Add redis-data");
			}
		});
		
		
		$('#addModal').on("click", ".plus_btn", function() {
			let dataType = $(this).attr("value1");
			
			let formEle;
			let addHtml = '';
			switch(dataType) {
			case 'STRING':
				//never go here
				break;
			case 'LIST':
				formEle = $(this).parent().parent().parent();
				addHtml = listHtml;
				break;
			case 'SET':
				formEle = $(this).parent().parent().parent();
				addHtml = setHtml;
				break;
			case 'ZSET':
				formEle = $(this).parent().parent().parent().parent().parent().parent();
				addHtml = zsetHtml;
				break;
			case 'HASH':
				formEle = $(this).parent().parent().parent().parent().parent().parent();
				addHtml = hashHtml;
				break;
			}
			$(formEle).append(clearLineHtml);
			$(formEle).append(addHtml);
		});
		
		$('#addModal').on("click", ".minus_btn", function() {
			let dataType = $(this).attr("value1");
			
			let inputEle;
			let addHtml = '';
			switch(dataType) {
			case 'STRING':
				//never go here
				break;
			case 'LIST':
				inputEle = $(this).parent().parent();
				break;
			case 'SET':
				inputEle = $(this).parent().parent();
				break;
			case 'ZSET':
				inputEle = $(this).parent().parent().parent().parent().parent();
				break;
			case 'HASH':
				inputEle = $(this).parent().parent().parent().parent().parent();
				break;
			}
			$(inputEle).remove();
		});
		
		
		$("#addModal_dateType").on("change", function() {
			let dataType = $(this).val();
			let addModalForm = $("#addModalForm");
			
			let addHtml = '';
			switch(dataType) {
			case 'STRING':
				addHtml = stringHtml;
				break;
			case 'LIST':
				addHtml = listHtml;
				break;
			case 'SET':
				addHtml = setHtml;
				break;
			case 'ZSET':
				addHtml = zsetHtml;
				break;
			case 'HASH':
				addHtml = hashHtml;
				break;
			}
			$("#addModalForm").find(".input_div").remove();
			$("#addModalForm>span:last-child").text(dataType + ' values');
			$(addModalForm).append(addHtml);
		})
		
		$(".add_KV_btn").on("click", function() {
			let addFormParam = $("#addModalForm").formSerialize();
			
			let url = basePath + '/redis/KV';
			$.ajax({
				type: "post",
				url: url,
				dataType: "json",
				data: addFormParam,
				success: function(data) {
					modelAlert(data, 2000);
				}
			})
			
		})
});