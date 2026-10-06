package com.yachiqo.spotgo.presentation.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.common.SpotGoWordmark
import com.yachiqo.spotgo.presentation.common.FigmaIcon
import com.yachiqo.spotgo.ui.theme.SpotGoSupportingText
import com.yachiqo.spotgo.ui.theme.SpotGoTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onOpenDriver: () -> Unit,
    onOpenParkingAdmin: () -> Unit,
    onCreateAccount: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val email = rememberTextFieldState()
    // Password is temporary UI state and is never persisted in saved instance state.
    val password = remember { TextFieldState() }
    var keepSignedIn by rememberSaveable { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val pendingMessage = stringResource(R.string.login_action_pending)
    val showPendingAction: () -> Unit = {
        focusManager.clearFocus()
        scope.launch {
            snackbar.currentSnackbarData?.dismiss()
            snackbar.showSnackbar(pendingMessage)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbar, Modifier.safeDrawingPadding()) },
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .safeDrawingPadding()
                .imePadding(),
            contentAlignment = Alignment.TopCenter,
        ) {
            val availableHeight = maxHeight
            Column(
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .heightIn(min = availableHeight),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp)
                        .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Box(Modifier.height(40.dp), contentAlignment = Alignment.CenterStart) {
                        SpotGoWordmark()
                    }
                    Text(stringResource(R.string.login_welcome), style = MaterialTheme.typography.headlineMedium)
                    Text(
                        stringResource(R.string.login_intro),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
                        color = SpotGoSupportingText,
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                ) {
                    Text(stringResource(R.string.login_sign_in), style = MaterialTheme.typography.titleLarge)
                    Text(
                        stringResource(R.string.login_account_hint),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    OutlinedTextField(
                        state = email,
                        labelPosition = TextFieldLabelPosition.Attached(alwaysMinimize = true),
                        label = { Text(stringResource(R.string.login_email)) },
                        placeholder = { Text(stringResource(R.string.login_email_placeholder)) },
                        modifier = Modifier.fillMaxWidth().figmaFieldBounds(),
                        shape = RoundedCornerShape(16.dp),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                        colors = loginFieldColors(),
                    )
                    OutlinedSecureTextField(
                        state = password,
                        labelPosition = TextFieldLabelPosition.Attached(alwaysMinimize = true),
                        label = { Text(stringResource(R.string.login_password)) },
                        placeholder = { Text(stringResource(R.string.login_password_placeholder)) },
                        trailingIcon = {
                            IconButton(onClick = { password.edit { replace(0, length, "") } }) {
                                FigmaIcon(
                                    R.drawable.ic_cancel,
                                    contentDescription = stringResource(R.string.login_clear_password),
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth().figmaFieldBounds(),
                        shape = RoundedCornerShape(16.dp),
                        textObfuscationMode = TextObfuscationMode.Hidden,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                        onKeyboardAction = { focusManager.clearFocus() },
                        colors = loginFieldColors(),
                    )
                    // Wrapping the two controls independently also works with larger accessibility fonts.
                    androidx.compose.foundation.layout.FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Row(
                            modifier = Modifier.heightIn(min = 48.dp).toggleable(
                                value = keepSignedIn,
                                role = Role.Checkbox,
                                onValueChange = { keepSignedIn = it },
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Checkbox(checked = keepSignedIn, onCheckedChange = null, modifier = Modifier.size(48.dp))
                            Text(stringResource(R.string.login_remember), style = MaterialTheme.typography.bodyMedium)
                        }
                        TextButton(onClick = showPendingAction) {
                            Text(stringResource(R.string.login_forgot_password))
                        }
                    }
                    Button(
                        onClick = showPendingAction,
                        modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Text(stringResource(R.string.login_sign_in), style = MaterialTheme.typography.bodyLarge)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            stringResource(R.string.login_new_here),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        TextButton(
                            onClick = { focusManager.clearFocus(); onCreateAccount() },
                            modifier = Modifier.height(30.dp),
                            contentPadding = PaddingValues(horizontal = 6.dp),
                        ) {
                            Text(stringResource(R.string.login_create_account))
                        }
                    }
                    OutlinedButton(
                        onClick = showPendingAction,
                        modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    ) {
                        Text(
                            stringResource(R.string.login_google),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }

                Spacer(Modifier.weight(1f))
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        stringResource(R.string.login_preview_label),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Button(
                        onClick = { focusManager.clearFocus(); onOpenDriver() },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
                        shape = RoundedCornerShape(16.dp),
                    ) { Text(stringResource(R.string.role_driver)) }
                    OutlinedButton(
                        onClick = { focusManager.clearFocus(); onOpenParkingAdmin() },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                    ) { Text(stringResource(R.string.role_parking_admin)) }
                }
            }
        }
    }
}

@Composable
internal fun Modifier.figmaFieldBounds(): Modifier {
    // Figma measures the 56 dp outline, with the floating label extending above it.
    // Material reserves half a label line above that outline; keep the artwork's bounds.
    val labelInset = MaterialTheme.typography.bodySmall.lineHeight / 2
    return layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val inset = labelInset.toPx().roundToInt()
        layout(placeable.width, placeable.height - inset) {
            placeable.placeRelative(0, -inset)
        }
    }
}

@Composable
internal fun loginFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
)

@Preview(name = "01 · Login", widthDp = 412, heightDp = 915, showBackground = true)
@Preview(name = "Login · Compact", widthDp = 360, heightDp = 800, showBackground = true)
@Composable
private fun LoginPreview() {
    SpotGoTheme { LoginScreen(onOpenDriver = {}, onOpenParkingAdmin = {}, onCreateAccount = {}) }
}
