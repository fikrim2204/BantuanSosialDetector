package app.capstone.bansosadmin.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.capstone.bansosadmin.domain.usecase.MyUseCase

class HomeViewModel(private val myUseCase: MyUseCase) :
    ViewModel() {

    fun getPenerimaById(id: String) = myUseCase.getRecipientById(id).asLiveData()
}