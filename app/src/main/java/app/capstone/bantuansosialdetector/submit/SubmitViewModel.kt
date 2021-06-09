package app.capstone.bantuansosialdetector.submit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
import app.capstone.bantuansosialdetector.core.domain.usecase.BanSosUseCase

class SubmitViewModel(private val banSosUseCase: BanSosUseCase) : ViewModel() {
    fun insertRecipient(recipient: Recipient) = banSosUseCase.insertRecipient(recipient).asLiveData()
}