package app.capstone.bantuansosialdetector.submit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.capstone.bantuansosialdetector.core.domain.model.Predict
import app.capstone.bantuansosialdetector.core.domain.usecase.BanSosUseCase

class ResultViewModel(private val banSosUseCase: BanSosUseCase) : ViewModel() {
    private val _nikGetRecipient = MutableLiveData<String?>()
    val nikGetRecipient: LiveData<String?> get() = _nikGetRecipient

    fun inputNikGetRecipient(nik: String?) {
        _nikGetRecipient.value = nik
    }

    fun getRecipientByNik(nik: String?) = banSosUseCase.getRecipientByNik(nik).asLiveData()

    fun postPredict(predict: Predict) = banSosUseCase.postPredict(predict).asLiveData()

    fun updateRecipientById(id: String?, status: Int) =
        banSosUseCase.updateRecipient(id, status).asLiveData()

    fun getTracking(nikTracking: String?) = banSosUseCase.getTracking(nikTracking).asLiveData()
}