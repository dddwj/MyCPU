package edu.ecust.mycpu.service;

import edu.ecust.mycpu.model.PCB;
import edu.ecust.mycpu.model.State;
import edu.ecust.mycpu.util.PCBComprator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;


@Service
public class PriorityService {
    /*进程数*/
    private Integer processNum = 20;
    public void Priority(ArrayList<PCB> unreachedList, ArrayList<PCB> readyList, ArrayList<PCB> finishList, PCB running, Integer currentTime){
        /*
        若发现某个进程的PCB为空，直接返回错误
        */
        for(int i = 0; i<unreachedList.size(); i++){
            if (unreachedList.get(i)==null){
                return ;
            }
        }
        Collections.sort(unreachedList,new PCBComprator());
        ArrayList<PCB> runningList = new ArrayList<PCB>();
        if(running==null){
            return;
        }
        runningList.add(running);
        /*
        把就绪队列中的进程放入优先队列中
        */
        PCBComprator comprator = new PCBComprator();
        PriorityQueue<PCB> readyQueue = new PriorityQueue<PCB>(processNum+10,comprator);
        for(int i=0;i<readyList.size();i++){
            /*
            若发现某个进程的PCB为空，直接返回错误
             */
            if(readyList.get(i)==null)
                return;
            readyQueue.add(readyList.get(i));
        }
        while(true){
            /*
            正在运行的进程，就绪队列，未到达队列都为空时结束
             */
            if(running==null&&unreachedList.isEmpty()&&readyQueue.isEmpty()){
                break;
            }
            /*
            遍历未到达队列，若有当前时间即将到达的进程，将其加入就绪队列
             */
            if(!unreachedList.isEmpty()){
                for(int i=0;i<unreachedList.size();i++){
                    PCB unreachedProcess = unreachedList.get(i);
                    if(unreachedProcess.getArrivalTime().equals(currentTime)){
                        readyList.add(unreachedProcess);
                        unreachedProcess.setState(State.READY);
                        unreachedList.remove(unreachedProcess);
                    }
                }
            }

            /*
            有进程正在运行时，若该进程的总共需要占用的CPU时间小于已占用CPU的处理时间，那么继续运行，否则将它取出，使运行队列为空。
             */
            if(!runningList.isEmpty()){
                PCB runningProcess = runningList.get(0);
                if(runningProcess.getCpuTime()<runningProcess.getServiceTime()){
                    runningProcess.setCpuTime(runningProcess.getCpuTime()+1);
                } else{
                    runningList.remove(0);
                    runningProcess.setState(State.FINISH);
                    finishList.add(runningProcess);
                }
            }
            /*
            没有进程正在运行时，若就绪队列不为空，那么取出就绪队列首的进程，放入运行队列中运行,否则等待。
             */
            if(runningList.isEmpty()){
                if(!readyQueue.isEmpty()){
                    PCB topPriorityProcess = readyQueue.remove();
                    topPriorityProcess.setState(State.RUN);
                    runningList.add(topPriorityProcess);
                    topPriorityProcess.setCpuTime(topPriorityProcess.getCpuTime()+1);
                }
            }




        }
    }
}
