package ru.hse.connecteam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.hse.connecteam.route.LoginNavHost
import ru.hse.connecteam.route.MainNavHost
import ru.hse.connecteam.shared.models.UserAuthState
import ru.hse.connecteam.shared.services.datastore.AuthenticationService
import ru.hse.connecteam.ui.components.animated.LoadingAnimation
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.OutlinedButtonLabel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authenticationService: AuthenticationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConnecteamTheme(darkTheme = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    var userAuthState by mutableStateOf(UserAuthState.UNKNOWN)

                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            authenticationService.isAuthenticated().collect {
                                userAuthState = it
                            }
                        }
                    }
                    when (userAuthState) {
                        UserAuthState.UNAUTHENTICATED -> {
                            LoginNavHost(
                                navController = navController
                            )
                        }

                        UserAuthState.AUTHENTICATED -> {
                            MainNavHost(
                                navController = navController
                            )
                        }

                        UserAuthState.UNKNOWN -> {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Box(modifier = Modifier.align(Alignment.Center)) {
                                    LoadingAnimation()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = "$name!",
            textAlign = TextAlign.Center,
            style = OutlinedButtonLabel.copy(fontSize = 35.sp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConnecteamTheme {
        Greeting("Android")
    }
}