package com.example.quickpractice.ui.theme.screen.exam.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.quickpractice.ui.theme.Green
import com.example.quickpractice.ui.theme.GreyEF
import com.example.quickpractice.ui.theme.component.FooterView
import com.example.quickpractice.ui.theme.screen.exam.component.ItemQuestionPreview
import com.example.quickpractice.ui.theme.component.ShadowCommon
import com.example.quickpractice.ui.theme.screen.exam.model.QuestionModel

@Composable
fun DialogListQuestion(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit,
    questionsState: List<QuestionModel>,
    onTapQuestion: (Int) -> Unit,
) {
    if (!showDialog) return
    Dialog(
        onDismissRequest = { onDismiss.invoke() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.8f)
                .background(Color.White, shape = RoundedCornerShape(8.dp)),
        ) {
            Text(
                "Bảng câu hỏi",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
                textAlign = TextAlign.Center
            )

            ShadowCommon()

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 15.dp)
            ) {
                itemsIndexed(questionsState) { index, item ->
                    Column {
                        ItemQuestionPreview(item) {
                            onDismiss.invoke()
                            onTapQuestion.invoke(index)
                        }
                        if (index > 0 && index < questionsState.size - 1 && (index + 1) % 3 == 0) {
                            Box(
                                modifier = Modifier
                                    .padding(bottom = 15.dp)
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(GreyEF)
                            )
                        }
                    }
                }
            }

            FooterView(
                leftTitle = "Huỷ",
                rightTitle = "Nộp",
                leftTap = { onDismiss.invoke() },
                rightTap = {
                    onDismiss.invoke()
                    onSubmit.invoke()
                }, padding = 10.dp)
        }
    }
}

@Preview
@Composable
fun DialogListQuestionPreview() {
    DialogListQuestion(
        showDialog = true,
        onDismiss = {},
        onSubmit = {},
        questionsState = listOf(),
        onTapQuestion = {}
    )
}