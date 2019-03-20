
var maskDiv = '<div class="l-mask"></div>';

function mask(){
	//var h = Geometry.getViewportHeight();
	//var w = Geometry.getViewportWidth();
	var lMask = $('.l-mask');
	var len = lMask.length;
	if(len>0){
		lMask.css('display','block');
	}else{
		$('body').append(maskDiv);
	}
	//$('.l-mask').css({width:w+'px',height:h+'px'});
}

function unmask(){
	var lMask = $('.l-mask');
	var len = lMask.length;;
	if(len>0){
		lMask.css('display','none');
	}
}