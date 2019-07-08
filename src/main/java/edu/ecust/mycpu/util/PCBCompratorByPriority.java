package edu.ecust.mycpu.util;

import edu.ecust.mycpu.model.PCB;

import java.util.Comparator;

public class PCBCompratorByPriority  implements Comparator<PCB> {
    @Override
    public int compare(PCB o1, PCB o2) {
        if(o1.getPrio()<o2.getPrio()){
            return 1;
        }else {
            return -1;
        }
    }
}
