package com.andersen.test_ufm.service.spent_calc;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
public class DroolsSpentCalc implements ISpentCalc {

    @PostConstruct
    public void init() {

    }

    @Override
    public Long process(List<JSONObject> listSpent) {

        return 20000l;
    }

}
