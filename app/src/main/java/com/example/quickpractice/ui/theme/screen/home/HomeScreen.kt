package com.example.quickpractice.ui.theme.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quickpractice.R
import com.example.quickpractice.ui.theme.screen.home.component.ItemSubject
import com.example.quickpractice.util.clickView

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val subjects = viewModel.subjects.collectAsState().value ?: listOf()

    LaunchedEffect(Unit) {
        viewModel.fetchSubjects()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Box {
            Text(
                text = "Thi Trắc Nghiệm",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                textAlign = TextAlign.Center
            )

            Icon(
                painter = painterResource(R.drawable.ic_history), contentDescription = "list",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(25.dp)
                    .clickView {
                        viewModel.goToExamHistory(navController)
                    }
                    .padding(end = 15.dp)
                    .align(alignment = Alignment.CenterEnd),
                tint = Color.Black
            )
        }

        Text(
            text = "Chủ đề", color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.W500,
            modifier = Modifier
                .padding(start = 15.dp, top = 8.dp),
            textAlign = TextAlign.Start
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 👉 Mỗi hàng có 2 item
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(subjects) { item ->
                ItemSubject(item) {
                    viewModel.goToExamList(navController, item)
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = NavController(LocalContext.current))
}

