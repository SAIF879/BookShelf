package com.example.bookshelf

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ReaderApplication : Application() {
}

//application is the higher class that governes the whole application