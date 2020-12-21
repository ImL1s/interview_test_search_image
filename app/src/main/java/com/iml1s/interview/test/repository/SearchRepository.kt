package com.iml1s.interview.test.repository

import com.iml1s.interview.test.model.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun search(keyword: String): Flow<SearchResponse>
}
