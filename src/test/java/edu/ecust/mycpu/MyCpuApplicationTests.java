package edu.ecust.mycpu;

import edu.ecust.mycpu.model.PCB;
import edu.ecust.mycpu.model.State;
import edu.ecust.mycpu.service.RoundListService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyCpuApplicationTests {

    @Test
    public void contextLoads() {
        List<PCB> unreachedList = new LinkedList<>();
        List<PCB> runList = new LinkedList<>();
        List<PCB> readyList = new LinkedList<>();
        List<PCB> finishList = new LinkedList<>();
        Map<Integer, Map<String,List<PCB>>> allData = new HashMap<>();
        RoundListService rs = new RoundListService(unreachedList,readyList,runList,finishList,allData,3);
        rs.init();
        rs.sortUnreachedList();
        rs.run();
        //queue可以改
//        List<PCB> q = new LinkedList<>();
//        PCB p = new PCB();
//        p.setState(State.READY);
//        q.offer(p);
//        System.out.println(q.peek().getState());
//        q.peek().setState(State.READY);
//        System.out.println(q.peek().getState());
//        PCB pcb = new PCB();
//        pcb.setState(State.READY);
//        System.out.println(pcb.getState());
    }

}
