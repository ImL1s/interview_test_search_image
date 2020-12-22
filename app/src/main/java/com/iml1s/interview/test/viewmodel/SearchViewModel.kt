package com.iml1s.interview.test.viewmodel

import androidx.lifecycle.*
import com.iml1s.interview.test.model.Hits
import com.iml1s.interview.test.repository.SearchRepository
import com.iml1s.interview.test.repository.SearchRepositoryImpl
import com.iml1s.interview.test.utils.SingleLiveEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import timber.log.Timber

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchViewModel : ViewModel() {

    // region [private property]
    private val _hits = MutableLiveData<List<Hits>>(emptyList())

    // TODO: using DI (Dagger2)
    private val searchRepository: SearchRepository = SearchRepositoryImpl()
    // endregion

    // region [public property]
    val input = MutableLiveData("")
    val isEditTextFocus = MutableLiveData(false)
    val isGrid = MutableLiveData(false)
    val hits: LiveData<List<Hits>> = _hits
    val onChangeGrid = SingleLiveEvent<Boolean>()
    // endregion

    init {
        input.asFlow()
            .filter { isEditTextFocus.value == true }
            .debounce(200)
            .flatMapLatest { searchRepository.search(it) }
            .onEach {
                Timber.d("GG: search!")
                _hits.value = it.hits
            }
            .launchIn(viewModelScope)

        isGrid.asFlow()
            .distinctUntilChanged()
            .onEach { onChangeGrid.value = it }
            .launchIn(viewModelScope)
    }
}
