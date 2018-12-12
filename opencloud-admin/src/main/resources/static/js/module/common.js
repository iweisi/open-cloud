layui.define(['layer', 'jquery', 'form', 'table', 'tools', 'laytpl'], function (exports) {
    var $ = layui.jquery,
        table = layui.table,
        tools = layui.tools,
        form = layui.form;

    var common = {
        /**
         * 打开窗口页面
         * @param title 标题
         * @param url 路径
         * @param w 宽
         * @param h 高
         * @param okcallback 回调
         */
        page: function (title, type, content, w, h, success) {
            if (!type) {
                type = 2;
            }

            var index;
            if (title == null || title == '') {
                title = false;
            }
            if (w == null || w == '') {
                w = '700px';
            }
            if (h == null || h == '') {
                h = '350px';
            }

            function dosuccess(lay, index) {
                if (success && $.isFunction(success)) {
                    success(lay, index);
                }
            }

            //如果手机端，全屏显示
            if (window.innerWidth <= 768) {
                index = layer.open({
                    type: type,
                    title: title,
                    area: [320, h],
                    fixed: false, //不固定
                    content: content,
                    success: function (layero, index) {
                        dosuccess(layero, index);
                        form.render();
                    }
                });
                layer.full(index);
            } else {
                index = layer.open({
                    type: type,
                    title: title,
                    fixed: false, //不固定
                    area: [w, h],
                    content: content,
                    success: function (layero, index) {
                        dosuccess(layero, index);
                        form.render();
                    }
                });
            }

            this.close = function () {
                layer.close(index);
            }
        }
    }
//表格设定全局默认参数
    var options = {
        height: 'full-150',
        method: 'post',
        parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
            return {
                "code": res.code, //解析接口状态
                "msg": res.message, //解析提示文本
                "count": res.data.total, //解析数据长度
                "data": res.data.list //解析数据列表
            };
        }
    };
    table.set(options);

// AJAX全局设置
    $.ajaxSetup({
        type: "POST",
        error: function (xhr, status, error) {
            switch (xhr.status) {
                case(500):
                    layui.layer.msg("服务器系统内部错误");
                    break;
                case(401):
                    layui.layer.msg("未登录");
                    break;
                case(403):
                    layui.layer.msg("无权限执行此操作");
                    break;
                case(408):
                    layui.layer.msg("请求超时");
                    break;
                default:
                    break;
            }
        }
    });

// AJAX全局设置,前置过滤器
    $.ajaxPrefilter(function (options, originalOptions, xhr) {
        // options对象 包括accepts、crossDomain、contentType、url、async、type、headers、error、dataType等许多参数选项
        // originalOptions对象 就是你为$.ajax()方法传递的参数对象，也就是 { url: "/index.php" }
        // jqXHR对象 就是经过jQuery封装的XMLHttpRequest对象(保留了其本身的属性和方法)
        if (options.url !== '/sign') {
            if (Object.prototype.toString.call(options.data) == "[object FormData]") {
            } else if (Object.prototype.toString.call(options.data) == "[object String]") {
                if (Object.prototype.toString.call(originalOptions.data) == "[object Object]") {
                    options.data = $.param($.extend(originalOptions.data || {}, {}));
                } else if (Object.prototype.toString.call(originalOptions.data) == "[object String]") {
                }
            }
            var data = {};
            //服务器签名参数并返回.
            layui.$.ajax({
                url: '/sign',
                type: 'post',
                async: false, // 同步加载
                data: options.data,
                success: function (res) {
                    if (res.code === 0) {
                        data = res.data;
                    }
                }
            })
            // 加入请求头
            var token = tools.getCookie("token");
            if (token) {
                options.headers = {
                    "Authorization": "Bearer " + token
                }
            }
            options.data = $.param(data);
        }
    });

    $(document).on("click", '[data-toggle="topTab"]', function (e) {
        var element = window.parent.layui.element;
        if (!element) {
            element = layui.element;
        }
        var navA = $(this);
        var id = navA.attr('data-id');
        var url = navA.attr('data-url');
        var text = navA.attr('data-text');
        if (!url) {
            return;
        }
        var document = window.parent.document;
        if (!document) {
            document = window.document;
        }
        var isActive = $(document).find('.main-layout-tab .layui-tab-title').find("li[lay-id=" + id + "]");

        if (isActive.length > 0) {
            //切换到选项卡
            element.tabChange('tab', id);
        } else {
            element.tabAdd('tab', {
                title: text,
                content: '<iframe src="' + url + '" name="iframe' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
                id: id
            });
            element.tabChange('tab', id);
        }
    });


    exports("common", common);
});