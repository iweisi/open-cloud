layui.define(['jquery', 'element'], function (exports) {
        var $ = layui.jquery,
            element = layui.element;
        var MOD_NANE = 'navmenu';
        $.fn.navmenu = function (options) {
            var that = this;
            var opts = $.extend({}, $.fn.navmenu.defaults, options);
            var _createChildrenNode = function (node) {
                var children = [];
                var data = opts.data;
                $.each(data, function (i, t) {
                    if (node.id === t.pid) {
                        t.children = _createChildrenNode(t);
                        if (t.children && t.children.length > 0) {
                            t.hasChild = true;
                        } else {
                            t.hasChild = false;
                        }
                        children.push(t);
                    }
                })
                return children;
            };

            this.createTreeNode = function () {
                var roots = [];
                var data = opts.data;
                if (data) {
                    $.each(data, function (i, t) {
                        if (!t.pid || t.pid === '0') {
                            t.children = _createChildrenNode(t);
                            if (t.children && t.children.length > 0) {
                                t.hasChild = true;
                            } else {
                                t.hasChild = false;
                            }
                            roots.push(t);
                        }
                    })
                }
                opts.treeNodes = roots;
                return roots;
            };

            //初始化叶子节点
            var _initChildrenMenu = function (childMenus, a) {
                if (!childMenus) {
                    return;
                }
                var $dl = $('<dl class="layui-nav-child"></dl>');
                $.each(childMenus, function (i, t) {
                    var $dd = $('<dd></dd>');
                    var $a = $('<a href="javascript:;"></a>');
                    var $span = $('<span class="layui-nav-down"></span>');
                    $a.append($('<span>' + t.name + '</span>'));
                    $dd.append($a);
                    $dl.append($dd);
                    $(a).parent().append($dl);
                    $a.attr("data-id", t.id);
                    $a.attr("data-text", t.name);
                    if (t.hasChild) {
                        $a.append($span);
                        _initChildrenMenu(t.children, $a);
                    }
                    if (!t.hasChild && t.url) {
                        $a.attr("data-url", t.url);
                    }
                });
            };

            var _createMenu = function (nodes) {
                if (!nodes) {
                    return;
                }
                var $ul = $('<ul class="layui-nav layui-nav-tree left-nav" lay-filter="leftNav"></ul>');
                $.each(nodes, function (i, t) {
                    var $li = $('<li class="layui-nav-item">');
                    var $a = $('<a href="javascript:;">');
                    $a.append($('<i class="iconfont"></i>'));
                    $a.append($('<span>' + t.name + '</span>'));
                    $li.append($a);
                    $ul.append($li);
                    //data-url="article-list.html" data-id='3' data-text="文章管理"
                    $a.attr("data-id", t.id);
                    $a.attr("data-text", t.name);
                    if (t.hasChild) {
                        var $span = $('<span class="layui-nav-more"></span>');
                        $a.append($span);
                        _initChildrenMenu(t.children, $a);
                    }
                    if (!t.hasChild && t.url) {
                        $a.attr("data-url", t.url);
                    }
                });
                $(that).html($ul);
                //重新渲染组件
            }

            this.initMenu = function () {
                this.createTreeNode();
                var nodes = opts.treeNodes;
                var $navTop = $(opts.navTopId);
                if (!nodes) {
                    return;
                }
                if ($navTop[0]) {
                    // 开启顶部导航
                    var $ul = $('<ul class="layui-nav  top-nav f-l" lay-filter="topNav"></ul>');
                    $.each(nodes, function (i, t) {
                        var $li = $('<li class="layui-nav-item">');
                        var $a = $('<a href="javascript:;">');
                        $a.append($('<i class="iconfont"></i>'));
                        $a.append($('<span>' + t.name + '</span>'));
                        $li.append($a);
                        $ul.append($li);
                        //data-url="article-list.html" data-id='3' data-text="文章管理"
                        $a.attr("data-id", t.id);
                        $a.attr("data-text", t.name);
                        $a.attr("data-index", i);
                        if (!t.hasChild && t.url) {
                            $a.attr("data-url", t.url);
                        }
                    });
                    $navTop.html($ul);
                } else {
                    //加载左侧菜单
                    _createMenu(nodes);
                }
                element.render('nav');
                $('.top-nav .layui-nav-item a').eq(0).trigger('click');
            };
            //顶部菜单动态加载
            element.on('nav(topNav)', function (elem) {
                var navA = $(elem);
                var index = navA.attr('data-index');
                if (!index) {
                    return;
                }
                var node = opts.treeNodes[index];
                _createMenu(node.children);
                element.render('nav');
            });

            //监听导航点击
            element.on('nav(leftNav)', function (elem) {
                var navA = $(elem);
                var id = navA.attr('data-id');
                var url = navA.attr('data-url');
                var text = navA.attr('data-text');
                if (!url) {
                    return;
                }
                var isActive = $('.main-layout-tab .layui-tab-title').find("li[lay-id=" + id + "]");

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
            that.initMenu();
            return this;
        };

        /**
         *data:
         *  [
         {"gen": "1", "name": "父节点1", "url": "http://www.baidu.com", "pid": 0, "icon": ""},
         {"gen": "4", "name": "父节点11", "url": "http://www.baidu.com", "pid": "1", "icon": ""},
         {"gen": "13", "name": "父节点111", "url": "http://www.baidu.com", "pid": "4", "icon": ""},
         * @type {{navTopId: string, data: Array}}
         */
        //默认参数
        $.fn.navmenu.defaults = {
            navTopId: '',//顶部导航Id
            data: []
        };
        exports(MOD_NANE, $);
    }
);