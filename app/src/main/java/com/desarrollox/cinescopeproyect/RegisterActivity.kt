package com.desarrollox.cinescopeproyect

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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.desarrollox.cinescopeproyect.ui.theme.CineScopeProyectTheme

// ─── Colores ──────────────────────────────────────────────────────────────────
private val BgDark      = Color(0xFF120A0A)
private val FieldBg     = Color(0xFF1E1414)
private val FieldBorder = Color(0xFF2E2020)
private val RedMain     = Color(0xFFE53935)
private val RedDark     = Color(0xFFB71C1C)
private val TextWhite   = Color(0xFFF5F5F5)
private val TextGray    = Color(0xFF9E8E8E)

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineScopeProyectTheme {
                RegisterScreen(
                    onGoToLogin = { finish() }
                )
            }
        }
    }
}

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onGoToLogin: () -> Unit = {}
) {
    var fullName            by remember { mutableStateOf("") }
    var email               by remember { mutableStateOf("") }
    var password            by remember { mutableStateOf("") }
    var confirmPassword     by remember { mutableStateOf("") }
    var passwordVisible     by remember { mutableStateOf(false) }
    var confirmPassVisible  by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // ── TOP BAR ───────────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "←",
                    color = TextWhite,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable { onGoToLogin() }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(RedMain, RoundedCornerShape(6.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🎬", fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "CineScope",
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }

            // ── HERO: sala de cine simulada ───────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF2A0808),
                                    Color(0xFF4A0D0D),
                                    Color(0xFF3D0A0A),
                                    Color(0xFF1A0505)
                                )
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.radialGradient(
                                colors = listOf(Color(0x44FFFFFF), Color(0x00000000)),
                                radius = 280f
                            )
                        )
                )
                // Butacas
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                ) {
                    repeat(3) { row ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = (row * 6).dp)
                                .padding(bottom = 3.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            repeat(11 - row) {
                                Box(
                                    modifier = Modifier
                                        .width(26.dp)
                                        .height(16.dp)
                                        .background(
                                            Color(0xFF8B0000).copy(alpha = 0.75f + row * 0.08f),
                                            RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                        )
                                )
                            }
                        }
                    }
                }
                // Degradado inferior
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .align(Alignment.BottomCenter)
                        .background(Brush.verticalGradient(listOf(Color.Transparent, BgDark)))
                )
                // Texto hero
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 24.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = "Join the Spotlight",
                        color = TextWhite,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Start your cinematic journey today",
                        color = TextGray,
                        fontSize = 13.sp
                    )
                }
            }

            // ── FORMULARIO ────────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = "Create Account",
                    color = TextWhite,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Full Name
                RegFieldLabel("Full Name")
                RegTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = "John Doe",
                    icon = Icons.Default.Person
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Email
                RegFieldLabel("Email Address")
                RegTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "john@example.com",
                    icon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Password
                RegFieldLabel("Password")
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("••••••••", color = TextGray.copy(alpha = 0.6f), fontSize = 14.sp) },
                    leadingIcon = { Icon(Icons.Default.Lock, null, tint = TextGray) },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                null, tint = TextGray
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = regFieldColors()
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Confirm Password
                RegFieldLabel("Confirm Password")
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = { Text("••••••••", color = TextGray.copy(alpha = 0.6f), fontSize = 14.sp) },
                    leadingIcon = { Icon(Icons.Default.Lock, null, tint = TextGray) },
                    trailingIcon = {
                        IconButton(onClick = { confirmPassVisible = !confirmPassVisible }) {
                            Icon(
                                if (confirmPassVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                null, tint = TextGray
                            )
                        }
                    },
                    visualTransformation = if (confirmPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = regFieldColors()
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Botón Create Account
                Button(
                    onClick = { onRegisterSuccess() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(listOf(RedDark, RedMain)),
                                RoundedCornerShape(14.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Create Account",
                            color = TextWhite,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Link login
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Already have an account? ", color = TextGray, fontSize = 13.sp)
                    Text(
                        text = "Log in",
                        color = RedMain,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { onGoToLogin() }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

// ─── Helpers ─────────────────────────────────────────────────────────────────
@Composable
private fun RegFieldLabel(text: String) {
    Text(
        text = text,
        color = Color(0xFFCCCCCC),
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
private fun RegTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color(0xFF9E8E8E).copy(alpha = 0.6f), fontSize = 14.sp) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = Color(0xFF9E8E8E)) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = regFieldColors()
    )
}

@Composable
private fun regFieldColors() = OutlinedTextFieldDefaults.colors(
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
fun RegisterScreenPreview() {
    CineScopeProyectTheme {
        RegisterScreen()
    }
}