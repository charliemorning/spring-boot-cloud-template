package org.charlie.example.common.utils.io.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public InputStream getInputStream(String filePath) throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        if (classLoader == null) {
            return null;
        }
        return classLoader.getResourceAsStream(filePath);
    }

    public static List<String> readFromInputStream(InputStream inputStream, String encoding) {
        List<String> fileContent = new ArrayList<String>();
        BufferedReader br = null;
        try {
            if (inputStream == null) {
                return new ArrayList<String>();
            }
            br = new BufferedReader(new InputStreamReader(inputStream, encoding));

            String line_record = br.readLine();

            while (line_record != null) {
                fileContent.add(line_record);
                line_record = br.readLine();
            }
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {


        } finally {
            if (br != null) {
                try {
                    br.close();
                    inputStream.close();
                } catch (Exception e2) {

                }
            }
        }
        return fileContent;
    }

    public static List<String> loadFileFromStream(InputStream inputStream) throws IOException {
        return readFromInputStream(inputStream, "utf-8");
    }
}