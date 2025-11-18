package com.example.quickpractice.ui.theme.screen.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quickpractice.R
import com.example.quickpractice.ui.theme.BlueBB
import com.example.quickpractice.util.UnFocusKeyBoardView
import com.example.quickpractice.util.clickView

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    UnFocusKeyBoardView {
        Text(
            "Tạo tài khoản",
            color = BlueBB,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.seriftext_regular, FontWeight.Normal)),
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            "Tạo một tài khoản để bạn có thể khám phá tất cả các tính năng hiện có",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier
                .padding(top = 26.dp, start = 30.dp, end = 30.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            placeholder = { Text("Email") },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 20.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            placeholder = { Text("Password") },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 10.dp)
                .fillMaxWidth(),
            visualTransformation = if (isPasswordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),

            trailingIcon = {
                val icon = if (isPasswordVisible)
                    painterResource(R.drawable.ic_visibility)
                else
                    painterResource(R.drawable.ic_visibility_off)

                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(painter = icon, contentDescription = null)
                }
            }
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            placeholder = { Text("Confirm Password") },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 10.dp)
                .fillMaxWidth(),
            visualTransformation = if (isConfirmPasswordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),

            trailingIcon = {
                val icon = if (isConfirmPasswordVisible)
                    painterResource(R.drawable.ic_visibility)
                else
                    painterResource(R.drawable.ic_visibility_off)

                IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                    Icon(painter = icon, contentDescription = null)
                }
            }
        )

        Button(
            onClick = {},
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                .fillMaxWidth(),
            colors = ButtonColors(
                containerColor = BlueBB,
                contentColor = BlueBB,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.LightGray
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(vertical = 15.dp),
        ) {
            Text("Đăng Kí", color = Color.White, fontSize = 16.sp)
        }

        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Đã có tài khoản",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center
            )

            Text(
                "Đăng nhập",
                color = BlueBB,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .padding(start = 3.dp)
                    .clickView {

                    }
            )
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = NavController(LocalContext.current))
}