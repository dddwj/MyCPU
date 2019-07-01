package edu.ecust.mycpu.service;

import edu.ecust.mycpu.model.PCB;
import org.springframework.stereotype.Service;

import java.util.Queue;

@Service
public class RoundListService {
    private Queue<PCB> readyList;
    private Queue<PCB> finishList;

}
