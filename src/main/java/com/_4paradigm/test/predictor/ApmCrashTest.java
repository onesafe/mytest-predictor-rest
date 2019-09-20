package com._4paradigm.test.predictor;

import com._4paradigm.prophet.rest.client.HttpExecution;
import com._4paradigm.prophet.rest.client.HttpOperator;
import com._4paradigm.prophet.rest.client.SyncHttpOperator;

import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class ApmCrashTest
{

    public static void main( String[] args ) throws Exception
    {
        HttpOperator httpOperator = new SyncHttpOperator(10, 10);

        List<Integer> ids = Arrays.asList(21,172,173,187,217,218,219,671,675,676,677,678,679,688,689,695,697,698,700
        ,701,702,704,705,710,712,720,723,758,781,782,783,794);

        for (Integer id : ids) {
            new Thread(

                () -> {
                    try {
                        getInstance(httpOperator, id);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

            ).start();
        }
    }


    private static byte[] getInstance(HttpOperator httpOperator, Integer id) throws Exception {
        long postStart = System.currentTimeMillis();
        byte[] bytes = HttpExecution.get("https://172.27.128.191/app-manager/v1/query/instances")
                .header("User-Token", "dafb8896-d2a8-4ee1-91e1-2c040e7d4abb")
                .param("instanceIdsSepByComma", id.toString())
                .executeForRaw(httpOperator);
        long postEnd = System.currentTimeMillis();
        System.out.println("查询intances ID: " + id + "请求 cost time: " + (postEnd - postStart) + "ms");
        return bytes;
    }

}
