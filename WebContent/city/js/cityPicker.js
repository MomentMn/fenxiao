/**
 *
 * cityPicker.js
 * cityPicker is made by Framework picker component which effects like iOS native select.
 *
 * nzb329@163.com
 *
 * 2017-10-28
 *
 */
 ;
(function(window, $, undefined) {

        /**
         * [getProvince 获取省]
         * @param  {[Object]} regions [省市区数据]
         * @return {[Array]}          [省数组]
         */
        function getProvince(regions) {

            var provinceArr = [];

            $.each(regions, function() {
                provinceArr.push(this.provinceName);
            });

            return provinceArr;
        }

        /**
         * [getCity 获取市]
         * @param  {[Object]} regions      [省市区数据]
         * @param  {[String]} provinceName [省名]
         * @return {[Array]}               [市数组]
         */
        function getCity(regions, provinceName) {

            var cityArr = [];

            $.each(regions, function(i, province) {
                if (province.provinceName === provinceName) {
                    $.each(province.cities, function(j, city) {
                        cityArr.push(city.cityName);
                    });
                    return false;
                }
            });

            return cityArr;
        }

        /**
         * [getArea 获取区]
         * @param  {[Object]} regions      [省市区数据]
         * @param  {[String]} provinceName [省名]
         * @param  {[String]} cityName     [市名]
         * @return {[Array]}               [区数组]
         */
        function getArea(regions, provinceName, cityName) {

            var areaArr = [];

            $.each(regions, function(i, province) {
                if (province.provinceName === provinceName) {
                    $.each(province.cities, function(j, city) {
                        if (city.cityName === cityName) {
                            $.each(city.areas, function(k, area) {
                                areaArr.push(area.areaName);
                            });
                            return false;
                        }
                    });
                    return false;
                }
            });

            return areaArr;
        }
        //省市区数据
        regions=JSON.parse(localStorage.getItem('regions'));

    // 初始化 Framework7
    var myApp = new Framework7();

    // 初始化省市区
    var province = getProvince(regions),
        city = getCity(regions, '北京市'),
        area = getArea(regions, '北京市', '北京市');


    function getArea1(regions, provinceName, cityName, areaName) {

        var area1 = 0;

        $.each(regions, function(i, province) {
            if (province.provinceName === provinceName) {
                $.each(province.cities, function(j, city) {
                    if (city.cityName === cityName) {
                        $.each(city.areas, function(k, area) {
                            if(area.areaName === areaName){
                               area1=area.areaId;
                            }
                            //areaArr.push(area.areaName);
                        });
                        return false;
                    }
                });
                return false;
            }
        });

        return area1;
    }

    // 保存 picker 选择的省
    var provinceSelect = '';

    $("#commitimg").click(function (){
        //用户名
        var name = $("#name").val();
        if(name == ''){
            $("#span1").html("*必填项");
            return false;
        }else {
            $("#span1").html("");
        }
        //手机号
        var tel = $("#tel").val();
        if(tel == '' || !(/^1[34578]\d{9}$/.test(tel))){
            $("#span2").html("*必填项,正确手机号码");
            return false;
        }else {
            $("#span2").html("");
        }
        //确认手机号
        var oktel = $("#oktel").val();
        if(oktel == '' || !(/^1[34578]\d{9}$/.test(tel)) || oktel!=tel){
            $("#span3").html("*必填项,号码不一致");
            return false;
        }else {
            $("#span3").html("");
        }
        //地址
        var location = $("#location").val();
        if(location == ''){
            $("#span4").html("*必填项");
            return false;
        }else {
            $("#span4").html("");
        }
        var strs = location.split(" ");
        var a = getArea1(regions,strs[0],strs[1],strs[2]);
        $("#inp").val(a);
        //详细地址
        var content = $("#content").val();
        if(content == ''){
            $("#span5").html("*必填项");
            return false;
        }else {
            $("#span5").html("");
        }
        $("#commitimg").attr('src','../images/commit_hui.png');
        $.ajax({
            url: "/ordersPay",
            type: "post",
            dataType: "json",
            data: $('#form1').serialize(),
            async: false,
            success: function(data) {
                if ("1" == data.status) {
                    setTimeout(function () {
                        window.location.href='unifiedOrder?orderId='+data.oid;
                        return false;
                    },100);
                }
                else{
                    alertDefaultStyle("mini", data.message);
                }
            },
            error: function() {
                alertDefaultStyle("mini", "请求失败");
            }
        });

    });

    // 省市区联动 / Framework7 picker
    var pickerLocation = myApp.picker({
        input: '#location',
        rotateEffect: true,
        toolbarTemplate: '<div class="toolbar">\
                            <div class="toolbar-inner">\
                                <div class="left">\
                                    <a href="#" class="link close-picker">取消</a>\
                                </div>\
                                <div class="right">\
                                    <a href="#" class="link close-picker">完成</a>\
                                </div>\
                            </div>\
                        </div>',
        cols: [{
            cssClass: 'f-s-14',
            width: '33.33%',
            textAlign: 'left',
            values: province,
            onChange: function(picker, province) {
                if (picker.cols[1].replaceValues) {
                    provinceSelect = province;
                    city = getCity(regions, province);
                    area = getArea(regions, province, city[0]);
                    picker.cols[1].replaceValues(city);
                    picker.cols[2].replaceValues(area);
                }
            }
        },
            {
                cssClass: 'f-s-14',
                width: '33.33%',
                textAlign: 'center',
                values: city,
                onChange: function(picker, city) {
                    if (picker.cols[2].replaceValues) {
                        area = getArea(regions, provinceSelect, city);
                        picker.cols[2].replaceValues(area);
                    }
                }
            },
            {
                cssClass: 'f-s-14',
                width: '33.33%',
                textAlign: 'right',
                values: area,
            }
        ]
    });


})(window, jQuery);

