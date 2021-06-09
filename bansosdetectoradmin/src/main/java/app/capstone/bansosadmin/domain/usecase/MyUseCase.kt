package app.capstone.bansosadmin.domain.usecase

import app.capstone.bansosadmin.data.Resource
import app.capstone.bansosadmin.domain.model.*
import kotlinx.coroutines.flow.Flow

interface MyUseCase {
    fun getRecipientById(id: String): Flow<Resource<List<Recipients>>>
    fun insertTrack(insert: Insert): Flow<Resource<List<Insert>>>
    fun deleteTrack(id: String): Flow<Resource<List<Insert>>>
    fun updateTrack(id: String, updateItemTrack: UpdateItemTrack): Flow<Resource<UpdateItemTrack>>
    fun getTrackById(id: String): Flow<Resource<List<Tracks>>>
    fun getTrackDetailById(id: String): Flow<Resource<List<DetailTracks>>>
    fun finishTrack(insert: Insert): Flow<Resource<Insert>>
}