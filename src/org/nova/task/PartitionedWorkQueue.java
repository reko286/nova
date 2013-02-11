/**
 * Copyright (c) 2012, Hadyn Richard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal 
 * in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
 * THE SOFTWARE.
 */

package org.nova.task;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;

/**
 * Created by Hadyn Richard
 */
public final class PartitionedWorkQueue {

    /**
     * The current work queue.
     */
    private WorkQueue currentWorkQueue;

    /**
     * The work queues to execute
     */
    private Queue<WorkQueue> workQueues;

    /**
     * The amount of tasks to queue per work queue.
     */
    private int tasksPerQueue;

    /**
     * The counter of how many tasks are left to insert into the queue.
     */
    private int counter;

    /**
     * Constructs a new {@link PartitionedWorkQueue};
     */
    public PartitionedWorkQueue(int tasksPerQueue) {
        currentWorkQueue = new WorkQueue();
        workQueues = new LinkedList<WorkQueue>();
        counter = tasksPerQueue;

        this.tasksPerQueue = tasksPerQueue;
    }

    /**
     * Adds a task to this work queue.
     *
     * @param task  The task to add.
     */
    public void add(Task task) {

        /* Save the current work queue if it contains the maximum amount of tasks per queue */
        if(counter >= tasksPerQueue) {
            workQueues.add(currentWorkQueue);
            currentWorkQueue = new WorkQueue();
            counter = tasksPerQueue;
        }

        currentWorkQueue.add(task);
    }

    /**
     * Executes all the tasks in each work queue on the executor.
     *
     * @param executor  The executor to use to execute tasks.
     */
    public void execute(Executor executor) {
         for(WorkQueue queue : workQueues) {
             executor.execute(new ExecuteWorkQueueTask(queue).wrap());
         }
    }
}
