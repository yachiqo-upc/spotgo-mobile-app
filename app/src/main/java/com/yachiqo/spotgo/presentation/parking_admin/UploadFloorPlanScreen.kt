package com.yachiqo.spotgo.presentation.parking_admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.parking_admin.components.*
import com.yachiqo.spotgo.ui.theme.SpotGoTheme

@Composable
fun UploadFloorPlanScreen(onBack: () -> Unit, onOpenMap: () -> Unit, onUnavailableAction: () -> Unit) {
    AdminDetailPage(stringResource(R.string.admin_upload_title), onBack) {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(stringResource(R.string.admin_upload_heading), style = MaterialTheme.typography.titleLarge)
            Text(stringResource(R.string.admin_upload_subtitle), style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        AdminDisplayField(stringResource(R.string.admin_parking_name), stringResource(R.string.admin_upload_parking_zone))
        AdminDisplayField(stringResource(R.string.admin_upload_level_value), stringResource(R.string.admin_upload_level))
        Surface(Modifier.fillMaxWidth().heightIn(min = 204.dp), shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceContainer, contentColor = MaterialTheme.colorScheme.onSurface,
            border = BorderStroke(1.dp, AdminCardOutline)) {
            Column(Modifier.padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(stringResource(R.string.admin_upload_file_heading), style = MaterialTheme.typography.titleMedium)
                Text(stringResource(R.string.admin_upload_file_formats), style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                OutlinedButton(onClick = onUnavailableAction, modifier = Modifier.widthIn(min = 180.dp).heightIn(min = 56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)) {
                    Text(stringResource(R.string.admin_upload_choose_file), style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
        AdminFileRow(stringResource(R.string.admin_floor_plan_file), stringResource(R.string.admin_upload_file_size),
            Modifier.padding(end = 36.dp))
        Surface(shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.surfaceContainer) {
            Column(Modifier.padding(horizontal = 14.dp, vertical = 13.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(stringResource(R.string.admin_upload_before), style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary)
                Text(stringResource(R.string.admin_upload_requirements), style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Button(onClick = onOpenMap, modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
            shape = RoundedCornerShape(16.dp)) {
            Text(stringResource(R.string.admin_upload_title), style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(widthDp = 412, heightDp = 915)
@Composable
private fun UploadFloorPlanPreview() {
    SpotGoTheme { Surface { UploadFloorPlanScreen({}, {}, {}) } }
}
