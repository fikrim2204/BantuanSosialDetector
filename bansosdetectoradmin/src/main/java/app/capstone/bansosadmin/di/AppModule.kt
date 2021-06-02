package app.capstone.bansosadmin.di

import app.capstone.bansosadmin.BuildConfig
import app.capstone.bansosadmin.data.MyRepository
import app.capstone.bansosadmin.data.source.remote.RemoteDataSource
import app.capstone.bansosadmin.data.source.remote.network.ApiInterface
import app.capstone.bansosadmin.domain.repository.IMyRepository
import app.capstone.bansosadmin.domain.usecase.MyInteractor
import app.capstone.bansosadmin.domain.usecase.MyUseCase
import app.capstone.bansosadmin.ui.fragments.home.HomeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val loginModule = module {
    single {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("16525290177-0533u5apas3q92rg1ou3cc340qq2i0q8.apps.googleusercontent.com")
            .requestEmail().build()
        GoogleSignIn.getClient(androidContext(), gso)
    }
    single {
        FirebaseAuth.getInstance()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiInterface::class.java)
    }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single<IMyRepository> { MyRepository(get()) }
}

val useCaseModule = module {
    factory<MyUseCase> { MyInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}