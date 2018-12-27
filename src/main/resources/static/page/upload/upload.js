var d4 = d4 || {};
d4.fileupload = d4.fileupload || {};

(window.jQuery)
$(document).ready(function(){
	d4.fileupload.init();
});


d4.fileupload.init = function() {
	$("#saveBtn").on("click", d4.fileupload.save);
}

d4.fileupload.save = function() {
	var fileData = $('#uploadId');
	if ($('#uploadId')[0].files[0] == undefined) {
		alert("Upload file is required");
		return false;
	}
	var formData = new FormData();
	formData.append('file', $('#uploadId')[0].files[0]);
	
		$.ajax({
			url : "/file/upload",
			type : "POST",
			data : formData,
			enctype : 'multipart/form-data',
			processData : false,
			contentType : false,
			cache : false,
			success : function(data) {
				d4.fileupload.load("filestorage/all");
				 $("#uploadId").val("");
			},
			error : function(data) {
				alert(data.message);
			}
		});
		return;
}

d4.fileupload.load = function(url) {
	$("#loadingmask2").show();
	$.get( url, function(data) {
		d4.fileupload.destroyDataTable("#listdata1");
		$("#view").html(data);
		
		$("#listdata1").DataTable({
			"columnDefs": [ {
			"targets": 'no-sort',
			"orderable": false,
			} ]
		});
		$(".downloadBt").bind("click", d4.fileupload.download);
		console.log( "success" );
		})
		.done(function() {
			console.log( "second success" );
		})
		.fail(function() {
			console.log( "error" );
		})
		.always(function() {
			console.log( "finished" );
			$("#loadingmask2").hide();
		});
}

d4.fileupload.destroyDataTable = function(target) {
	try {
		if ($(target + " tbody tr").length != 0) {
			$(target).dataTable().fnDestroy();
			$(target + " tbody tr").remove();
		}
	} catch (e) {
		console.log("Error while destroying data table.....");
		console.log(e);
	}
}

d4.fileupload.download = function () {
	
}