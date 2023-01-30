package com.midcores.ahlanarabiyyah

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.midcores.ahlanarabiyyah.helper.StatusBarHelper.setTransparentStatusBar
import com.midcores.ahlanarabiyyah.ui.component.CustomDialog
import com.midcores.ahlanarabiyyah.ui.component.MaterialTextField
import com.midcores.ahlanarabiyyah.ui.component.RoundedButton
import com.midcores.ahlanarabiyyah.ui.theme.AhlanArabiyyahTheme
import com.midcores.ahlanarabiyyah.ui.theme.SugarCrystal
import com.midcores.ahlanarabiyyah.ui.theme.spacing
import com.midcores.ahlanarabiyyah.view_model.LoginViewModel

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTransparentStatusBar(this)

        val sharedPref = "sharedPreferences"
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(sharedPref, Context.MODE_PRIVATE)

        setContent {
            AhlanArabiyyahTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = SugarCrystal
                ) {
                    val viewModel = LoginViewModel(sharedPreferences)

                    if (viewModel.checkIfLoggedIn()) {
                        startActivity(
                            Intent(this, JourneyActivity::class.java)
                        )
                    }

                    LoginScreen(this, viewModel)
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    context: Context,
    viewModel: LoginViewModel = viewModel()
) {
    var isDialogOpen by remember {
        mutableStateOf(false)
    }

    CustomDialog(
        title = viewModel.dialogTitle,
        description = viewModel.dialogDescription,
        isOpen = isDialogOpen,
        onDismiss = {
            isDialogOpen = false
        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = spacing.extraLarge)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_app),
            contentDescription = "App Logo",
            modifier = Modifier.padding(spacing.extraLarge)
        )

        MaterialTextField(
            label = "Username",
            modifier = Modifier.padding(horizontal = spacing.medium),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "username"
                )
            },
            placeholder = "Isi Username Anda",
            onTextChange = {
                viewModel.username = it
            }
        )
        MaterialTextField(
            label = "Password",
            modifier = Modifier.padding(horizontal = spacing.medium),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_password),
                    contentDescription = "password"
                )
            },
            placeholder = "Isi Kata Sandi Anda",
            keyboardType = KeyboardType.Password,
            isPasswordField = true,
            onTextChange = {
                viewModel.password = it
            }
        )

        RoundedButton(
            label = "Login",
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = spacing.lessLarge,
                    horizontal = spacing.large
                ),
            cornerRadius = spacing.lessLarge,
            height = spacing.veryLarge,
            onClick = {
                val isAuthenticated = viewModel.authenticateLogin()

                if (isAuthenticated) {
                    context.startActivity(
                        Intent(context, JourneyActivity::class.java)
                    )
                } else {
                    isDialogOpen = true
                }
            }
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(horizontal = spacing.lessLarge)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Belum Punya Akun?",
                    style = typography.body2
                )
                TextButton(onClick = {
                    context.startActivity(
                        Intent(context, RegisterActivity::class.java)
                    )
                }) {
                    Text(
                        text = "Daftar",
                        style = typography.body2
                    )
                }
            }
        }
    }
}
