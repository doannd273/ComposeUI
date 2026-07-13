package com.example.youtobecompose.ui.search

import androidx.annotation.StringRes

data class SearchState(
    val searchQuery: String = "",
    val suggestions: List<SearchSuggestionModel> = emptyList(),
)

data class SearchSuggestionModel(
    val id: String,
    @StringRes val queryRes: Int,
    val thumbnailUrl: String? = null,
)

sealed interface SearchEvent {
    data class QueryChange(val query: String) : SearchEvent
    data object MicrophoneClick : SearchEvent
    data class SuggestionClick(
        val suggestion: SearchSuggestionModel,
        val queryText: String,
    ) : SearchEvent
    data class SuggestionInsertClick(
        val suggestion: SearchSuggestionModel,
        val queryText: String,
    ) : SearchEvent
}

sealed interface SearchEffect {
    data object MicrophoneClickSuccess : SearchEffect
    data class SuggestionClickSuccess(val queryText: String) : SearchEffect
    data class SuggestionInsertClickSuccess(val queryText: String) : SearchEffect
}
