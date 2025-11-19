package com.example.quickpractice.ui.theme.component.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.quickpractice.R
import com.example.quickpractice.ui.theme.Green

@Composable
fun ApiSuccessDialog(
    successMessage: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .background(Color.White, RoundedCornerShape(20.dp))
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // 🎉 Icon thành công
                Image(
                    painter = painterResource(R.drawable.image_done),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 🎉 Tiêu đề
                Text(
                    text = "Thành công",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Green // xanh lá tươi
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 🎉 Nội dung thông báo
                Text(
                    text = successMessage,
                    color = Color.DarkGray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 🎉 Nút OK (style đẹp)
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green // xanh lá
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("OK", color = Color.White)
                }
            }
        }
    }
}
