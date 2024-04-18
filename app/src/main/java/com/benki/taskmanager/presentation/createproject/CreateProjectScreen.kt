package com.benki.taskmanager.presentation.createproject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.benki.taskmanager.presentation.components.PrimaryButton
import com.benki.taskmanager.presentation.components.TaskTextFiled

@Composable
fun CreateProjectScreen(
    modifier: Modifier = Modifier,
    viewmodel: CreateProjectViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit,
    onProjectCreate: () -> Unit,
) {
    val project by viewmodel.project.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = modifier.fillMaxWidth()) {
                IconButton(
                    onClick = onBackButtonClick,
                    modifier = modifier
                        .align(Alignment.TopStart)
                        .size(40.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
                }
                Text(
                    text = "Create Project",
                    modifier = modifier.align(Alignment.TopCenter),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 40.sp
                )
            }
            Spacer(modifier = modifier.height(80.dp))
            TaskTextFiled(
                value = project.name,
                title = "Project name",
                onValueChange = viewmodel::updateProjectName,
                placeholder = "Type here..."
            )
            Spacer(modifier = modifier.height(80.dp))
            TaskTextFiled(
                value = project.description,
                title = "Project description",
                onValueChange = viewmodel::updateProjectDescription,
                placeholder = "Type here...",
                modifier = modifier.heightIn(min = 150.dp, max = 250.dp)
            )
        }

        PrimaryButton(text = "Create Project", enabled = project.name.isNotEmpty()) {
            viewmodel.insertProject()
            onProjectCreate()
        }
    }
}