// enter键提交搜索
$(window).keydown(function(e){
    var curKey = e.which; 
    if(curKey == 13){
    	submitForm();
    }
})