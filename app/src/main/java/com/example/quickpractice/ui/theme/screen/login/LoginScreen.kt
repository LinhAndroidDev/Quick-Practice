package com.example.quickpractice.ui.theme.screen.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.text.font.Font
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quickpractice.R
import com.example.quickpractice.ui.theme.BlueBB
import com.example.quickpractice.ui.theme.component.AnimatedLoading
import com.example.quickpractice.ui.theme.component.dialog.ApiErrorDialog
import com.example.quickpractice.util.UnFocusKeyBoardView
import com.example.quickpractice.util.clickView

@SuppressLint("RememberInComposition")
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val state = viewModel.state.collectAsState().value
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(state) {
        when (state) {
            is LoginState.Loading -> {
                isLoading = true
            }

            is LoginState.Success -> {
                isLoading = false
                viewModel.goToHome(navController)
            }

            is LoginState.Failure -> {
                isLoading = false
                errorMessage = state.message
            }

            is LoginState.Idle -> {
                isLoading = false
            }
        }
    }

    Box {
        UnFocusKeyBoardView {
            Text(
                "Đăng Nhập",
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
                "Chào mừng bạn trở lại, chúng tôi rất nhớ bạn!",
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

            Text(
                "Quên mật khẩu?",
                color = BlueBB,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .padding(top = 15.dp, end = 15.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Button(
                onClick = {
                    viewModel.login(email, password)
                },
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
                Text("Đăng Nhập", color = Color.White, fontSize = 16.sp)
            }

            Text(
                "Tạo tài khoản mới",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .padding(top = 20.dp, start = 50.dp, end = 50.dp)
                    .fillMaxWidth()
                    .clickView {
                        viewModel.goToRegister(navController)
                    },
                textAlign = TextAlign.Center
            )
        }

        AnimatedLoading(isLoading)

        errorMessage?.let { message ->
            ApiErrorDialog(
                errorMessage = message,
                onDismiss = { errorMessage = null }
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = NavController(LocalContext.current))
}