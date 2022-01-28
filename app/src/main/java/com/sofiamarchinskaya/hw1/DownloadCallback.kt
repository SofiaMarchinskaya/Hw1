package com.sofiamarchinskaya.hw1

import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.states.ExceptionTypes

interface DownloadCallback {

    fun onSuccess(list: List<Note>)

    fun onFailed(msg: ExceptionTypes)

}