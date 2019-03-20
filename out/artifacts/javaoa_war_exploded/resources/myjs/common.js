function download(url, path) {
		path = encodeURIComponent(path);
		alert(path);
		window.open(url + '?path=' + path);
	}


// 打印页面
function printHtml() {
		var body1 = "<div style='width:80%'>" + $('#print').html() + "</div>";
		$('body').html(body1);
		window.print();
	}


// 导出页面文件
function exportExcel(table, fileName) {
		var html='<html><head><meta charset="UTF-8"></head><body><table>'
		+ table +'</table></body></html>';
		//base64 URL形式文件下载
		var oa = document.createElement('a');
		var blob = new Blob([html], { type: "application/vnd.ms-excel" });
	    // 利用URL.createObjectURL()方法为a元素生成blob URL
	    oa.href = URL.createObjectURL(blob);
		oa.download = fileName;
		document.body.appendChild(oa);
		oa.click();
	}


//校验字符串是否为空
function testStr(id, str) {
	if(!$('#' + id).val()) {
		layer.alert(str + '不能为空！', {icon :2});
		return true;
	}
	return false;
}


function testDouble(id, str) {
	var reg = /^([0-9]{1,}[.][0-9]*)$/;
	var reg2 = /^([0-9]{1,})$/;
	var num = $('#' + id).val();
	if (!num) {
		layer.alert(str + '不能为空！', {icon :2});
		$('#' + id).val(1);
		return true;
	}
	if ((!reg.test(num) && !reg2.test(num)) || num < 0) {
		layer.alert(str + '格式错误，请填写数字！', {icon : 2});
		$('#' + id).val(1);
		return true;
	}
	return false;
}

// 校验正整数
function testNum(id, str) {
		var reg = /^\d+$/;
		var num = $('#' + id).val();
		if (!num) {
			layer.alert(str + '不能为空！', {icon :2});
			$('#' + id).val(1);
			return true;
		}
		if (!reg.test(num) || num < 1) {
			layer.alert(str + '格式错误，请填写正整数！', {icon : 2});
			$('#' + id).val(1);
			return true;
		}
		return false;
	}


var excel_file = new Array('.xlsx', '.xls');
var word_file = new Array('.doc', '.docx');
var zip_file = new Array('.zip', '.rar');
var pdf_file = new Array('.pdf');
var cad_file = new Array('.cad');
var coordinate_file = new Array('.318');
function testFileFormat(id, arr, str) {
	for(var i = 0; i < arr.length; i++) {
		if(getFileFormat(id) == arr[i]) {
			return true;
		}
	}
	var formats = '';
	for(var i in arr) {
		formats += arr[i] + ' ';
	}
	layer.alert(str + '格式必须为：' + formats + '!', {icon : 2});
	return false;
}

// 获取上传文件的格式
function getFileFormat(id) {
	var fileName = $('#' + id).val();
	var suffix = fileName.substr(fileName.lastIndexOf(".")).toLowerCase();
	return suffix;
}

//判断是否是IE内核的浏览器
function isIE(){
	if(!!window.ActiveXObject || "ActiveXObject" in window){
		return true;
	}else{
		return false;
	}
}

// js数组去重
function unique(arr) {
	var len = arr.length;
	var result = []
	for (var i = 0; i < len; i++) {
		var flag = true;
		for (var j = i; j < arr.length - 1; j++) {
			if (arr[i] == arr[j + 1]) {
				flag = false;
				break;
			}
		}
		if (flag) {
			result.push(arr[i])
		}
	}
	return result;
}