package br.senai.sp.jandira.firebaseauthentication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.senai.sp.jandira.firebaseauthentication.ui.theme.FirebaseAuthenticationTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseAuthenticationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    var emailState by remember {
        mutableStateOf("")
    }

    var passwordState by remember {
        mutableStateOf("")
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(48.dp)) {
        OutlinedTextField(value = emailState,
            onValueChange = {emailState = it},
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "email" )
            } )

        OutlinedTextField(value = passwordState,
            onValueChange = {passwordState = it},
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "password" )
            } )
        Button(onClick = {
            accountCreate(email = emailState, password = passwordState)
        }, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Criar usuario")
            
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FirebaseAuthenticationTheme {
        Greeting("Android")
    }
}

fun accountCreate(email: String, password: String){

    //obter uma instancia do firebase auth
    val auth = FirebaseAuth.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener {
            Log.i("ds3m", "${it.user!!.uid}")
        }
        .addOnFailureListener {error ->

            try {
                throw error
            }catch (e: FirebaseAuthUserCollisionException){
                Log.i("ds3m", "essa conta ja existe")
                Log.i("ds3m", "${e.message}")
            }catch (e: FirebaseAuthWeakPasswordException){
                Log.i("ds3m", "essa senha esta fraca")
                Log.i("ds3m", "${e.message}")
            }catch (e: FirebaseAuthInvalidUserException){
                Log.i("ds3m", "esse usuario e invalido")
                Log.i("ds3m", "${e.message}")
            }

        }
}