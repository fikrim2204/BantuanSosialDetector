package app.capstone.bantuansosialdetector

import android.app.Application
import app.capstone.bantuansosialdetector.core.di.networkModule
import app.capstone.bantuansosialdetector.core.di.preferenceModule
import app.capstone.bantuansosialdetector.core.di.repositoryModule
import app.capstone.bantuansosialdetector.core.di.useCaseModule
import app.capstone.bantuansosialdetector.di.loginModule
import app.capstone.bantuansosialdetector.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@Suppress("UnUsed")
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(listOf(loginModule, viewModelModule, repositoryModule, useCaseModule, networkModule, preferenceModule))
        }
    }
}