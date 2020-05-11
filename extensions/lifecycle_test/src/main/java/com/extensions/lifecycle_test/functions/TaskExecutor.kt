package com.extensions.lifecycle_test.functions

import androidx.arch.core.executor.TaskExecutor
import java.util.concurrent.Executor

/**
 * Creates [TaskExecutor] implementation, which runs all operations on passed [executor].
 * Current [Thread] is used by default.
 */
fun createTaskExecutor(executor: Executor = Executor(Runnable::run)): TaskExecutor {
    return object : TaskExecutor() {

        override fun executeOnDiskIO(runnable: Runnable) {
            executor.execute(runnable)
        }

        override fun executeOnMainThread(runnable: Runnable) {
            executor.execute(runnable)
        }

        override fun isMainThread(): Boolean {
            return true
        }

        override fun postToMainThread(runnable: Runnable) {
            executor.execute(runnable)
        }
    }
}