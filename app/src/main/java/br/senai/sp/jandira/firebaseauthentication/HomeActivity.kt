package br.senai.sp.jandira.firebaseauthentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import br.senai.sp.jandira.firebaseauthentication.ui.theme.FirebaseAuthenticationTheme
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseAuthenticationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting3("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting3(name: String) {
    
    val auth = FirebaseAuth.getInstance()
    
    var emailState by remember {
        mutableStateOf(auth.currentUser!!.email)
    }
    
    Column {
        Text(text = "Hello! $emailState")
        Text(text = "Sair",
            color = Color.Red,
        modifier = Modifier.clickable {
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    FirebaseAuthenticationTheme {
        Greeting3("Android")
    }
}