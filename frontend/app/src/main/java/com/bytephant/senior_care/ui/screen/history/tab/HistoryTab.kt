package com.bytephant.senior_care.ui.screen.history.tab

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bytephant.senior_care.ui.screen.history.DailyHistory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun HistoryTab(
    dailyHistory: DailyHistory,
    formatter: DateTimeFormatter,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(true) }


    val expandImage = if (!isExpanded) Icons.Sharp.KeyboardArrowDown
    else Icons.Sharp.KeyboardArrowUp
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.shapes.medium // Card와 동일한 둥근 모서리 모양
            )
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.95f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Column{
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription ="캘린더",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(32.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = formatter.format(dailyHistory.date),
                        fontSize = 16.sp
                    )
                    Text (
                        text = "${dailyHistory.logs.size}개의 기억 정보",
                        fontSize = 12.sp
                    )
                }
                Column(
                    modifier = Modifier
                ) {
                    IconButton(
                        onClick = { isExpanded = !isExpanded }
                    ) {
                        Icon(
                            imageVector = expandImage,
                            contentDescription = "자세히 보기",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
        // 확장된 콘텐츠
        if (isExpanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(start=10.dp)
                ) {
                    dailyHistory.logs.forEach { log ->
                        Text(
                            text = log,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }
    }
}

 @Preview
@Composable
fun HistoryTabPreview() {
    HistoryTab(dailyHistory = DailyHistory(
        date = LocalDate.now(),
        logs = listOf("abc:def", "abc:grt")
    ),
        formatter = DateTimeFormatter.ofPattern("M월 d일 EEEE", Locale.KOREAN),
    )
}