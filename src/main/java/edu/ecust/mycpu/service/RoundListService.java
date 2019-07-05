package edu.ecust.mycpu.service;

import edu.ecust.mycpu.model.PCB;
import edu.ecust.mycpu.model.State;
import edu.ecust.mycpu.util.PCBComprator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

//@Service
public class RoundListService {
    /*全部运行数据*/
    Map<Integer,Map<String,List<PCB>>> allData;

    /*当前时间*/
    private Integer currentTime;

    /*
    @TODO：进程数（未来加入构造函数）
    */
    private Integer processNum;

    /*未到达进程队列*/
    private List<PCB> unreachedList;

    /*就绪进程队列*/
    private List<PCB> readyList;

    /*运行进程队列*/
    private List<PCB> runList;

    /*已完成进程队列*/
    private List<PCB> finishList;

    /*阻塞进程队列*/
    private List<PCB> jamList;

    /*时间片长度*/
    private Integer round;

    private Random random;

    private int[][] p={{2,8,0,8},{3,5,0,5},{1,10,0,10},{5,9,0,9},{1,4,0,4},{8,7,0,7},{10,2,0,2},{15,3,0,3},{17,6,0,6},{13,12,0,12}};

    public RoundListService(Integer currentTime,List<PCB> unreachedList, List<PCB> readyList, List<PCB> runList,List<PCB> finishList,Map<Integer,Map<String,List<PCB>>> allData,Integer round) {
        this.currentTime = currentTime;
        this.processNum = 15;
        this.unreachedList = unreachedList;
        this.readyList = readyList;
        this.runList = runList;
        this.finishList = finishList;
        this.allData = allData;
        this.round = round;
    }

    public RoundListService(List<PCB> unreachedList, List<PCB> readyList, List<PCB> runList,List<PCB> finishList, Map<Integer,Map<String,List<PCB>>> allData,Integer round) {
        this.currentTime = 0;
        this.processNum = 15;
        this.unreachedList = unreachedList;
        this.readyList = readyList;
        this.runList = runList;
        this.finishList = finishList;
        this.allData = allData;
        this.round = round;
    }

    public void init(String type){
        if("default".equals(type)){
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
        }else{//随机生成
            Random random = new Random();
            for(int i=0;i<processNum;i++){
                PCB pcb = new PCB();
                pcb.setPID(i+1);
                pcb.setName("进程"+(i+1));
                pcb.setPrio(0);
                pcb.setRound(round);
                pcb.setArrivalTime(random.nextInt(20));
                pcb.setServiceTime(Math.abs(random.nextInt() % 20) + 1);
                pcb.setCpuTime(0);
                pcb.setRemainNeedTime(pcb.getServiceTime());
                pcb.setState(State.BLOCK_UP);
                unreachedList.add(pcb);
            }
        }

    }

    /*给未到达进程按到达时间升序排序*/
    public void sortUnreachedList(){
        Collections.sort(unreachedList,new PCBComprator());
    }

    /*模拟进程运行*/
    public Map<Integer,Map<String,List<PCB>>> run() throws IOException, ClassNotFoundException {
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
                        PCB temp = p.deepClone();
                        readyList.add(temp);
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
                    cur.setFinishTime(currentTime);
                    cur.setTurnoverTime(cur.getFinishTime()-cur.getArrivalTime());
                    Double t = Double.valueOf(cur.getTurnoverTime())/cur.getServiceTime();
                    java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");
                    cur.setWeightedTurnoverTime(Double.valueOf(df.format(t)));
                    //弹出runlist中进程放入finishlist;
                    runList.remove(0);
                    finishList.add(cur);

                    //进行阻塞判定

                    //readylist第一个进入runlist
                    if(!readyList.isEmpty())
                        runList.add(readyList.remove(0));
                //时间片时间走完
                }else if(roundTime==0){
                    cur.setRound(round);
                    cur.setState(State.READY);
                    //弹出runlist中进程放入readylist末尾
                    runList.remove(0);
                    readyList.add(cur);

                    //进行阻塞判定

                    //readylist第一个进入runlist
                    runList.add(readyList.remove(0));
                //时间片和剩余CPU时间均大于0，执行一秒操作
                }
                //是否阻塞
                else {
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


            Map<String,List<PCB>> currentData = new HashMap<>();
            List<PCB> ur = new LinkedList<>();
            for (PCB p:
                 unreachedList) {
                //新队列插入存入深克隆对象
                ur.add(p.deepClone());
            }
            currentData.put("blockup",ur);

            List<PCB> re = new LinkedList<>();
            for (PCB p:
                    readyList) {
                //新队列插入存入深克隆对象
                re.add(p.deepClone());
            }
            currentData.put("ready",re);

            List<PCB> ru = new LinkedList<>();
            for (PCB p:
                    runList) {
                //新队列插入存入深克隆对象
                ru.add(p.deepClone());
            }
            currentData.put("run",ru);

            List<PCB> fi = new LinkedList<>();
            for (PCB p:
                    finishList) {
                //新队列插入存入深克隆对象
                fi.add(p.deepClone());
            }
            currentData.put("finish",fi);
            allData.put(currentTime,currentData);
//            System.out.println(allData.toString());

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
