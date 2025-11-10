package com.example.quickpractice.ui.theme.screen.exam.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.quickpractice.ui.theme.screen.exam.component.ItemQuestionPreview
import com.example.quickpractice.ui.theme.screen.exam.component.ShadowCommon

@Composable
fun DialogListQuestion(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit,
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
                items(20) {
                    ItemQuestionPreview()
                }
            }

            FooterView(dismiss = { onDismiss.invoke() }, submit = {
                onDismiss.invoke()
                onSubmit.invoke()
            })
        }
    }
}

@Composable
private fun FooterView(dismiss: () -> Unit = {}, submit: () -> Unit = {}) {
    val is1 = remember { MutableInteractionSource() }
    val is2 = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White, shape = RoundedCornerShape(
                    bottomStart = 8.dp,
                    bottomEnd = 8.dp
                )
            )
            .padding(10.dp)
    ) {
        Text(
            "Huỷ",
            color = Green,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(end = 10.dp)
                .indication(is1, rememberRipple())
                .weight(1f)
                .clickable(
                    interactionSource = is1,
                    indication = null
                ) {
                    dismiss.invoke()
                }
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .border(width = 1.dp, color = Green, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 10.dp),
            textAlign = TextAlign.Center,
        )

        Text(
            "Nộp ",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .indication(is2, rememberRipple())
                .weight(1f)
                .clickable(
                    interactionSource = is2,
                    indication = null
                ) {
                    submit.invoke()
                }
                .background(Green, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 10.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun DialogListQuestionPreview() {
    DialogListQuestion(
        showDialog = true,
        onDismiss = {},
        onSubmit = {}
    )
}