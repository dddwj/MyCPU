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
    public void contextLoads() {
//        List<PCB> unreachedList = new LinkedList<>();
//        List<PCB> runList = new LinkedList<>();
//        List<PCB> readyList = new LinkedList<>();
//        List<PCB> finishList = new LinkedList<>();
//        Map<Integer, Map<String,List<PCB>>> allData = new HashMap<>();
//        RoundListService rs = new RoundListService(unreachedList,readyList,runList,finishList,allData,3);
//        rs.init();
//        rs.sortUnreachedList();
//        try {
//            Map<Integer, Map<String, List<PCB>>> res = rs.run();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        String myjson = "{ \"6\":{\n" +
                "        \"blockup\":[\n" +
                "            {\n" +
                "                \"name\":\"进程6\",\n" +
                "                \"prio\":0,\n" +
                "                \"round\":3,\n" +
                "                \"arrivalTime\":8,\n" +
                "                \"serviceTime\":7,\n" +
                "                \"cpuTime\":0,\n" +
                "                \"remainNeedTime\":7,\n" +
                "                \"finishTime\":null,\n" +
                "                \"turnoverTime\":null,\n" +
                "                \"weightedTurnoverTime\":null,\n" +
                "                \"count\":null,\n" +
                "                \"state\":\"BLOCK_UP\",\n" +
                "                \"pid\":6\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\":\"进程7\",\n" +
                "                \"prio\":0,\n" +
                "                \"round\":3,\n" +
                "                \"arrivalTime\":10,\n" +
                "                \"serviceTime\":2,\n" +
                "                \"cpuTime\":0,\n" +
                "                \"remainNeedTime\":2,\n" +
                "                \"finishTime\":null,\n" +
                "                \"turnoverTime\":null,\n" +
                "                \"weightedTurnoverTime\":null,\n" +
                "                \"count\":null,\n" +
                "                \"state\":\"BLOCK_UP\",\n" +
                "                \"pid\":7\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\":\"进程10\",\n" +
                "                \"prio\":0,\n" +
                "                \"round\":3,\n" +
                "                \"arrivalTime\":13,\n" +
                "                \"serviceTime\":12,\n" +
                "                \"cpuTime\":0,\n" +
                "                \"remainNeedTime\":12,\n" +
                "                \"finishTime\":null,\n" +
                "                \"turnoverTime\":null,\n" +
                "                \"weightedTurnoverTime\":null,\n" +
                "                \"count\":null,\n" +
                "                \"state\":\"BLOCK_UP\",\n" +
                "                \"pid\":10\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\":\"进程8\",\n" +
                "                \"prio\":0,\n" +
                "                \"round\":3,\n" +
                "                \"arrivalTime\":15,\n" +
                "                \"serviceTime\":3,\n" +
                "                \"cpuTime\":0,\n" +
                "                \"remainNeedTime\":3,\n" +
                "                \"finishTime\":null,\n" +
                "                \"turnoverTime\":null,\n" +
                "                \"weightedTurnoverTime\":null,\n" +
                "                \"count\":null,\n" +
                "                \"state\":\"BLOCK_UP\",\n" +
                "                \"pid\":8\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\":\"进程9\",\n" +
                "                \"prio\":0,\n" +
                "                \"round\":3,\n" +
                "                \"arrivalTime\":17,\n" +
                "                \"serviceTime\":6,\n" +
                "                \"cpuTime\":0,\n" +
                "                \"remainNeedTime\":6,\n" +
                "                \"finishTime\":null,\n" +
                "                \"turnoverTime\":null,\n" +
                "                \"weightedTurnoverTime\":null,\n" +
                "                \"count\":null,\n" +
                "                \"state\":\"BLOCK_UP\",\n" +
                "                \"pid\":9\n" +
                "            }\n" +
                "        ],\n" +
                "        \"ready\":[\n" +
                "            {\n" +
                "                \"name\":\"进程1\",\n" +
                "                \"prio\":0,\n" +
                "                \"round\":3,\n" +
                "                \"arrivalTime\":2,\n" +
                "                \"serviceTime\":8,\n" +
                "                \"cpuTime\":0,\n" +
                "                \"remainNeedTime\":8,\n" +
                "                \"finishTime\":null,\n" +
                "                \"turnoverTime\":null,\n" +
                "                \"weightedTurnoverTime\":null,\n" +
                "                \"count\":null,\n" +
                "                \"state\":\"READY\",\n" +
                "                \"pid\":1\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\":\"进程2\",\n" +
                "                \"prio\":0,\n" +
                "                \"round\":3,\n" +
                "                \"arrivalTime\":3,\n" +
                "                \"serviceTime\":5,\n" +
                "                \"cpuTime\":0,\n" +
                "                \"remainNeedTime\":5,\n" +
                "                \"finishTime\":null,\n" +
                "                \"turnoverTime\":null,\n" +
                "                \"weightedTurnoverTime\":null,\n" +
                "                \"count\":null,\n" +
                "                \"state\":\"READY\",\n" +
                "                \"pid\":2\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\":\"进程5\",\n" +
                "                \"prio\":0,\n" +
                "                \"round\":3,\n" +
                "                \"arrivalTime\":1,\n" +
                "                \"serviceTime\":4,\n" +
                "                \"cpuTime\":3,\n" +
                "                \"remainNeedTime\":1,\n" +
                "                \"finishTime\":null,\n" +
                "                \"turnoverTime\":null,\n" +
                "                \"weightedTurnoverTime\":null,\n" +
                "                \"count\":null,\n" +
                "                \"state\":\"READY\",\n" +
                "                \"pid\":5\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\":\"进程4\",\n" +
                "                \"prio\":0,\n" +
                "                \"round\":3,\n" +
                "                \"arrivalTime\":5,\n" +
                "                \"serviceTime\":9,\n" +
                "                \"cpuTime\":0,\n" +
                "                \"remainNeedTime\":9,\n" +
                "                \"finishTime\":null,\n" +
                "                \"turnoverTime\":null,\n" +
                "                \"weightedTurnoverTime\":null,\n" +
                "                \"count\":null,\n" +
                "                \"state\":\"READY\",\n" +
                "                \"pid\":4\n" +
                "            }\n" +
                "        ],\n" +
                "        \"run\":[\n" +
                "            {\n" +
                "                \"name\":\"进程3\",\n" +
                "                \"prio\":0,\n" +
                "                \"round\":1,\n" +
                "                \"arrivalTime\":1,\n" +
                "                \"serviceTime\":10,\n" +
                "                \"cpuTime\":2,\n" +
                "                \"remainNeedTime\":8,\n" +
                "                \"finishTime\":null,\n" +
                "                \"turnoverTime\":null,\n" +
                "                \"weightedTurnoverTime\":null,\n" +
                "                \"count\":null,\n" +
                "                \"state\":\"READY\",\n" +
                "                \"pid\":3\n" +
                "            }\n" +
                "        ],\n" +
                "        \"finish\":[\n" +
                "\n" +
                "        ]\n" +
                "    }}";
        Map allData = JSON.parseObject(myjson, Map.class);
        Integer currentTime = 0;
        String currentDataStr = null;
        Map<String, List<PCB>> result = new HashMap<>();
        List<List<PCB>> allList = new ArrayList<>();
        List<PCB> blockupList = new LinkedList<>();
        List<PCB> readyList = new LinkedList<>();
        List<PCB> runList = new LinkedList<>();
        List<PCB> finishList = new LinkedList<>();
        for (Object obj : allData.keySet()) {
            currentTime = Integer.parseInt(String.valueOf(obj));
            currentDataStr = String.valueOf(allData.get(obj));
            System.out.println("key为：" + currentTime + "  值为：" + currentDataStr);
            result = JSONObject.parseObject(currentDataStr)
                    .entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> JSONObject.parseArray(String.valueOf(entry.getValue()), PCB.class)));
            System.out.println(result.toString());
        }
        //每个List赋值
        blockupList.addAll(result.get("blockup"));
        readyList.addAll(result.get("ready"));
        runList.addAll(result.get("run"));
        finishList.addAll(result.get("finish"));

        System.out.println("从前端接收到的数据为：");
        System.out.println("第" + currentTime + "秒：");
        System.out.println("==========================================");
        System.out.println("未到达队列：");
        for (PCB p : blockupList) {
            System.out.println(p.toString());
        }
        System.out.println("就绪队列:");
        for (PCB p : readyList) {
            System.out.println(p.toString());
        }
        System.out.println("执行队列:");
        for (PCB p : runList) {
            System.out.println(p.toString());
        }
        System.out.println("完成队列:");
        for (PCB p : finishList) {
            System.out.println(p.toString());
        }
        System.out.println("==========================================");

        System.out.println("再次执行run方法");
        Map<Integer, Map<String, List<PCB>>> modifiedallData = new HashMap<>();
//        RoundListService rs = new RoundListService(currentTime + 1, blockupList, readyList, runList, finishList, modifiedallData, 2,10);
//        try {
//            Map<Integer, Map<String, List<PCB>>> res = rs.run();
//            for (Object key : res.keySet()) {
//                System.out.println("==========================================");
//                System.out.println("当前时间为第" + key + "秒");
//                Map<String, List<PCB>> map = res.get(key);
//                for (Object name : map.keySet()) {
//                    System.out.println("列表名：" + name);
//                    System.out.println("PCB数据：" + map.get(name));
//                }
//                System.out.println("==========================================");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

}
