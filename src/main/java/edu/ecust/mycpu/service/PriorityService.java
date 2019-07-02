package edu.ecust.mycpu.service;

import edu.ecust.mycpu.model.PCB;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class PriorityService {
    /*进程数*/
    private Integer processNum;

    /*正在运行进程*/
    private Integer running;

    /*未到达进程队列*/
    private ArrayList<Integer> unreachedList;

    /*就绪进程队列*/
    private ArrayList<Integer> readyList;

    /*已完成进程队列*/
    private ArrayList<Integer> finishList;

    public void Priority(ArrayList<PCB> unreachedList, ArrayList<PCB> readyList, ArrayList<PCB> finishList, PCB running, Integer currentTime){

    }

    private boolean setProcessStatus(ArrayList<PCB> unreachedList, ArrayList<PCB> readyList, ArrayList<PCB> finishList, PCB running){
        for(int i=0;i<unreachedList.size();i++){
            this.unreachedList.add(unreachedList.get(i).getPID());
        }
        return true;
    }
}
