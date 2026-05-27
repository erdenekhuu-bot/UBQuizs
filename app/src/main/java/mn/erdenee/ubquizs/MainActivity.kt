package mn.erdenee.ubquizs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mn.erdenee.ubquizs.api.RetrofitClient
import mn.erdenee.ubquizs.factory.QuizViewModelFactory
import mn.erdenee.ubquizs.repository.QuizRepository
import mn.erdenee.ubquizs.ui.Screens
import mn.erdenee.ubquizs.ui.bottomNavItems
import mn.erdenee.ubquizs.ui.screens.CategoryScreen
import mn.erdenee.ubquizs.ui.screens.HomeScreen
import mn.erdenee.ubquizs.ui.screens.LeaderScreen
import mn.erdenee.ubquizs.ui.screens.LoadingScreen
import mn.erdenee.ubquizs.ui.screens.LoginScreen
import mn.erdenee.ubquizs.ui.screens.ProfileScreen
import mn.erdenee.ubquizs.ui.screens.QuizScreen
import mn.erdenee.ubquizs.viewmodel.QuizViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val apiService = RetrofitClient.apiService
        val repository = QuizRepository(apiService)
        setContent {
            QuizApp(repository = repository)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizApp(repository: QuizRepository) {
    val navController= rememberNavController()

    Scaffold(modifier=Modifier.padding(10.dp).background(Color.White).fillMaxSize().background(Color.White),
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
            composable(
                route = Screens.Quiz.route,
                arguments = listOf(navArgument("levelId") { type = NavType.IntType })
            ) { backStackEntry ->
                val levelId = backStackEntry.arguments?.getInt("levelId") ?: 0

                val viewModel: QuizViewModel = viewModel(
                    factory = QuizViewModelFactory(repository)
                )

                QuizScreen(
                    navController = navController,
                    levelId = levelId,
                    viewModel = viewModel
                )
            }

        }
    }

}