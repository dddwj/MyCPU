package edu.ecust.mycpu.util;

import edu.ecust.mycpu.model.PCB;
import java.util.Comparator;

public class PCBRemainTimeComprator implements Comparator<PCB> {
    @Override
    public int compare(PCB o1, PCB o2) {
        if(o1.getRemainNeedTime()>o2.getRemainNeedTime()){
            return 1;
        }else if(o1.getRemainNeedTime()==o2.getRemainNeedTime() && o1.getPID()>o2.getPID()){
            return 1;
        }else{
            return -1;
        }
    }
}

