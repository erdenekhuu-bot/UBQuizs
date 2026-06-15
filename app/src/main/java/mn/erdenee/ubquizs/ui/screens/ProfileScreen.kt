package mn.erdenee.ubquizs.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mn.erdenee.ubquizs.LocalStore
import mn.erdenee.ubquizs.api.RetrofitClient
import mn.erdenee.ubquizs.model.HistoryData
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val localStore = LocalStore(context.applicationContext)
    val username=runBlocking {
        localStore.username.first()
    }
    val profileid=runBlocking { localStore.userId.first() }
    var profileList: HistoryData? by remember { mutableStateOf<HistoryData?>(null) }
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    var profileScore by remember { mutableStateOf("") }
    var allTotal by remember { mutableStateOf("") }

    fun fetchProfileHistory() {
        isRefreshing = true
        scope.launch {
            runCatching { RetrofitClient.apiService(context).getHistory(profileid) }
                .onSuccess { response ->
                    isRefreshing = false
                    if (response.isSuccessful && response.body() != null) {
                        profileList = response.body()!!.data
                        profileScore = profileList?.total_point
                            ?.toString()
                            ?: "0"

                        val totalPoint = profileList?.total_point ?: 0
                        val allPoints = profileList?.all_points ?: 0
                        allTotal = if (allPoints > 0) {
                            "${((totalPoint.toDouble() / allPoints) * 100).toInt()}%"
                        } else {
                            "0%"
                        }
                    } else {
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }
                .onFailure { e ->
                    isRefreshing = false
                    Log.d("errors",e.message.toString())
                    Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }

    LaunchedEffect(Unit) {
        fetchProfileHistory()
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { fetchProfileHistory() },
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFF))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color(0xFFEDF2FF)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp),
                                    tint = Color(0xFF2879E0)
                                )
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .offset(x = 8.dp, y = 4.dp)
                                        .size(24.dp)
                                        .background(Color(0xFF2879E0), CircleShape)
                                        .padding(4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                }
                            }

                            Column {
                                Text(
                                    text = username.toString(),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1A1A1A)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            StatItem(label = "Нийт", value = profileScore)
                            StatItem(label = "Үнэлгээ", value = allTotal)
                        }
                    }
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        profileList?.history?.forEach { item ->
                            val percentage = if (item.level_total > 0) {
                                (item.question_point.toDouble() / item.level_total.toDouble()) * 100
                            } else {
                                0.0
                            }
                            ScoreItem(
                                title = item.category_name,
                                level = item.level_name,
                                score = "${percentage.toInt()}%",
                                badge = when {
                                    percentage >= 90 -> "Мундаг"
                                    percentage >= 60 -> "Ер нь давгүй"
                                    else -> "Муу байна"
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(text = label, fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A1A))
    }
}
@Composable
fun ScoreItem(title: String, level:String,score: String, badge: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(level, fontSize = 12.sp, color = Color.Gray)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(score, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF2879E0))
                Text(
                    text = badge,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = if(badge == "PERFECT") Color(0xFF00C853) else Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}