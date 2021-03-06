package utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CSVReader {

    public static double getCoverage() {
        System.out.println("start reading csv file...");
        String csvFile = "build/reports/coverage/unit/jacowcowTestReport.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        double coverage = 0;
        int index = 0;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] country = line.split(cvsSplitBy);
                System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");
                if (index > 0) {
                    coverage = Double.parseDouble(country[4]) / Double.parseDouble(country[3]);
                    break;
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return coverage;
    }

    public static void setCoverage(double coverage) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("minimum", String.valueOf(coverage));
        properties.store(new FileOutputStream("build.gradle"), "");
    }

    public static String getOriginCoverage() {
        System.out.println("start getting origin coverage...");
        String f = "build.gradle";
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String minimum = props.getProperty("minimum");
        return minimum;
    }

    //????????????
    //FileWriter ????????????????????????????????? OutputStreamWriter ???????????? FileOutputStream
    //???????????????
    private static void writeFile(String filePath, String content) {
        System.out.println("start writting file...");
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

    //??????????????????????????????
    private static String readFileContent(String filePath, HashMap<String, String> hashMap) {
        System.out.println("start reading file content...");
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

    public static void main(String[] args) throws IOException {
        double coverage = getCoverage(); //?????????????????????
        String originCoverage = getOriginCoverage(); //???????????????????????????????????????
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(originCoverage, String.valueOf(coverage));

        String gradlePath = "build.gradle";
        String totalContent = readFileContent(gradlePath, hashMap); //??????????????????????????????,??????????????????
        writeFile(gradlePath, totalContent); //??????gradle???????????????????????????????????????
    }
}
