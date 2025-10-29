package com.example.quickpractice.ui.theme.screen.exam

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quickpractice.ui.theme.Blue
import com.example.quickpractice.ui.theme.Green
import com.example.quickpractice.ui.theme.Grey
import com.example.quickpractice.ui.theme.screen.exam.model.Choice
import com.example.quickpractice.ui.theme.screen.exam.model.QuestionModel
import com.example.quickpractice.util.clickView

@Composable
fun ExamScreen(navController: NavController, viewModel: ExamViewModel = hiltViewModel()) {
    val questions = viewModel.questions.collectAsState().value ?: listOf()

    LaunchedEffect(Unit) {
        viewModel.getArgument(navController)
    }

    Column {
        HeaderExam(navController = navController)

        LazyColumn {
            items(questions) { question ->
                ItemQuestion(question)
            }
        }
    }
}

@Composable
private fun HeaderExam(navController: NavController) {
    Box(
        modifier = Modifier
            .background(color = Color.White)
            .padding(vertical = 20.dp)
    ) {
        Icon(
            Icons.Filled.KeyboardArrowLeft, contentDescription = "Back",
            modifier = Modifier
                .size(40.dp)
                .clickView {
                    navController.popBackStack()
                }
                .padding(start = 10.dp, end = 15.dp),
        )

        Text(
            text = "Câu 1 / 10",
            fontSize = 18.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ItemQuestion(question: QuestionModel) {
    var expanded by remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }
    var choices by remember { mutableStateOf(arrayListOf(false, false, false, false)) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false,
                ambientColor = Grey.copy(alpha = 0.4f),
                spotColor = Grey.copy(alpha = 0.4f)
            )
            .indication(interactionSource, rememberRipple())
            .clickable(interactionSource = interactionSource, indication = null) {
                expanded = !expanded
            },
        colors = CardDefaults.cardColors(containerColor = Blue)
    ) {
        Column {
            Row(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier
                    .weight(1f)
                    .padding(end = 30.dp)) {
                    Text(
                        text = "${question.id}.",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = question.content ?: "",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                    )
                }
                Icon(
                    Icons.Filled.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .rotate(if (expanded) 0f else 180f),
                    tint = Color.White
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                        .background(color = Color.White)
                        .padding(8.dp)
                ) {
                    val items = listOf(
                        Choice.A to question.optionA,
                        Choice.B to question.optionB,
                        Choice.C to question.optionC,
                        Choice.D to question.optionD,
                    )

                    items.forEach { (choice, text) ->
                        ItemChoice(choice.title, text, choices[choice.value]) {
                            choices = List(choices.size) { index ->
                                index == choice.value
                            }.toCollection(ArrayList())
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemChoice(
    typeChoice: String,
    content: String,
    selected: Boolean = false,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .clickable(interactionSource = interactionSource, indication = null) {
                onClick()
            }
            .indication(interactionSource, rememberRipple()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = { onClick() },
            colors = RadioButtonDefaults.colors(
                selectedColor = Green,
                unselectedColor = Grey
            )
        )

        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500
                    )
                ) {
                    append("$typeChoice. ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                ) {
                    append(content)
                }
            }
        )
    }
}

@Preview
@Composable
fun ExamScreenPreview() {
    ExamScreen(navController = NavController(LocalContext.current))
}