layui.define(['common'], function (exports) {
    //每个模块必须引入common否则一些公共配置无法使用
    var $ = layui.jquery,
        table = layui.table,
        apis = {
            info: '/server',
        };

    //图表
    var myChart;
    require.config({
        paths: {
            echarts: '../static/lib/echarts'
        }
    });
    require(
        [
            'echarts',
            'echarts/chart/bar',
            'echarts/chart/line',
            'echarts/chart/map'
        ],
        function (ec) {
            //--- 折柱 ---
            myChart = ec.init(document.getElementById('chart'));
            myChart.setOption(
                {
                    title: {
                        text: "数据统计",
                        textStyle: {
                            color: "rgb(85, 85, 85)",
                            fontSize: 18,
                            fontStyle: "normal",
                            fontWeight: "normal"
                        }
                    },
                    tooltip: {
                        trigger: "axis"
                    },
                    legend: {
                        data: ["会员", "文章", "评论"],
                        selectedMode: false,
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            dataView: {
                                show: false,
                                readOnly: true
                            },
                            magicType: {
                                show: false,
                                type: ["line", "bar", "stack", "tiled"]
                            },
                            restore: {
                                show: true
                            },
                            saveAsImage: {
                                show: true
                            },
                            mark: {
                                show: false
                            }
                        }
                    },
                    calculable: false,
                    xAxis: [
                        {
                            type: "category",
                            boundaryGap: false,
                            data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]
                        }
                    ],
                    yAxis: [
                        {
                            type: "value"
                        }
                    ],
                    grid: {
                        x2: 30,
                        x: 50
                    },
                    series: [
                        {
                            name: "会员",
                            type: "line",
                            smooth: true,
                            itemStyle: {
                                normal: {
                                    areaStyle: {
                                        type: "default"
                                    }
                                }
                            },
                            data: [10, 12, 21, 54, 260, 830, 710]
                        },
                        {
                            name: "文章",
                            type: "line",
                            smooth: true,
                            itemStyle: {
                                normal: {
                                    areaStyle: {
                                        type: "default"
                                    }
                                }
                            },
                            data: [30, 182, 434, 791, 390, 30, 10]
                        },
                        {
                            name: "评论",
                            type: "line",
                            smooth: true,
                            itemStyle: {
                                normal: {
                                    areaStyle: {
                                        type: "default"
                                    },
                                    color: "rgb(110, 211, 199)"
                                }
                            },
                            data: [1320, 1132, 601, 234, 120, 90, 20]
                        }
                    ]
                }
            );
        }
    );
    $(window).resize(function () {
        myChart.resize();
    })

    //服务器信息
    table.render({
        elem: '#server-info',
        url: apis.info, //数据接口
        method: 'get',
        height:"auto",
        page: false, //开启分页
        cols: [
            [ //表头
                {field: "serverInfo", title: '服务器环境',align:'center'},
                {field: 'ip', title: '服务器IP地址',align:'center'},
                {field: 'domain', title: '服务器域名',align:'center'},
                {field: 'apiGatewayAddr', title: '网关服务地址',align:'center'},
                {field: 'authServerAddr', title: '认证授权地址',align:'center'},
                {field: 'discoveryAddr', title: '服务发现地址',align:'center'},
                {field: 'services', title: '服务数量',align:'center',templet:function (d) {
                    return '<a href="http://'+d.discoveryAddr+'/nacos/index.html" target="_blank">'+d.services+'</a>';
                }},
                {field: 'javaVersion', title: 'JAVA版本',align:'center'},
                {field: 'percentFreeFmt', title: 'JVM检测',align:'center'},
                {field: 'serverDate', title: '服务器当前时间',align:'center'}
            ]
        ]
    });

    exports("home",{});
});