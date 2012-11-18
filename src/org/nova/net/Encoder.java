package org.nova.net;

public interface Encoder<T, V> {

	public V encode(T t);
}
