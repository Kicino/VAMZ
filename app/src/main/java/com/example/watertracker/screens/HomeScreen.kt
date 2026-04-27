package com.example.watertracker.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.watertracker.API.WaterViewModel
import com.example.watertracker.R
import androidx.compose.foundation.combinedClickable

@Composable
fun HomeScreen(
    viewModel: WaterViewModel
) {

    val totalWater by viewModel.todayWater.collectAsState()
    val goal by viewModel.dailyGoal.collectAsState()
    val progress = (totalWater.toFloat() / goal).coerceIn(0f, 1f)//vypocita percento 0 - 100%

    val imageRes = when {
        progress < 0.4f -> R.drawable.thirsty
        progress < 0.8f -> R.drawable.ok
        else ->R.drawable.perfect
    }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    //premenne na vypis prepoictane na litre
    val totalLiters = totalWater / 1000f
    val goalLiters = goal / 1000f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .background(Color.LightGray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .background(Color(14, 195, 215))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLandscape) {
            //vedla seba
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = stringResource(R.string.main_image_description),
                        modifier = Modifier
                            .fillMaxSize()
                            .combinedClickable(
                                onClick = {},
                                onLongClick = {}
                        )
                    )
                }

                Spacer(modifier = Modifier.width(32.dp))

                Text(
                    text = stringResource(R.string.main_screen_text1) +
                            String.format(java.util.Locale.getDefault(), "%.2f", totalLiters) +
                            " / " +
                            String.format(java.util.Locale.getDefault(), "%.2f", goalLiters) +
                            stringResource(R.string.main_screen_text2),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        } else {
            // pod sebou
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = stringResource(R.string.main_image_description),
                        modifier = Modifier
                            .fillMaxSize()
                            .combinedClickable(
                                onClick = {},
                                onLongClick = {}
                            )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.main_screen_text1) +
                            String.format(java.util.Locale.getDefault(), "%.2f", totalLiters) +
                            " / " +
                            String.format(java.util.Locale.getDefault(), "%.2f", goalLiters) +
                            stringResource(R.string.main_screen_text2),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}