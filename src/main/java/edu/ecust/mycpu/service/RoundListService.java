package edu.ecust.mycpu.service;

import edu.ecust.mycpu.model.PCB;
import edu.ecust.mycpu.model.State;
import org.springframework.stereotype.Service;
import java.util.Queue;
import java.util.Random;

//@Service
public class RoundListService {
    /*当前时间*/
    private Integer currentTime;

    /*进程数*/
    private Integer processNum;

    /*未到达进程队列*/
    private Queue<PCB> unreachedList;

    /*就绪进程队列*/
    private Queue<PCB> readyList;

    /*已完成进程队列*/
    private Queue<PCB> finishList;

    /*时间片长度*/
    private Integer round;

    private Random random;

    private int[][] p={{1,10,0,10},{2,8,0,8},{3,5,0,5},{5,9,0,9}};

    public RoundListService(Queue<PCB> unreachedList, Queue<PCB> readyList, Queue<PCB> finishList, Integer round) {
        this.currentTime = 0;
        this.processNum = 4;
        this.unreachedList = unreachedList;
        this.readyList = readyList;
        this.finishList = finishList;
        this.round = round;
    }

    public void init(){
        for(int i=0;i<processNum;i++){
            PCB pcb = new PCB();
            pcb.setName("进程"+(i+1));
            pcb.setPrio(0);
            pcb.setRound(round);
            pcb.setArrivalTime(p[i][0]);
            pcb.setServiceTime(p[i][1]);
            pcb.setCpuTime(p[i][2]);
            pcb.setRemainNeedTime(p[i][3]);
            pcb.setState(State.unreached);
            unreachedList.add(pcb);
        }

        for (PCB p: unreachedList) {
            System.out.println(p.toString());
        }
    }

    /*给未到达进程按到达时间升序排序*/
    public void sortUnreachedList(){

    }

    /*模拟进程运行*/
    public void run(){
        while (true){
            //将已经到达的进程放入就绪队列
            if(!unreachedList.isEmpty()){
                for (PCB p: unreachedList) {
                    if(p.getArrivalTime()==currentTime){
                        p.setState(State.ready);
                        readyList.offer(p);
                    }else{
                        break;
                    }
                }
            }

            //取就绪队列的第一个进程进行按时间片时间获得CPU服务(每次走一秒)
            if(!readyList.isEmpty()){
                readyList.peek().setState(State.run);
                PCB cur = readyList.peek();
                int cpuTime = cur.getCpuTime();
                int remainNeedTime = cur.getRemainNeedTime();
                int roundTime = cur.getRound();
                cpuTime++;
                remainNeedTime--;
                roundTime--;
                if(remainNeedTime==0){
                    cur.setState(State.finish);
                    finishList.add(cur);
                    readyList.poll();
                }else if(roundTime==0){
                    cur.setRemainNeedTime(remainNeedTime);
                    cur.setCpuTime(cpuTime);
                    cur.setRound(round);
                    cur.setState(State.ready);
                    readyList.poll();
                    readyList.offer(cur);
                }else {
                    readyList.peek().setCpuTime(cpuTime);
                    readyList.peek().setRemainNeedTime(remainNeedTime);
                    readyList.peek().setRound(roundTime);
                }

            }


            currentTime++;
            //超时就跳出循环
            if(unreachedList.isEmpty()&&readyList.isEmpty())
                break;
            if(currentTime==100)
                break;
        }

    }

}
