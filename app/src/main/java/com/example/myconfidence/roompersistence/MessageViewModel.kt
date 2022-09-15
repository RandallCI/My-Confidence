package com.example.myconfidence.roompersistence

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MessageViewModel(private val repository: MessageRepository): ViewModel() {

    val allMessages: LiveData<List<MotivationalMessage>> = repository.allMessages.asLiveData()

    fun insert(message: MotivationalMessage) = viewModelScope.launch() { repository.insertMessage(message) }

}

class MessageViewModelFactory(private val repository: MessageRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MessageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}