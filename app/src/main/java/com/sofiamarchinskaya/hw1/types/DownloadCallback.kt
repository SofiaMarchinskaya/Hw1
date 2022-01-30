package com.sofiamarchinskaya.hw1.types

import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.types.ExceptionTypes

interface DownloadCallback {

    fun onSuccess(list: List<Note>)

    fun onFailed(msg: ExceptionTypes)

}