package mn.erdenee.ubquizs.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mn.erdenee.ubquizs.data.AppData

@Composable
fun CategoryScreen(navController: NavController){
    val context = LocalContext.current
    val db = AppData.getDatabase(context)
    val categories by db.categoryDao().getAllCategories().collectAsState(initial = emptyList())
    LazyColumn {
        items(categories) { category ->
            Text(text = "Нэр: ${category.name}", modifier = Modifier.padding(8.dp))
        }
    }
}