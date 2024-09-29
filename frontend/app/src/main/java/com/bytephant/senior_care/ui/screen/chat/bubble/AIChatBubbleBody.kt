package com.bytephant.senior_care.ui.screen.chat.bubble

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bytephant.senior_care.R
import com.bytephant.senior_care.domain.data.BaseMessage

@Composable
fun AIChatBubbleBody(
    message: BaseMessage,
    bubbleWidth: Dp
) {
    Image(
        painter = painterResource(id = R.drawable.ai_icon),
        contentDescription = null,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                shape = RoundedCornerShape(
                    topStart = 0.dp, topEnd = 16.dp,
                    bottomStart = 16.dp, bottomEnd = 16.dp
                )
            )
            .widthIn(min = 0.dp, max = bubbleWidth)
            .padding(12.dp),
    ) {
        Text(
            text = message.message,
            color = Color.White,
            fontSize = 20.sp
        )
    }
}