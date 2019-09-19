package com._4paradigm.test.predictor;

import com._4paradigm.prophet.rest.client.HttpExecution;
import com._4paradigm.prophet.rest.client.HttpOperator;
import com._4paradigm.prophet.rest.client.SyncHttpOperator;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.apache.http.entity.ContentType;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class RestPredictor
{




    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );

        long serStart = System.currentTimeMillis();
        String bodyStr = readFileFromResourceToString("predictor.json");
        long serEnd = System.currentTimeMillis();
        System.out.println("Get Body cost time: " + (serEnd - serStart) + "ms");


        HttpOperator httpOperator = new SyncHttpOperator(10, 10);


        long predictStart = System.currentTimeMillis();
        for(int i=0; i<999; i++) {
            predictor(httpOperator, bodyStr);
        }
        byte[] bytes = predictor(httpOperator, bodyStr);
        long predictEnd = System.currentTimeMillis();
        System.out.println("Average 1000 cost time: " + (predictEnd - predictStart)/1000 + "ms");


        long printStart = System.currentTimeMillis();
        System.out.println(new String(bytes));
        long printEnd = System.currentTimeMillis();
        System.out.println("Print cost time: " + (printEnd -  printStart) + "ms");
    }


    private static byte[] predictor(HttpOperator httpOperator, String bodyStr) throws Exception {
        long postStart = System.currentTimeMillis();
        byte[] bytes = HttpExecution.post("http://172.16.32.7:31195/api/predict")
                .textBody(bodyStr, ContentType.APPLICATION_JSON).executeForRaw(httpOperator);
        long postEnd = System.currentTimeMillis();
        System.out.println("Rest predictor cost time: " + (postEnd - postStart) + "ms");
        return bytes;
    }


    public static String readFileFromResourceToString(String path) throws IOException {
        return Resources.toString(Resources.getResource(path), Charsets.UTF_8);
    }
}
