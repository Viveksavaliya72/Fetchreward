package com.example.fetchreward.repository

import com.example.fetchreward.Network.NetworkModule
import com.example.fetchreward.modal.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemRepository {
    private val apiService = NetworkModule.apiService

    suspend fun getItems(): Result<Map<Int, List<Item>>> = withContext(Dispatchers.IO) {
        try {
            val items = apiService.getItems()

            // Filter out items with blank or null names
            val filteredItems = items.filter { !it.name.isNullOrBlank() }

            // Group items by listId
            val groupedItems = filteredItems.groupBy { it.listId }

            // Sort each group by name
            val sortedGroups = groupedItems.mapValues { (_, items) ->
                items.sortedBy { it.name }
            }

            // Sort the groups by listId
            val sortedByListId = sortedGroups.toSortedMap()

            Result.success(sortedByListId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}