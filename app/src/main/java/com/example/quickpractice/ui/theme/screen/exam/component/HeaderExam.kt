import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quickpractice.R
import com.example.quickpractice.ui.theme.Orange
import com.example.quickpractice.ui.theme.Red
import com.example.quickpractice.ui.theme.screen.exam.component.dialog.DialogConfirmSave
import com.example.quickpractice.ui.theme.screen.exam.component.dialog.DialogListQuestion
import com.example.quickpractice.ui.theme.screen.exam.model.ExamType
import com.example.quickpractice.ui.theme.screen.exam.model.QuestionModel
import com.example.quickpractice.util.clickView
import com.example.quickpractice.util.getTimerFormat
import kotlinx.coroutines.delay

@Composable
fun HeaderExam(
    navController: NavController,
    examType: ExamType,
    pageState: PagerState,
    duration: Int,
    questionsState: List<QuestionModel>,
    onFinished: () -> Unit = {},
    onSubmit: () -> Unit = {},
    onTabQuestion: (Int) -> Unit = {},
) {
    var timeLeft by remember { mutableIntStateOf(0) }
    var showDialogConfirmSave by remember { mutableStateOf(false) }
    var showDialogListQuestionPreview by remember { mutableStateOf(false) }
    val currentPage = if (pageState.currentPage == pageState.pageCount - 1) {
        ""
    } else {
        "Trang ${pageState.currentPage + 1}"
    }

    LaunchedEffect(duration) {
        timeLeft = duration
    }

    LaunchedEffect(key1 = timeLeft) {
        if (examType == ExamType.HISTORY) return@LaunchedEffect
        if (timeLeft > 0) {
            delay(1000)
            timeLeft--
        } else {
            onFinished()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(vertical = 20.dp),
        verticalAlignment = CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_save), contentDescription = "Save",
            modifier = Modifier
                .padding(start = 10.dp, end = 15.dp)
                .size(25.dp)
                .clickView {
                    showDialogConfirmSave = true
                },
            tint = Color.Black
        )

        Text(
            text = currentPage,
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier
                .padding(start = 20.dp)
                .weight(1f),
            textAlign = TextAlign.Center
        )

        if (examType == ExamType.PRACTICE) {
            Row(
                verticalAlignment = CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_timer), contentDescription = "list",
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .size(20.dp),
                    tint = Orange
                )

                Text(
                    getTimerFormat(timeLeft),
                    color = Red,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }

        Icon(
            painter = painterResource(R.drawable.ic_setting), contentDescription = "list",
            modifier = Modifier
                .padding(start = 20.dp)
                .size(25.dp),
            tint = Color.Black
        )

        Icon(
            painter = painterResource(R.drawable.ic_list), contentDescription = "list",
            modifier = Modifier
                .padding(start = 20.dp, end = 10.dp)
                .size(25.dp)
                .clickView {
                    showDialogListQuestionPreview = true
                },
            tint = Color.Black
        )
    }

    DialogConfirmSave(
        showDialog = showDialogConfirmSave,
        onDismiss = {
            showDialogConfirmSave = false
        },
        onConfirm = {
            navController.popBackStack()
        }
    )

    DialogListQuestion(
        showDialog = showDialogListQuestionPreview,
        onDismiss = {
            showDialogListQuestionPreview = false
        },
        onSubmit = { onSubmit.invoke() },
        questionsState,
        onTapQuestion = { index -> onTabQuestion.invoke(index) })
}