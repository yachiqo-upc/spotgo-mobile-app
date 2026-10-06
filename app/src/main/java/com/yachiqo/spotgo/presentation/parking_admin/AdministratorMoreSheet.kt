package com.yachiqo.spotgo.presentation.parking_admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yachiqo.spotgo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdministratorMoreSheet(onDismiss: () -> Unit, onOpenInfrastructure: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface,
        scrimColor = Color.Black.copy(alpha = 0.6f),
    ) {
        Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp).padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(stringResource(R.string.admin_more), style = MaterialTheme.typography.titleLarge)
            Text(stringResource(R.string.admin_more_organization),
                Modifier.padding(top = 4.dp, bottom = 2.dp), color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium)
            MoreAction(R.string.admin_more_parking_zone, enabled = false, onClick = {})
            MoreAction(R.string.admin_more_reports, enabled = false, onClick = {})
            MoreAction(R.string.admin_more_guest_parking, enabled = false, onClick = {})
            MoreAction(R.string.admin_infrastructure_title, enabled = true, onClick = onOpenInfrastructure)
            MoreAction(R.string.admin_more_billing, enabled = false, onClick = {})
            MoreAction(R.string.admin_more_close, enabled = true, onClick = onDismiss)
        }
    }
}

@Composable
private fun MoreAction(label: Int, enabled: Boolean, onClick: () -> Unit) {
    OutlinedButton(onClick = onClick, enabled = enabled, modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
        shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        )) {
        Text(stringResource(label), style = MaterialTheme.typography.bodyLarge)
    }
}
