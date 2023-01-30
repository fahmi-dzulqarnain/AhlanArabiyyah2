package com.midcores.ahlanarabiyyah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
import com.midcores.ahlanarabiyyah.view_model.RegisterViewModel

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTransparentStatusBar(this)
        setContent {
            AhlanArabiyyahTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = SugarCrystal
                ) {
                    RegisterScreen(this)
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(
    activity: RegisterActivity,
    viewModel: RegisterViewModel = viewModel()
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
        modifier = Modifier
            .padding(top = spacing.veryLarge)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_app),
            contentDescription = "App Logo",
            modifier = Modifier.padding(spacing.large)
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
                viewModel.usernameData = it
            }
        )
        MaterialTextField(
            label = "Email",
            modifier = Modifier.padding(horizontal = spacing.medium),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = "username"
                )
            },
            placeholder = "seseorang@email.com",
            keyboardType = KeyboardType.Email,
            onTextChange = {
                viewModel.emailData = it
            }
        )
        MaterialTextField(
            label = "Nama Lengkap",
            modifier = Modifier.padding(horizontal = spacing.medium),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "full name"
                )
            },
            placeholder = "Isikan Nama Anda",
            onTextChange = {
                viewModel.fullNameData = it
            }
        )
        MaterialTextField(
            label = "Password",
            modifier = Modifier.padding(horizontal = spacing.medium),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_password),
                    contentDescription = "username"
                )
            },
            placeholder = "Minimal 8 Karakter",
            keyboardType = KeyboardType.Password,
            isPasswordField = true,
            onTextChange = {
                viewModel.passwordData = it
            }
        )

        RoundedButton(
            label = "Daftar",
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = spacing.lessLarge,
                    horizontal = spacing.large
                ),
            cornerRadius = spacing.lessLarge,
            height = spacing.veryLarge,
            onClick = {
                isDialogOpen = !viewModel.addAkun()

                if (!isDialogOpen) {
                    activity.finish()
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
                    text = "Sudah Punya Akun?",
                    style = MaterialTheme.typography.body2
                )
                TextButton(onClick = {
                    activity.finish()
                }) {
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}
