package com.sofiamarchinskaya.hw1.states

class DownloadState(val status: DownloadStates)
enum class DownloadStates {
    RUNNING,
    SUCCESS,
    FAILED
}