package com.treehole.appointment;

import com.treehole.appointment.client.PsychologistClient;
import com.treehole.framework.domain.psychologist.Detail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PsychologistTest {
    @Autowired
    private PsychologistClient psychologistClient;

    @Test
    public void  aaa(){
        Detail detailById = psychologistClient.getDetailById("1");
        System.out.println(detailById);
    }
}
