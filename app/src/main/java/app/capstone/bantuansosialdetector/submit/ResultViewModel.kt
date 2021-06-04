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

//    private val _inputPostPredict = MutableLiveData<Predict?>()
//    val predict: LiveData<Predict?> get() = _inputPostPredict
//    private val _inputIdUpdateRecipient = MutableLiveData<String?>()
//    val inputIdUpdateRecipient: LiveData<String?> get() = _inputIdUpdateRecipient
//    private val _inputStatusUpdateRecipient = MutableLiveData<Int?>()
//    val inputStatusUpdateRecipient: LiveData<Int?> get() = _inputStatusUpdateRecipient
//    private val _inputNikGetTracking= MutableLiveData<String?>()
//    val inputNikGetTracking: LiveData<String?> get() = _inputNikGetTracking

    private var predict: Predict? = null
    private var nikTracking: String? = null
    private var id: String? = null
    private var status: Int? = null

    fun inputNikGetRecipient(nik: String?) {
        _nikGetRecipient.value = nik
    }

    fun inputPostPredict(predict: Predict?) {
        this.predict = predict
    }

    fun updateRecipient(id: String?, status: Int?) {
        this.id = id
        this.status = status
    }

    fun inputGetTracking(nik: String?) {
        this.nikTracking = nik
    }

    fun getRecipientByNik(nik: String?) = banSosUseCase.getRecipientByNik(nik).asLiveData()

    fun postPredict() = banSosUseCase.postPredict(predict).asLiveData()

    fun updateRecipientById() =
        banSosUseCase.updateRecipient(id, status).asLiveData()

    fun getTracking() = banSosUseCase.getTracking(nikTracking).asLiveData()
}