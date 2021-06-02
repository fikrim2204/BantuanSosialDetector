package app.capstone.bantuansosialdetector.core.domain.usecase

import app.capstone.bantuansosialdetector.core.data.source.Resource
import app.capstone.bantuansosialdetector.core.data.source.remote.response.InsertItems
import app.capstone.bantuansosialdetector.core.domain.model.Insert
import app.capstone.bantuansosialdetector.core.domain.model.Predict
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
import app.capstone.bantuansosialdetector.core.domain.model.ResultPredict
import kotlinx.coroutines.flow.Flow

interface BanSosUseCase {
    fun insertRecipient(recipient: Recipient): Flow<Resource<Insert>>
    fun getRecipientByNik(nik: String): Flow<Resource<List<Recipient>>>
    fun updateRecipient(recipient: Recipient, status: Boolean)
    fun postPredict(predict: Predict): Flow<Resource<ResultPredict>>
}
