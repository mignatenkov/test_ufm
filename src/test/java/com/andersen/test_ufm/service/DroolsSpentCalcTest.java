package com.andersen.test_ufm.service;

import com.andersen.test_ufm.Application;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class DroolsSpentCalcTest {

    @Autowired
    private IProcessService droolsSpentCalc;

    @Test
    public void testProcess() throws Exception {
        List<JSONObject> inputData = new ArrayList<>();
        int numTestElms = 20000;
        for (int i = 0; i < numTestElms; i++) {
            JSONObject newElm = new JSONObject();
            newElm.put("id", 0);
            newElm.put("spent", 1l);
            inputData.add(newElm);
        }
        log.info("START TIME: " + LocalDateTime.now().toString());
        Long res = droolsSpentCalc.process(inputData);
        log.info("FINISH TIME: " + LocalDateTime.now().toString());

        Assert.assertEquals(20000l, res.longValue());
    }

}