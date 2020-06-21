package com.meow.rentalz_kotlin

import android.app.Application

import timber.log.Timber
import timber.log.Timber.DebugTree

class RentalZApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Setup Timber logging
        val debugTree = DebugTree()
        Timber.plant(debugTree)
    }
}