package com.benki.taskmanager.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.benki.taskmanager.R
import com.benki.taskmanager.ui.theme.TaskManagerTheme

@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier, navigateToHome: () -> Unit) {
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.task_management_onboarding),
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            16.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.onboarding_title),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.onboarding_description),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                    textAlign = TextAlign.Start
                )
            }

            Button(
                onClick = navigateToHome,
                modifier = modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                    containerColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "Get Started",
                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun OnBoardingPreview() {
    TaskManagerTheme {
        OnBoardingScreen(navigateToHome = {})
    }
}