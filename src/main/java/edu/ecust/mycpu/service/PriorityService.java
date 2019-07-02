package edu.ecust.mycpu.service;

import edu.ecust.mycpu.model.PCB;

import java.util.ArrayList;

public class PriorityService {
    /*进程数*/
    private Integer processNum;

    /*未到达进程队列*/
    private ArrayList<Integer> unreachedList;

    /*就绪进程队列*/
    private ArrayList<Integer> readyList;

    /*已完成进程队列*/
    private ArrayList<Integer> finishList;


}
