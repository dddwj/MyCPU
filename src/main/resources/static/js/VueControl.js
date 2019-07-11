var PCBs;
var temp;

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
            radius: '90%'
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
        data: ['已完成', '就绪状态', '正在运行', '未到达', '阻塞']
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
            radius: [20, 60],
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
                {value: 4, name: '阻塞'}
            ]
        }
    ]
};

// 阶梯瀑布图
var waterfall_opt = {
    title: {
        text: '完成进程统计'
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {
            type : 'cross',
            crossStyle: {
                color: '#999'
            }
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        type : 'category',
        splitLine: {show:false},
        axisLabel: {
            interval: 0,
        },
        data :  function (){
            var list = [];
            return list;
        }()
    },
    yAxis: [
        {
            type : 'value',
            name: '周转时间',
            min: 0,
            max: 100,
            interval: 10,
            axisLabel: {
                formatter: '{value}秒'
            }
        },
        {
            type: 'value',
            name: '带权周转时间',
            min: 1,
            max: 21,
            interval: 10,
            axisLabel: {
                formatter: '{value}'
            }
        }
    ],
    toolbox: {
        feature: {
            dataView: {show: true},
            magicType: {show: true, type: ['line', 'bar']},
            saveAsImage: {show: true},
        }
    },
    legend: {
        data: ['周转时间', '带权周转时间']
    },
    series: [
        {
            name: '开始时间',
            type: 'bar',
            stack: '总量',
            itemStyle: {
                normal: {
                    barBorderColor: 'rgba(0,0,0,0)',
                    color: 'rgba(0,0,0,0)'
                },
                emphasis: {
                    barBorderColor: 'rgba(0,0,0,0)',
                    color: 'rgba(0,0,0,0)'
                }
            },
            data: []
        },
        {
            name: '周转时间',
            type: 'bar',
            stack: '总量',
            barWidth: '35%',
            label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            },
            data: []
        },
        {
            name: '带权周转时间',
            type: 'line',
            yAxisIndex: 1,
            data: []
        }
    ]
};

var heatmap_opt = {
    title: {
        text: '进程运行情况',
        // textAlign: 'center',
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
    tooltip: {
        position: 'top',
        formatter: function (params, ticket, callback) {
            return '进程'+ params['data'][2]
        }
    },
    xAxis: {
        type: 'category',
        data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20],
        splitArea: {
            show: true
        }
    },
    yAxis: {
        // axisLabel: {formatter: function() {return "";}},
        type: 'category',
        data: [1, 21, 41, 61, 81],
        splitArea: {
            show: true
        }
    },
    visualMap: {
        type: 'continuous',
        min: 0,
        max: 20,
        calculable: true,
        orient: 'horizontal',
        left: 'center',
        text: ["进程PID"],
        color: ['orangered','yellow','lightskyblue']
        // inRange: {
        //     colorHue: [0, 360],
        //     colorSaturation: [0.5],
        //     colorLightness: [1]
        // }
    },
    series: [{
        name: '当前进程',
        type: 'heatmap',
        data: [],
        label: {
            normal: {
                show: true
            }
        },
        itemStyle: {
            emphasis: {
                shadowBlur: 10,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
        }
    }]
};



var app = new Vue({
    el: "#app",
    data: {
        visible: false,
        alg: '1',
        current_time: 0,
        round: 3,
        limitNum: 5,
        isPreemptive: true,
        runPCBTable: [],
        readyPCBTable: [],
        blockupPCBTable: [],
        finishPCBTable: [],
        jamPCBTable: [],
        addPCBForm: {
            pid: null,
            name: null,
            prio: null,
            arrivalTime: null,
            round: null,
            serviceTime: null,
            cpuTime: null,
            remainNeedTime: null,
            finishTime: null,
            turnoverTime: null,
            weightedTurnoverTime: null
        },
        needInit: false,
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
        drawWaterFall() {
            this.waterfallChart = echarts.init(document.getElementById('waterfall'));
            window.addEventListener("resize", this.waterfallChart.resize);
            this.waterfallChart.setOption(waterfall_opt);
        },
        drawHeatMap() {
            this.heatmapChart = echarts.init(document.getElementById('haetmap'));
            window.addEventListener("resize", this.heatmapChart.resize);
            this.heatmapChart.setOption(heatmap_opt);
        },
        showLists(time) {
            this.showRunPCBTable(time);
            this.showReadyPCBTable(time);
            this.showBlockupPCBTable(time);
            this.showFinishPCBTable(time);
            this.showJamPCBTable(time);
        },
        showBlockupPCBTable(time) {
            this.blockupPCBTable = [];
            this.blockupPCBTable = ( function () {
                let blockupPCBList = PCBs[time]["blockup"];
                let blockupPCBTable = [];
                for (let i = 0; i < blockupPCBList.length; i++){
                    let PCB = blockupPCBList[i];
                    blockupPCBTable.push({
                        "pid": PCB.pid,
                        "name": PCB.name,
                        "prio": PCB.prio,
                        "arrivalTime": PCB.arrivalTime,
                        "remainNeedTime": PCB.remainNeedTime,
                        "cpuTime": PCB.cpuTime,
                        "round": PCB.round,
                        "serviceTime": PCB.serviceTime,
                        "finishTime": PCB.finishTime,
                        "turnoverTime": PCB.turnoverTime,
                        "weightedTurnoverTime": PCB.weightedTurnoverTime
                    });
                }
                return blockupPCBTable;
            })();
        },
        showJamPCBTable(time) {
            this.jamPCBTable = [];
            this.jamPCBTable = ( function () {
                let jamPCBList = PCBs[time]["jam"];
                let jamPCBTable = [];
                for (let i = 0; i < jamPCBList.length; i++){
                    let PCB = jamPCBList[i];
                    jamPCBTable.push({
                        "pid": PCB.pid,
                        "name": PCB.name,
                        "prio": PCB.prio,
                        "arrivalTime": PCB.arrivalTime,
                        "progress": Math.round(PCB.cpuTime / PCB.serviceTime * 100),
                        "remainNeedTime": PCB.remainNeedTime,
                        "cpuTime": PCB.cpuTime,
                        "round": PCB.round,
                        "serviceTime": PCB.serviceTime,
                        "finishTime": PCB.finishTime,
                        "turnoverTime": PCB.turnoverTime,
                        "weightedTurnoverTime": PCB.weightedTurnoverTime
                    });
                }
                return jamPCBTable;
            })();
        },
        showRunPCBTable(time) {
            this.runPCBTable = (function () {
                let runPCBList = PCBs[time]["run"];
                let runPCBTable = [];
                for (let i = 0; i < runPCBList.length; i++) {
                    let PCB = runPCBList[i];
                    runPCBTable.push({
                        "pid": PCB.pid,
                        "name": PCB.name,
                        "prio": PCB.prio,
                        "arrivalTime": PCB.arrivalTime,
                        "progress": Math.round(PCB.cpuTime / PCB.serviceTime * 100),
                        "remainNeedTime": PCB.remainNeedTime,
                        "cpuTime": PCB.cpuTime,
                        "round": PCB.round,
                        "serviceTime": PCB.serviceTime,
                        "finishTime": PCB.finishTime,
                        "turnoverTime": PCB.turnoverTime,
                        "weightedTurnoverTime": PCB.weightedTurnoverTime
                    });
                }
                return runPCBTable;
            })();
        },
        showReadyPCBTable(time) {
            // this.readyPCBTable = [];
            this.readyPCBTable = (function () {
                let readyPCBList = PCBs[time]["ready"];
                let readyPCBTable = [];
                for (let i = 0; i < readyPCBList.length; i++) {
                    let PCB = readyPCBList[i];
                    readyPCBTable.push({
                        "pid": PCB.pid,
                        "name": PCB.name,
                        "prio": PCB.prio,
                        "arrivalTime": PCB.arrivalTime,
                        "progress": Math.round(PCB.cpuTime / PCB.serviceTime * 100),
                        "remainNeedTime": PCB.remainNeedTime,
                        "cpuTime": PCB.cpuTime,
                        "round": PCB.round,
                        "serviceTime": PCB.serviceTime,
                        "finishTime": PCB.finishTime,
                        "turnoverTime": PCB.turnoverTime,
                        "weightedTurnoverTime": PCB.weightedTurnoverTime
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
                        "pid": PCB.pid,
                        "name": PCB.name,
                        "prio": PCB.prio,
                        "arrivalTime": PCB.arrivalTime,
                        "progress": 100,
                        "remainNeedTime": PCB.remainNeedTime,
                        "cpuTime": PCB.cpuTime,
                        "round": PCB.round,
                        "serviceTime": PCB.serviceTime,
                        "finishTime": PCB.finishTime,
                        "turnoverTime": PCB.turnoverTime,
                        "weightedTurnoverTime": Math.round(PCB.weightedTurnoverTime * 100) / 100
                    });
                }
                return finishPCBTable;
            })();
        },
        start() {   // 连续运行
            this.timer = setInterval(() => {
                this.current_time += 1;
            }, '500');
        },
        pause() {  // 暂停连续运行
            clearInterval(this.timer);
        },
        addPCBFormLine() {
            this.addPCBForm.round = this.round;
            this.addPCBForm.cpuTime = 0;
            this.addPCBForm.remainNeedTime = this.addPCBForm.serviceTime;
            this.blockupPCBTable.push(JSON.parse(JSON.stringify(this.addPCBForm)));
            // 在当秒，将新增的进程加入到就绪队列中
            PCBs['' + this.current_time]["blockup"].push(JSON.parse(JSON.stringify(this.addPCBForm)));
            this.needInit = true;
            app.$message({
                message: "添加成功！"
            });
        },
        deletePCBFormLine(index, row) {
            let PCB = row;
            if (this.runPCBTable.indexOf(PCB) >= 0 ) {
                this.runPCBTable.splice(this.runPCBTable.indexOf(PCB), 1);
                PCBs[this.current_time]["run"].splice(PCBs[0]["run"].indexOf(PCB), 1);
            }
            if (this.blockupPCBTable.indexOf(PCB) >= 0 ) {
                this.blockupPCBTable.splice(this.blockupPCBTable.indexOf(PCB), 1);
                PCBs[this.current_time]["blockup"].splice(PCBs[0]["blockup"].indexOf(PCB), 1);
            }
            if (this.readyPCBTable.indexOf(PCB) >= 0 ) {
                this.readyPCBTable.splice(this.readyPCBTable.indexOf(PCB), 1);
                PCBs[this.current_time]["ready"].splice(PCBs[0]["ready"].indexOf(PCB), 1);
            }
            if (this.jamPCBTable.indexOf(PCB) >= 0 ) {
                this.jamPCBTable.splice(this.jamPCBTable.indexOf(PCB), 1);
                PCBs[this.current_time]["jam"].splice(PCBs[0]["jam"].indexOf(PCB), 1);
            }
            this.needInit = true;
        },
        removeData() {
            PCBs = [];
            this.runPCBTable = [];
            this.blockupPCBTable = [];
            this.readyPCBTable = [];
            this.jamPCBTable = [];
            this.finishPCBTable = [];
            heatmap_opt.series[0].data = [];
            this.heatmapChart.setOption(heatmap_opt);
            waterfall_opt.series[0].data = [];
            waterfall_opt.series[1].data = [];
            waterfall_opt.series[2].data = [];
            waterfall_opt.xAxis.data = [];
            this.waterfallChart.setOption(waterfall_opt);
            this.current_time = 0;
        },
        getSummaries(param) {
            const {columns, data} = param;
            const sums = [];
            columns.forEach((column, index) => {
                if (index === 0) {
                    sums[index] = '平均';
                    return;
                }
                if (index === 1) {
                    sums[index] = '';
                    return;
                }

                const values = data.map(item => Number(item[column.property]));
                if (!values.every(value => isNaN(value))) {
                    sums[index] = values.reduce((prev, curr) => {
                        const value = Number(curr);
                        if (!isNaN(value)) {
                            return prev + curr;
                        } else {
                            return prev;
                        }
                    }, 0);
                    sums[index] /= data.length;
                    sums[index] = Math.round(sums[index] * 100) / 100;
                    sums[index] += ' 秒';
                } else {
                    sums[index] = 'N/A';
                }
            });

            return sums;
        },
        initLocalDataAndList() { // 获取模拟数据
            PCBs = back_front;
            this.current_time = 0;
            this.showLists(this.current_time);
            this.visible = false;
            this.needInit = false;
        },
        initServerDataAndList() {     // 获取服务器端生成的随机数据
            let url;
            if (this.alg == '1') {
                url = "/FCFSData";
            }
            if (this.alg == '2') {
                url = "/RRData";
            }
            if (this.alg == '3') {
                url = "/PriorityData";
            }
            if (this.alg == '4') {
                url = "/SJFData";
            }
            let data = {
                "currentTime": this.current_time,
                "round": this.round,
                "limitNum": this.limitNum,
                "isPreemptive": this.isPreemptive
            };
            if(this.current_time == 0)
                data['0'] = {
                    "blockup": [],
                    "ready": [],
                    "run": [],
                    "finish": [],
                    "jam": []
                };
            else {
                this.$message({
                    type: "warning",
                    message: "请先清除现有数据！"
                });
                return;
            }
            // 获取后台数据
            let app = this;
            $.ajax({
                url: url,
                type: "post",
                async: false,  // 取消ajax异步功能，防止ajax外部得不到服务器的返回值
                dataType: "json",
                data: JSON.stringify(data),
                success: function (res) {
                    PCBs = res;
                    app.current_time = 0;
                    app.showLists(app.current_time);
                    app.needInit = false;
                    app.visible = false;
                },
                error: function (res) {
                    app.$message({
                        type: "warning",
                        message: "服务器发生错误！"
                    });
                    console.log(res);
                    app.visible = false;
                }
            });
        },
        updateServerDataAndList() {
            let url;
            if (this.alg == '1') {
                url = "/FCFSData";
            }
            if (this.alg == '2') {
                url = "/RRData";
            }
            if (this.alg == '3') {
                url = "/PriorityData";
            }
            if (this.alg == '4') {
                url = "/SJFData";
            }
            let data = {
                "currentTime": this.current_time,
                "round": this.round,
                "limitNum": this.limitNum,
                "isPreemptive": this.isPreemptive
            };
            data[''+this.current_time] = {
                "blockup": this.blockupPCBTable,
                "ready": this.readyPCBTable,
                "run": this.runPCBTable,
                "finish": this.finishPCBTable,
                "jam": this.jamPCBTable,
            };
            // 获取后台数据
            let app = this;
            $.ajax({
                url: url,
                type: "post",
                async: false,  // 取消ajax异步功能，防止ajax外部得不到服务器的返回值
                dataType: "json",
                data: JSON.stringify(data),
                success: function (res) {
                    let new_PCBs = res;
                    let length = Object.keys(new_PCBs).length;
                    for (var i = 0; i < length; i++) {
                        let time = app.current_time + 1 + i;
                        PCBs['' + time] = res['' + time];
                    }
                    app.showLists(app.current_time);
                    app.needInit = false;
                    app.visible = false;
                },
                error: function (res) {
                    app.$message({
                        type: "warning",
                        message: "服务器发生错误！"
                    });
                    console.log(res);
                    app.visible = false;
                }
            });
        },
    },
    mounted: function () {
        this.initLocalDataAndList();
        this.drawGauge();
        this.drawRose();
        this.drawWaterFall();
        this.drawHeatMap();
    },
    watch: {
        current_time: {
            handler(newVal, oldVal) {
                if(newVal == 0)
                    return;
                this.showLists(newVal);
            }
        },
        runPCBTable: {
            handler(newVal, oldVal) {
                // 更新rose数据
                rose_opt.series[0].data[2].value = this.runPCBTable.length;
                this.roseChart.setOption(rose_opt);

                // 当所有进程都结束时，停止连续运行
                if (this.readyPCBTable.length === 0 && this.blockupPCBTable.length === 0 && newVal.length === 0
                && this.jamPCBTable.length === 0){
                    this.pause();
                    this.$message({
                        type: "success",
                        message: "模拟运行结束！"
                    })
                }

                // 更新heatmap数据
                if (typeof(newVal[0]) != "undefined") {      // 如果存在正在运行的进程
                    heatmap_opt.series[0].data.push([(this.current_time - 1) % 20, Math.floor((this.current_time - 1)/20), newVal[0].pid]);
                } else {
                    heatmap_opt.series[0].data.push([(this.current_time - 1) % 20, Math.floor((this.current_time - 1)/20), '-']);
                }
                this.heatmapChart.setOption(heatmap_opt);
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

                // 更新waterfall数据
                let pcb = newVal[newVal.length - 1];
                if (newVal.length > 0 && waterfall_opt.xAxis.data.indexOf(pcb.name) === -1) {
                    waterfall_opt.xAxis.data.push(pcb.name);
                    waterfall_opt.series[0].data.push(pcb.arrivalTime);
                    waterfall_opt.series[1].data.push(pcb.turnoverTime);
                    waterfall_opt.series[2].data.push(pcb.weightedTurnoverTime);
                    this.waterfallChart.setOption(waterfall_opt);
                }

                // 更新表格最后一行平均时间计算的汇总数据
                this.$refs.finishPCBTableSummary.doLayout();
            },
            deep: true
        },
        jamPCBTable: {
            handler(newVal, oldVal) {
                // 更新rose数据
                rose_opt.series[0].data[4].value = this.jamPCBTable.length;
                this.roseChart.setOption(rose_opt);
            },
            deep: true
        },
        round: {
            handler() {
                this.needInit = true;
            }
        }
    }
});
