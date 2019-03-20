/*
  FileReader共有4种读取方法：
  1.readAsArrayBuffer(file)：将文件读取为ArrayBuffer。
  2.readAsBinaryString(file)：将文件读取为二进制字符串
  3.readAsDataURL(file)：将文件读取为Data URL
  4.readAsText(file, [encoding])：将文件读取为文本，encoding缺省值为'UTF-8'
 */
var xlsxData;// 读取完成的数据
var rABS = true; // 是否将文件读取为二进制字符串

/**
 * 表格文件文件上传
 * 
 * @param obj
 */
function xlsxUpload(obj, head) {
	if (!obj.files) {
		return;
	}
	var file = obj.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		var data = e.target.result;
		if (rABS) {
			xlsxData = XLSX.read(btoa(fixData(data)), {// 手动转化
				type : 'base64'
			});
		} else {
			xlsxData = XLSX.read(data, {
				type : 'binary'
			});
		}

		var xData = xlsxData.Sheets[xlsxData.SheetNames[0]];
		var dataStr = xlsxData.Sheets[xlsxData.SheetNames[0]]['!ref'];
		var index = dataStr.substr(0, 1);
		var index2 = dataStr.substr(2, dataStr.length);
		// xlsxData.SheetNames[0]是获取Sheets中第一个Sheet的名字
		// xlsxData.Sheets[Sheet名]获取第一个Sheet的数据
		xlsxData.Sheets[xlsxData.SheetNames[0]]['!ref'] = index + head + index2;
		// 插入DIV
		var start = '<div class="container"><div class="row clearfix"><div class="col-md-12 column"><table class="table table-condensed table-bordered table-hover">';

		var obj = XLSX.utils
				.sheet_to_json(xlsxData.Sheets[xlsxData.SheetNames[0]]);// 拿到表格对象。默认表格第一行是字段，从第二行开始是数据
		// document.getElementById("demo").innerHTML = JSON.stringify(obj);// 输出
		console.log(obj);
		var row = '<thead><tr>'
		for ( var key in obj[0]) {
			row += '<td>' + key + '</td>';
		}
		row += '</tr></thead>';
		var col = '';
		for (var i = 0, l = obj.length; i < l; i++) {
			col += '<tr>';
			for ( var key in obj[0]) {
				var value = obj[i][key];
				if (!obj[i][key]) {
					value = 0;
				}
				col += '<td>' + value + '</td>';
			}
			col += '</tr>';
		}
		// var tb = ;
		var end = '</table></div></div></div>';
		var content = start + row + col + end;
		layer.open({
			type : 1,// Page层类型
			area : [ '1000px', '800px' ],
			title : '<a style="color:red;font-size:18px;">请仔细核对文件信息</a>',
			shade : 0.6,// 遮罩透明度
			maxmin : true,
			anim : 0,// 0-6的动画形式，-1不开启
			content : content
		});
	};
	if (rABS) {
		reader.readAsArrayBuffer(file);
	} else {
		reader.readAsBinaryString(file);
	}
}

/**
 * 文件流转BinaryString
 * 
 * @param data
 * @returns {string}
 */
function fixData(data) {
	var o = "";
	var l = 0;
	var w = 10240;
	for (; l < data.byteLength / w; ++l)
		o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l
				* w + w)));
	o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
	return o;
}