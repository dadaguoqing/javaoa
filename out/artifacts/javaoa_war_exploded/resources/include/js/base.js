/*
*/
var Geometry = {};

if(window.innerWidth){//All but IE
	Geometry.getViewportWidth = function(){ return window.innerWidth; }
	Geometry.getViewportHeight = function(){ return window.innerHeight; }
	Geometry.getHScroll = function(){ return window.pageXOffset }
	Geometry.getVScroll = function(){ return window.pageYOffset }
}else if(document.documentElement && document.documentElement.clientWidth){
	Geometry.getViewportWidth = function(){ return document.documentElement.clientWidth; }
	Geometry.getViewportHeight = function(){ return document.documentElement.clientHeight; }
	Geometry.getHScroll = function(){ return document.documentElement.scrollLeft; }
	Geometry.getVScroll = function(){ return document.documentElement.scrollTop; }
}else if(document.body.clientWidth){
	Geometry.getViewportWidth = function(){ return document.body.clientWidth; }
	Geometry.getViewportHeight = function(){ return document.body.clientHeight;}
	Geometry.getHScroll = function(){ return document.body.scrollLeft; }
	Geometry.getVScroll = function(){ return document.body.scrollTop; }
}

if(document.documentElement && document.documentElement.scrollWidth){
	Geometry.getDocWidth = function(){ return document.documentElement.scrollWidth; }
	Geometry.getDocHeight = function(){ return document.documentElement.scrollHeight; }
}else if(document.body.scrollWidth){
	Geometry.getDocWidth = function(){ return document.body.scrollWidth; }
	Geometry.getDocWidth = function(){ return document.body.scrollHeight;}
}