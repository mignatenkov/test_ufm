package com.andersen.test_ufm.service.spent_calc;

import org.json.simple.JSONObject;

import java.util.List;

public interface ISpentCalc {

    Long process(List<JSONObject> listSpent);

}
