package mn.erdenee.ubquizs.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mn.erdenee.ubquizs.R

@Preview(showBackground = true)
@Composable
fun LoginScreen(){
    val painter= painterResource(id=R.drawable.loginbanner)
    val description="Background main banner"
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Card(
                modifier= Modifier.background(Color.White),
                shape=RoundedCornerShape(10.dp),
            ) {
                Box(
                    modifier= Modifier.width(80.dp).background(Color.White).align(Alignment.CenterHorizontally)
                ){
                    Image(painter=painter, contentDescription=description)
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text("QuizGenius", style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            ))
            Spacer(modifier = Modifier.height(10.dp))
            Text("Welcome back", style = TextStyle(fontWeight = FontWeight.Bold))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("И-мэйл хаяг") },
                placeholder = { Text("example@mail.com") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email Icon") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Нууц үг") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password Icon") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,       // Vertically center
//            horizontalAlignment = Alignment.CenterHorizontally // Horizontally center
//        ) {
//            Text("Item 1")
//            Text("Item 2")
//        }
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween, // Pushes to opposite ends
//            verticalAlignment = Alignment.CenterVertically    // Vertically center inside row
//        ) {
//            Text("Left Side")
//            Text("Right Side")
//        }
    }
}

@Composable
fun loginbannerimage(
    painter: Painter,
    contentDescription: String?,
    title:String,
    modifier: Modifier = Modifier
){
    Card(
        modifier=modifier.fillMaxWidth(),
        shape=RoundedCornerShape(16.dp),
    ) {
        Box(
            modifier= Modifier.height(200.dp).background(Brush.verticalGradient(listOf(Color.White, Color.LightGray), startY = 300f))
        ){
            Image(painter=painter, contentDescription=contentDescription, contentScale = ContentScale.Crop)
        }
    }
}