/**
 * 穿梭框
 */
layui.define(['jquery', 'layer'], function (exports) {
        var $ = layui.jquery,
            form = layui.form;
    var MOD_NANE = 'transfer';

    $.fn.transfer = function (options) {
        var opts = $.extend({}, $.fn.transfer.defaults, options);
            var that = this;
        if (opts.name && opts.name.indexOf("[") == -1) {
            opts.name += "[]";
            }
            layui.use(['tools'], function () {
                var tools = layui.tools;
                opts.id = 'trf_' + tools.uuid();
                that.render();
                that.bind();
            })
        this.render = function () {
            //获取下拉控件的name
            var name = opts.name;
            //获取下拉控件的默认值
            var values = opts.value;
            var id = opts.id;
            //获取复选框禁用的值
            var disableds = opts.disabled || "";
            //查询款/级联父级
            var searchHtml = [
                '<div lay-value="" class="transfer-search-div">',
                '<i class="layui-icon  drop-search-btn"></i>',
                '<input class="layui-input search_condition" placeholder="关键字搜索">',
                '<i class="layui-icon  clear-btn search-clear-btn">&#x1006;</i>',
                '</div>'
            ].join("");

            var data = opts.data;
            //选中列表
            var leftList = "";
            //未选中列表
            var rightList = "";

            if (data !== undefined) {
                for (var i = 0; i < data.length; i++) {
                    //左侧
                    if (values.indexOf(data[i].value) == -1) {
                        var _input = '<li class="transfer-item" lay-value="' + data[i].value + '" lay-title="' + data[i].text + '"><input type="checkbox" lay-filter="transferLeftChecked" title="' + data[i].text + '" lay-skin="primary"></li>';
                        //设置禁用
                        if (disableds.indexOf(data[i].value) != -1) {
                            _input = _input.replace("<input", "<input disabled ")
                        }
                        leftList += _input;
                    }
                    //右侧
                    else {
                        var _input = '<li class="transfer-item" lay-value="' + data[i].value + '"  lay-title="' + data[i].text + '"><input type="hidden" name="' + name + '" value="' + data[i].value + '"><input lay-filter="transferRightChecked"   type="checkbox"  title="' + data[i].text + '" lay-skin="primary"></li>';
                        //设置禁用
                        if (disableds.indexOf(data[i].value) != -1) {
                            _input = _input.replace("<input", "<input disabled ")
                        }
                        rightList += _input;
                    }
                    this.append(_input);
                }
            }

            /** 渲染结果**/
            var outHtml = $([
                '<div gen="' + id + '" class="transfer-content">',
                '<div class="transfer-panel transfer-panel-left">',
                searchHtml,
                '<ul class="transfer-div">',
                leftList,
                '</ul>',
                '</div>',
                '<div class="transfer-btn transfer-to-right">',
                '<button title="右移" type="button"  class="layui-btn layui-btn-normal layui-btn-sm layui-btn-disabled"><i class="layui-icon">&#xe65b;</i></button>',
                '</div>',
                '<div class="transfer-btn  transfer-to-left">',
                '<button title="左移"  type="button" class="layui-btn layui-btn-normal layui-btn-sm layui-btn-disabled"><i class="layui-icon">&#xe65a;</i></button>',
                '</div>',
                '<div class="transfer-panel transfer-panel-right">',
                '<div lay-value="" class="transfer-search-div">',
                '<span  class="transfer-title" >',
                ' 已选列表',
                '</span>',
                '</div>',
                '<ul class="transfer-div">',
                rightList,
                '</ul>',
                '</div>',
                '</div>'
            ].join(""));
            this.html(outHtml);
            form.render();
            return this;
        };

        this.bind = function () {
            //左侧选中
            form.on('checkbox(transferLeftChecked)', function (data) {
                var $this = $(data.othis);
                var _parent = $this.parents(".transfer-content");
                var inputs = $this.parents(".transfer-div").find("li input[type='checkbox']");
                for (var i = 0; i < inputs.length; i++) {
                    if ($(inputs[i]).is(':checked')) {
                        _parent.find(".transfer-to-right button").removeClass("layui-btn-disabled");
                        break;
                    }
                    _parent.find(".transfer-to-right button").addClass("layui-btn-disabled");
                }
            });
            //右侧选中
            form.on('checkbox(transferRightChecked)', function (data) {
                var $this = $(data.othis);
                var _parent = $this.parents(".transfer-content");
                var inputs = $this.parents(".transfer-div").find("li input[type='checkbox']");
                for (var i = 0; i < inputs.length; i++) {
                    if ($(inputs[i]).is(':checked')) {
                        _parent.find(".transfer-to-left button").removeClass("layui-btn-disabled");
                        break;
                    }
                    _parent.find(".transfer-to-left button").addClass("layui-btn-disabled");
                }
            });

            //右移监听
            $(document).on("click", "#" + opts.id + " .transfer-to-right", function () {
                var _name = opts.name;
                var $this = $(this);
                var $parent = $this.parents(".transfer-content");
                var inputs = $parent.find(".transfer-panel-left .transfer-div").find("li input[type='checkbox']");
                for (var i = 0; i < inputs.length; i++) {
                    if ($(inputs[i]).is(':checked')) {
                        //右侧添加
                        var _value = $(inputs[i]).parents("li").attr("lay-value");
                        var _title = $(inputs[i]).parents("li").attr("lay-title");
                        var _input = ['<li class="transfer-item" lay-value="' + _value + '" lay-title="' + _title + '"><input type="hidden" name="' + _name + '" value="' + _value + '">',
                            '<input lay-filter="transferRightChecked"  ',
                            ' type="checkbox"  title="' + _title + '" lay-skin="primary"></li>'
                        ].join("");
                        _value && _title && $parent.find(".transfer-panel-right .transfer-div").append(_input);
                        //左侧删除
                        $(inputs[i]).parents("li").remove();
                    }
                }
                //重置按钮禁用
                $parent.find(".transfer-to-right button").addClass("layui-btn-disabled");
                form.render('checkbox');
            });

            //左移监听
            $(document).on("click", "#" + opts.id + " .transfer-to-left", function () {
                var $this = $(this);
                var $parent = $this.parents(".transfer-content");
                var inputs = $parent.find(".transfer-panel-right .transfer-div").find("li input[type='checkbox']");
                for (var i = 0; i < inputs.length; i++) {
                    if ($(inputs[i]).is(':checked')) {
                        //右侧添加
                        var _value = $(inputs[i]).parents("li").attr("lay-value");
                        var _title = $(inputs[i]).parents("li").attr("lay-title");
                        var _input = ['<li class="transfer-item" lay-value="' + _value + '" lay-title="' + _title + '">',
                            '<input lay-filter="transferLeftChecked"  ',
                            ' type="checkbox"  title="' + _title + '" lay-skin="primary"></li>'
                        ].join("");
                        _value && _title && $parent.find(".transfer-panel-left .transfer-div").append(_input);
                        //右侧删除
                        $(inputs[i]).parents("li").remove();
                    }
                }
                //重置按钮禁用
                $parent.find(".transfer-to-left button").addClass("layui-btn-disabled");
                form.render('checkbox');
            });

            /**搜索监听  **/
            $(document).on("keyup", ".transfer-search-div .search_condition", function (e) {
                searchData($(this));
            });

            /**清空搜索条件**/
            $(document).on("click", ".transfer-search-div .search-clear-btn", function (event) {
                $(this).prev().val("");
                searchData($(this));
            });

            /**获取搜索后的数据  **/
            function searchData($this) {
                var value = $($this).val();
                var $parent = $this.parents(".transfer-content");
                var lis = $parent.find(".transfer-panel-left .transfer-div").find("li");
                //显示搜索结果菜单
                var k = value;
                var patt = new RegExp(k);
                for (var i = 0; i < lis.length; i++) {
                    if (k == "") {
                        $(lis[i]).show();
                    }
                    else if (patt.test($(lis[i]).attr("lay-title"))) {
                        $(lis[i]).show();
                    }
                    else {
                        $(lis[i]).hide();
                    }
                }

            }

            return this;
        };
        return this;
    };
    //默认参数
    $.fn.transfer.defaults = {
        data: null,
        value: [],
        disabled: [],
        name: ''
    };
        /**
         * 接口输出
         */
        exports(MOD_NANE, $);
    }
);
