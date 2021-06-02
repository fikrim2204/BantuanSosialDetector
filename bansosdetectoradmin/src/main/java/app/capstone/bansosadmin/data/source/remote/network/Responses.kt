package app.capstone.bansosadmin.data.source.remote.network

sealed class Responses<out R> {
    data class Success<out T>(val data: T) : Responses<T>()
    data class Error(val errorMessage: String) : Responses<Nothing>()
    object Empty : Responses<Nothing>()
}