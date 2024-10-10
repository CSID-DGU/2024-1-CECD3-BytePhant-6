package com.bytephant.senior_care.ui.routing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(
    currentScreenType: AppScreenType,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
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
            modifier = Modifier,
            horizontalAlignment = Alignment.Start,
        ) {
            IconButton(
                onClick = navigateUp,
                enabled = canNavigateBack
            ) {
                if (canNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
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
                .width(48.dp)
                .height(48.dp),
            horizontalAlignment = Alignment.End,
        ) {
        }
    }
}