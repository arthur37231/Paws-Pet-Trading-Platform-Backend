package usyd.elec5619.group42.backend.controller;


import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;

@RestController
@RequestMapping(path = "/weather")

public class APIController {

    @GetMapping("/Sydney")
    public JSONObject getSydneyWeather() {
        return httpRequest("https://devapi.qweather.com/v7/weather/now?key=d185914c2fb445bdae0d6fbc7e695305&location=151.20,-33.87&lang=en","GET",null);
    }

    @GetMapping("/Melbourne")
    public JSONObject getMelbourneWeather() {
        return httpRequest("https://devapi.qweather.com/v7/weather/now?key=d185914c2fb445bdae0d6fbc7e695305&location=144.96,-37.82&lang=en","GET",null);
    }
    @GetMapping("/Canberra")
    public JSONObject getCanberraWeather() {
        return httpRequest("https://devapi.qweather.com/v7/weather/now?key=d185914c2fb445bdae0d6fbc7e695305&location=149.13,-35.29&lang=en","GET",null);
    }


    public static net.sf.json.JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        net.sf.json.JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(outputStr.getBytes());
                outputStream.close();
            }

            inputStream = httpUrlConn.getInputStream();
            GZIPInputStream gZipS = new GZIPInputStream(inputStream);
            InputStreamReader inputStreamReader = new InputStreamReader(gZipS);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
            System.out.println(jsonObject.get("code"));
        } catch (ConnectException ce) {
            ce.printStackTrace();
            System.out.println("server connection timed out");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("http request error:{}");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
}