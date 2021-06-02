package app.capstone.bantuansosialdetector.di

import app.capstone.bantuansosialdetector.submit.ResultViewModel
import app.capstone.bantuansosialdetector.submit.SubmitViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

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

val viewModelModule = module {
    viewModel { SubmitViewModel(get()) }
    viewModel { ResultViewModel(get()) }
}