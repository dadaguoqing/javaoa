$.fn.pager = function(page, total, callback) {
    var html = '';
    html += '<a  href="javascript:;">首页</a>';
    html += '<a  href="javascript:;">上一页</a>';
    var start = page - 5 < 0 ? 0 : page - 5;
    var end = page + 5 < total ? page + 5 : total;
    for (var i = start; i < end; i++) {
        html += i == page - 1 ? '<span>' + (i + 1) + '</span>' : '<a href="javascript:;">' + (i + 1) + '</a>';
    }
    html += '<a  href="javascript:;">下一页</a>';
    html += '<a class="last" href="javascript:;">末页</a>';
    $(this).html(html).find('a').click(function() {
        var p = $(this).text();
        if (p == '上一页') p = page == 1 ? 1 : page - 1;
        if (p == '下一页') p = page == total ? total : page + 1;
        if (p == '首页') p = 1;
        if (p == '末页') p = total;
        if (p != page) callback(parseInt(p));
    });
}
//onload = function() {
//    //用用回调
//    function go(p) {
//        $('.pager').pager(p, 82, go);
//    }
//    $('.pager').pager(1, 82, go);
//}