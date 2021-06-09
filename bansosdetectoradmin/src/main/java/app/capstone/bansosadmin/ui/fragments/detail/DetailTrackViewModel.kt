package app.capstone.bansosadmin.ui.fragments.detail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.capstone.bansosadmin.data.Resource
import app.capstone.bansosadmin.domain.model.Tracks
import app.capstone.bansosadmin.domain.usecase.MyUseCase

class DetailTrackViewModel(private val myUseCase: MyUseCase) : ViewModel() {
    val tracking = MediatorLiveData<Resource<List<Tracks>>>()
    fun getTracking(id: String) {
        tracking.addSource(myUseCase.getTrackById(id).asLiveData()) {
            tracking.value = it
        }
    }

    fun deleteTrackById(id: String) = myUseCase.deleteTrack(id).asLiveData()
}