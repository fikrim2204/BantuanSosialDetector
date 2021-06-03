package app.capstone.bantuansosialdetector.core.di

import app.capstone.bantuansosialdetector.core.data.source.BanSosRepository
import app.capstone.bantuansosialdetector.core.data.source.remote.RemoteDataSource
import app.capstone.bantuansosialdetector.core.data.source.remote.network.ApiService
import app.capstone.bantuansosialdetector.core.data.source.remote.network.ApiService2
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
import app.capstone.bantuansosialdetector.core.domain.repository.IBanSosRepository
import app.capstone.bantuansosialdetector.core.domain.usecase.BanSosInteractor
import app.capstone.bantuansosialdetector.core.domain.usecase.BanSosUseCase
import app.capstone.bantuansosialdetector.core.utils.Prefs
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val repositoryModule = module {
    single { RemoteDataSource(get(),get()) }
    single<IBanSosRepository> { BanSosRepository(get()) }
}

val useCaseModule = module {
    factory<BanSosUseCase> { BanSosInteractor(get()) }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(5, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS).build()
    }
    single {
        val retrofit = Retrofit.Builder().baseUrl("http://35.202.170.106").addConverterFactory(
            GsonConverterFactory.create()
        ).client(get()).build()
        retrofit.create(ApiService::class.java)
    }

    single {
        val retrofit = Retrofit.Builder().baseUrl("http://35.202.170.106:8501").addConverterFactory(
            GsonConverterFactory.create()
        ).client(get()).build()
        retrofit.create(ApiService2::class.java)
    }
}

val preferenceModule = module {
    single { Prefs(get()) }
}

val modelModule = module {
    single { Recipient() }
}