package io.jalorx.export.word;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.utils.StreamUtils;
import io.jalorx.export.utils.ExportWordUsingFTLUtils;
import io.micronaut.core.beans.BeanProperty;
import io.micronaut.core.beans.BeanWrapper;
import io.micronaut.http.server.types.files.SystemFile;

public class BaseExport<T> {

  @Inject
  private ExportWordUsingFTLUtils exportWordUtils;

  @SuppressWarnings({"resource"})
  public SystemFile export(WDefinition<T> def) throws BusinessAccessException {

    String fileName = def.getFileName();
    String contracttemplatePath = fileName + ".ftl";;
    fileName = fileName + ".doc";
    byte[] bytes;
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    try {
      File tempFile = File.createTempFile(fileName, ".xlsx");
      tempFile.deleteOnExit();
      OutputStream tempout = new FileOutputStream(tempFile);
      T l = def.getData();
      Map<String, Object> param = conversionToMap(l);
      bytes = exportWordUtils.replaceDoc(contracttemplatePath, param);
      tempout.write(bytes);
      return (new SystemFile(tempFile)).attach(URLEncoder.encode(fileName, "UTF-8"));

    } catch (Exception ex) {
      throw new BusinessAccessException(null, ex);
    } finally {
      StreamUtils.safeClose(output);
    }
  }

  @SuppressWarnings({"rawtypes", "unchecked", "hiding"})
  private <T> Map<String, Object> conversionToMap(T bean) throws Exception {
    Map<String, Object> map = new HashMap<String, Object>();
    BeanWrapper<T> wrapper = BeanWrapper.getWrapper(bean);
    Collection<BeanProperty<T, Object>> descriptors = wrapper.getBeanProperties();

    // 对象中属性遍历
    for (BeanProperty<T, Object> d : descriptors) {
      String fieldName = d.getName();
      Class<Object> beanType = d.getType();
      List<Map> mapList = new ArrayList<Map>();

      if (beanType.getName() == String.class.getName()) {
        Object value = wrapper.getRequiredProperty(fieldName, String.class);
        map.put(fieldName, value);
      } else {
        List<Object> listDescriptors = wrapper.getRequiredProperty(fieldName, ArrayList.class);
        // 对象List进行遍历
        for (int i = 0; i < listDescriptors.size(); i++) {
          Map<String, Object> listMap = new HashMap<String, Object>();
          BeanWrapper<Object> listBean = BeanWrapper.getWrapper(listDescriptors.get(i));
          Collection<BeanProperty<Object, Object>> listBeandescriptors =
              listBean.getBeanProperties();
          // List中对象进行遍历
          for (BeanProperty<Object, Object> listBeandescriptor : listBeandescriptors) {
            String listBeanfieldName = listBeandescriptor.getName();
            Object value = listBean.getRequiredProperty(listBeanfieldName, String.class);
            listMap.put(listBeanfieldName, value);
          }
          mapList.add(listMap);
        }
        map.put(fieldName, mapList);
      }


    }
    return map;
  }
}
