package app.capstone.bansosadmin.domain.usecase

import app.capstone.bansosadmin.data.Resource
import app.capstone.bansosadmin.domain.model.Penerima
import kotlinx.coroutines.flow.Flow

interface MyUseCase {
    fun getAllMovies(id: String): Flow<Resource<List<Penerima>>>
}