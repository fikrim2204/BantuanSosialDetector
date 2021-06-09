package app.capstone.bansosadmin.domain.usecase

import app.capstone.bansosadmin.domain.model.Insert
import app.capstone.bansosadmin.domain.model.UpdateItemTrack
import app.capstone.bansosadmin.domain.repository.IMyRepository

class MyInteractor(private val myRepository: IMyRepository) : MyUseCase {
    override fun getRecipientById(id: String) = myRepository.getRecipientById(id)
    override fun insertTrack(insert: Insert) = myRepository.insertTrack(insert)
    override fun deleteTrack(id: String) = myRepository.deleteTrack(id)
    override fun updateTrack(id: String, updateItemTrack: UpdateItemTrack) =
        myRepository.updateTrack(id, updateItemTrack)

    override fun getTrackById(id: String) = myRepository.getTrackById(id)
    override fun getTrackDetailById(id: String) = myRepository.getTrackDetailById(id)
    override fun finishTrack(insert: Insert) =
        myRepository.finishTrack(insert)

}