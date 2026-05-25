package mn.erdenee.ubquizs.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import mn.erdenee.ubquizs.LocalStore
import mn.erdenee.ubquizs.api.RetrofitClient
import mn.erdenee.ubquizs.model.level.LevelModel
import mn.erdenee.ubquizs.ui.Screens


@Composable
fun HomeScreen(navController: NavController){
    val context = LocalContext.current
    var levelList by remember { mutableStateOf<List<LevelModel>>(emptyList()) }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        runCatching {
            RetrofitClient.apiService.getLevel(1)
        }.onSuccess { response ->
            if(response.isSuccessful){
                levelList = response.body()!!.results

            } else {
                Toast.makeText(context, response?.message().toString(), Toast.LENGTH_SHORT).show()
            }
        }.onFailure { e ->
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp).verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            levelList.forEach { level ->
                val earned = level.user_earned.toDouble()
                val required = level.level_required_total.toDouble()
                val percentage = if (required > 0) ((earned / required) * 100).toInt() else 0
                val starCount = when {
                    percentage >= 100 -> 3
                    percentage >= 75 -> 2
                    percentage >= 50 -> 1
                    else -> 0
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    onClick = {
                       scope.launch {
                           LocalStore(context).saveCategory(level.level_id.toInt())
                           navController.navigate(Screens.Answer.route) {
                               popUpTo(0) { inclusive = true }
                           }
                       }
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            for (i in 1..3) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Star",
                                    tint = if (i <= starCount) Color(0xFFFFB300) else Color(0xFFC5CAE9),
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = level.level_name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A1A2E)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        val isComplete = level.level_passed == 1 || percentage >= 100
                        val badgeBgColor = if (isComplete) Color(0xFFE8F5E9) else Color(0xFFE8EAF6)
                        val badgeTextColor = if (isComplete) Color(0xFF2E7D32) else Color(0xFF3F51B5)
                        val badgeText = if (isComplete) "100%" else "$percentage%"

                        Box(
                            modifier = Modifier
                                .background(color = badgeBgColor, shape = RoundedCornerShape(50.dp))
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = badgeText,
                                color = badgeTextColor,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

        }
    }
}