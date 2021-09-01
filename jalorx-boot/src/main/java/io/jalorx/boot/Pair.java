/**
 * 
 */
package io.jalorx.boot;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * @author chenb
 *
 */
public class Pair implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6687096517445965626L;

	private String key;

	private String value;

	public Pair() {}

	public Pair(String key, String value) {
		this.key   = key;
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime  = 31;
		int       result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	public static class Q extends Pair {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1989046708103679564L;

		public Q(String key, String value) {
			super(key, value);
		}

		public static Q valueOf(String query) {
			int index = StringUtils.indexOf(query, '=');
			if (index < 0) {
				return new Q(query, null);
			}
			return new Q(StringUtils.substring(query, 0, index), StringUtils.substring(query, index + 1));
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		return StringUtils.equals(key, other.key) && StringUtils.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "Pair[" + key + ":" + value + "]";
	}
}
