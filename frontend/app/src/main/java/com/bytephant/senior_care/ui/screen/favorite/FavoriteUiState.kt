package com.bytephant.senior_care.ui.screen.favorite

import com.bytephant.senior_care.domain.data.FavoriteKeyword

data class FavoriteUiState(
    val keywordList: List<FavoriteKeyword> = listOf(),
    val maxImportance: Int = 0,
)
