package mn.erdenee.ubquizs.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import mn.erdenee.ubquizs.LocalStore
import kotlinx.coroutines.flow.first

@Composable
fun AnswerScreen(navController: NavController){
    val context = LocalContext.current
    val localStore = remember { LocalStore(context) }
    val categoryId by localStore.categoryId.collectAsState(initial = null)

//    LaunchedEffect(categoryId) {
//        Log.d("storeread", "Current categoryId value: $categoryId")
//    }
    Demo()
}


@Preview(showBackground = true)
@Composable
fun Demo(){
    Box(modifier = Modifier.fillMaxSize()){
        Text("Answer screen")
    }
}
