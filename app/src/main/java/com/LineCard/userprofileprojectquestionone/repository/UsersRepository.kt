package com.LineCard.userprofileprojectquestionone.repository

import androidx.lifecycle.MutableLiveData
import com.LineCard.userprofileprojectquestionone.api.ApiService
import com.LineCard.userprofileprojectquestionone.model.Users
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsersRepository {

    var mApiService: ApiService? = null
    private val currencyLiveData = MutableLiveData<List<Users>>()
    var rateOfSecondCurrency = 0f

    companion object {
        private var usersRepository: UsersRepository? = null

        @get:Synchronized
        val instance: UsersRepository
            get() {
                if (usersRepository == null) usersRepository =
                    UsersRepository()
                return usersRepository!!
            }
    }

    init {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mApiService = retrofit.create<ApiService>(ApiService::class.java)
    }

}