package mn.erdenee.ubquizs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.FontScaling
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mn.erdenee.ubquizs.ui.QuizViewModel
import mn.erdenee.ubquizs.ui.Screens
import mn.erdenee.ubquizs.ui.bottomNavItems
import mn.erdenee.ubquizs.ui.screens.AnswerScreen
import mn.erdenee.ubquizs.ui.screens.CategoryScreen
import mn.erdenee.ubquizs.ui.screens.GameCompleteScreen
import mn.erdenee.ubquizs.ui.screens.HomeScreen
import mn.erdenee.ubquizs.ui.screens.LeaderScreen
import mn.erdenee.ubquizs.ui.screens.LevelCompleteScreen
import mn.erdenee.ubquizs.ui.screens.LoadingScreen
import mn.erdenee.ubquizs.ui.screens.LoginScreen
import mn.erdenee.ubquizs.ui.screens.ProfileScreen
import mn.erdenee.ubquizs.ui.screens.QuizScreen
import mn.erdenee.ubquizs.ui.theme.UBQuizsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            UBQuizsTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    QuizApp(modifier = Modifier.padding(innerPadding))
//                }
//            }
            QuizApp(modifier = Modifier.padding(10.dp).background(Color.White))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun QuizApp(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val navController= rememberNavController()


    Scaffold(modifier= Modifier.fillMaxSize().background(Color.White),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Асуулт хариулт",
                        style = TextStyle(
                            color = Color(0xff2879e0),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White){
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = { navController.navigate(screen.route) },
                        label = { Text(screen.route) },
                        icon = {
                            Icon(
                                imageVector = screen.icon ?: Icons.Default.Star,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }) {
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Loading.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screens.Login.route) {
                LoginScreen(navController = navController)
            }
            composable(route = Screens.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(route= Screens.Profile.route) {
                ProfileScreen(navController=navController)
            }
            composable(route = Screens.Category.route) {
                CategoryScreen(navController = navController)
            }
            composable(route = Screens.Leader.route) {
                LeaderScreen(navController = navController)
            }
            composable(route= Screens.Loading.route) {
                LoadingScreen(navController = navController)
            }
            composable(route= Screens.Answer.route) {
                AnswerScreen(navController=navController)
            }
        }
    }

//    Box(modifier = modifier.fillMaxSize()) {
//        when {
//            uiState.isGameComplete -> {
//                GameCompleteScreen(
//                    uiState = uiState,
//                    onRestart = { viewModel.restartGame() }
//                )
//            }
//            uiState.isLevelComplete -> {
//                LevelCompleteScreen(
//                    uiState = uiState,
//                    onNextLevel = { viewModel.nextLevel() }
//                )
//            }
//            else -> {
//                QuizScreen(
//                    uiState = uiState,
//                    onAnswerSelected = { viewModel.selectAnswer(it) },
//                    onCheckAnswer = { viewModel.checkAnswer() },
//                    onNext = { viewModel.nextQuestionOrLevel() }
//                )
//            }
//        }
//    }
}