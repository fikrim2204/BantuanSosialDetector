package app.capstone.bantuansosialdetector.core.domain.repository

import app.capstone.bantuansosialdetector.core.data.source.Resource
import app.capstone.bantuansosialdetector.core.domain.model.*
import kotlinx.coroutines.flow.Flow

interface IBanSosRepository {
    fun insertRecipient(recipient: Recipient): Flow<Resource<Insert>>
    fun getRecipientByNik(nik: String?): Flow<Resource<List<Recipient>>>
    fun updateRecipient(id: String?, status: Int?): Flow<Resource<Insert>>
    fun getTracking(nik: String?): Flow<Resource<List<Tracking>>>
    fun getDetailTracking(id: String?): Flow<Resource<List<DetailTracking>>>
    fun postPredict(predict: Predict?): Flow<Resource<ResultPredict>>
}