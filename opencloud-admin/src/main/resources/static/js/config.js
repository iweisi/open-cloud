layui.config({
    base: '../static/js/module/',
    version: false, //一般用于更新模块缓存，默认不开启。设为true即让浏览器不缓存。也可以设为一个固定的值，如：201610
    debug: false, //用于开启调试模式，默认false，如果设为true，则JS模块的节点会保留在页面
    apiPath: '@gateway.server-addr@' //替换变量,api网关地址
}).extend({
    tools: 'components/tools',
    dialog: 'components/dialog',
    transfer: 'components/transfer',
    steps: 'components/steps',
    navmenu: 'components/navmenu',
    treeselect: 'components/treeselect',
    treetable: 'components/treetable'
});
