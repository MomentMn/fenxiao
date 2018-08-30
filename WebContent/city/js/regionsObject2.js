//jQuery 启动函数
$(function () {
    var key = JSON.parse(localStorage.getItem('regions'));
    if (key == "" || key == null) {
        $.ajax({
            url: "../datadict/list?parentCode=-1",
            type: "get",
            dataType: "json",
            async: false,
            success: function (data) {
                localStorage.setItem('regions', JSON.stringify(data));
            },
            error: function () {
                alertDefaultStyle("mini", "请求失败");
            }
        });
    }
})