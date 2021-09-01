package io.jalorx.export.word;

/**
 * 导出定义
 * 
 * @author chenb
 *
 * @param <T>
 */
public class WDefinition<T> {

  private final String fileName;
  private final T data;

  private WDefinition(Builder<T> builder) {
    fileName = builder.fileName;
    data = builder.data;
  }

  public String getFileName() {
    return fileName;
  }

  public T getData() {
    return data;
  }

  public static class Builder<T> {

    private String fileName;
    private T data;

    public Builder<T> fileName(String fileName) {
      this.fileName = fileName;
      return this;
    }

    public Builder<T> data(T data) {
      this.data = data;
      return this;
    }

    public WDefinition<T> build() {
      return new WDefinition<>(this);
    }
  }
}
