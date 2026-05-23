package mn.erdenee.ubquizs.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import mn.erdenee.ubquizs.LocalStore
import mn.erdenee.ubquizs.R
import mn.erdenee.ubquizs.api.RetrofitClient
import mn.erdenee.ubquizs.model.profile.LoginRequest

@Preview(showBackground = true)
@Composable
fun LoginScreen(){
    val painter= painterResource(id=R.drawable.loginbanner)
    val description="Background main banner"
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val localStore = LocalStore(LocalContext.current)

    Box(modifier = Modifier.fillMaxSize().background(Color.White)){
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Card(
                shape=RoundedCornerShape(10.dp),
            ) {
                Box(
                    modifier= Modifier.width(150.dp).background(Color.White).align(Alignment.CenterHorizontally)
                ){
                    Image(painter=painter, contentDescription=description)
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text("UBquiz", style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            ))
            Spacer(modifier = Modifier.height(5.dp))
            Text("Мэдлэгийн хүрээгээ тэлээрэй", style = TextStyle(fontWeight = FontWeight.Medium))
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = username,
                onValueChange = { username = it },
                label = { Text("Нэвтрэх нэр") },
                leadingIcon = { Icon(Icons.Default.AccountBox, contentDescription = "Email Icon") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                label = { Text("Нууц үг") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password Icon") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(30.dp))
            ElevatedButton(
                onClick = {
                    scope.launch {
                        runCatching { RetrofitClient.apiService.login(LoginRequest(username, password)) }
                            .onSuccess {
                                val body=it.body()
                                localStore.saveUserData(body!!.token, body.id as Int)
                            }
                            .onFailure { e -> Log.d("login", "Error: ${e.message}")  }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(0xFF1A24E8),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "НЭВТРЭХ",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
            }

        }
    }
}
