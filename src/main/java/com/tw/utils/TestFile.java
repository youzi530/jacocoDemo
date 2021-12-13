package com.tw.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TestFile {


    //把文件内容找到并修改
    private static String readFileContent(String filePath, HashMap<String, String> hashMap) {
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,
                    StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (line.contains(key)) {
                        line = line.replace(key, value);
                    }
                }
                result.append(line);
                result.append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    //写回文件
    //FileWriter 不允许你指定编码，使用 OutputStreamWriter 包装一个 FileOutputStream
    //并设置编码
    private static void writeFile(String filePath, String content) {
        BufferedWriter bufferedWriter = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,
                    StandardCharsets.UTF_8);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "test.json";
        File file = new File(filePath);
        System.out.println("path===============" + file.getPath());
        System.out.println("AbsolutePath=======" + file.getAbsolutePath());
        HashMap<String, String> hashMap = new HashMap<>();
        //key   ： 待修改文字
        //value ： 希望的文字
        hashMap.put("语文", "十一国庆节");
        hashMap.put("请背诵全文", "今天放假不上课");
        String totalContent = readFileContent(filePath, hashMap);
        writeFile(filePath, totalContent);


        String gradlePath = "build.gradle";
        File gradleFile = new File(gradlePath);
    }

}


