package com.bytephant.senior_care.ui.routing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(
    currentScreenType: AppScreenType,
    navigationIcon: @Composable () -> Unit,
    modifier : Modifier = Modifier
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column (
            modifier = Modifier
                .size(56.dp)
                .padding(4.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            navigationIcon()
        }
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = currentScreenType.title,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top= 10.dp)
            )
        }
        Column(
            modifier = Modifier
                .size(56.dp)
                .padding(4.dp),
            horizontalAlignment = Alignment.End,
        ) {
        }
    }
}