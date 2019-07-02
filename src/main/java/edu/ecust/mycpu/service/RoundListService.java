package edu.ecust.mycpu.service;

import edu.ecust.mycpu.model.PCB;
import edu.ecust.mycpu.model.State;
import edu.ecust.mycpu.util.PCBComprator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

//@Service
public class RoundListService {
    /*全部运行数据*/
    Map<Integer,Map<String,List<PCB>>> allData;

    /*当前时间*/
    private Integer currentTime;

    /*进程数*/
    private Integer processNum;

    /*未到达进程队列*/
    private List<PCB> unreachedList;

    /*就绪进程队列*/
    private List<PCB> readyList;

    /*运行进程队列*/
    private List<PCB> runList;

    /*已完成进程队列*/
    private List<PCB> finishList;

    /*时间片长度*/
    private Integer round;

    private Random random;

    private int[][] p={{2,8,0,8},{3,5,0,5},{1,10,0,10},{5,9,0,9}};

    public RoundListService(List<PCB> unreachedList, List<PCB> readyList, List<PCB> runList,List<PCB> finishList, Map<Integer,Map<String,List<PCB>>> allData,Integer round) {
        this.currentTime = 0;
        this.processNum = 4;
        this.unreachedList = unreachedList;
        this.readyList = readyList;
        this.runList = runList;
        this.finishList = finishList;
        this.allData = allData;
        this.round = round;
    }

    public void init(){
        for(int i=0;i<processNum;i++){
            PCB pcb = new PCB();
            pcb.setPID(i+1);
            pcb.setName("进程"+(i+1));
            pcb.setPrio(0);
            pcb.setRound(round);
            pcb.setArrivalTime(p[i][0]);
            pcb.setServiceTime(p[i][1]);
            pcb.setCpuTime(p[i][2]);
            pcb.setRemainNeedTime(p[i][3]);
            pcb.setState(State.BLOCK_UP);
            unreachedList.add(pcb);
        }
    }

    /*给未到达进程按到达时间升序排序*/
    public void sortUnreachedList(){
        Collections.sort(unreachedList,new PCBComprator());
    }

    /*模拟进程运行*/
    public Map<Integer,Map<String,List<PCB>>> run(){
        while (true){
            //看做模拟运行已结束
            if(unreachedList.isEmpty()&&readyList.isEmpty()&&runList.isEmpty())
                return allData;
            //将已经到达的进程放入就绪队列
            if(!unreachedList.isEmpty()){
                Iterator<PCB> itr = unreachedList.iterator();
                while(itr.hasNext()){
                    PCB p = itr.next();
                    if(p.getArrivalTime().equals(currentTime)){
                        p.setState(State.READY);
                        readyList.add(p);
                        itr.remove();
                    }else {
                        break;
                    }
                }
            }

            //判断当前run是否为空
            if(runList.isEmpty()){
                //如果空取就绪队列的第一个进程放入run队列
                if(!readyList.isEmpty()){
                    PCB cur = readyList.remove(0);
                    cur.setState(State.RUN);
                    runList.add(cur);

                    // 按时间片时间获得CPU服务(每次走一秒)
                    int cpuTime = cur.getCpuTime();
                    int remainNeedTime = cur.getRemainNeedTime();
                    int roundTime = cur.getRound();
                    cpuTime++;
                    remainNeedTime--;
                    roundTime--;
                    cur.setCpuTime(cpuTime);
                    cur.setRemainNeedTime(remainNeedTime);
                    cur.setRound(roundTime);
                    //更新runlist状态
                    runList.set(0,cur);
                }
            }
            //run队列不空，证明有进程在跑,查看当前进程状态
            else{
                PCB cur = runList.get(0);
                int cpuTime = cur.getCpuTime();
                int remainNeedTime = cur.getRemainNeedTime();
                int roundTime = cur.getRound();
                //已运行完毕
                if(remainNeedTime==0){
                    cur.setState(State.FINISH);
                    //弹出runlist中进程放入finishlist;
                    runList.remove(0);
                    finishList.add(cur);
                //时间片时间走完
                }else if(roundTime==0){
                    cur.setRound(round);
                    cur.setState(State.READY);
                    //弹出runlist中进程放入readylist末尾
                    runList.remove(0);
                    readyList.add(cur);
                    //readylist第一个进入runlist
                    runList.add(readyList.remove(0));
                //时间片和剩余CPU时间均大于0，执行一秒操作
                }else {
                    cpuTime++;
                    remainNeedTime--;
                    roundTime--;
                    cur.setCpuTime(cpuTime);
                    cur.setRemainNeedTime(remainNeedTime);
                    cur.setRound(roundTime);
                    //更新runlist状态
                    runList.set(0,cur);
                }
            }


            //超时就跳出循环
            if(currentTime==100)
                return null;

            Map<String,List<PCB>> currentData = new HashMap<String,List<PCB>>();
            List<PCB> ur = new LinkedList<>();
            ur.addAll(unreachedList);
            currentData.put("blockup",ur);

            List<PCB> re = new LinkedList<>();
            re.addAll(readyList);
            currentData.put("ready",re);

            List<PCB> ru = new LinkedList<>();
            ru.addAll(runList);
            currentData.put("run",ru);

            List<PCB> fi = new LinkedList<>();
            fi.addAll(finishList);
            currentData.put("finish",fi);
            allData.put(currentTime,currentData);
//            allData.put(currentTime,new HashMap<String,List<PCB>>(){{
//                put("blockup",unreachedList);
//                put("ready",readyList);
//                put("run",runList);
//                put("finish",finishList);
//            }});
            System.out.println(allData.toString());

            //单元测试
            System.out.println("第"+currentTime+"秒：");
            System.out.println("==========================================");
            System.out.println("未到达队列：");
            for (PCB p: unreachedList) {
                System.out.println(p.toString());
            }
            System.out.println("就绪队列:");
            for (PCB p: readyList) {
                System.out.println(p.toString());
            }
            System.out.println("执行队列:");
            for (PCB p: runList) {
                System.out.println(p.toString());
            }
            System.out.println("完成队列:");
            for (PCB p: finishList) {
                System.out.println(p.toString());
            }
            System.out.println("==========================================");


            //时间前进一秒
            currentTime++;
        }

    }

}
