// 基于准备好的dom，初始化echarts实例
// var myChart = echarts.init(document.getElementById('gauge'));

// 指定图表的配置项和数据
var gauge_opt = {
    tooltip : {
        formatter: "{a} <br/>{b} : {c}%"
    },
    toolbox: {
        feature: {
            restore: {},
            saveAsImage: {}
        }
    },
    series: [
        {
            name: '任务数量',
            type: 'gauge',
            detail: {formatter:'{value}项'},
            data: [{value: 50, name: '任务数量'}]
        }
    ]
};


// gauge_opt.series[0].data[0].value = app.readyPCBTable.length;

// 使用刚指定的配置项和数据显示图表。
// myChart.setOption(gauge_opt);

// var rose_opt = {
//     title : {
//         text: '任务数量统计',
//         x:'center'
//     },
//     tooltip : {
//         trigger: 'item',
//         formatter: "{a} <br/>{b} : {c} ({d}%)"
//     },
//     legend: {
//         x : 'center',
//         y : 'bottom',
//         data:['已完成','就绪状态','正在运行','未到达']
//     },
//     toolbox: {
//         show : true,
//         feature : {
//             mark : {show: true},
//             dataView : {show: true, readOnly: false},
//             magicType : {
//                 show: true,
//                 type: ['pie', 'funnel']
//             },
//             restore : {show: true},
//             saveAsImage : {show: true}
//         }
//     },
//     calculable : true,
//     series : [
//         {
//             name:'半径模式',
//             type:'pie',
//             radius : [20, 110],
//             roseType : 'radius',
//             label: {
//                 normal: {
//                     show: true
//                 },
//                 emphasis: {
//                     show: true
//                 }
//             },
//             lableLine: {
//                 normal: {
//                     show: false
//                 },
//                 emphasis: {
//                     show: true
//                 }
//             },
//             data:[
//                 {value:100, name:'已完成'},
//                 {value:53, name:'就绪状态'},
//                 {value:152, name:'正在运行'},
//                 {value:225, name:'未到达'},
//             ]
//         }
//     ]
// };
