package com.example.composetutorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    var currentUser: LiveData<User> = userRepository.findLiveUserById(0)


    fun saveUser(userName: String) {
        viewModelScope.launch {
            val user = User(uid = 0, username = userName)
            userRepository.upsertUser(user)
        }
    }

    suspend fun findUserById(uid: Int): User {
        return userRepository.findUserById(uid)
    }

    fun insertDefaultUser(userName: String) {
        viewModelScope.launch {
            val user = User(uid = 0, username = userName)
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

    suspend fun findUserById(uid: Int): User {
        return userDao.findUserById(uid)
    }

    fun findLiveUserById(uid: Int): LiveData<User> {
        return userDao.findLiveUserById(uid)
    }

    suspend fun insertDefaultUser(user: User) {
        userDao.insertDefaultUser(user)
    }
}