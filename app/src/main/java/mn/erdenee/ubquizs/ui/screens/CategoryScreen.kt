package mn.erdenee.ubquizs.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import androidx.navigation.NavController
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import mn.erdenee.ubquizs.R
import mn.erdenee.ubquizs.api.RetrofitClient
import mn.erdenee.ubquizs.model.CategoryModel

//@Composable
//fun CategoryScreen(navController: NavController){
//    val context = LocalContext.current
//    var categoryList by remember { mutableStateOf<List<CategoryModel>>(emptyList()) }
//    val scrollState = rememberScrollState()
//    val painter = painterResource(id = R.drawable.categoryicon)
//    val description = "Category icon"
//
//    LaunchedEffect(Unit){
//        runCatching { RetrofitClient.apiService.getCategory(1,10) }
//            .onSuccess {
//                    response ->
//                if(response.isSuccessful){
//                    categoryList = response.body()!!.results
//                } else {
//                    Toast.makeText(context, response?.message().toString(), Toast.LENGTH_SHORT).show()
//                }
//            }
//            .onFailure { e ->
//                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
//            }
//    }
//
////    val db = AppData.getDatabase(context)
////    val categories by db.categoryDao().getAllCategories().collectAsState(initial = emptyList())
////    LazyColumn {
////        items(categories) { category ->
////            Text(text = "Нэр: ${category.name}", modifier = Modifier.padding(8.dp))
////        }
////    }
//    Box(modifier = Modifier.fillMaxSize().padding(38.dp)){
//        FlowRow(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            categoryList.forEach { item ->
//                Card(
//                    modifier = Modifier.size(100.dp),
//                    shape = RoundedCornerShape(10.dp),
//                    elevation = CardDefaults.cardElevation(1.dp)
//                ) {
//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Box(modifier = Modifier.background(Color.White).width(50.dp)) {
//                            Image(painter = painter, contentDescription = description)
//                        }
//                        Spacer(modifier = Modifier.height(4.dp))
//                        Text(
//                            text = item.name,
//                            color = Color(0xff4085de),
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Bold,
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
@Serializable
data class Category(
    val id:Int,
    val name: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Demo(){
    val painter = painterResource(id = R.drawable.categoryicon)
    val description = "Category icon"
    val demoData=listOf(
        Category(1,"Geography"),
        Category(2,"History"),
        Category(3,"Mathematic"),
        Category(4,"Physic"),
        Category(5,"Chemistry"),
        Category(6,"Biology"),
        Category(7,"Geography"),
        Category(8,"History"),
        Category(9,"Mathematic"),
        Category(10,"Physic"),
        Category(11,"Chemistry"),
        Category(12,"Biology"),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ангилалууд",
                        color = Color(0xff2879e0),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.TopCenter){
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                demoData.forEach {
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
                                text = it.name,
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
}