import java.lang.IndexOutOfBoundsException

fun<T> printArrayList(list: MyArrayList<T>) {
    for (i in 0 until list.size) {
        print(list[i].toString() + " ")
    }
    println()
}

fun main() {
    var list = MyArrayList<Int>(3)
    for (i in 0..10) {
        list.add(i)
    }
    printArrayList(list)
    list.delete(5)
    list.delete(6)
    printArrayList(list)
    list.insert(2, 99)
    list.insert(8, 44)
    printArrayList(list)
    try {
        list.insert(99, 9)
    } catch (e: IndexOutOfBoundsException) {
        println("Successfully caught exception!")
    }
}