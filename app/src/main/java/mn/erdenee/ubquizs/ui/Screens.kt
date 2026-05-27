package mn.erdenee.ubquizs.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route:String,val icon: ImageVector? = null ) {
    object Home: Screens("Home", Icons.Default.Home)
    object Login: Screens("Login")
    object Category: Screens("Category",Icons.Default.CheckCircle)
    object Leader: Screens("Leader",Icons.Default.AccountCircle)
    object Profile: Screens("Profile",Icons.Default.Person)
    object Loading: Screens("Loading")

    object Quiz : Screens("Quiz/{levelId}") {
        fun createRoute(levelId: Int) = "Quiz/$levelId"
    }
}

val bottomNavItems = listOf(
    Screens.Home,
    Screens.Category,
    Screens.Leader,
    Screens.Profile
)