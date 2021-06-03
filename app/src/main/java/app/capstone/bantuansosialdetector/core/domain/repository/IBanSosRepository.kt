package app.capstone.bantuansosialdetector.core.domain.repository

import app.capstone.bantuansosialdetector.core.data.source.Resource
import app.capstone.bantuansosialdetector.core.domain.model.Insert
import app.capstone.bantuansosialdetector.core.domain.model.Predict
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
import app.capstone.bantuansosialdetector.core.domain.model.ResultPredict
import kotlinx.coroutines.flow.Flow

interface IBanSosRepository {
    fun insertRecipient(recipient: Recipient): Flow<Resource<Insert>>
    fun getRecipientByNik(nik: String): Flow<Resource<List<Recipient>>>
    fun updateRecipient(id: String?, status: Int?): Flow<Resource<Insert>>
    fun postPredict(predict: Predict): Flow<Resource<ResultPredict>>
}