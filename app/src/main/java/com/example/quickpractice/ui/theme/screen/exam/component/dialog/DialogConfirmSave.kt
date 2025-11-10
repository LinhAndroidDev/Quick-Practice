package com.example.quickpractice.ui.theme.screen.exam.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.example.quickpractice.ui.theme.Red
import com.example.quickpractice.util.clickView

@Composable
fun DialogConfirmSave(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {

    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss.invoke() },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .background(Color.White, shape = RoundedCornerShape(18.dp)),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Bạn có muốn lưu lại bài làm và thoát không?",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(vertical = 18.dp, horizontal = 15.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(GreyEF)
                    ) { }

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)) {
                        Text("Huỷ",
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W500,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(color = Red)
                                .clickView {
                                    onDismiss.invoke()
                                }
                                .fillMaxHeight()
                                .weight(1f)
                                .padding(vertical = 12.dp)
                        )

                        Box(modifier = Modifier
                            .width(1.dp)
                            .fillMaxHeight()
                            .background(GreyEF)) { }

                        Text("Lưu",
                            color = Green,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W500,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(color = Red)
                                .clickView {
                                    onDismiss.invoke()
                                    onConfirm.invoke()
                                }
                                .fillMaxHeight()
                                .weight(1f)
                                .padding(vertical = 12.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DialogConfirmSavePreview() {
    DialogConfirmSave(showDialog = true, onDismiss = {}, onConfirm = {})
}