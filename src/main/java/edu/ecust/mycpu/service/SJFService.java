package edu.ecust.mycpu.service;

import edu.ecust.mycpu.model.PCB;
import edu.ecust.mycpu.model.State;
import edu.ecust.mycpu.util.PCBComprator;
import edu.ecust.mycpu.util.PCBRemainTimeComprator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

public class SJFService {

    /*全部运行数据*/
    Map<Integer, Map<String, List<PCB>>> allData;

    /*当前时间*/
    private Integer currentTime;

    /*进程数*/
    private Integer processNum;

    /*未到达进程队列*/
    private List<PCB> unreachedList;

    /*运行进程队列*/
    private List<PCB> runList;

    /*已完成进程队列*/
    private List<PCB> finishList;

    /*就绪进程队列*/
    private List<PCB> readyList;

    /*阻塞进程队列*/
    private List<PCB> jamList;

    public SJFService(List<PCB> unreachedList, List<PCB> readyList, List<PCB> runList,List<PCB> finishList, List<PCB> jamList, Map<Integer,Map<String, List<PCB>>> allData, Integer processNum) {
        this.currentTime = 0;
        this.processNum = processNum;
        this.unreachedList = unreachedList;
        this.readyList = readyList;
        this.runList = runList;
        this.finishList = finishList;
        this.jamList = jamList;
        this.allData = allData;
    }

    public SJFService(Integer currentTime, List<PCB> unreachedList, List<PCB> readyList, List<PCB> runList,List<PCB> finishList, List<PCB> jamList, Map<Integer,Map<String, List<PCB>>> allData) {
        this.currentTime = currentTime;
        this.unreachedList = unreachedList;
        this.readyList = readyList;
        this.runList = runList;
        this.finishList = finishList;
        this.jamList = jamList;
        this.allData = allData;
    }

    public void init() {

        /*随机生成进程数量*/
        double random;

        /*将进程添加到未到达队列中*/
        for (int i = 0; i < processNum; i++) {
            unreachedList.add(new PCB());
        }

        /*随机生成进程到达时间*/
        for (int i = 0; i < processNum; i++) {
            random = 20*Math.random();
            unreachedList.get(i).setArrivalTime((int) random + 1);
        }

        /*随机生成运行进程所需时间*/
        for (int i = 0; i < processNum; i++) {
            random = 30*Math.random();
            unreachedList.get(i).setServiceTime((int) random + 1);
            unreachedList.get(i).setRemainNeedTime((int) random + 1);
        }

        /*设置PID*/
        for (int i = 0; i < processNum; i++) {
            unreachedList.get(i).setPID(i+1);
        }

        /*设置进程名*/
        for (int i = 0; i < processNum; i++) {
            unreachedList.get(i).setName("Process"+(i+1));
        }

        /*设置已运行时间*/
        for (int i = 0; i < processNum; i++) {
            unreachedList.get(i).setCpuTime(0);
        }

        /*设置进程状态*/
        for (int i = 0; i < processNum; i++) {
            unreachedList.get(i).setState(State.BLOCK_UP);
        }
    }

    /*给未到达进程按到达时间升序排序*/
    public void sortUnreachedList(){
        Collections.sort( unreachedList,new PCBComprator());
    }

    /*给ready队列中的进程按到达剩余时间升序排序*/
    public void sortReadyList(){
        Collections.sort( readyList,new PCBRemainTimeComprator());
    }

    public Map<Integer,Map<String,List<PCB>>> run() throws IOException, ClassNotFoundException {
        sortUnreachedList();
        double random;
        if(!runList.isEmpty()) {
            PCB cur = runList.get(0);
            int remainNeedTime = cur.getRemainNeedTime();
            int finishTime = currentTime;
            int serviceTime = cur.getServiceTime();
            int arrivalTime = cur.getArrivalTime();
            /*周转时间*/
            int turnoverTime = finishTime - arrivalTime;
            /*带权周转时间*/
            double weightedTurnoverTime = (double) turnoverTime / (double) serviceTime;
            if (remainNeedTime == 0) {
                cur.setFinishTime(finishTime);
                cur.setTurnoverTime(turnoverTime);
                java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                cur.setWeightedTurnoverTime(Double.valueOf(df.format(weightedTurnoverTime)));
                cur.setState(State.FINISH);
                //弹出run队列中进程放入finish队列;
                runList.remove(0);
                finishList.add(cur);
            } else {
                cur.setState(State.READY);
                runList.remove(0);
                readyList.add(cur);
            }
        }
        sortReadyList();

        while (true){
            //看做模拟运行已结束
            if(unreachedList.isEmpty()&&readyList.isEmpty()&&runList.isEmpty()&&jamList.isEmpty()){
                return allData;
            }

            //将已经到达的进程放入ready队列
            if(!unreachedList.isEmpty()){
                Iterator<PCB> itr = unreachedList.iterator();
                while(itr.hasNext()){
                    PCB p = itr.next();
                    if(p.getArrivalTime().equals(currentTime)){
                        p.setState(State.READY);
                        readyList.add(p);
                        itr.remove();
                        if(!runList.isEmpty()) {
                            PCB cur = runList.get(0);
                            int remainNeedTime = cur.getRemainNeedTime();
                            int finishTime = currentTime;
                            int serviceTime = cur.getServiceTime();
                            int arrivalTime = cur.getArrivalTime();
                            /*周转时间*/
                            int turnoverTime = finishTime - arrivalTime;
                            /*带权周转时间*/
                            double weightedTurnoverTime = (double) turnoverTime / (double) serviceTime;
                            if (remainNeedTime == 0) {
                                cur.setFinishTime(finishTime);
                                cur.setTurnoverTime(turnoverTime);
                                java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                                cur.setWeightedTurnoverTime(Double.valueOf(df.format(weightedTurnoverTime)));
                                cur.setState(State.FINISH);
                                //弹出run队列中进程放入finish队列;
                                runList.remove(0);
                                finishList.add(cur);
                            } else {
                                cur.setState(State.READY);
                                runList.remove(0);
                                readyList.add(cur);
                            }
                        }
                        sortReadyList();
                    }else {
                        break;
                    }
                }
            }

            //0.2的概率将阻塞队列中的进程取出并放入就绪队列中
            if(!jamList.isEmpty()){
                Iterator<PCB> itr = jamList.iterator();
                while(itr.hasNext()){
                    PCB p = itr.next();
                    random = Math.random();
                    if( random < 0.2 ){
                        p.setState(State.READY);
                        readyList.add(p);
                        itr.remove();
                        if(!runList.isEmpty()) {
                            PCB cur = runList.get(0);
                            int remainNeedTime = cur.getRemainNeedTime();
                            int finishTime = currentTime;
                            int serviceTime = cur.getServiceTime();
                            int arrivalTime = cur.getArrivalTime();
                            /*周转时间*/
                            int turnoverTime = finishTime - arrivalTime;
                            /*带权周转时间*/
                            double weightedTurnoverTime = (double) turnoverTime / (double) serviceTime;
                            if (remainNeedTime == 0) {
                                cur.setFinishTime(finishTime);
                                cur.setTurnoverTime(turnoverTime);
                                java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                                cur.setWeightedTurnoverTime(Double.valueOf(df.format(weightedTurnoverTime)));
                                cur.setState(State.FINISH);
                                //弹出run队列中进程放入finish队列;
                                runList.remove(0);
                                finishList.add(cur);
                            } else {
                                cur.setState(State.READY);
                                runList.remove(0);
                                readyList.add(cur);
                            }
                        }
                        sortReadyList();
                    }
                }
            }

            //判断当前run队列是否为空
            if(runList.isEmpty()){
                //如果run队列为空就取ready队列的第一个进程放入run队列
                if(!readyList.isEmpty()){
                    PCB cur = readyList.remove(0);
                    cur.setState(State.RUN);
                    runList.add(cur);
                    int cpuTime = cur.getCpuTime();
                    int remainNeedTime = cur.getRemainNeedTime();
                    cpuTime++;
                    remainNeedTime--;
                    cur.setCpuTime(cpuTime);
                    cur.setRemainNeedTime(remainNeedTime);
                    //更新run队列状态
                    runList.set(0,cur);
                }
            }

            //run队列不空，证明有进程在跑,查看当前进程状态
            else{
                PCB cur = runList.get(0);
                int cpuTime = cur.getCpuTime();
                int remainNeedTime = cur.getRemainNeedTime();
                int finishTime = currentTime;
                int serviceTime = cur.getServiceTime();
                int arrivalTime = cur.getArrivalTime();
                /*周转时间*/
                int turnoverTime = finishTime - arrivalTime;
                /*带权周转时间*/
                double weightedTurnoverTime = (double) turnoverTime / (double) serviceTime;
                //已运行完毕
                if(remainNeedTime==0){
                    cur.setFinishTime(finishTime);
                    cur.setTurnoverTime(turnoverTime);
                    java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");
                    cur.setWeightedTurnoverTime(Double.valueOf(df.format(weightedTurnoverTime)));
                    cur.setState(State.FINISH);
                    //弹出run队列中进程放入finish队列;
                    runList.remove(0);
                    finishList.add(cur);
                    //如果ready队列不为空，则取ready队列的第一个进程放入run队列
                    if(!readyList.isEmpty()){
                        PCB run = readyList.remove(0);
                        run.setState(State.RUN);
                        runList.add(run);
                        cpuTime = run.getCpuTime();
                        remainNeedTime = run.getRemainNeedTime();
                        cpuTime++;
                        remainNeedTime--;
                        run.setCpuTime(cpuTime);
                        run.setRemainNeedTime(remainNeedTime);
                        //更新run队列状态
                        runList.set(0,run);
                    }
                }else {
                    /*运行中的进程有0.05的概率阻塞*/
                    random=Math.random();
                    if( random < 0.05 ){
                        cur.setState(State.JAM);
                        runList.remove(0);
                        jamList.add(cur);
                        if(!readyList.isEmpty()){
                            PCB run = readyList.remove(0);
                            run.setState(State.RUN);
                            runList.add(run);
                            cpuTime = run.getCpuTime();
                            remainNeedTime = run.getRemainNeedTime();
                            cpuTime++;
                            remainNeedTime--;
                            run.setCpuTime(cpuTime);
                            run.setRemainNeedTime(remainNeedTime);
                            //更新run队列状态
                            runList.set(0,run);
                        }
                    }else{
                        cpuTime++;
                        remainNeedTime--;
                        cur.setCpuTime(cpuTime);
                        cur.setRemainNeedTime(remainNeedTime);
                        //更新run队列状态
                        runList.set(0,cur);
                    }
                }
            }

            //超时就跳出循环
            if(currentTime==200)
                return null;

            Map<String,List<PCB>> currentData = new HashMap<>();
            List<PCB> ur = new LinkedList<>();
            for (PCB p:
                    unreachedList) {
                //新队列插入存入深克隆对象
                ur.add(p.deepClone());
            }
            currentData.put("blockup",ur);

            List<PCB> re = new LinkedList<>();
            for (PCB p:
                    readyList) {
                //新队列插入存入深克隆对象
                re.add(p.deepClone());
            }
            currentData.put("ready",re);

            List<PCB> ru = new LinkedList<>();
            for (PCB p:
                    runList) {
                //新队列插入存入深克隆对象
                ru.add(p.deepClone());
            }
            currentData.put("run",ru);

            List<PCB> fi = new LinkedList<>();
            for (PCB p:
                    finishList) {
                //新队列插入存入深克隆对象
                fi.add(p.deepClone());
            }
            currentData.put("finish",fi);

            List<PCB> ja = new LinkedList<>();
            for (PCB p:
                    jamList) {
                //新队列插入存入深克隆对象
                ja.add(p.deepClone());
            }
            currentData.put("jam",ja);
            allData.put(currentTime,currentData);
            allData.put(currentTime,currentData);

            //单元测试
            System.out.println("第"+currentTime+"秒：");
            System.out.println("==========================================");
            System.out.println("未到达队列：");
            for (PCB p: unreachedList) {
                System.out.println(p.toString());
            }
            System.out.println("就绪队列:");
            for (PCB p: readyList) {
                System.out.println(p.toString());
            }
            System.out.println("执行队列:");
            for (PCB p: runList) {
                System.out.println(p.toString());
            }
            System.out.println("完成队列:");
            for (PCB p: finishList) {
                System.out.println(p.toString());
            }
            System.out.println("完成队列:");
            for (PCB p: finishList) {
                System.out.println(p.toString());
            }
            System.out.println("==========================================");


            //时间前进一秒
            currentTime++;
        }

    }
}
