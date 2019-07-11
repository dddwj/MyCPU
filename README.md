# MyCPU 进程调度模拟程序
> 《系统软件课程设计》成果

## 程序功能
> 模拟操作系统中的单核单线程CPU调度
- 选择调度算法
    - 先来先服务（aka. 先进先出）
    - 时间片轮转
    - 优先级队列（抢占式、非抢占式）
    - 短进程优先
- 随机生成一组进程 / 使用本地模拟数据
- 连续运行/暂停
- 单步运行（上一秒、下一秒）
- 新增进程
- 删除进程

## 开发框架
- SpringBoot 2.1.6
- Vue.js 2 + Element-UI
- ECharts 4

## 运行方式
- maven打包
- `java -jar mycpu-0.0.1-SNAPSHOT.jar`
- 浏览器访问`http://localhost:8080`

## Demo
![主界面](http://github.com/dddwj/MyCPU/raw/master/demo/主界面.png)
![热力图和瀑布图](http://github.com/dddwj/MyCPU/raw/master/demo/热力图和瀑布图.png)
![新增进程](http://github.com/dddwj/MyCPU/raw/master/demo/新增进程.png)
![demo](http://github.com/dddwj/MyCPU/raw/master/demo/demo.gif)
