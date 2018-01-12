package com.andersen.test_ufm.service;

import com.andersen.test_ufm.service.big_client_check.BigClientChecker;
import com.andersen.test_ufm.service.spent_calc.ISpentCalc;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainProcessService implements IProcessService {

    @Autowired
    @Qualifier("multithreadSpentCalc")
    private ISpentCalc spentCalc;

    @Override
    public JSONObject process(JSONObject inputData) {
        JSONObject outputData = new JSONObject();
        outputData.put("clientId", inputData.get("clientId"));
        outputData.put("spentTotal", spentCalc.process((List) inputData.get("subscribers")));
        outputData.put("isBig", BigClientChecker.isBigClient(inputData));
        return outputData;
    }

}
