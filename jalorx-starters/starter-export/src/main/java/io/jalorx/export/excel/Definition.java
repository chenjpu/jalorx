package io.jalorx.export.excel;

import java.util.ArrayList;
import java.util.List;

import io.jalorx.boot.Pair;
import io.jalorx.export.DataSource;

/**
 * 导出定义
 * 
 * @author chenb
 *
 * @param <T>
 */
public class Definition<T> {

  private final List<Pair> column;
  private final String fileName;
  private final String tableTitle;
  private final DataSource<T> dataSource;

  private Definition(Builder<T> builder) {
    column = builder.column;
    fileName = builder.fileName;
    tableTitle = builder.tableTitle;
    dataSource = builder.dataSource;
  }

  public List<Pair> getColumn() {
    return column;
  }

  public String getFileName() {
    return fileName;
  }

  public String getTableTitle() {
    return tableTitle;
  }

  public DataSource<T> getDataSource() {
    return dataSource;
  }

  public int getColumnSize() {
    return this.column.size();
  }

  public static class Builder<T> {

    private List<Pair> column = new ArrayList<>();
    private String fileName;
    private String tableTitle;
    private DataSource<T> dataSource;

    public Builder<T> addColumn(String name, String label) {
      column.add(new Pair(name, label));
      return this;
    }

    public Builder<T> tableTitle(String tableTitle) {
      this.tableTitle = tableTitle;
      return this;
    }

    public Builder<T> fileName(String fileName) {
      this.fileName = fileName;
      return this;
    }

    public Builder<T> dataSource(DataSource<T> dataSource) {
      this.dataSource = dataSource;
      return this;
    }

    public Definition<T> build() {
      return new Definition<>(this);
    }
  }
}
