package com.andersen.test_ufm.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IProcessService {

    JSONObject process(JSONObject inputData);

}
