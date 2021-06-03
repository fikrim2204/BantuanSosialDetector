package app.capstone.bantuansosialdetector.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.capstone.bantuansosialdetector.core.domain.usecase.BanSosUseCase

class TrackingViewModel(private val banSosUseCase: BanSosUseCase) : ViewModel() {

    fun getDetailTracking(id: String) = banSosUseCase.getDetailTracking(id).asLiveData()
}