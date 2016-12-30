package com.sunstar.vegnet.kootl.comm.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by louisgeek on 2016/12/2.
 */

public class FileTool {

    public static String getFileContent(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        if (SdcardTool.hasSDCardMounted()) {
            File file = new File(path);
            if (file.exists()) {
                FileInputStream fileInputStream = null;
                InputStreamReader inputStreamReader = null;
                BufferedReader bufReader = null;
                try {
                    fileInputStream = new FileInputStream(file);

                    // inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                    inputStreamReader = new InputStreamReader(fileInputStream);
                    bufReader = new BufferedReader(inputStreamReader);

                    String line = "";
                    while ((line = bufReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fileInputStream.close();
                        inputStreamReader.close();
                        bufReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    public static String getFileContentWithRN(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        if (SdcardTool.hasSDCardMounted()) {
            File file = new File(path);
            if (file.exists()) {
                FileInputStream fileInputStream = null;
                InputStreamReader inputStreamReader = null;
                BufferedReader bufReader = null;
                try {
                    fileInputStream = new FileInputStream(file);

                    // inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                    inputStreamReader = new InputStreamReader(fileInputStream);
                    bufReader = new BufferedReader(inputStreamReader);

                    String line = "";
                    while ((line = bufReader.readLine()) != null) {
                        stringBuilder.append(line.trim() + "\r\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fileInputStream.close();
                        inputStreamReader.close();
                        bufReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return stringBuilder.toString();
    }
}
