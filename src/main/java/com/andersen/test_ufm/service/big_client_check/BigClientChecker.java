package com.andersen.test_ufm.service.big_client_check;

import org.json.simple.JSONObject;

import java.util.List;

public class BigClientChecker {

    public static Boolean isBigClient(JSONObject clientData) {
        return ((List) clientData.get("subscribers")).size() > 100;
    }

}
