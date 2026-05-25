package mn.erdenee.ubquizs.ui.screens

import android.graphics.fonts.FontStyle
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mn.erdenee.ubquizs.data.AppData
import mn.erdenee.ubquizs.model.category.CategoryModel
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mn.erdenee.ubquizs.R
import mn.erdenee.ubquizs.api.RetrofitClient


@Composable
fun CategoryScreen(navController: NavController){
    val context = LocalContext.current
    var categoryList by remember { mutableStateOf<List<CategoryModel>>(emptyList()) }
    val scrollState = rememberScrollState()
    val painter = painterResource(id = R.drawable.categoryicon)
    val description = "Category icon"

    LaunchedEffect(Unit){
        runCatching { RetrofitClient.apiService.getCategory(1,10) }
            .onSuccess {
                    response ->
                if(response.isSuccessful){
                    categoryList = response.body()!!.results
                } else {
                    Toast.makeText(context, response?.message().toString(), Toast.LENGTH_SHORT).show()
                }
            }
            .onFailure { e ->
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

//    val db = AppData.getDatabase(context)
//    val categories by db.categoryDao().getAllCategories().collectAsState(initial = emptyList())
//    LazyColumn {
//        items(categories) { category ->
//            Text(text = "Нэр: ${category.name}", modifier = Modifier.padding(8.dp))
//        }
//    }
    Box(modifier = Modifier.fillMaxSize().padding(38.dp)){
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            categoryList.forEach { item ->
                Card(
                    modifier = Modifier.size(100.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(modifier = Modifier.background(Color.White).width(50.dp)) {
                            Image(painter = painter, contentDescription = description)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.name,
                            color = Color(0xff4085de),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}