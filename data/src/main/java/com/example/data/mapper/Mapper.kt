package com.example.data.mapper

interface Mapper<in A, out B> {
    fun map(from: A): B
}