package edu.ecust.mycpu.model;

/**
 * @Author SYY
 * @CreateDate 2019-07-01
 */
public class PCB {

    /*进程ID*/
    private Integer PID;

    /*进程名称*/
    private String name;

    /*进程优先级，数字越大优先级越高*/
    private Integer prio;

    /*进程轮转时间片时间*/
    private Integer round;

    /*进程的到达时间*/
    private Integer arrivalTime;

    /*进程总共需要占用的CPU时间*/
    private Integer serviceTime;

    /*进程已占用CPU的处理时间*/
    private Integer cpuTime;

    /*进程还需占用的CPU时间*/
    private Integer remainNeedTime;

    /*增加完成时间*/
    private Integer finishTime;

    /*周转时间*/
    private Integer turnoverTime;

    /*带权周转时间*/
    private Double weightedTurnoverTime;

    /*进程计数器*/
    private Integer count;

    /*进程的状态,0阻塞,1就绪,2正在运行,3完成*/
    private State state;

    public Integer getPID() {
        return PID;
    }

    public void setPID(Integer PID) {
        this.PID = PID;
    }

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

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Integer serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Integer getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(Integer cpuTime) {
        this.cpuTime = cpuTime;
    }

    public Integer getRemainNeedTime() {
        return remainNeedTime;
    }

    public void setRemainNeedTime(Integer remainNeedTime) {
        this.remainNeedTime = remainNeedTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Integer getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Integer finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getTurnoverTime() {
        return turnoverTime;
    }

    public void setTurnoverTime(Integer turnoverTime) {
        this.turnoverTime = turnoverTime;
    }

    public Double getWeightedTurnoverTime() {
        return weightedTurnoverTime;
    }

    public void setWeightedTurnoverTime(Double weightedTurnoverTime) {
        this.weightedTurnoverTime = weightedTurnoverTime;
    }

    @Override
    public String toString() {
        return "PCB{" +
                "PID=" + PID +
                ", name='" + name + '\'' +
                ", prio=" + prio +
                ", round=" + round +
                ", arrivalTime=" + arrivalTime +
                ", serviceTime=" + serviceTime +
                ", cpuTime=" + cpuTime +
                ", remainNeedTime=" + remainNeedTime +
                ", finishTime=" + finishTime +
                ", turnoverTime=" + turnoverTime +
                ", weightedTurnoverTime=" + weightedTurnoverTime +
                ", count=" + count +
                ", state=" + state +
                '}';
    }
}
