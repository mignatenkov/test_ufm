package com.andersen.test_ufm.service;

import org.json.simple.JSONObject;

import java.util.List;

public interface IProcessService {

    Long process(List<JSONObject> listSpent);

}
