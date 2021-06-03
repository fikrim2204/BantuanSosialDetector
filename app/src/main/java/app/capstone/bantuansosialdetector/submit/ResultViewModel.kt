package app.capstone.bantuansosialdetector.submit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.capstone.bantuansosialdetector.core.domain.model.Predict
import app.capstone.bantuansosialdetector.core.domain.usecase.BanSosUseCase

class ResultViewModel(private val banSosUseCase: BanSosUseCase) : ViewModel() {
    private var predict: Predict? = null
    private var nik: String? = null
    private var nikTracking: String? = null
    private var id: String? = null
    private var status: Int? = null

    fun inputPostPredict(predict: Predict?) {
        this.predict = predict
    }

    fun inputNikGetRecipient(nik: String?) {
        this.nik = nik
    }

    fun updateRecipient(id: String?, status: Int?) {
        this.id = id
        this.status = status
    }

    fun inputGetTracking(nik: String?) {
        this.nikTracking = nik
    }

    fun postPredict() = banSosUseCase.postPredict(predict).asLiveData()

    fun getRecipientByNik() = banSosUseCase.getRecipientByNik(nik).asLiveData()

    fun updateRecipientById() =
        banSosUseCase.updateRecipient(id, status).asLiveData()

    fun getTracking() = banSosUseCase.getTracking(nik).asLiveData()
}