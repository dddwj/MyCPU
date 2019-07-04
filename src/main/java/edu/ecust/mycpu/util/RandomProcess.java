package edu.ecust.mycpu.util;

import edu.ecust.mycpu.model.PCB;
import edu.ecust.mycpu.model.State;

import java.util.ArrayList;
import java.util.List;

public class RandomProcess {
    static public ArrayList<PCB> getRandomUnreachedProcess(int num){
        ArrayList<PCB> result = new ArrayList<>();
        int round = (int)(Math.random()*(5-3+1)+3);
        for(int i=1;i<=num;i++){
            PCB newProcess = new PCB();
            newProcess.setPID(i);
            newProcess.setState(State.BLOCK_UP);
            newProcess.setCpuTime(0);
            newProcess.setRound(round);
            newProcess.setName("进程"+i);
            if(i==1){
                newProcess.setArrivalTime(1);
            } else {
                newProcess.setArrivalTime((int)(Math.random()*(20-2+1)+2));
            }
            newProcess.setPrio((int)(Math.random()*(99-1+1)+1));
            newProcess.setServiceTime((int)(Math.random()*(10-1+1)+1));
            newProcess.setRemainNeedTime(newProcess.getServiceTime());
            newProcess.setFinishTime(-1);
            newProcess.setTurnoverTime(-1);
            newProcess.setWeightedTurnoverTime(-1.0);

            result.add(newProcess);
        }
        return result;
    }
}
