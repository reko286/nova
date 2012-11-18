package org.nova;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.nova.event.Event;

public class Reactor extends Dispatcher {

	private Selector selector;
	
	public Reactor(Selector selector) {
		this.selector = selector;
		//add required accept/read/write events
	}
	
	@Override
	public void handleEvent(Event event) {
		getHandlerChainFor(event).createNewEventHandlerChainContext(event).doAll();
	}

	@Override
	public void dispatchEvents(ExecutorService executor) {
		try {
			selector.select();
			Iterator<SelectionKey> it= selector.selectedKeys().iterator();
			while(it.hasNext()) {
				SelectionKey key = it.next();
				it.remove();
				if (key.isValid()) {
					if (key.isAcceptable()) {
						//create AcceptEvent, pass to handler
					} else if (key.isWritable()) {
						//create WriteEvent, pass to handler
					} else if (key.isReadable()) {
						//create ReadEvent, pass to handler
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
