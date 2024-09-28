package com.bytephant.senior_care.ui.screen.chat.bubble

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytephant.senior_care.data.BaseMessage

@Composable
fun ChatBubbleList(listState: LazyListState, messages: List<BaseMessage>) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(messages.size) { index ->
            val message = messages[index]
            val contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
            val horizontalAlignment = if (message.isUser) Arrangement.End else Arrangement.Start
            val rowModifier = if (message.isUser) {
                Modifier
                    .fillMaxSize()
                    .padding(end = 12.dp)
            } else {
                Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp)
            }
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentAlignment = contentAlignment
            ) {
                val bubbleWidth = maxWidth * 0.8f
                Row(
                    horizontalArrangement = horizontalAlignment,
                    verticalAlignment = Alignment.Top,
                    modifier = rowModifier
                ) {
                    if (message.isUser) {
                        UserChatBubbleBody(
                            message = message,
                            bubbleWidth = bubbleWidth
                        )
                    }
                    else {
                        AIChatBubbleBody(
                            message = message,
                            bubbleWidth = bubbleWidth
                        )
                    }
                }
            }
        }
    }
}




@Preview
@Composable
fun TestList()  {
    val listState = rememberLazyListState()
    val messages = listOf(
        BaseMessage("안녕\n\n", false),
        BaseMessage("안녕")
    )
    ChatBubbleList(
        listState = listState,
        messages = messages
    )
}