package com.bytephant.senior_care.ui.screen.favorite

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bytephant.senior_care.ui.screen.favorite.component.InterestBar

@Composable
fun FavoriteScreen(
    favoriteViewModel: FavoriteViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by favoriteViewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        favoriteViewModel.updateKeyword()
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(top = 4.dp),
    ) {
        items(uiState.keywordList) { keyword ->
            InterestBar(
                favoriteKeyword = keyword,
                maxImportance = uiState.maxImportance
            )
        }
    }
}