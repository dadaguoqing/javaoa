window.alert = function alertw(name) {
	$('.div_overlay').show();
	$('.div_content').show();
	$('#content').text(name);
}

$('#alertBtn').click(function(){
	$('.div_overlay').hide();
	$('.div_content').hide();
});