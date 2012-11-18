/**
 * Copyright Evelus, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Hadyn Richard (sini@evel.us), July 2012
 */

package org.nova;

import java.util.concurrent.ExecutorService;

/**
 * Evelus Development
 * Created by Hadyn Richard
 */
public abstract class Service {

	protected ExecutorService executor;
	protected Dispatcher dispatcher;
	protected boolean isRunning;
	protected boolean hasStopped;
	
	public Service(ExecutorService executor, Dispatcher dispatcher) {
		this.executor = executor;
		this.dispatcher = dispatcher;
		this.isRunning = false;
		this.hasStopped = true;
	}
	
	public void start() {
		if (dispatcher.hasMetRequirements()) {
			while(isRunning) {
				dispatcher.dispatchEvents(executor);
			}
		}
		hasStopped = true;
	}
	
	public void stop() {
		isRunning = false;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public boolean hasStopped() {
		return hasStopped;
	}
}
