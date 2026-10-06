package com.yachiqo.spotgo.presentation.parking_admin.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.common.SpotGoWordmark

internal val AdminAvailable = Color(0xFF44E38B)
internal val AdminWarning = Color(0xFFFFB84D)
internal val AdminError = Color(0xFFFF5D68)
internal val AdminMapBackground = Color(0xFF171B27)
internal val AdminOccupied = Color(0xFF273247)
internal val AdminCardOutline = Color(0xFFCAC4D0)

@Composable
internal fun AdminPage(
    title: String,
    subtitle: String,
    onAccount: () -> Unit,
    contentSpacing: androidx.compose.ui.unit.Dp = 10.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxWidth().heightIn(min = 145.dp)
                .padding(horizontal = 20.dp).padding(top = 18.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Row(Modifier.fillMaxWidth().height(40.dp), verticalAlignment = Alignment.CenterVertically) {
                SpotGoWordmark(Modifier.weight(1f))
                IconButton(onClick = onAccount) {
                    Icon(painterResource(R.drawable.ic_profile), stringResource(R.string.admin_account),
                        Modifier.size(28.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Text(title, style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp, lineHeight = 30.sp))
            Text(subtitle, style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp, lineHeight = 19.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Column(
            Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp).padding(top = 12.dp, bottom = 18.dp),
            verticalArrangement = Arrangement.spacedBy(contentSpacing),
            content = content,
        )
    }
}

@Composable
internal fun AdminPanel(
    modifier: Modifier = Modifier,
    outlined: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(if (outlined) 1.dp else 0.6.dp,
            if (outlined) AdminCardOutline else MaterialTheme.colorScheme.outline),
    ) { Column(content = content) }
}

@Composable
internal fun AdminSectionTitle(text: String) {
    Text(text, style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp))
}

@Composable
internal fun AdminStatusRow(title: String, subtitle: String, value: String, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(title, style = MaterialTheme.typography.titleMedium) },
        supportingContent = { Text(subtitle, style = MaterialTheme.typography.bodyMedium) },
        trailingContent = { Text(value, style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant) },
        modifier = Modifier.fillMaxWidth().heightIn(min = 60.dp).clickable(onClick = onClick),
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    )
}
