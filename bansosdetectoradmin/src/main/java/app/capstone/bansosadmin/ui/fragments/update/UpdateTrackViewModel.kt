package app.capstone.bansosadmin.ui.fragments.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.capstone.bansosadmin.domain.model.Insert
import app.capstone.bansosadmin.domain.model.UpdateItemTrack
import app.capstone.bansosadmin.domain.usecase.MyUseCase

class UpdateTrackViewModel(private val myUseCase: MyUseCase) : ViewModel() {
    fun update(id: String, updateItemTrack: UpdateItemTrack) =
        myUseCase.updateTrack(id, updateItemTrack).asLiveData()

    fun tracksDetail(id: String) = myUseCase.getTrackDetailById(id).asLiveData()

    fun finishTrack(insert: Insert) = myUseCase.finishTrack(insert).asLiveData()
}