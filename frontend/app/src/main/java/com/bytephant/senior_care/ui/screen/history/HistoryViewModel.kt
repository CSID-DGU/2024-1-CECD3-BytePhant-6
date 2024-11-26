package com.bytephant.senior_care.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bytephant.senior_care.application.SeniorCareApplication
import com.bytephant.senior_care.domain.user.loader.MockUserMetaLoader
import com.bytephant.senior_care.domain.user.loader.UserMetaLoader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val userMetaLoader: UserMetaLoader
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    fun loadDailyHistories() {
        viewModelScope.launch {
            try {
                val histories = userMetaLoader.loadHistory()
                _uiState.update { currentState ->
                    currentState.copy(
                        history = histories,
                    )
                }
            } catch (ignored: Exception) {
            }
        }
    }
    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val container = (this[APPLICATION_KEY] as SeniorCareApplication).container

                HistoryViewModel(MockUserMetaLoader())
            }
        }
    }

}