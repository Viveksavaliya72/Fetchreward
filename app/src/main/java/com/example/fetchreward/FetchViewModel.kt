package com.example.fetchreward

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fetchreward.modal.Item
import com.example.fetchreward.repository.ItemRepository
import kotlinx.coroutines.launch

class FetchViewModel : ViewModel() {
    private val repository = ItemRepository()

    private val _itemsData = MutableLiveData<Map<Int, List<Item>>>()
    val itemsData: LiveData<Map<Int, List<Item>>> = _itemsData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        fetchItems()
    }

    fun fetchItems() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getItems()
                .onSuccess { groupedItems ->
                    _itemsData.value = groupedItems
                    _isLoading.value = false
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "An unknown error occurred"
                    _isLoading.value = false
                }
        }
    }
}