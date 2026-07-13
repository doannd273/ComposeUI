package com.example.youtobecompose.ui.search

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil3.compose.AsyncImage
import com.example.youtobecompose.R
import com.example.youtobecompose.ui.theme.YoutobeComposeTheme

private val SearchBackground = Color.White
private val SearchInputBackground = Color(0xFFF1F1F1)
private val SearchTextPrimary = Color.Black
private val SearchTextSecondary = Color(0xFF6F6F6F)
private const val SearchLogTag = "SearchScreen"

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    SearchScreen(
        modifier = modifier,
        state = state,
        onBackClick = onBackClick,
        onSearchQueryChange = {
            viewModel.onEvent(SearchEvent.QueryChange(query = it))
        },
        onMicroPhoneClick = {
            viewModel.onEvent(SearchEvent.MicrophoneClick)
        },
        onSuggestionClick = { suggestion, queryText ->
            viewModel.onEvent(
                SearchEvent.SuggestionClick(
                    suggestion = suggestion,
                    queryText = queryText,
                )
            )
        },
        onSuggestionInsertClick = { suggestion, queryText ->
            viewModel.onEvent(
                SearchEvent.SuggestionInsertClick(
                    suggestion = suggestion,
                    queryText = queryText,
                )
            )
        },
    )

    LaunchedEffect(viewModel.effect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    SearchEffect.MicrophoneClickSuccess -> {
                        Toast.makeText(context, "MicrophoneClickSuccess", Toast.LENGTH_SHORT)
                            .show()
                    }

                    is SearchEffect.SuggestionClickSuccess -> {
                        Toast.makeText(
                            context,
                            "SuggestionClickSuccess: ${effect.queryText}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is SearchEffect.SuggestionInsertClickSuccess -> {
                        Toast.makeText(
                            context,
                            "SuggestionInsertClickSuccess: ${effect.queryText}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    state: SearchState,
    onBackClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onMicroPhoneClick: () -> Unit,
    onSuggestionClick: (SearchSuggestionModel, String) -> Unit,
    onSuggestionInsertClick: (SearchSuggestionModel, String) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SearchBackground,
        topBar = {
            SearchTopBar(
                searchQuery = state.searchQuery,
                onBackClick = onBackClick,
                onSearchQueryChange = onSearchQueryChange,
                onMicroPhoneClick = onMicroPhoneClick,
            )
        },
    ) { innerPadding ->
        SearchSuggestionList(
            suggestions = state.suggestions,
            onSuggestionClick = onSuggestionClick,
            onSuggestionInsertClick = onSuggestionInsertClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        )
    }
}

@Composable
fun SearchTopBar(
    searchQuery: String,
    onBackClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onMicroPhoneClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SearchBackground)
            .padding(start = 8.dp, end = 14.dp, top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            modifier = Modifier.size(48.dp),
            onClick = {
                onBackClick()
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = stringResource(R.string.search_back),
                modifier = Modifier.size(32.dp),
                tint = SearchTextPrimary,
            )
        }

        SearchInput(
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            modifier = Modifier.weight(1f),
        )

        IconButton(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(SearchInputBackground),
            onClick = {
                onMicroPhoneClick()
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search_microphone),
                contentDescription = stringResource(R.string.search_voice),
                modifier = Modifier.size(30.dp),
                tint = SearchTextPrimary,
            )
        }
    }
}

@Composable
private fun SearchInput(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val textStyle = TextStyle(
        color = SearchTextPrimary,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
    )

    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(SearchInputBackground)
            .padding(horizontal = 18.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        BasicTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = textStyle,
            singleLine = true,
            cursorBrush = SolidColor(SearchTextPrimary),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search_hint),
                            style = textStyle,
                            color = SearchTextSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    innerTextField()
                }
            },
        )
    }
}

@Composable
private fun SearchSuggestionList(
    suggestions: List<SearchSuggestionModel>,
    onSuggestionClick: (SearchSuggestionModel, String) -> Unit,
    onSuggestionInsertClick: (SearchSuggestionModel, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.background(SearchBackground),
        contentPadding = PaddingValues(top = 6.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        items(
            items = suggestions,
            key = { it.id },
            contentType = { "search_suggestion" },
        ) { suggestion ->
            SearchSuggestionItem(
                suggestion = suggestion,
                onSuggestionClick = onSuggestionClick,
                onSuggestionInsertClick = onSuggestionInsertClick,
            )
        }
    }
}

@Composable
private fun SearchSuggestionItem(
    suggestion: SearchSuggestionModel,
    onSuggestionClick: (SearchSuggestionModel, String) -> Unit,
    onSuggestionInsertClick: (SearchSuggestionModel, String) -> Unit,
) {
    val suggestionText = stringResource(suggestion.queryRes)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
            .clickable {
                onSuggestionClick(suggestion, suggestionText)
            }
            .padding(start = 20.dp, end = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_search_history),
            contentDescription = stringResource(R.string.search_history),
            modifier = Modifier.size(34.dp),
            tint = SearchTextPrimary,
        )

        Text(
            text = suggestionText,
            modifier = Modifier
                .weight(1f)
                .padding(start = 26.dp, end = 12.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 20.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.sp,
            ),
            color = SearchTextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        if (suggestion.thumbnailUrl != null) {
            AsyncImage(
                model = suggestion.thumbnailUrl,
                contentDescription = stringResource(R.string.search_thumbnail),
                placeholder = painterResource(R.drawable.ic_placeholder_background),
                error = painterResource(R.drawable.ic_placeholder_background),
                onError = { state ->
                    Log.e(
                        SearchLogTag,
                        "Failed to load search thumbnail: ${suggestion.thumbnailUrl}",
                        state.result.throwable,
                    )
                },
                modifier = Modifier
                    .width(86.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop,
            )
        }

        IconButton(
            modifier = Modifier.size(48.dp),
            onClick = {
                onSuggestionInsertClick(suggestion, suggestionText)
            },
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_search_insert),
                contentDescription = stringResource(R.string.search_insert_suggestion),
                modifier = Modifier.size(32.dp),
                tint = SearchTextPrimary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    YoutobeComposeTheme(darkTheme = false) {
        SearchScreen(
            state = SearchState(
                suggestions = listOf(
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
                        id = "suggestion-best-food",
                        queryRes = R.string.search_suggestion_best_food,
                    ),
                )
            ),
            onBackClick = {},
            onSearchQueryChange = {},
            onMicroPhoneClick = {},
            onSuggestionClick = { _, _ -> },
            onSuggestionInsertClick = { _, _ -> },
        )
    }
}
