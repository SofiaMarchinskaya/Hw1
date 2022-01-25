package com.sofiamarchinskaya.hw1.states

class DownloadState(val status: DownloadStates, val msg: String? = null)

enum class DownloadStates {
    SUCCESS,
    FAILED
}