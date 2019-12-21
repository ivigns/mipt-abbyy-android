package main

fun main() {
    var id: Int by StorageDelegate("id", 20)
    id = id + 1
    println(id)
}