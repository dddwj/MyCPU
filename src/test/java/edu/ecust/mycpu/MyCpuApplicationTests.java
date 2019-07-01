package edu.ecust.mycpu;

import edu.ecust.mycpu.model.PCB;
import edu.ecust.mycpu.model.State;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyCpuApplicationTests {

    @Test
    public void contextLoads() {
        PCB pcb = new PCB();
        pcb.setState(State.ready);
        System.out.println(pcb.getState());
    }

}
