package com.bytephant.senior_care.ui.screen.chat.bubble

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bytephant.senior_care.domain.data.BaseMessage

@Composable
fun UserChatBubbleBody(
    message: BaseMessage,
    bubbleWidth: Dp
) {
    Column(
        modifier = Modifier
            .widthIn(min = 0.dp, max = bubbleWidth),
        horizontalAlignment = Alignment.End
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 0.dp
                    )
                )
                .padding(
                    top = 12.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 12.dp
                )
        ) {
            Row (
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = message.message,
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }
    }
}