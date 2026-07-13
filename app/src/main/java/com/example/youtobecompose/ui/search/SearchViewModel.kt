package com.example.youtobecompose.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtobecompose.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SearchState())
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<SearchEffect>()
    val effect: SharedFlow<SearchEffect> = _effect.asSharedFlow()

    init {
        loadSuggestions()
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            SearchEvent.MicrophoneClick -> {
                submitMicrophoneClick()
            }

            is SearchEvent.QueryChange -> {
                updateQuery(query = event.query)
            }

            is SearchEvent.SuggestionClick -> {
                submitSuggestionClick(queryText = event.queryText)
            }

            is SearchEvent.SuggestionInsertClick -> {
                submitSuggestionInsertClick(queryText = event.queryText)
            }
        }
    }

    private fun loadSuggestions() {
        val suggestions = listOf(
            SearchSuggestionModel(
                id = "suggestion-mixigaming",
                queryRes = R.string.search_suggestion_mixigaming,
                thumbnailUrl = "https://picsum.photos/id/1043/480/270",
            ),
            SearchSuggestionModel(
                id = "suggestion-iphone-ultra",
                queryRes = R.string.search_suggestion_iphone_ultra,
                thumbnailUrl = "https://picsum.photos/id/1015/480/270",
            ),
            SearchSuggestionModel(
                id = "suggestion-qnt",
                queryRes = R.string.search_suggestion_qnt,
                thumbnailUrl = "https://picsum.photos/id/1025/240/426",
            ),
            SearchSuggestionModel(
                id = "suggestion-dev-nguyen",
                queryRes = R.string.search_suggestion_dev_nguyen,
                thumbnailUrl = "https://picsum.photos/id/1011/240/426",
            ),
            SearchSuggestionModel(
                id = "suggestion-best-food",
                queryRes = R.string.search_suggestion_best_food,
            ),
            SearchSuggestionModel(
                id = "suggestion-rambo",
                queryRes = R.string.search_suggestion_rambo,
                thumbnailUrl = "https://picsum.photos/seed/video1/480/270",
            ),
            SearchSuggestionModel(
                id = "suggestion-military-knowledge",
                queryRes = R.string.search_suggestion_military_knowledge,
                thumbnailUrl = "https://picsum.photos/seed/video2/480/270",
            ),
            SearchSuggestionModel(
                id = "suggestion-long",
                queryRes = R.string.search_suggestion_long,
                thumbnailUrl = "https://picsum.photos/seed/video3/480/270",
            ),
            SearchSuggestionModel(
                id = "suggestion-blv-anh-quan",
                queryRes = R.string.search_suggestion_blv_anh_quan,
                thumbnailUrl = "https://picsum.photos/id/1002/720/1280",
            ),
        )

        _uiState.update {
            it.copy(suggestions = suggestions)
        }
    }

    private fun updateQuery(query: String) {
        _uiState.update {
            it.copy(searchQuery = query)
        }
    }

    private fun submitMicrophoneClick() {
        viewModelScope.launch {
            _effect.emit(SearchEffect.MicrophoneClickSuccess)
        }
    }

    private fun submitSuggestionClick(queryText: String) {
        viewModelScope.launch {
            _effect.emit(SearchEffect.SuggestionClickSuccess(queryText = queryText))
        }
    }

    private fun submitSuggestionInsertClick(queryText: String) {
        _uiState.update {
            it.copy(searchQuery = queryText)
        }

        viewModelScope.launch {
            _effect.emit(SearchEffect.SuggestionInsertClickSuccess(queryText = queryText))
        }
    }
}
