package com.sofiamarchinskaya.hw1.states

class DownloadState(val status: DownloadStates, val msg: ExceptionTypes? = null)

enum class DownloadStates {
    SUCCESS, FAILED, FINISH, DOWNLOAD
}