package com.iml1s.interview.test.repository

import com.iml1s.interview.test.datasource.defaultApiService
import com.iml1s.interview.test.model.SearchResponse
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl : SearchRepository {

    // TODO: using DI (Dagger2)
    val apiService = defaultApiService

    override fun search(keyword: String): Flow<SearchResponse> {
        return defaultApiService.search(keyword = keyword)
    }
}
