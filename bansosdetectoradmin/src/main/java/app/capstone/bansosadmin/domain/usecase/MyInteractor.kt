package app.capstone.bansosadmin.domain.usecase

import app.capstone.bansosadmin.domain.repository.IMyRepository

class MyInteractor(private val myRepository: IMyRepository) : MyUseCase {
    override fun getAllMovies(id: String) = myRepository.getPenerimaById(id)

}