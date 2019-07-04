package edu.ecust.mycpu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.ecust.mycpu.model.PCB;
//import edu.ecust.mycpu.service.RoundListService;
import edu.ecust.mycpu.service.RoundListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class RRController {

//    @ResponseBody
//    @PostMapping("/RRData")
//    public Map<Integer, Map<String, List<PCB>>> sentRRData(){
//        List<PCB> unreachedList = new LinkedList<>();
//        List<PCB> runList = new LinkedList<>();
//        List<PCB> readyList = new LinkedList<>();
//        List<PCB> finishList = new LinkedList<>();
//        Map<Integer,Map<String,List<PCB>>> allData = new HashMap<>();
//        RoundListService rs = new RoundListService(unreachedList,readyList,runList,finishList,allData,3);
//        rs.init();
//        rs.sortUnreachedList();
//        Map<Integer, Map<String, List<PCB>>> res = null;
//        try {
//            res = rs.run();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return res;
//    }

    @ResponseBody
    @PostMapping("/RRData")
    public Map<Integer, Map<String, List<PCB>>> sentRRData(@RequestBody String data){
        try {
            data = java.net.URLDecoder.decode(data,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] split = data.split("=");
        //获得当前秒JSON字符串
        String currentDataMap = split[0];
        System.out.println(currentDataMap);
        //将第一层Map转化出来
        Map allData = JSON.parseObject(currentDataMap,Map.class);
        //获取当前时间
        Integer currentTime = Integer.parseInt(String.valueOf(allData.get("current_time")));
        System.out.println("当前时间为："+currentTime);
        //获取当前队列JSON字符串
        String currentDataStr = String.valueOf(allData.get(String.valueOf(currentTime)));
        Map<String, List<PCB>> result = JSONObject.parseObject(currentDataStr)
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> JSONObject.parseArray(String.valueOf(entry.getValue()), PCB.class)));
        System.out.println("Map<String, List<PCB>> "+result.toString());
        //获取时间片长度
        Integer round = Integer.parseInt(String.valueOf(allData.get("round")));
        System.out.println("时间片长度："+round);
        //创建相应队列
        List<List<PCB>> allList = new ArrayList<>();
        List<PCB> blockupList = new LinkedList<>();
        List<PCB> readyList = new LinkedList<>();
        List<PCB> runList = new LinkedList<>();
        List<PCB> finishList = new LinkedList<>();
//        for (Object obj : allData.keySet()){
////            currentTime = Integer.parseInt(String.valueOf(obj));
//            currentDataStr = String.valueOf(allData.get(obj));
////            System.out.println("key为："+currentTime+"  值为："+currentDataStr);
//            result = JSONObject.parseObject(currentDataStr)
//                    .entrySet().stream()
//                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> JSONObject.parseArray(String.valueOf(entry.getValue()), PCB.class)));
//            System.out.println("Map<String, List<PCB>>"+result.toString());
//        }
        //每个队列存入数据
        for(String name:result.keySet()){
            allList.add(result.get(name));
        }
        blockupList.addAll(allList.get(0));
        readyList.addAll(allList.get(1));
        runList.addAll(allList.get(2));
        finishList.addAll(allList.get(3));

        System.out.println("从前端接收到的数据为：");
        System.out.println("第"+currentTime+"秒：");
        System.out.println("==========================================");
        System.out.println("未到达队列：");
        for (PCB p: blockupList) {
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

        System.out.println("再次执行run方法");
        Map<Integer,Map<String,List<PCB>>> modifiedallData = new HashMap<>();
        RoundListService rs;
        if(currentTime==0){
            rs = new RoundListService(blockupList,readyList,runList,finishList,modifiedallData,round);
            rs.init();
            rs.sortUnreachedList();
        }else {
            rs = new RoundListService(currentTime+1,blockupList,readyList,runList,finishList,modifiedallData,round);

        }
        try {
            Map<Integer, Map<String, List<PCB>>> res = rs.run();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
