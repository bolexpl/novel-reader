package com.example.novelreader.test

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NameViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<NameItem>>
    private val repository: NameRepository

    init {
        val nameDao = NameDatabase.getInstance(application).nameDao()
        repository = NameRepository(nameDao)
        readAllData = repository.readAllData
    }

    fun addName(nameItem: NameItem){
        viewModelScope.launch(Dispatchers.IO){
            repository.addName(nameItem)
        }
    }

    fun updateName(nameItem: NameItem){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateName(nameItem)
        }
    }

    fun deleteName(nameItem: NameItem){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteName(nameItem)
        }
    }
}

class NameViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NameViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return NameViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


