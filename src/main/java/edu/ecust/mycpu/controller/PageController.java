package edu.ecust.mycpu.controller;

import edu.ecust.mycpu.model.PCB;
import edu.ecust.mycpu.model.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class PageController {

    @GetMapping("/")
    public String showMainPage(){
        return "main";
    }


    @ResponseBody
    @GetMapping("/data")
    public Map<Integer, LinkedList<PCB>> showPCB(){
        LinkedList<PCB> list = new LinkedList<>();
        for(int i=0; i < 3; i++){
            list.add(new PCB(){{
                setName("hello");
            }});
        }
        return new HashMap<Integer, LinkedList<PCB>>(){{
            put(1, list);
            put(2, list);
        }};
    }

    @ResponseBody
    @GetMapping("/data2")
    public Queue<PCB> showPCB2(){
        Queue<PCB> list = new LinkedList<>();
        for(int i=0; i < 5; i++){
            list.add(new PCB(){{
                setName("hello");
                setState(State.READY);
            }});
        }
        return list;
    }

}
