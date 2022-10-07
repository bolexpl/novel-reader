package com.example.novelreader.source

data class SourceSetting(
    val settingName: String,
    val valueType: String = String::class.java.name,
    var stringVal: String? = null,
    var intVal: Int? = null,
    var floatVal: Float? = null,
    var boolVal: Boolean? = null,
)


