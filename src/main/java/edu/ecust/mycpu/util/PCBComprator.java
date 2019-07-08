package edu.ecust.mycpu.util;

import edu.ecust.mycpu.model.PCB;
import java.util.Comparator;

public class PCBComprator implements Comparator<PCB> {
    @Override
    public int compare(PCB o1, PCB o2) {
        if(o1.getArrivalTime()>o2.getArrivalTime()){
            return 1;
        }else if(o1.getArrivalTime()==o2.getArrivalTime() && o1.getServiceTime()>o2.getServiceTime()){
            return 1;
        }else if(o1.getArrivalTime()==o2.getArrivalTime() && o1.getServiceTime()==o2.getServiceTime() && o1.getPID()>o2.getPID()){
            return 1;
        }else{
            return -1;
        }
    }
}
