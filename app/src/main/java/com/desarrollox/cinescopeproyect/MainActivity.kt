package com.desarrollox.cinescopeproyect

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.desarrollox.cinescopeproyect.ui.theme.CineScopeProyectTheme
import com.desarrollox.cinescopeproyect.ui.viewmodel.AuthViewModel

private val BgDark      = Color(0xFF120A0A)
private val FieldBg     = Color(0xFF1E1414)
private val FieldBorder = Color(0xFF2E2020)
private val RedMain     = Color(0xFFE53935)
private val RedDark     = Color(0xFFB71C1C)
private val TextWhite   = Color(0xFFF5F5F5)
private val TextGray    = Color(0xFF9E8E8E)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineScopeProyectTheme {
                val viewModel: AuthViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                
                LaunchedEffect(uiState.loginSuccess) {
                    if (uiState.loginSuccess) {
                        viewModel.resetLoginSuccess()
                        startActivity(Intent(this, DashboardActivity::class.java))
                        finish()
                    }
                }
                
                MainLoginScreen(
                    isLoading = uiState.isLoading,
                    error = uiState.error,
                    onLogin = { email, password -> viewModel.login(email, password) },
                    onGoToRegister = {
                        startActivity(Intent(this, RegisterActivity::class.java))
                    },
                    onClearError = { viewModel.clearError() }
                )
            }
        }
    }
}

@Composable
fun MainLoginScreen(
    isLoading: Boolean = false,
    error: String? = null,
    onLogin: (String, String) -> Unit = { _, _ -> },
    onGoToRegister: () -> Unit = {},
    onClearError: () -> Unit = {}
) {
    var email           by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberDevice  by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(BgDark)) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Box(
                    modifier = Modifier.size(32.dp).background(RedMain, RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center
                ) { Text("🎬", fontSize = 16.sp) }
                Spacer(Modifier.width(10.dp))
                Text("CineScope", color = TextWhite, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold, fontFamily = FontFamily.SansSerif)
            }

            Box(modifier = Modifier.fillMaxWidth().height(220.dp)) {
                Box(modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(listOf(
                        Color(0xFF1A0505), Color(0xFF3D0A0A),
                        Color(0xFF6B1010), Color(0xFF4A0D0D), Color(0xFF1A0505)
                    ))
                ))
                Box(modifier = Modifier.fillMaxSize().background(
                    Brush.radialGradient(
                        listOf(Color(0x99FFFFFF), Color(0x44AAAAAA), Color(0x00000000)),
                        radius = 220f
                    )
                ))
                Column(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()) {
                    repeat(4) { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = (row * 8).dp).padding(bottom = 4.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            repeat(10 - row) {
                                Box(modifier = Modifier.width(24.dp).height(14.dp).background(
                                    Color(0xFF8B0000).copy(alpha = 0.7f + row * 0.1f),
                                    RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                ))
                            }
                        }
                    }
                }
                Box(modifier = Modifier.fillMaxWidth().height(80.dp).align(Alignment.BottomCenter)
                    .background(Brush.verticalGradient(listOf(Color.Transparent, BgDark))))
                Column(modifier = Modifier.align(Alignment.BottomStart).padding(start = 24.dp, bottom = 20.dp)) {
                    Text("Welcome Back", color = TextWhite, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Text("Ready for your next premiere?", color = TextGray, fontSize = 13.sp)
                }
            }

            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(top = 8.dp)) {

                if (error != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = RedDark.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(error, color = TextWhite, fontSize = 13.sp)
                            Text("✕", color = TextWhite, modifier = Modifier.clickable { onClearError() })
                        }
                    }
                }

                Text("EMAIL ADDRESS", color = TextGray, fontSize = 11.sp,
                    fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp,
                    modifier = Modifier.padding(bottom = 8.dp))
                OutlinedTextField(
                    value = email, onValueChange = { email = it },
                    placeholder = { Text("name@example.com", color = TextGray.copy(alpha = 0.6f), fontSize = 14.sp) },
                    leadingIcon = { Icon(Icons.Default.Email, null, tint = TextGray) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp), colors = loginFieldColors(),
                    enabled = !isLoading
                )

                Spacer(Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("PASSWORD", color = TextGray, fontSize = 11.sp,
                        fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                    Text("Forgot?", color = RedMain, fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable { })
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = password, onValueChange = { password = it },
                    placeholder = { Text("Enter your password", color = TextGray.copy(alpha = 0.6f), fontSize = 14.sp) },
                    leadingIcon = { Icon(Icons.Default.Lock, null, tint = TextGray) },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                null, tint = TextGray)
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp), colors = loginFieldColors(),
                    enabled = !isLoading
                )

                Spacer(Modifier.height(14.dp))

                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(enabled = !isLoading) { rememberDevice = !rememberDevice }) {
                    Checkbox(checked = rememberDevice, onCheckedChange = { rememberDevice = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = RedMain, uncheckedColor = TextGray, checkmarkColor = TextWhite),
                        enabled = !isLoading)
                    Spacer(Modifier.width(4.dp))
                    Text("Remember this device", color = TextGray, fontSize = 13.sp)
                }

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = { onLogin(email, password) },
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    enabled = !isLoading
                ) {
                    Box(modifier = Modifier.fillMaxSize()
                        .background(Brush.horizontalGradient(listOf(RedDark, RedMain)), RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = TextWhite,
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Sign In", color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.width(8.dp))
                                Text("→", color = TextWhite, fontSize = 18.sp)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = FieldBorder)
                    Text("  OR  ", color = TextGray, fontSize = 12.sp, letterSpacing = 1.sp)
                    HorizontalDivider(modifier = Modifier.weight(1f), color = FieldBorder)
                }

                Spacer(Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(onClick = { },
                        modifier = Modifier.weight(1f).height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, FieldBorder),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = FieldBg),
                        enabled = !isLoading) {
                        Text("G", color = Color(0xFF4285F4), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.width(6.dp))
                        Text("Google", color = TextWhite, fontSize = 13.sp)
                    }
                    OutlinedButton(onClick = { },
                        modifier = Modifier.weight(1f).height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, FieldBorder),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = FieldBg),
                        enabled = !isLoading) {
                        Text("", color = TextWhite, fontSize = 16.sp)
                        Spacer(Modifier.width(6.dp))
                        Text("Apple", color = TextWhite, fontSize = 13.sp)
                    }
                }

                Spacer(Modifier.height(28.dp))

                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    Text("New to CineScope? ", color = TextGray, fontSize = 13.sp)
                    Text("Create an account", color = RedMain, fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable(enabled = !isLoading) { onGoToRegister() })
                }

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun loginFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Color(0xFFE53935),
    unfocusedBorderColor = Color(0xFF2E2020),
    focusedContainerColor = Color(0xFF1E1414),
    unfocusedContainerColor = Color(0xFF1E1414),
    focusedTextColor = Color(0xFFF5F5F5),
    unfocusedTextColor = Color(0xFFF5F5F5),
    cursorColor = Color(0xFFE53935)
)

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun MainLoginPreview() {
    CineScopeProyectTheme {
        MainLoginScreen()
    }
}