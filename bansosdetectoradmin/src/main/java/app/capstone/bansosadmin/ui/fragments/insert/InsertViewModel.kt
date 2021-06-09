package app.capstone.bansosadmin.ui.fragments.insert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.capstone.bansosadmin.domain.model.Insert
import app.capstone.bansosadmin.domain.usecase.MyUseCase

class InsertViewModel(private val myUseCase: MyUseCase) : ViewModel() {
    fun insert(insert: Insert) = myUseCase.insertTrack(insert).asLiveData()
}