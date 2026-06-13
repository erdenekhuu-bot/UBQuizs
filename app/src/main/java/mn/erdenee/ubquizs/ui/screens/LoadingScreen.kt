package mn.erdenee.ubquizs.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import mn.erdenee.ubquizs.R
import mn.erdenee.ubquizs.api.RetrofitClient
import mn.erdenee.ubquizs.data.AppData
import mn.erdenee.ubquizs.ui.Screens

@Composable
fun LoadingScreen(navController: NavController) {
    var progress by remember { mutableStateOf(0f) }
    val context = LocalContext.current
    val db = AppData.getDatabase(context)

    LaunchedEffect(Unit) {
        while (progress < 1f) {
            progress += 0.1f
            delay(50)
        }

        navController.navigate(Screens.Login.route) {
            popUpTo(0) { inclusive = true }
        }
//        runCatching {
//            RetrofitClient.apiService(context).getCategory(1, 20)
//        }.onSuccess { response ->
//            if (response.isSuccessful) {
////                val data = response.body()?.results ?: emptyList()
////                if (data.isNotEmpty()) {
////                    val entities = data.map { model ->
////                        CategoryEntity(
////                            id = model.id.toInt(),
////                            name = model.name,
////                            created = model.created
////                        )
////                    }
////                    db.categoryDao().insertCategory(entities)
////                }
//                while (progress < 1f) {
//                    progress += 0.1f
//                    delay(50)
//                }
//
//                navController.navigate(Screens.Login.route) {
//                    popUpTo(0) { inclusive = true }
//                }
//            } else {
//                Toast.makeText(context, response?.message().toString(), Toast.LENGTH_SHORT).show()
//            }
//        }.onFailure { e ->
//            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
//        }
    }

    LoadingUI(progress)
}

@Composable
fun LoadingUI(progress: Float){
    val painter = painterResource(id = R.drawable.banner)
    val description = "Background main banner"
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(4.dp) // Flutter-ийн shadow
            ) {
                Box(modifier = Modifier.background(Color.White)) {
                    Image(painter = painter, contentDescription = description)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("Уншиж байна... ${(progress * 100).toInt()}%")
            Spacer(modifier = Modifier.height(10.dp))

            // Loading Bar (Flutter-ийн LinearProgressIndicator шиг)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.LightGray)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress) // Хувиар өргөнийг нь тохируулна
                        .fillMaxHeight()
                        .background(Color.Blue)
                )
            }
        }
    }
}
