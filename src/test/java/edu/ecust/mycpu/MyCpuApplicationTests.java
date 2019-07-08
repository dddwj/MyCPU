package edu.ecust.mycpu;

import edu.ecust.mycpu.model.PCB;
import edu.ecust.mycpu.model.State;
import edu.ecust.mycpu.service.RoundListService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyCpuApplicationTests {

    @Test
    public void contextLoads() {
        PCB pcb = new PCB();
        pcb.setPID(123);
        PCB bcp = pcb;
        System.out.println(pcb.getPID());
        System.out.println(pcb.getPID());
        pcb.setPID(321);
        System.out.println(pcb.getPID());
        System.out.println(pcb.getPID());

    }

}
