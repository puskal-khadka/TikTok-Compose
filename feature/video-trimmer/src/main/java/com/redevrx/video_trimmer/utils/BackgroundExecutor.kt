package com.redevrx.video_trimmer.utils

import android.util.Log
import java.util.ArrayList
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.max

object BackgroundExecutor {

    private const val TAG = "BackgroundExecutor"

    private val DEFAULT_EXECUTOR: Executor = Executors.newScheduledThreadPool(2 * Runtime.getRuntime().availableProcessors())
    private val executor = DEFAULT_EXECUTOR
    private val TASKS = ArrayList<Task>()
    private val CURRENT_SERIAL = ThreadLocal<String>()

    /**
     * Execute a runnable after the given delay.
     *
     * @param runnable the task to execute
     * @param delay    the time from now to delay execution, in milliseconds
     *
     *
     * if `delay` is strictly positive and the current
     * executor does not support scheduling (if
     * Executor has been called with such an
     * executor)
     * @return Future associated to the running task
     * @throws IllegalArgumentException if the current executor set by Executor
     * does not support scheduling
     */
    private fun directExecute(runnable: Runnable, delay: Long): Future<*>? {
        var future: Future<*>? = null
        if (delay > 0) {
            /* no serial, but a delay: schedule the task */
            if (executor !is ScheduledExecutorService) {
                throw IllegalArgumentException("The executor set does not support scheduling")
            }
            future = executor.schedule(runnable, delay, TimeUnit.MILLISECONDS)
        } else {
            if (executor is ExecutorService) {
                future = executor.submit(runnable)
            } else {
                /* non-cancellable task */
                executor.execute(runnable)
            }
        }
        return future
    }

    /**
     * Execute a task after (at least) its delay **and** after all
     * tasks added with the same non-null `serial` (if any) have
     * completed execution.
     *
     * @param task the task to execute
     * @throws IllegalArgumentException if `task.delay` is strictly positive and the
     * current executor does not support scheduling (if
     * Executor has been called with such an
     * executor)
     */
    @Synchronized
    fun execute(task: Task) {
        var future: Future<*>? = null
        if (task.serial == null || !hasSerialRunning(task.serial)) {
            task.executionAsked = true
            future = directExecute(task, task.remainingDelay)
        }
        if ((task.id != null || task.serial != null) && !task.managed.get()) {
            /* keep task */
            task.future = future
            TASKS.add(task)
        }
    }

    /**
     * Indicates whether a task with the specified `serial` has been
     * submitted to the executor.
     *
     * @param serial the serial queue
     * @return `true` if such a task has been submitted,
     * `false` otherwise
     */
    private fun hasSerialRunning(serial: String?): Boolean {
        for (task in TASKS) {
            if (task.executionAsked && serial == task.serial) {
                return true
            }
        }
        return false
    }

    /**
     * Retrieve and remove the first task having the specified
     * `serial` (if any).
     *
     * @param serial the serial queue
     * @return task if found, `null` otherwise
     */
    private fun take(serial: String): Task? {
        val len = TASKS.size
        for (i in 0 until len) {
            if (serial == TASKS[i].serial) {
                return TASKS.removeAt(i)
            }
        }
        return null
    }

    /**
     * Cancel all tasks having the specified `id`.
     *
     * @param id                    the cancellation identifier
     * @param mayInterruptIfRunning `true` if the thread executing this task should be
     * interrupted; otherwise, in-progress tasks are allowed to
     * complete
     */
    @Synchronized
    fun cancelAll(id: String, mayInterruptIfRunning: Boolean) {
        for (i in TASKS.indices.reversed()) {
            val task = TASKS[i]
            if (id == task.id) {
                if (task.future != null) {
                    task.future?.cancel(mayInterruptIfRunning)
                    if (!task.managed.getAndSet(true)) {
                        /*
						 * the task has been submitted to the executor, but its
						 * execution has not started yet, so that its run()
						 * method will never call postExecute()
						 */
                        task.postExecute()
                    }
                } else if (task.executionAsked) {
                    Log.w(TAG, "A task with id " + task.id + " cannot be cancelled (the executor set does not support it)")
                } else {
                    /* this task has not been submitted to the executor */
                    TASKS.removeAt(i)
                }
            }
        }
    }

    abstract class Task(id: String, delay: Long, serial: String) : Runnable {

        var id: String? = null
        var remainingDelay: Long = 0
        private var targetTimeMillis: Long = 0 /* since epoch */
        var serial: String? = null
        var executionAsked: Boolean = false
        var future: Future<*>? = null

        /*
         * A task can be cancelled after it has been submitted to the executor
         * but before its run() method is called. In that case, run() will never
         * be called, hence neither will postExecute(): the tasks with the same
         * serial identifier (if any) will never be submitted.
         *
         * Therefore, cancelAll() *must* call postExecute() if run() is not
         * started.
         *
         * This flag guarantees that either cancelAll() or run() manages this
         * task post execution, but not both.
         */
        val managed = AtomicBoolean()

        init {
            if ("" != id) {
                this.id = id
            }
            if (delay > 0) {
                remainingDelay = delay
                targetTimeMillis = System.currentTimeMillis() + delay
            }
            if ("" != serial) {
                this.serial = serial
            }
        }

        override fun run() {
            if (managed.getAndSet(true)) {
                /* cancelled and postExecute() already called */
                return
            }

            try {
                CURRENT_SERIAL.set(serial)
                execute()
            } finally {
                /* handle next tasks */
                postExecute()
            }
        }

        abstract fun execute()

        fun postExecute() {
            if (id == null && serial == null) {
                /* nothing to do */
                return
            }
            CURRENT_SERIAL.set(null)
            synchronized(BackgroundExecutor::class.java) {
                /* execution complete */
                TASKS.remove(this)

                if (serial != null) {
                    val next = take(serial!!)
                    if (next != null) {
                        if (next.remainingDelay != 0L) {
                            /* the delay may not have elapsed yet */
                            next.remainingDelay = max(0L, targetTimeMillis - System.currentTimeMillis())
                        }
                        /* a task having the same serial was queued, execute it */
                        execute(next)
                    }
                }
            }
        }
    }
}