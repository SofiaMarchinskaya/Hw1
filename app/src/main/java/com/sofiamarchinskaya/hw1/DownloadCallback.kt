package com.sofiamarchinskaya.hw1

import com.sofiamarchinskaya.hw1.models.entity.Note

interface DownloadCallback {

    fun onSuccess(list:List<Note>)

    fun onFailed(msg: String)
}