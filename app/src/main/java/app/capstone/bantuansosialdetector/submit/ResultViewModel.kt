package app.capstone.bantuansosialdetector.submit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.capstone.bantuansosialdetector.core.domain.model.Predict
import app.capstone.bantuansosialdetector.core.domain.usecase.BanSosUseCase

class ResultViewModel(private val banSosUseCase: BanSosUseCase) : ViewModel() {
    fun postPredict(predict: Predict) = banSosUseCase.postPredict(predict).asLiveData()

    fun getRecipientByNik(nik: String) = banSosUseCase.getRecipientByNik(nik).asLiveData()
}