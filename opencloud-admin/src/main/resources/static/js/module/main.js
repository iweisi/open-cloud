layui.define([ 'navmenu', 'common'], function (exports) {
    var $ = layui.jquery,
        hideBtn = $('#hideBtn'),
        mainLayout = $('#main-layout'),
        mainMask = $('.main-mask'),
        apis={
            list:layui.cache.apiPath +'/rbac/permissions/user/menus'
        }

    //菜单隐藏显示
    hideBtn.on('click', function () {
        if (!mainLayout.hasClass('hide-side')) {
            mainLayout.addClass('hide-side');
        } else {
            mainLayout.removeClass('hide-side');
        }
    });
    //遮罩点击隐藏
    mainMask.on('click', function () {
        mainLayout.removeClass('hide-side');
    })

    //菜单数据格式化
    function menuFilter(data) {
        var menus = [];
        if (data) {
            $.each(data, function (i, t) {
                var m = {
                    id: t.id,
                    name: t.name,
                    url: t.url,
                    pid: t.resourcePid,
                    icon: ''
                }
                menus.push(m)
            })
        }
        return menus;
    }

    //加载菜单
    $.ajax({
        url: apis.list,
        type: 'get',
        success: function (res) {
            if (res.code === 0) {
                $("#left-menu").navmenu({navTopId: '#top-menu', data: menuFilter(res.data)});
            }
        }
    })
    exports("main",{});
});