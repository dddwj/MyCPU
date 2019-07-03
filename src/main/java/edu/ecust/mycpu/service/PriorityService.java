package edu.ecust.mycpu.service;

import edu.ecust.mycpu.model.PCB;
import edu.ecust.mycpu.util.PCBComprator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.PriorityQueue;


@Service
public class PriorityService {
    /*进程数*/
    private Integer processNum;

    /*正在运行进程*/
    private PCB running;

    /*未到达进程队列*/
    private ArrayList<PCB> unreachedList;

    /*就绪进程队列*/
    private PriorityQueue<PCB> readyList;

    /*已完成进程队列*/
    private ArrayList<PCB> finishList;

    public void Priority(ArrayList<PCB> unreachedList, ArrayList<PCB> readyList, ArrayList<PCB> finishList, PCB running, Integer currentTime){
        PCBComprator comprator = new PCBComprator();
        PriorityQueue<PCB> readyQueue = new PriorityQueue<PCB>(processNum+10,comprator);
        for(int i=0;i<unreachedList.size();i++){
            readyQueue.add(readyList.get(i));
        }

    }
}
