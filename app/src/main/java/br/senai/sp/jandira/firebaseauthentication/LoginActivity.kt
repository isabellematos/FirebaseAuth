package br.senai.sp.jandira.firebaseauthentication

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.senai.sp.jandira.firebaseauthentication.ui.theme.FirebaseAuthenticationTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }


        Log.i("ds3m", if (auth.currentUser == null) "Nao tem usuario" else "")

        setContent {
            FirebaseAuthenticationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting2("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    val context = LocalContext.current

    var emailState by remember {
        mutableStateOf("")
    }

    var passwordState by remember {
        mutableStateOf("")
    }


    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "LOGIN",
        modifier = Modifier.fillMaxWidth(),)
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
            authenticate(emailState,passwordState, context)
        }, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "ENTRAR")

        }
    }
}

fun authenticate(email: String, password: String, context: Context) {

    //criar instancia no firebaseAuth
    val auth = FirebaseAuth.getInstance()

    //Autenticacao
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            Log.i("ds3m", "${it.isSuccessful}")
            val intent = Intent(context, HomeActivity::class.java )
            context.startActivity(intent)
        }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    FirebaseAuthenticationTheme {
        Greeting2("Android")
    }
}

fun accountLogin(email: String, password: String){

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