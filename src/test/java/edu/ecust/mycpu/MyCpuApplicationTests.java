package edu.ecust.mycpu;

import com.alibaba.fastjson.*;
import edu.ecust.mycpu.model.PCB;
import edu.ecust.mycpu.model.State;
import edu.ecust.mycpu.service.RoundListService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyCpuApplicationTests {

    @Test
    public void contextLoads(){
        List<PCB> unreachedList = new LinkedList<>();
        List<PCB> runList = new LinkedList<>();
        List<PCB> readyList = new LinkedList<>();
        List<PCB> finishList = new LinkedList<>();
        Map<Integer, Map<String,List<PCB>>> allData = new HashMap<>();
        RoundListService rs = new RoundListService(unreachedList,readyList,runList,finishList,allData,3);
        rs.init();
        rs.sortUnreachedList();
        try {
            Map<Integer, Map<String, List<PCB>>> res = rs.run();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//        String myjson = "{ \"6\":{\n" +
//                "        \"blockup\":[\n" +
//                "\n" +
//                "        ],\n" +
//                "        \"ready\":[\n" +
//                "            {\n" +
//                "                \"name\":\"进程2\",\n" +
//                "                \"prio\":0,\n" +
//                "                \"round\":1,\n" +
//                "                \"arrivalTime\":3,\n" +
//                "                \"serviceTime\":5,\n" +
//                "                \"cpuTime\":5,\n" +
//                "                \"remainNeedTime\":0,\n" +
//                "                \"finishTime\":27,\n" +
//                "                \"turnoverTime\":24,\n" +
//                "                \"weightedTurnoverTime\":4.8,\n" +
//                "                \"count\":null,\n" +
//                "                \"state\":\"FINISH\",\n" +
//                "                \"pid\":2\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"name\":\"进程3\",\n" +
//                "                \"prio\":0,\n" +
//                "                \"round\":2,\n" +
//                "                \"arrivalTime\":1,\n" +
//                "                \"serviceTime\":10,\n" +
//                "                \"cpuTime\":10,\n" +
//                "                \"remainNeedTime\":0,\n" +
//                "                \"finishTime\":40,\n" +
//                "                \"turnoverTime\":39,\n" +
//                "                \"weightedTurnoverTime\":3.9,\n" +
//                "                \"count\":null,\n" +
//                "                \"state\":\"FINISH\",\n" +
//                "                \"pid\":3\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"name\":\"进程4\",\n" +
//                "                \"prio\":0,\n" +
//                "                \"round\":0,\n" +
//                "                \"arrivalTime\":5,\n" +
//                "                \"serviceTime\":9,\n" +
//                "                \"cpuTime\":9,\n" +
//                "                \"remainNeedTime\":0,\n" +
//                "                \"finishTime\":44,\n" +
//                "                \"turnoverTime\":39,\n" +
//                "                \"weightedTurnoverTime\":4.33,\n" +
//                "                \"count\":null,\n" +
//                "                \"state\":\"FINISH\",\n" +
//                "                \"pid\":4\n" +
//                "            }\n" +
//                "        ],\n" +
//                "        \"run\":[\n" +
//                "            {\n" +
//                "                \"name\":\"进程1\",\n" +
//                "                \"prio\":0,\n" +
//                "                \"round\":1,\n" +
//                "                \"arrivalTime\":2,\n" +
//                "                \"serviceTime\":8,\n" +
//                "                \"cpuTime\":8,\n" +
//                "                \"remainNeedTime\":0,\n" +
//                "                \"finishTime\":38,\n" +
//                "                \"turnoverTime\":36,\n" +
//                "                \"weightedTurnoverTime\":4.5,\n" +
//                "                \"count\":null,\n" +
//                "                \"state\":\"FINISH\",\n" +
//                "                \"pid\":1\n" +
//                "            }\n" +
//                "        ],\n" +
//                "        \"finish\":[\n" +
//                "\n" +
//                "        ]\n" +
//                "    }}";
//        Map allData = JSON.parseObject(myjson,Map.class);
//        Integer currentTime = 0;
//        String currentDataStr = null;
//        Map<String, List<PCB>> result = new HashMap<>();
//        List<List<PCB>> allList = new ArrayList<>();
//        List<PCB> blockupList = new LinkedList<>();
//        List<PCB> readyList = new LinkedList<>();
//        List<PCB> runList = new LinkedList<>();
//        List<PCB> finishList = new LinkedList<>();
//        for (Object obj : allData.keySet()){
//            currentTime = Integer.parseInt(String.valueOf(obj));
//            currentDataStr = String.valueOf(allData.get(obj));
//            System.out.println("key为："+currentTime+"  值为："+currentDataStr);
//            result = JSONObject.parseObject(currentDataStr)
//                    .entrySet().stream()
//                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> JSONObject.parseArray(String.valueOf(entry.getValue()), PCB.class)));
//            System.out.println(result.toString());
//        }
//        for(String name:result.keySet()){
//            allList.add(result.get(name));
//        }
//        blockupList.addAll(allList.get(0));
//        readyList.addAll(allList.get(1));
//        runList.addAll(allList.get(2));
//        finishList.addAll(allList.get(3));
//
//        System.out.println("从前端接收到的数据为：");
//        System.out.println("第"+currentTime+"秒：");
//        System.out.println("==========================================");
//        System.out.println("未到达队列：");
//        for (PCB p: blockupList) {
//            System.out.println(p.toString());
//        }
//        System.out.println("就绪队列:");
//        for (PCB p: readyList) {
//            System.out.println(p.toString());
//        }
//        System.out.println("执行队列:");
//        for (PCB p: runList) {
//            System.out.println(p.toString());
//        }
//        System.out.println("完成队列:");
//        for (PCB p: finishList) {
//            System.out.println(p.toString());
//        }
//        System.out.println("==========================================");
//
//        System.out.println("再次执行run方法");
//        Map<Integer,Map<String,List<PCB>>> modifiedallData = new HashMap<>();
//        RoundListService rs = new RoundListService(currentTime+1,blockupList,readyList,runList,finishList,modifiedallData,2);
//        try {
//            Map<Integer, Map<String, List<PCB>>> res = rs.run();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

}
