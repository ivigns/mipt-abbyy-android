package main

import kotlin.reflect.KProperty

class StorageDelegate<T>(private val key: String, value: T) {

    private val storage: IStorage = Storage()

    init {
        put(value)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return storage.get(key) as T
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        put(value)
    }

    private fun put(value: T) {
        when (value) {
            is String -> storage.put(key, value)
            is Int -> storage.put(key, value)
            is Long -> storage.put(key, value)
            is Char -> storage.put(key, value)
            is Double -> storage.put(key, value)
            else -> throw IllegalArgumentException()
        }
    }

}
