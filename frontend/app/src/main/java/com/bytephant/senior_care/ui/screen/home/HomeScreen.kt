package com.bytephant.senior_care.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bytephant.senior_care.R
import com.bytephant.senior_care.domain.data.AgentStatus

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by homeViewModel.uiState.collectAsState()
    val agentStatus by homeViewModel.agentState.collectAsState()
    val agentLastSentence by homeViewModel.agentLastSentence.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier
                .weight(0.5f)
                .padding(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            val background = when (agentStatus) {
                AgentStatus.WAITING -> Color.Gray
                AgentStatus.TALKING -> Color.Blue
                AgentStatus.LISTENING -> Color.Green
                AgentStatus.THINKING -> Color.Yellow
            }

            Box(
                modifier = Modifier
                    .height(300.dp)
                    .width(300.dp)
                    .clip(CircleShape)
                    .background(
                        color = background
                    )
            )
            {
                IconButton(
                    onClick = { homeViewModel.replyWithVoice() },
                    enabled = true,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ai_icon),
                        contentDescription = "icon",
                        modifier = Modifier
                            .padding(12.dp)
                            .clip(CircleShape)
                            .fillMaxSize(),
                    )
                }
            }

        }
        Column(
            modifier = Modifier.weight(0.3f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .heightIn(min = 24.dp, max = 320.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = agentLastSentence,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    maxLines = 7,
                    lineHeight = 32.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}
