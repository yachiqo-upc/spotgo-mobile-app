package com.yachiqo.spotgo.presentation.parking_admin.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.common.FigmaIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AdminDetailPage(title: String, onBack: () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(title, style = MaterialTheme.typography.titleLarge) },
            navigationIcon = {
                IconButton(onClick = onBack) { FigmaIcon(R.drawable.ic_back, stringResource(R.string.admin_back)) }
            },
            windowInsets = WindowInsets(0),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        )
        Column(Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp).padding(top = 18.dp, bottom = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp), content = content)
    }
}

@Composable
internal fun AdminDisplayField(value: String, label: String, modifier: Modifier = Modifier) {
    OutlinedTextField(value = value, onValueChange = {}, readOnly = true, singleLine = true,
        modifier = modifier.fillMaxWidth(), label = { Text(label, style = MaterialTheme.typography.bodySmall) },
        textStyle = MaterialTheme.typography.bodyLarge, shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            focusedBorderColor = MaterialTheme.colorScheme.outline,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ))
}
