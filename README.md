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
![主界面](https://github.com/dddwj/MyCPU/blob/master/demo/%E4%B8%BB%E7%95%8C%E9%9D%A2.png)
![热力图和瀑布图](https://github.com/dddwj/MyCPU/blob/master/demo/%E7%83%AD%E5%8A%9B%E5%9B%BE%E5%92%8C%E7%80%91%E5%B8%83%E5%9B%BE.png)
![新增进程](https://github.com/dddwj/MyCPU/blob/master/demo/%E6%96%B0%E5%A2%9E%E8%BF%9B%E7%A8%8B.png)
![demo](https://github.com/dddwj/MyCPU/blob/master/demo/demo.gif)
