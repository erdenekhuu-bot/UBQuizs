package mn.erdenee.ubquizs.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import mn.erdenee.ubquizs.LocalStore
import mn.erdenee.ubquizs.R
import mn.erdenee.ubquizs.api.RetrofitClient
import mn.erdenee.ubquizs.model.CategoryModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(){
    //    val db = AppData.getDatabase(context)
//    val categories by db.categoryDao().getAllCategories().collectAsState(initial = emptyList())
//    LazyColumn {
//        items(categories) { category ->
//            Text(text = "Нэр: ${category.name}", modifier = Modifier.padding(8.dp))
//        }
//    }

    val context = LocalContext.current
    var categoryList by remember { mutableStateOf<List<CategoryModel>>(emptyList()) }
    val painter = painterResource(id = R.drawable.categoryicon)
    val description = "Category icon"
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    fun fetchCategory(){
        isRefreshing = true
        scope.launch {
            runCatching { RetrofitClient.apiService(context).getCategory(1,10) }
                .onSuccess { response ->
                    isRefreshing = false
                    if(response.isSuccessful){
                        categoryList = response.body()!!.results
                    } else {
                        Toast.makeText(context, response?.message().toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                .onFailure { e ->
                    isRefreshing = false
                    Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }

    LaunchedEffect(Unit){
        fetchCategory()
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { fetchCategory() },
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categoryList) { category ->
                Card(
                    modifier = Modifier.size(100.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(1.dp),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier.background(Color.White).size(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(painter = painter, contentDescription = description, modifier = Modifier.fillMaxSize())
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = category.name,
                            color = Color(0xff4085de),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}
