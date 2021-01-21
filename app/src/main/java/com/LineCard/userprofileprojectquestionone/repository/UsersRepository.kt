package com.LineCard.userprofileprojectquestionone.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.LineCard.userprofileprojectquestionone.api.ApiService
import com.LineCard.userprofileprojectquestionone.model.UserImage
import com.LineCard.userprofileprojectquestionone.model.Users
import com.LineCard.userprofileprojectquestionone.persistence.UserImageDao
import com.LineCard.userprofileprojectquestionone.persistence.UsersDao
import com.LineCard.userprofileprojectquestionone.persistence.UsersDatabases
import com.google.gson.JsonArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class UsersRepository {

    var mApiService: ApiService? = null
    private val usersLiveData = MutableLiveData<List<Users>>()

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


    fun fetchUsers(): LiveData<List<Users>> {

        val call: Call<JsonArray> = mApiService!!.getUserData()

        call.enqueue(object : Callback<JsonArray> {
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("ResponseStatus", "Failed: " + t.message)
                usersLiveData.postValue(null)
                t.printStackTrace()
            }

            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {

                if (!response.isSuccessful){
                    Log.d("ResponseStatus", "Unsuccessful: ${response.code()}" + response.body())
                    if (response.message() == "null"){
                        return
                    }else{
                        Log.d("ResponseMessage", "${response.message()}")
                    }
                    usersLiveData.postValue(null)
                }

                if (response.isSuccessful){

                    Log.d("ResponseStatus", "successful: ${response.code()} " + response.body())
                    val getting = response.body()
                    val data = getting!!.asJsonArray

                    val usersList = ArrayList<Users>()

                    for(datavalue in data){
                        val users = Users()
                        users.id = datavalue.asJsonObject.get("id").asInt
                        users.name = datavalue.asJsonObject.get("name").asString
                        users.email = datavalue.asJsonObject.get("email").asString
                        users.username = datavalue.asJsonObject.get("username").asString
                        users.phone = datavalue.asJsonObject.get("phone").asString

                        usersList.add(users)
                    }

                    usersLiveData.postValue(usersList)

                }

            }
        })

        return usersLiveData
    }


    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)



    private fun initializeDatabase(application: Application): UsersDao {
        return UsersDatabases.getDatabase(application).usersDao()
    }


    fun insertUsersData(data: Users, application: Application){
        scope.launch(Dispatchers.IO)  {
            initializeDatabase(application).insertData(data)
        }
    }


    fun getAllUsersData(application: Application): LiveData<List<Users>> {
        val userData = initializeDatabase(application).getAllUsers()
        return userData
    }

    fun getSpecificUser(id: Int, application: Application): LiveData<Users>{
        val userData = initializeDatabase(application).selectUser(id)
        return userData
    }

    fun getTotalUserNumber(application: Application): LiveData<Int> {
        val num = initializeDatabase(application).userCount()
        return num
    }



    // Userimage Dao portion

    private fun initializeImageDatabase(application: Application): UserImageDao {
        return UsersDatabases.getDatabase(application).usersImageDao()
    }


    fun insertImageData(data: UserImage, application: Application){
        scope.launch(Dispatchers.IO)  {
            initializeImageDatabase(application).insertImage(data)
        }
    }


    fun updateImage(id: Int, image: String, application: Application) {
        scope.launch(Dispatchers.IO)  {
            initializeImageDatabase(application).updateUserImage(id, image)
        }
    }

    fun getSpecificImage(id: Int, application: Application): LiveData<UserImage>{
        val imageData = initializeImageDatabase(application).selectImage(id)
        return imageData
    }

}