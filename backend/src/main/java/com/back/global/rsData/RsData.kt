package com.back.global.rsData

import com.fasterxml.jackson.annotation.JsonIgnore


class RsData<T> @JvmOverloads constructor(
    private val _resultCode: String,
    private val _msg: String,
    val data: T? = null // jackson이 직렬화 할 때 접근해야 하므로 private 제거
) {

    @get:JsonIgnore
    val statusCode: Int
        get() {
            val statusCode =
                resultCode.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
            return statusCode.toInt()
        }

    val resultCode: String
        get() = this._resultCode

    val msg: String
        get() = this._msg
}
