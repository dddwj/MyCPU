package edu.ecust.mycpu.util;


import edu.ecust.mycpu.model.PCB;

import java.util.Comparator;

public class PCBComprator implements Comparator<PCB> {
    @Override
    public int compare(PCB o1, PCB o2) {
        if(o1.getArrivalTime()<o2.getArrivalTime()){
            return 1;
        }else {
            return -1;
        }
    }
}
