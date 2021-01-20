package com.LineCard.userprofileprojectquestionone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.LineCard.userprofileprojectquestionone.model.Users
import com.LineCard.userprofileprojectquestionone.repository.UsersRepository

class UsersViewModelconstructor(application: Application): AndroidViewModel(application) {

    val app = application

    private val repository: UsersRepository = UsersRepository.instance

    private val allUsers: LiveData<List<Users>> = repository.fetchUsers()


    fun getUsers(): LiveData<List<Users>>? {
        return allUsers
    }

    fun insertResponse(response: Users) {
        repository.insertUsersData(response, app)
    }

    fun updateResponse(id: Int, images: String){
        repository.updateUser(id, images, app)
    }

    fun getParticularUsers(id: Int): LiveData<Users> {
        return repository.getSpecificUser(id, app)
    }


    fun  getAllUsera() : LiveData<List<Users>>{
        return repository.getAllUsersData(app)
    }


}