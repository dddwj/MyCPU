package edu.ecust.mycpu;

import edu.ecust.mycpu.model.PCB;
import edu.ecust.mycpu.model.State;
import edu.ecust.mycpu.service.RoundListService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.Queue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyCpuApplicationTests {

    @Test
    public void contextLoads() {
//        Queue<PCB> unreachedList = new LinkedList<>();
//        Queue<PCB> readyList = new LinkedList<>();
//        Queue<PCB> finishList = new LinkedList<>();
//        RoundListService rs = new RoundListService(unreachedList,readyList,finishList,2);
//        rs.init();
        //queue可以改
        Queue<PCB> q = new LinkedList<>();
        PCB p = new PCB();
        p.setState(State.ready);
        q.offer(p);
        System.out.println(q.peek().getState());
        q.peek().setState(State.finish);
        System.out.println(q.peek().getState());
    }

}
