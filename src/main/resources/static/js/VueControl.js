// 获取后台数据
var PCBs = back_front;

// 仪表盘option
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

var rose_opt = {
    // title : {
    //     text: '任务数量统计',
    //     x:'center'
    // },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        x : 'center',
        y : 'bottom',
        data:['已完成','就绪状态','正在运行','未到达']
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {
                show: true,
                type: ['pie', 'funnel']
            },
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    series : [
        {
            name:'任务数量统计',
            type:'pie',
            radius : [20, 100],
            roseType : 'radius',
            label: {
                normal: {
                    show: true,
                    formatter: '{b}\n{c}个任务({d}%)'
                },
                emphasis: {
                    show: true
                },

            },
            data:[
                {value:1, name:'已完成'},
                {value:53, name:'就绪状态'},
                {value:1, name:'正在运行'},
                {value:5, name:'未到达'},
            ]
        }
    ]
};


var app = new Vue({
    el: "#app",
    data:  {
       visible: false,
       alg: '1',
       current_time: 10,
       round: 3,
       isPreemptive: true,
       runPCBTable: [],
       readyPCBTable: [],
    },
    methods: {
       drawGauge(){
           this.gaugeChart = echarts.init(document.getElementById('gauge'));
           window.addEventListener("resize", this.gaugeChart.resize);
           this.gaugeChart.setOption(gauge_opt);
       },
        drawRose(){
           this.roseChart = echarts.init(document.getElementById('rose'));
            window.addEventListener("resize", this.roseChart.resize);
            this.roseChart.setOption(rose_opt);
        }
    },
    mounted: function () {
        this.drawGauge();
        this.drawRose();
    },
    watch: {
        runPCBTable: {
            handler(newVal, oldVal) {
                // 更新rose数据
                rose_opt.series[0].data[2].value = this.runPCBTable.length;
                this.roseChart.setOption(rose_opt);
            },
            deep: true
        },
        readyPCBTable: {
            handler(newVal, oldVal) {
                // 更新gauge仪表盘数据
                gauge_opt.series[0].data[0].value = this.readyPCBTable.length;
                this.gaugeChart.setOption(gauge_opt);
                // 更新rose数据
                rose_opt.series[0].data[1].value = this.readyPCBTable.length;
                this.roseChart.setOption(rose_opt);
            },
            deep: true
        },
    }
});


app.runPCBTable.push((function(){
    var current_time = app.current_time;
    var PCB = PCBs[current_time]["run"][0];
    return {
        "PID": PCB.PID,
        "name": PCB.name,
        "prio": PCB.prio,
        "arrivalTime": PCB.arrivalTime,
        "progress": Math.round(PCB.cpuTime / PCB.serviceTime * 100),
        "remainNeedTime": PCB.remainNeedTime
    }
})());

app.readyPCBTable = (function(){
    var current_time = app.current_time;
    var readyPCBList = PCBs[current_time]["ready"];
    var readyPCBTable = [];
    for(var i = 0; i < readyPCBList.length; i++){
        var PCB = readyPCBList[i];
        readyPCBTable.push({
            "PID": PCB.PID,
            "name": PCB.name,
            "prio": PCB.prio,
            "arrivalTime": PCB.arrivalTime,
            "progress": Math.round(PCB.cpuTime / PCB.serviceTime * 100),
            "remainNeedTime": PCB.remainNeedTime
        });
    }
    return readyPCBTable;
})();

