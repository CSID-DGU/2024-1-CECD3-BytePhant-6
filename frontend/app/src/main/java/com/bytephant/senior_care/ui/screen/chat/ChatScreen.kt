package com.bytephant.senior_care.ui.screen.chat

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bytephant.senior_care.domain.data.AgentStatus
import com.bytephant.senior_care.ui.screen.chat.bubble.ChatBubbleList

@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by chatViewModel.uiState.collectAsState()
    val dialogue by chatViewModel.dialogueState.collectAsState()
    val agentStatus by chatViewModel.agentState.collectAsState()

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val listState = rememberLazyListState()

    val inputEnable = agentStatus == AgentStatus.WAITING || agentStatus == AgentStatus.LISTENING

    LaunchedEffect(dialogue.messageList.size, listState.isScrollInProgress) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.size - 1)
        }
    }
    LaunchedEffect(agentStatus) {
        if (agentStatus == AgentStatus.LISTENING) {
            focusRequester.requestFocus()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                }
        ) {
            ChatBubbleList(
                listState = listState,
                messages = dialogue.messageList
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = uiState.inputText,
                onValueChange = { chatViewModel.updateInputText(it) },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Bottom)
                    .focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                ),
                enabled = inputEnable,
            )
            Button(
                onClick = {
                    if (uiState.inputText.isNotBlank()) {
                        chatViewModel.sendQuestion(uiState.inputText)
                    }
                },
                enabled = inputEnable,
                modifier = Modifier
                    .padding(end = 16.dp)
            ) {
                Text(
                    text="보내기",
                )
            }
        }
    }
}
