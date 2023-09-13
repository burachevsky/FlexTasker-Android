package com.github.flextasker.core.eventbus

interface AppEventHandler {
    fun handleEvent(event: AppEvent): Boolean
}