package io.jalorx.gradle;

import java.util.Map;

import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.util.GradleVersion;

import io.jalorx.gradle.dsl.GeneratorExtension;

public class GeneratorPlugin implements Plugin<Project> {

  // final GeneratorAction action = new GeneratorAction();

  @Override
  public void apply(Project project) {
    verifyGradleVersion();
    createExtension(project);


    GeneratorExtension ext = project.getExtensions().getByType(GeneratorExtension.class);

    Task task = project.task("generator");
    task.setDescription(
        "gradle generator -PbasePackage=io.jalorx.xxx -Pcode=xxxxx -Ptables=t1");
    task.setGroup("jalorx");


    task.doFirst(t -> {

      Map<String, ?> props = project.getProperties();

      if (project.hasProperty("basePackage")) {
        ext.setBasePackage(String.valueOf(props.get("basePackage")));
      } else {
        ext.setBasePackage(String.valueOf(props.get("group")));
      }

      if (project.hasProperty("tables")) {
        ext.setTables(String.valueOf(props.get("tables")));
      } else {
        throw new RuntimeException("Please configure tables? gradle generator -PbasePackage=io.jalorx.xxx -Ptables=t1,t2 -Pcode=90000");
      }

      if (project.hasProperty("code")) {
        ext.setCode(String.valueOf(props.get("code")));
      } else {
        throw new RuntimeException("Please configure resource code? gradle generator -PbasePackage=io.jalorx.xxx -Ptables=t1,t2 -Pcode=90000");
      }
    });

    task.doLast(t -> {
      new GeneratorTask(ext).execute(t);
    });

  }

  private void createExtension(Project project) {
    project.getExtensions().create("jalorxGenerator", GeneratorExtension.class, project);
  }

  private void verifyGradleVersion() {
    if (GradleVersion.current().compareTo(GradleVersion.version("6.0")) < 0) {
      throw new GradleException(
          "JalorX plugin requires Gradle 6.0 or later. The current version is "
              + GradleVersion.current());
    }
  }
}
