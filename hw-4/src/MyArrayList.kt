import java.lang.IndexOutOfBoundsException

class MyArrayList<T> {
    constructor(initialCapacity: Int) {
        buffer = arrayOfNulls<Any>(initialCapacity)
    }
    constructor() {
        buffer = arrayOfNulls<Any>(10)
    }

    fun add(elem: T?) {
        tryGrow()
        buffer[size++] = elem
    }

    fun insert(idx: Int, elem: T?) {
        checkBounds(idx)
        tryGrow()
        for (i in size downTo idx + 1) {
            buffer[i] = buffer[i - 1]
        }
        buffer[idx] = elem
        ++size
    }

    fun delete(idx: Int) {
        checkBounds(idx)
        for (i in idx + 1 until size) {
            buffer[i - 1] = buffer[i]
        }
        --size
    }

    operator fun get(idx: Int): T? {
        checkBounds(idx)
        return buffer[idx] as T?
    }

    private fun checkBounds(idx: Int) {
        if (idx < 0 || idx >= size) {
            throw IndexOutOfBoundsException()
        }
    }

    private fun tryGrow() {
        if (size == buffer.size) {
            val newCapacity = (3 * buffer.size) / 2 + 1
            buffer = buffer.copyOf(newCapacity)
        }
    }

    private var buffer = emptyArray<Any?>()
    var size = 0
        private set
}