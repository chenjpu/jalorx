package io.jalorx.export.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.micronaut.context.annotation.Factory;

@Factory
public class ExportWordUsingFTLUtils {

  private static Logger logger = LoggerFactory.getLogger(ExportWordUsingFTLUtils.class);


  /**
   * * 读取word模板并替换变量字段,FTL方式
   * 
   * @param fileName 模板名
   * @param contentMap 要替换的内容
   * @return word的byte[]
   * @throws IOException
   */
  @SuppressWarnings("rawtypes")
  public byte[] replaceDoc(String fileName, Map contentMap) throws IOException {
    try {
      String path = System.getProperty("user.dir") + File.separatorChar + "src" + File.separatorChar
          + "main" + File.separatorChar + "resources" + File.separatorChar + "config"
          + File.separatorChar + fileName;
      path = path.substring(0, path.indexOf(fileName));
      Configuration configuration = new Configuration();
      configuration.setDefaultEncoding("UTF-8");

      configuration.setDirectoryForTemplateLoading(new File(path)); // 模板文件所在路径

      Template t = configuration.getTemplate(fileName); // 获取模板文件
      // 输出文档路径及名称
      String rootPath = System.getProperty("user.dir");

      String dirPath = rootPath + File.separatorChar + "temp" + File.separatorChar;

      File f = new File(dirPath);

      if (!f.exists()) {
        f.mkdirs();
      }

      String tempFileName = dirPath + StringTools.generateRanomNum(6) + ".doc";

      Writer out = null;
      try {
        // 输出文档路径及名称
        File outFile = new File(tempFileName);
        outFile.deleteOnExit();
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));

        t.process(contentMap, out);// 模板+数据=最终输出的word文档
//        out.close();
        byte[] b = FileUtils.getBytes(tempFileName);
        return b;
      } catch (TemplateException e) {
        logger.error("生成word文档出错" + e);
        e.printStackTrace();
      } catch (IOException ioe) {
        logger.error("生成word文档出错" + ioe);
        ioe.printStackTrace();
      } catch (Exception e1) {
        logger.error("另一个程序正在使用该word文档，请先关闭该word文档！" + e1);
        e1.printStackTrace();
      } finally {
        if (out != null) {
          try {
            out.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * * 读取word模板并替换变量字段,FTL方式
   * 
   * @param fileName 模板名
   * @param contentMap 要替换的内容
   * @return word的byte[]
   * @throws IOException
   */
  @SuppressWarnings("rawtypes")
  public byte[] replaceDoc2(File filePath, String fileName, Map contentMap) throws IOException {
    try {

      Configuration configuration = new Configuration();
      configuration.setDefaultEncoding("UTF-8");

      configuration.setDirectoryForTemplateLoading(filePath); // 模板文件所在路径

      Template t = configuration.getTemplate(fileName); // 获取模板文件
      // 输出文档路径及名称
      String rootPath = System.getProperty("user.dir");

      String dirPath = rootPath + File.separatorChar + "temp" + File.separatorChar;

      File f = new File(dirPath);

      if (!f.exists()) {
        f.mkdirs();
      }

      String tempFileName = dirPath + StringTools.generateRanomNum(6) + ".doc";

      // 输出文档路径及名称

      File outFile = new File(tempFileName);
      Writer out = null;

      try {
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
      } catch (Exception e1) {
        logger.error("另一个程序正在使用该word文档，请先关闭该word文档！" + e1);
        e1.printStackTrace();
      }

      try {
        t.process(contentMap, out);// 模板+数据=最终输出的word文档
        out.close();
        byte[] b = FileUtils.getBytes(tempFileName);
        return b;
      } catch (TemplateException e) {
        logger.error("生成word文档出错" + e);
        e.printStackTrace();
      } catch (IOException ioe) {
        logger.error("生成word文档出错" + ioe);
        ioe.printStackTrace();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @SuppressWarnings("rawtypes")
  public String replaceDoc3(File filePath, String fileName, Map contentMap) throws IOException {
    try {

      Configuration configuration = new Configuration();
      configuration.setDefaultEncoding("UTF-8");

      configuration.setDirectoryForTemplateLoading(filePath); // 模板文件所在路径

      Template t = configuration.getTemplate(fileName); // 获取模板文件
      // 输出文档路径及名称
      String rootPath = System.getProperty("user.dir");

      String dirPath = rootPath + File.separatorChar + "temp" + File.separatorChar;

      File f = new File(dirPath);

      if (!f.exists()) {
        f.mkdirs();
      }

      String tempFileName = dirPath + StringTools.generateRanomNum(6) + ".xml";

      // 输出文档路径及名称

      File outFile = new File(tempFileName);
      Writer out = null;
      try {
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
      } catch (Exception e1) {
        logger.error("另一个程序正在使用该word文档，请先关闭该word文档！" + e1);
        e1.printStackTrace();
      }

      try {
        t.process(contentMap, out);// 模板+数据=最终输出的word文档
        out.close();
        return tempFileName;
      } catch (TemplateException e) {
        logger.error("生成word文档出错" + e);
        e.printStackTrace();
      } catch (IOException ioe) {
        logger.error("生成word文档出错" + ioe);
        ioe.printStackTrace();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}

