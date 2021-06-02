package app.capstone.bantuansosialdetector.core.domain.repository

import app.capstone.bantuansosialdetector.core.data.source.Resource
import app.capstone.bantuansosialdetector.core.data.source.remote.response.RecipientRemote
import app.capstone.bantuansosialdetector.core.domain.model.Predict
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
import app.capstone.bantuansosialdetector.core.domain.model.ResultPredict
import kotlinx.coroutines.flow.Flow

interface IBanSosRepository {
    fun insertRecipient(recipient: Recipient): Flow<Resource<List<Recipient>>>
    fun getRecipientByNik(nik: String): Flow<Resource<List<Recipient>>>
    fun updateRecipient(recipientRemote: RecipientRemote, status: Boolean)
    fun postPredict(predict: Predict): Flow<Resource<ResultPredict>>
}