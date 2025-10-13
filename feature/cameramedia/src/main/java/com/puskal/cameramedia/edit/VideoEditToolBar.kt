package com.puskal.cameramedia.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun VideoEditToolBar(
    modifier: Modifier = Modifier,
    showFilters: Boolean = true,
    onToolSelected: (VideoEditTool) -> Unit = {},
    onFeatureSelected: (ResizeMenuFeature) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    val collapsedTools = remember(showFilters) {
        listOf(
            VideoEditTool.SETTINGS,
            VideoEditTool.SHARE,
            VideoEditTool.TRIM,
            VideoEditTool.TEXT,
            VideoEditTool.CHALLENGE,
            VideoEditTool.STICKERS,
            VideoEditTool.EFFECTS,
            VideoEditTool.MORE
        )
    }

    val expandedTools = remember(showFilters) {
        buildList {
            add(VideoEditTool.SETTINGS)
            add(VideoEditTool.SHARE)
            add(VideoEditTool.TRIM)
            add(VideoEditTool.TEXT)
            add(VideoEditTool.CHALLENGE)
            add(VideoEditTool.STICKERS)
            add(VideoEditTool.EFFECTS)
            if (showFilters) add(VideoEditTool.FILTERS)
            add(VideoEditTool.BEAUTY)
            add(VideoEditTool.CROP_RESIZE)
        }
    }

    val baseModifier = modifier.width(36.dp)
    val columnModifier = if (expanded) {
        baseModifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 50.dp)
    } else {
        baseModifier
    }

    Column(
        modifier = columnModifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val tools = if (expanded) expandedTools else collapsedTools
        tools.forEach { tool ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = tool.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            if (tool == VideoEditTool.MORE) {
                                expanded = true
                            } else {
                                onToolSelected(tool)
                            }
                        }
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = tool.title),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
        if (expanded) {
            ResizeMenuFeature.values().forEach { feature ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = feature.icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                if (feature == ResizeMenuFeature.COLLAPSE_TOOLBAR) {
                                    expanded = false
                                } else {
                                    onFeatureSelected(feature)
                                }
                            }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(id = feature.title),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
