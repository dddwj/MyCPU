package edu.ecust.mycpu.service;

import edu.ecust.mycpu.model.PCB;
import edu.ecust.mycpu.model.State;
import edu.ecust.mycpu.util.PCBComprator;
import edu.ecust.mycpu.util.PCBCompratorByPriority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;


@Service
public class PriorityService {

    public Map<Integer, Map<String, List<PCB>>> Priority(ArrayList<PCB> unreachedList, ArrayList<PCB> readyList, ArrayList<PCB> finishList, ArrayList<PCB> runningList, ArrayList<PCB> jamList, Integer currentTime) throws IOException, ClassNotFoundException {
        Integer processNum = 20;
        /*
        若发现某个进程的PCB为空，直接返回错误
        */
        for(int i = 0; i<unreachedList.size(); i++){
            if (unreachedList.get(i)==null){
                return null;
            }
        }

        /*
        把就绪队列中的进程放入优先队列中
        */
        PCBCompratorByPriority comprator = new PCBCompratorByPriority();
        PriorityQueue<PCB> readyQueue = new PriorityQueue<PCB>(processNum+10,comprator);
        for(int i=0;i<readyList.size();i++){
            /*
            若发现某个进程的PCB为空，直接返回错误
             */
            if(readyList.get(i)==null)
                return null;
            readyQueue.add(readyList.get(i));
        }
        Map<Integer, Map<String, List<PCB>>> result = new HashMap<>();

        /*
        模拟过程
         */
        while(true){
            /*
            正在运行的进程，就绪队列，未到达队列都为空时结束
             */
            System.out.println(currentTime);
            System.out.println("运行队列："+runningList);
            System.out.println("未到达队列："+unreachedList);
            System.out.println("就绪队列："+readyQueue);
            System.out.println("完成队列："+finishList);
            if(runningList.isEmpty()&&unreachedList.isEmpty()&&readyQueue.isEmpty()){
                break;
            }
            /*
            遍历未到达队列，若有当前时间即将到达的进程，将其加入就绪队列
             */
            if(!unreachedList.isEmpty()){
                for(int i=0;i<unreachedList.size();i++){
                    PCB unreachedProcess = unreachedList.get(i);
                    if(unreachedProcess.getArrivalTime().equals(currentTime)){
                        readyQueue.add(unreachedProcess);
                        unreachedProcess.setState(State.READY);
                        unreachedList.remove(unreachedProcess);
                        i--;
                    }
                }
            }

            /*
            若运行队列不为空，若该进程的总共需要占用的CPU时间大于或等于已占用CPU的处理时间，将它取出，使运行队列为空。
             */
            if(!runningList.isEmpty()){
                PCB runningProcess = runningList.get(0);
                if(runningProcess.getCpuTime()>=runningProcess.getServiceTime()){
                    runningList.remove(0);
                    runningProcess.setState(State.FINISH);
                    runningProcess.setFinishTime(currentTime);
                    runningProcess.setTurnoverTime(currentTime-runningProcess.getArrivalTime());
                    runningProcess.setWeightedTurnoverTime(Double.valueOf(runningProcess.getTurnoverTime())/runningProcess.getServiceTime());
                    finishList.add(runningProcess);
                }
            }

            /*
            若运行队列不为空，随机判断是否对运行中的进程进行阻塞。
            若阻塞，把它的状态修改为阻塞，将它从运行队列中转移到阻塞队列，使运行队列为空。
             */
            if(!runningList.isEmpty()){
                if(Math.random()*100+1<5){
                    PCB runningProcess = runningList.get(0);
                    runningList.remove(0);
                    runningProcess.setState(State.BLOCK_UP);
                    jamList.add(runningProcess);
                }
            }

            /*
            运行队列为空时，若就绪队列不为空，那么取出就绪队列首的进程，放入运行队列中,否则等待，不执行任何操作。
             */
            if(runningList.isEmpty()){
                if(!readyQueue.isEmpty()){
                    PCB topPriorityProcess = readyQueue.remove();
                    topPriorityProcess.setState(State.RUN);
                    runningList.add(topPriorityProcess);
                }
            }

            /*
            若运行队列不为空，将运行队列中的进程的运行时间加一。
             */
            if(!runningList.isEmpty()){
                PCB runningProcess = runningList.get(0);
                runningProcess.setCpuTime(runningProcess.getCpuTime()+1);
                runningProcess.setRemainNeedTime(runningProcess.getRemainNeedTime()-1);
            }

            /*
            输出结果
             */
            Map<String,List<PCB>> currentData = new HashMap<>();
            currentData.put("blockup",new ArrayList<>());
            currentData.put("ready",new ArrayList<>());
            currentData.put("run",new ArrayList<>());
            currentData.put("finish",new ArrayList<>());
            currentData.put("jam",new ArrayList<>());
            for(int i=0;i<unreachedList.size();i++){
                currentData.get("blockup").add(unreachedList.get(i).deepClone());
            }
            for(int i=0;i<runningList.size();i++){
                currentData.get("run").add(runningList.get(i).deepClone());
            }
            Iterator it = readyQueue.iterator();
            while(it.hasNext()){
                currentData.get("ready").add(((PCB)it.next()).deepClone());
            }
            for(int i=0;i<finishList.size();i++){
                currentData.get("finish").add(finishList.get(i).deepClone());
            }
            for(int i=0;i<jamList.size();i++){
                currentData.get("jam").add(jamList.get(i).deepClone());
            }
            result.put(currentTime,currentData);
            currentTime++;
            if(currentTime>500){
                System.out.println("TLE");
                return null;
            }
        }


        return result;
    }
}
