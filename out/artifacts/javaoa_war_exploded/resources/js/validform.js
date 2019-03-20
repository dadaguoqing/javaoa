// 正整数(不包括小数)：/^[+]{0,1}(\d+)$/
// 正数（包括小数）:/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/
var zzs = /^[+]{0,1}(\d+)$/;
var zs = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
function testZs(obj){
	// 校验包括小数的正数
	if(!zs.test($(obj).val())) {
		layer.msg('请输入正数');
	}
}
function testZzs(obj){
	var str;
	// 校验包括小数的正数
	if(!zzs.test($(obj).val())) {
		layer.msg('请输入正数');
	}
}

function testString(obj,str) {
	var ov = $(obj).val();
	if(!ov) {
		lay.msg(str + "不能为空");
		return;
	}
}