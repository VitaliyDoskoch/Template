package com.doskoch.legacy.kotlin

/**
 * It is the [Lazy] implementation, that allows to destroy the instance.
 * If the instance is already destroyed, it will be recreated on the next call.
 */
class DestroyableLazy<T>(
    private val initialize: () -> T,
    private val onDestroyInstance: ((T?) -> Unit)? = null
) : Lazy<T> {

    @Volatile
    private var instance: T? = null
    private val lock = Any()

    override val value: T
        get() {
            instance?.let {
                return it
            }

            synchronized(lock) {
                instance.let {
                    return if (it != null) {
                        it
                    } else {
                        val newInstance = initialize()
                        instance = newInstance
                        newInstance
                    }
                }
            }
        }

    override fun isInitialized(): Boolean = instance != null

    fun destroyInstance() {
        synchronized(lock) {
            instance.let { onDestroyInstance?.invoke(it) }
            instance = null
        }
    }

    fun recreateInstance() {
        synchronized(lock) {
            instance.let { onDestroyInstance?.invoke(it) }
            instance = initialize()
        }
    }
}
