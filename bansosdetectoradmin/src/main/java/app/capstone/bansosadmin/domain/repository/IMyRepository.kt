package app.capstone.bansosadmin.domain.repository

import app.capstone.bansosadmin.data.Resource
import app.capstone.bansosadmin.domain.model.Penerima
import kotlinx.coroutines.flow.Flow

interface IMyRepository {
    fun getPenerimaById(id: String): Flow<Resource<List<Penerima>>>
}