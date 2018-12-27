document.onreadystatechange = function () {
	var state = document.readyState
	if (state == 'complete') {
		$("#loadingmask2").css("display", "none");
	}
}
$(document).ready(function() {
	if ($("#loadingmask2").length > 0) {
		$("#loadingmask2").css("display", "none");
	}
});
