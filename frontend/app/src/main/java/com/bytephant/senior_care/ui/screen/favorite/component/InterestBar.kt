package com.bytephant.senior_care.ui.screen.favorite.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InterestBar(
    interest: String,
    importance: Int,
    maxImportance: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = interest,
                modifier = Modifier
                    .weight(1.0f)
                    .padding(start = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = importance.toString(),
                modifier = Modifier.padding(end = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(importance / (maxImportance * 1.0f))
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}

// 사용 예시
@Preview
@Composable
fun InterestProgressBarPreview() {
    InterestBar(
        interest = "Technology",
        importance = 45,
        maxImportance = 50,
    )
}
