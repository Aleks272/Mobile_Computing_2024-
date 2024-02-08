package com.example.composetutorial

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    var currentUser: LiveData<User> = userRepository.findLiveUserById(0)


    fun saveUser(userName: String, imageUri: String?) {
        viewModelScope.launch {
            val user = User(uid = 0, username = userName, image = imageUri)
            userRepository.upsertUser(user)
        }
    }

    fun findUserById(uid: Int): User {
        return userRepository.findUserById(uid)
    }

    fun findUserByName(name: String): User {
        return userRepository.findUserByName(name)
    }

    fun insertDefaultUser(userName: String, imageUri: String) {
        viewModelScope.launch {
            val user = User(uid = 0, username = userName, image = imageUri)
            userRepository.insertDefaultUser(user)
        }
    }

    fun findCurrentUser(uid: Int) {
        viewModelScope.launch {
            currentUser = userRepository.findLiveUserById(uid)
        }
    }

    suspend fun getAllUsers(): List<User> {
        return userRepository.getAll()
    }
}
class UserRepository(private val userDao: UserDao) {

    suspend fun upsertUser(user: User) {
        userDao.upsertUser(user)
    }

    suspend fun getAll(): List<User> {
        return userDao.getAll()
    }

    fun findUserById(uid: Int): User {
        return userDao.findUserById(uid)
    }

    fun findUserByName(name: String): User {
        return userDao.findByName(name)
    }

    fun findLiveUserById(uid: Int): LiveData<User> {
        return userDao.findLiveUserById(uid)
    }

    suspend fun insertDefaultUser(user: User) {
        userDao.insertDefaultUser(user)
    }
}