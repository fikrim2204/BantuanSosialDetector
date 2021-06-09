package app.capstone.bansosadmin

import android.app.Application
import app.capstone.bansosadmin.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    loginModule, networkModule, repositoryModule,
                    useCaseModule, viewModelModule
                )
            )
        }
    }
}