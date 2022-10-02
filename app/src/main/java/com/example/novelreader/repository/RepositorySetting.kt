package com.example.novelreader.repository

data class RepositorySetting(
    val settingName: String,
    val valueType: String = String::class.java.name,
    var stringVal: String? = null,
    var intVal: Int? = null,
    var floatVal: Float? = null,
    var boolVal: Boolean? = null,
)


