package edu.ecust.mycpu.service;

import edu.ecust.mycpu.model.PCB;
import org.springframework.stereotype.Service;

import java.util.Queue;

//@Service
public class RoundListService {
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

    public RoundListService(Integer processNum, Queue<PCB> unreachedList, Queue<PCB> readyList, Queue<PCB> finishList, Integer round) {
        this.processNum = processNum;
        this.unreachedList = unreachedList;
        this.readyList = readyList;
        this.finishList = finishList;
        this.round = round;
    }


}
