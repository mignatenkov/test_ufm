package com.andersen.test_ufm.service;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProcessService {

    @Value("${application.config.numthreads:20}")
    private Integer numthreads;

    public Long countSpentByClient(List<JSONObject> listSpent) {
//        TODO Многопоточная обработка
//        получать данные в виде List<JSONObject>
//            {
//                "id" : <subscriber id, Long>,
//                "spent" : <spent amount, Long>
//            }
//        и в %numthreads% потоков посчитать сумму полей "spent" полученного списка
//        Использовать Future и ThreadPool по необходимости
//        TODO Покрыть тестами и проверить вышеописанные ф-ции
        log.debug("numthreads: " + numthreads.toString());
        return null;
    }

}
