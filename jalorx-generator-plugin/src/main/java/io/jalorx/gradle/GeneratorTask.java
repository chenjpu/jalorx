package io.jalorx.gradle;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.Task;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.JavaTypeResolverConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import io.jalorx.gradle.dsl.GeneratorExtension;

public class GeneratorTask implements Action<Task> {

  private final GeneratorExtension ext;

  public GeneratorTask(GeneratorExtension ext) {
    this.ext = ext;
  }

  public void execute(Task task) {
    
    
    System.out.println("Generating............");

    List<String> warnings = new ArrayList<>();

    try {
      // ConfigurationParser cp = new ConfigurationParser(warnings);

      Configuration config = new Configuration();

      Context context = new Context(ModelType.CONDITIONAL);
      context.setId("jalorx");
      context.setTargetRuntime("MyBatis3");
      context.addProperty("basePackage", ext.getBasePackage());
      context.addProperty("code", ext.getCode());
      
      JDBCConnectionConfiguration jdbc = new JDBCConnectionConfiguration();
      
      jdbc.setConnectionURL(ext.getJdbcConnection().getConnectionURL());
      jdbc.setDriverClass(ext.getJdbcConnection().getDriverClass());
      jdbc.setUserId(ext.getJdbcConnection().getUserId());
      jdbc.setPassword(ext.getJdbcConnection().getPassword());
      context.setJdbcConnectionConfiguration(jdbc);

      {
        JavaTypeResolverConfiguration type = new JavaTypeResolverConfiguration();
        type.addProperty("forceBigDecimals", String.valueOf(ext.getContext().isForceBigDecimals()));
        context.setJavaTypeResolverConfiguration(type);
      }
      
      
      {
        JavaModelGeneratorConfiguration type = new JavaModelGeneratorConfiguration();
        type.setTargetPackage(ext.getBasePackage() + ".model");
        type.setTargetProject("src/main/java");
        context.setJavaModelGeneratorConfiguration(type);
      }
      
      {
        SqlMapGeneratorConfiguration type = new SqlMapGeneratorConfiguration();
        type.setTargetPackage(ext.getBasePackage() + ".dao");
        type.setTargetProject("src/main/resources");
        context.setSqlMapGeneratorConfiguration(type);
      }
      
      {
        JavaClientGeneratorConfiguration type = new JavaClientGeneratorConfiguration();
        type.setTargetPackage(ext.getBasePackage() + ".dao");
        type.setTargetProject("src/main/java");
        type.setConfigurationType("XMLMAPPER");
        type.addProperty("rootInterface", ext.getContext().getRootInterface());
        context.setJavaClientGeneratorConfiguration(type);
        
      }

      {
        String[] tableNames = ext.getTables().split(",");
        for (String tableName : tableNames) {
          TableConfiguration tc = new TableConfiguration(context);
          tc.addProperty("rootClass", ext.getContext().getRootClass());
          tc.setTableName(tableName);

          // String runtimeTableName = tableName.toUpperCase();

          String domainObjectName = tableName.toUpperCase();

          if (domainObjectName.startsWith("TPL_")) {
            domainObjectName = domainObjectName.substring(4);
          }
          if (domainObjectName.endsWith("_T")) {
            domainObjectName = domainObjectName.substring(0, domainObjectName.length() - 2);
          }

          domainObjectName = JavaBeansUtil.getCamelCaseString(domainObjectName, true);
          tc.setDomainObjectName(domainObjectName);
          tc.setMapperName(domainObjectName + "Dao");
          tc.setAlias("T");
          
          GeneratedKey generatedKey = new GeneratedKey("ID", ext.getJdbcConnection().getType(), false, "");
          
          tc.setGeneratedKey(generatedKey);
          
          
          context.addTableConfiguration(tc);
        }
      }
      
      config.addContext(context);

      DefaultShellCallback callback = new DefaultShellCallback(ext.isOverwrite());
      MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
      ProgressCallback progressCallback = ext.isVerbose() ? new VerboseProgressCallback() : null;
      myBatisGenerator.generate(progressCallback);

    } catch (SQLException | IOException e) {
      e.printStackTrace(System.out);
      return;
    } catch (InvalidConfigurationException e) {
      writeLine(getString("Progress.16")); //$NON-NLS-1$
      for (String error : e.getErrors()) {
        writeLine(error);
      }
      return;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    for (String warning : warnings) {
      writeLine(warning);
    }

    if (warnings.isEmpty()) {
      writeLine(getString("Progress.4")); //$NON-NLS-1$
    } else {
      writeLine();
      writeLine(getString("Progress.5")); //$NON-NLS-1$
    }
  }


  void usage() {
    System.out.println(getString("Usage"));
  }

  void writeLine(String message) {
    System.out.println(message);
  }

  void writeLine() {
    System.out.println();
  }

}
