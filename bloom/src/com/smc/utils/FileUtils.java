package com.smc.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.google.common.collect.Lists;

public class FileUtils {

  public static List<String> readFileIntoLines(String fileName)
      throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(fileName));
    List<String> lines = Lists.newArrayList();

    String eachLine;
    while ((eachLine = br.readLine()) != null) {
      lines.add(eachLine);
    }

    br.close();
    return lines;
  }

  public static void writeMapToFile(Map<String, Set<String>> map,
      String filePath) throws IOException {

    JSONObject jsonObject = new JSONObject(map);
    writeContentToFile(jsonObject.toString(), filePath);
  }

  public static void writeContentToFile(String content, String filePath)
      throws FileNotFoundException, UnsupportedEncodingException, IOException {
    File file = new File(filePath);
    FileOutputStream fos = new FileOutputStream(file);

    OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
    writer.append(content);

    writer.flush();
    writer.close();

    fos.flush();
    fos.close();
  }
}
