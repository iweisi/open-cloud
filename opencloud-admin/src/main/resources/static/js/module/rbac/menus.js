layui.define(['treetable', 'treeselect', 'common'], function (exports) {
    //每个模块必须引入common否则一些公共配置无法使用
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table,
        treetable = layui.treetable,
        treeselect = layui.treeselect,
        common = layui.common,
        apis = {
            list: layui.cache.apiPath + '/rbac/menus',
            listAll: layui.cache.apiPath + '/rbac/menus/all',
            add: layui.cache.apiPath + '/rbac/menus/add',
            update: layui.cache.apiPath + '/rbac/menus/update',
            remove: layui.cache.apiPath + '/rbac/menus/remove',
            disable: layui.cache.apiPath + '/rbac/menus/disable',
            enable: layui.cache.apiPath + '/rbac/menus/enable'
        };

    var data = [];

    $('.addBtn').click(function () {
        formPage();
        return false;
    });

    var getSelectTree = function () {
        var treeData = treeselect.listConvert(data, {
            primaryKey: 'menuId',
            startPid: 0,
            parentKey: 'parentId',
            nameKey: 'menuName',
            valueKey: 'menuId',
        });
        return treeselect.treeConvertSelect(treeData, {
            childKey: 'list'
        });
    }

    var loadMenus = function () {
        $.ajax({
            url: apis.listAll,
            type: 'post',
            async: false, // 同步加载
            success: function (res) {
                data = res.data.list;
            }
        });
    }
    //加载菜单
    loadMenus();

    treetable.render({
        treeColIndex: 1,
        treeSpid: 0,
        treeIdName: 'menuId',
        treePidName: 'parentId',
        treeDefaultClose: true,
        treeLinkage: false,
        elem: '#table-list',
        data: data,
        page: false,
        cols: [
            [ //表头
                {field: 'menuId', title: 'ID', sort: true},
                {field: 'menuName', title: '名称'},
                {field: 'menuCode', title: '编码', sort: true},
                {field: 'url', title: '路径'},
                {field: 'parentId', title: 'PID'},
                {field: 'priority', title: '优先级', sort: true},
                {field: 'description', title: '描述'},
                {
                    field: 'enabled', title: '状态', sort: true, templet: function (d) {
                    var html = '<input type="checkbox" data-id="' + d.menuId + '" name="enabled" value="' + d.enabled + '" lay-skin="switch"  lay-text="启用|禁用"  lay-filter="enableTable"  ' + (d.enabled ? "checked" : "") + '/>';
                    return html;
                }
                },
                {field: 'createTime', title: '创建时间', sort: true},
                {
                    fixed: 'right', title: '操作', align: 'center', width: 150, templet: function (d) {
                    var html = '';
                    html += '<a href="javascript:;" lay-event="edit">编辑</a>&nbsp;&nbsp;';
                    html += '<a href="javascript:;" lay-event="del">删除</a>&nbsp;';
                    return html;
                }
                }
            ]
        ]
    });
    treetable.expandAll('#table-list');

    var formPage = function (data) {
        if (!data) {
            data = {
                menuId: '',
                menuName: '',
                menuCode: '',
                url: '',
                parentId: 0,
                priority: '10',
                enabled: 'true',
                description: ''
            };
        }
        data.list = getSelectTree();
        //你也可以采用下述同步写法，将 render 方法的回调函数剔除，可直接返回渲染好的字符
        var html = laytpl($('#menus-form').html()).render(data);
        common.page("菜单添加", type = 1, html, w = "750px", h = "620px", function (page) {
        });
    }

    //监听行工具事件
    table.on('tool(table)', function (obj) {
        var data = obj.data;
        //console.log(obj)
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del();
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            $.ajax({
                url: apis.list + "/" + data.menuId,
                type: 'get',
                success: function (res) {
                    if (res.code === 0) {
                        formPage(res.data);
                    } else {
                        layer.msg("获取菜单失败");
                    }
                }
            })
        }
    });

    //表格启用开关
    form.on('switch(enableTable)', function (obj) {
        var id = $(obj.elem).data('id');
        var url;
        if (obj.elem.checked) {
            url = apis.enable;
        } else {
            url = apis.disable;
        }
        $.ajax({
            beforeSend: function () {
                // 防止重复提交
                $(obj.elem).attr("disabled", true);
            },
            url: url,
            type: 'post',
            data: {menuId: id},
            success: function (res) {
                if (res.code != 0) {
                    layer.msg("修改失败");
                }
            }
            , complete: function () {
                $(obj.elem).attr("disabled", false);
            }
        })
    });

    //表单下拉选择
    form.on('select(parentId)', function (obj) {
        console.log(obj.elem) //被执行事件的元素DOM对象，一般为button对象
        console.log(obj.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log(obj.elem) //当前容器的全部表单字段，名值对形式：{name: value}
    });

    //表单开关
    form.on('switch(enabledForm)', function (obj) {
        console.log(obj.elem) //被执行事件的元素DOM对象，一般为button对象
        console.log(obj.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log(obj.elem) //当前容器的全
    });

    //表单提交
    form.on('submit(form)', function(obj){
        console.log(obj.elem) //被执行事件的元素DOM对象，一般为button对象
        console.log(obj.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log(obj.elem) //当前容器的全部表单字段，名值对形式：{name: value}
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    exports("rbacMenu", {});
});