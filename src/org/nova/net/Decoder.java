package org.nova.net;

public interface Decoder<T, V> {

	public V decode(T t);
}
