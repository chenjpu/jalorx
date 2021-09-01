package io.jalorx.export;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 
 * 导出数据源接口
 * 
 * @author chenb
 *
 * @param <E>
 */
public interface DataSource<E> {
  /**
   * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true}
   * if {@link #next} would return an element rather than throwing an exception.)
   *
   * @return {@code true} if the iteration has more elements
   */
  boolean hasNext();

  /**
   * Returns the next element in the iteration.
   *
   * @return the next element in the iteration
   */
  E next();


  /**
   * Performs the given action for each remaining element until all elements have been processed or
   * the action throws an exception. Actions are performed in the order of iteration, if that order
   * is specified. Exceptions thrown by the action are relayed to the caller.
   *
   * @param action The action to be performed for each element
   * @throws NullPointerException if the specified action is null
   */
  default void forEachRemaining(Consumer<? super E> action) {
    Objects.requireNonNull(action);
    while (hasNext())
      action.accept(next());
  }


  /**
   * 集合类型数据源封装
   * @param <T>
   * @param c
   * @return
   */
  static <T> DataSource<T> from(Collection<T> c) {
    return new DefaultIterator<>(c);
  }


  /**
   * jdk集合类型数据源封装
   * @author chenb
   *
   * @param <T>
   */
  static class DefaultIterator<T> implements DataSource<T> {

    private final Iterator<T> iterator;

    DefaultIterator(Collection<T> data) {
      this.iterator = data.iterator();
    }

    @Override
    public boolean hasNext() {
      return iterator.hasNext();
    }

    @Override
    public T next() {
      return iterator.next();
    }
  }
}
