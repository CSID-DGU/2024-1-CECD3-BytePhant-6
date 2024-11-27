package com.bytephant.senior_care.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bytephant.senior_care.application.SeniorCareApplication
import com.bytephant.senior_care.domain.user.loader.UserMetaLoader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(
    val userMetaLoader: UserMetaLoader
): ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState = _uiState.asStateFlow()

    fun updateKeyword() {
        viewModelScope.launch {
            val keywordList = userMetaLoader.loadKeyword()
            _uiState.update { current ->
                current.copy(
                    keywordList = keywordList,
                    maxImportance = keywordList.maxBy { it.score }.score
                )
            }
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val container = (this[APPLICATION_KEY] as SeniorCareApplication).container

                FavoriteViewModel(container.userMetaLoader)
            }
        }
    }
}