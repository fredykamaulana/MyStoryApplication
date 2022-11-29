package com.miniapp.mystoryapplication.core.abstraction

abstract class BaseMapper<in I, out O> {
    abstract fun map(data: I): O
}