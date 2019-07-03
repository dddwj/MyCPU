// 获取后台数据
var PCBs = back_front;

// 仪表盘option
var gauge_opt = {
    tooltip: {
        formatter: "{a} <br/>{b} : {c}%"
    },
    series: [
        {
            name: '任务数量',
            type: 'gauge',
            detail: {formatter: '{value}项'},
            data: [{value: 20, name: '任务数量'}],
            max: 20,
            radius: '75%'
        }
    ]
};

var rose_opt = {
    title : {
        text: '任务数量统计',
        x:'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        x: 'center',
        y: 'bottom',
        data: ['已完成', '就绪状态', '正在运行', '未到达']
    },
    toolbox: {
        show: true,
        feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},
            magicType: {
                show: true,
                type: ['pie', 'funnel']
            },
            restore: {show: true},
            saveAsImage: {show: true}
        }
    },
    calculable: true,
    series: [
        {
            name: '任务数量统计',
            type: 'pie',
            radius: [20, 80],
            roseType: 'radius',
            label: {
                normal: {
                    show: true,
                    formatter: '{b}\n{c}个任务({d}%)'
                },
                emphasis: {
                    show: true
                },

            },
            data: [
                {value: 1, name: '已完成'},
                {value: 53, name: '就绪状态'},
                {value: 1, name: '正在运行'},
                {value: 5, name: '未到达'},
            ]
        }
    ]
};


var app = new Vue({
    el: "#app",
    data: {
        visible: false,
        alg: '1',
        current_time: 10,
        round: 3,
        isPreemptive: true,
        runPCBTable: [],
        readyPCBTable: [],
        blockupPCBTable: [],
        finishPCBTable: [],
    },
    methods: {
        drawGauge() {
            this.gaugeChart = echarts.init(document.getElementById('gauge'));
            window.addEventListener("resize", this.gaugeChart.resize);
            this.gaugeChart.setOption(gauge_opt);
        },
        drawRose() {
            this.roseChart = echarts.init(document.getElementById('rose'));
            window.addEventListener("resize", this.roseChart.resize);
            this.roseChart.setOption(rose_opt);
        },
        showLists(time) {
            this.showRunPCBTable(time);
            this.showReadyPCBTable(time);
            this.showBlockupPCBTable(time);
            this.showFinishPCBTable(time);
        },
        showBlockupPCBTable(time) {
            this.blockupPCBTable = [];
            this.blockupPCBTable = ( function () {
                let blockupPCBList = PCBs[time]["blockup"];
                let blockupPCBTable = [];
                for (let i = 0; i < blockupPCBList.length; i++){
                    let PCB = blockupPCBList[i];
                    blockupPCBTable.push({
                        "PID": PCB.PID,
                        "name": PCB.name,
                        "prio": PCB.prio,
                        "arrivalTime": PCB.arrivalTime,
                        "remainNeedTime": PCB.remainNeedTime
                    });
                }
                return blockupPCBTable;
            })();
        },
        showRunPCBTable(time) {
            this.runPCBTable = [];
            this.runPCBTable.push((function () {
                let PCB = PCBs[time]["run"][0];
                return {
                    "PID": PCB.PID,
                    "name": PCB.name,
                    "prio": PCB.prio,
                    "arrivalTime": PCB.arrivalTime,
                    "progress": Math.round(PCB.cpuTime / PCB.serviceTime * 100),
                    "remainNeedTime": PCB.remainNeedTime
                }
            })());
        },
        showReadyPCBTable(time) {
            // this.readyPCBTable = [];
            this.readyPCBTable = (function () {
                let readyPCBList = PCBs[time]["ready"];
                let readyPCBTable = [];
                for (let i = 0; i < readyPCBList.length; i++) {
                    let PCB = readyPCBList[i];
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
        },
        showFinishPCBTable(time) {
            // this.finishPCBTable = [];
            this.finishPCBTable = (function () {
                let finishPCBList = PCBs[time]["finish"];
                let finishPCBTable = [];
                for (let i = 0; i < finishPCBList.length; i++){
                    let PCB = finishPCBList[i];
                    finishPCBTable.push({
                        "PID": PCB.PID,
                        "name": PCB.name,
                        "prio": PCB.prio,
                        "arrivalTime": PCB.arrivalTime,
                        "finishTime": PCB.finishTime,
                        "turnoverTime": PCB.turnoverTime,  // 周转时间
                        "weightedTurnoverTime": PCB.weightedTurnoverTime,  // 带权周转时间
                    });
                }
                return finishPCBTable;
            })();
        }
    },
    mounted: function () {
        this.showLists(this.current_time);
        this.drawGauge();
        this.drawRose();
    },
    watch: {
        current_time: {
            handler(newVal, oldVal) {
                this.showLists(newVal);
            }
        },
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
        blockupPCBTable: {
            handler(newVal, oldVal) {
                // 更新rose数据
                rose_opt.series[0].data[3].value = this.blockupPCBTable.length;
                this.roseChart.setOption(rose_opt);
            },
            deep: true
        },
        finishPCBTable: {
            handler(newVal, oldVal) {
                // 更新rose数据
                rose_opt.series[0].data[0].value = this.finishPCBTable.length;
                this.roseChart.setOption(rose_opt);
            },
            deep: true
        },
    }
});
