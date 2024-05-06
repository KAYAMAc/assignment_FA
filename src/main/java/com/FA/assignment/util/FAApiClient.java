package com.FA.assignment.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import com.FA.assignment.entity.requests.FAConvertApiRequest;
import com.FA.assignment.entity.requests.FAReceiptApiRequest;
import com.FA.assignment.entity.responses.FAConvertApiResponse;
import com.FA.assignment.entity.responses.FAReceiptApiResponse;

import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.entity.mime.content.FileBody;





public class FAApiClient {

    private Map<String, String> prop;
    private static final int DEFAULT_TIMEOUT = 130000;
    private CloseableHttpClient apiClient;
    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYXN0YWNjb3VudGluZy5hcGkudG9rZW4iLCJhdWQiOiJmYXN0YWNjb3VudGluZy5hcGkudG9rZW4iLCJqdGkiOiJhOTliYWQ1MzI5ODQ5MTBjNmU1Mjg0ZmViMzZmYjVlYmU1ZTE4YWQ1NTIyZGQ1NmU3ZGRlNzhhMTQ0MjE4OWE1IiwiaWF0IjoxNjgwMDAwNDMyLCJpZGVudGlmaWVyIjoyMzk4fQ.tqdaT0b2s_unyH5-cyIpmvLVXTRgq0ileppb5jT-fGQ";

    @Autowired
    private Environment environment;


    public FAApiClient(){

        Map<String, String> properties = new HashMap<>();

        if (environment instanceof ConfigurableEnvironment) {
            for (PropertySource<?> propertySource : ((ConfigurableEnvironment) environment).getPropertySources()) {
                if (propertySource instanceof EnumerablePropertySource) {
                    for (String key : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
                        if (key.startsWith("client")) {
                            properties.put(key, (String) propertySource.getProperty(key));
                        }
                    }
                }
            }
        }

        this.prop = properties;

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(DEFAULT_TIMEOUT) // Connection timeout in milliseconds
                .setSocketTimeout(DEFAULT_TIMEOUT) // Socket timeout in milliseconds
                .build();

        this.apiClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
    }

    @Bean
    public FAConvertApiResponse callConvertApi(FAConvertApiRequest parameters) throws Exception{
        System.out.println("it's here");
        String url = this.prop.get("convertUrl");

        HttpPost request = new HttpPost(url);
    
        // Create multipart entity with token and file
        HttpEntity multipartEntity = MultipartEntityBuilder.create()
                .addPart("token", new StringBody(token, ContentType.TEXT_PLAIN))
                .addPart("file", new FileBody(parameters.uploadedPdf, ContentType.APPLICATION_OCTET_STREAM, parameters.uploadedPdf.getName()))
                .build();
        
        request.setEntity(multipartEntity);

        FAConvertApiResponse convertResponse = new FAConvertApiResponse();
        // Execute the request and process the response
        try {
            CloseableHttpResponse response = this.apiClient.execute(request);
            System.out.println(response);
        }catch(Exception e){

        }
        return convertResponse;
        
    }

    @Bean
    public FAReceiptApiResponse callReceiptApi(FAReceiptApiRequest parameters) throws Exception{
        

        String url = this.prop.get("receiptUrl");

        HttpPost request = new HttpPost(url);
    
        // Create multipart entity with token and file
        HttpEntity multipartEntity = MultipartEntityBuilder.create()
                .addPart("token", new StringBody(token, ContentType.TEXT_PLAIN))
                .addPart("file", new FileBody(parameters.uploadedImage, ContentType.APPLICATION_OCTET_STREAM, parameters.uploadedImage.getName()))
                .build();
        
        request.setEntity(multipartEntity);

        FAReceiptApiResponse receiptResponse = new FAReceiptApiResponse();

        // Execute the request and process the response
        try (CloseableHttpResponse response = this.apiClient.execute(request)) {
        }catch(Exception e){
        
        }
        return receiptResponse;

        
    }


}
