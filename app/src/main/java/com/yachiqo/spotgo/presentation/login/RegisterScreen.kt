package com.yachiqo.spotgo.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.common.FigmaIcon
import com.yachiqo.spotgo.presentation.common.SpotGoWordmark
import com.yachiqo.spotgo.ui.theme.SpotGoSupportingText
import com.yachiqo.spotgo.ui.theme.SpotGoTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onBackToLogin: () -> Unit, modifier: Modifier = Modifier) {
    val firstName = rememberTextFieldState()
    val lastName = rememberTextFieldState()
    val phone = rememberTextFieldState()
    val email = rememberTextFieldState()
    // Keep the password in temporary UI state, outside saved instance state.
    val password = remember { TextFieldState() }
    var acceptedTerms by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val snackbar = remember { SnackbarHostState() }
    var snackbarHeight by remember { mutableIntStateOf(0) }
    val snackbarPadding = with(LocalDensity.current) { snackbarHeight.toDp() }
    val scope = rememberCoroutineScope()
    val pendingMessage = stringResource(R.string.login_action_pending)
    val backToLogin = { focusManager.clearFocus(); onBackToLogin() }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0),
        snackbarHost = {
            SnackbarHost(snackbar, Modifier.safeDrawingPadding().onSizeChanged { snackbarHeight = it.height })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).safeDrawingPadding()
                .imePadding().padding(bottom = snackbarPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier.widthIn(max = 480.dp).fillMaxWidth()
                    .heightIn(min = 188.dp).padding(horizontal = 24.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    FilledIconButton(
                        onClick = backToLogin,
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                        ),
                    ) { FigmaIcon(R.drawable.ic_back, stringResource(R.string.back_to_login)) }
                    SpotGoWordmark()
                }
                Text(stringResource(R.string.register_title), style = MaterialTheme.typography.headlineMedium)
                Text(
                    stringResource(R.string.register_intro),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
                    color = SpotGoSupportingText,
                )
            }
            Column(
                modifier = Modifier.widthIn(max = 480.dp).fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                RegisterField(firstName, R.string.register_first_name, R.string.register_first_name_placeholder)
                RegisterField(lastName, R.string.register_last_name, R.string.register_last_name_placeholder)
                RegisterField(phone, R.string.register_phone, R.string.register_phone_placeholder, KeyboardType.Phone)
                RegisterField(email, R.string.login_email, R.string.login_email_placeholder, KeyboardType.Email)
                OutlinedSecureTextField(
                    state = password,
                    labelPosition = TextFieldLabelPosition.Attached(alwaysMinimize = true),
                    label = { Text(stringResource(R.string.login_password)) },
                    placeholder = { Text(stringResource(R.string.register_password_placeholder)) },
                    trailingIcon = {
                        IconButton(onClick = { password.edit { replace(0, length, "") } }) {
                            FigmaIcon(R.drawable.ic_cancel, stringResource(R.string.login_clear_password))
                        }
                    },
                    modifier = Modifier.fillMaxWidth().figmaFieldBounds(),
                    shape = RoundedCornerShape(16.dp),
                    textObfuscationMode = TextObfuscationMode.Hidden,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                    onKeyboardAction = { focusManager.clearFocus() },
                    colors = loginFieldColors(),
                )
                Row(
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).toggleable(
                        value = acceptedTerms, role = Role.Checkbox, onValueChange = { acceptedTerms = it },
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Checkbox(checked = acceptedTerms, onCheckedChange = null, modifier = Modifier.size(48.dp))
                    Text(stringResource(R.string.register_terms), style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f))
                }
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        scope.launch {
                            snackbar.currentSnackbarData?.dismiss()
                            snackbar.showSnackbar(pendingMessage)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
                    shape = RoundedCornerShape(16.dp),
                ) { Text(stringResource(R.string.login_create_account), style = MaterialTheme.typography.bodyLarge) }
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Center,
                    itemVerticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(stringResource(R.string.register_existing_account),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium)
                    TextButton(onClick = backToLogin, contentPadding = PaddingValues(horizontal = 6.dp)) {
                        Text(stringResource(R.string.login_sign_in))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterField(
    state: TextFieldState,
    @androidx.annotation.StringRes label: Int,
    @androidx.annotation.StringRes placeholder: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    OutlinedTextField(
        state = state,
        labelPosition = TextFieldLabelPosition.Attached(alwaysMinimize = true),
        label = { Text(stringResource(label)) },
        placeholder = { Text(stringResource(placeholder)) },
        modifier = Modifier.fillMaxWidth().figmaFieldBounds(),
        shape = RoundedCornerShape(16.dp),
        lineLimits = TextFieldLineLimits.SingleLine,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Next),
        colors = loginFieldColors(),
    )
}

@Preview(name = "02 · Register", widthDp = 412, heightDp = 915)
@Preview(name = "Register · Compact", widthDp = 320, heightDp = 800)
@Composable
private fun RegisterPreview() {
    SpotGoTheme { RegisterScreen(onBackToLogin = {}) }
}
