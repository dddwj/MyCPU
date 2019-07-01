package edu.ecust.mycpu.model;

/**
 * @Author SYY
 * @CreateDate 2019-07-01
 */
public class PCB {

    /*进程名称*/
    private String name;
    /*进程优先级，数字越大优先级越高*/
    private Integer prio;
    /*进程轮转时间片*/
    private Integer round;
    /*进程占用CPU的处理时间*/
    private Integer cpuTime;
    /*进程计数器*/
    private Integer count;
    /*进程完成仍需的CPU时间*/
    private Integer needTime;
    /*进程的状态*/
    private Integer state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrio() {
        return prio;
    }

    public void setPrio(Integer prio) {
        this.prio = prio;
    }

    public Integer getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(Integer cpuTime) {
        this.cpuTime = cpuTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getNeedTime() {
        return needTime;
    }

    public void setNeedTime(Integer needTime) {
        this.needTime = needTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    @Override
    public String toString() {
        return "PrioPCB{" +
                "name='" + name + '\'' +
                ", prio=" + prio +
                ", round=" + round +
                ", cpuTime=" + cpuTime +
                ", count=" + count +
                ", needTime=" + needTime +
                ", state=" + state +
                '}';
    }
}
