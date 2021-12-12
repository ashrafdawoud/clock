package com.github.amrmsaraya.clock.presentation.alarm.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerDefaults
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalUnitApi::class, ExperimentalSnapperApi::class, ExperimentalPagerApi::class)
@Composable
fun TimeChooser(
    modifier: Modifier,
    initial: Int,
    items: List<Int>,
    onTimeChange: (Int) -> Unit
) {
    val state = rememberPagerState(initialPage = initial)

    LaunchedEffect(key1 = initial) {
        state.scrollToPage(initial)
    }

    LaunchedEffect(state) {
        snapshotFlow { state.currentPage }.collect {
            onTimeChange(items[it])
        }
    }

    VerticalPager(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary),
        count = items.size,
        state = state,
        flingBehavior = PagerDefaults.flingBehavior(
            state = state,
            maximumFlingDistance = { it.distanceToIndexSnap(items.lastIndex).toFloat() }
        )
    ) {
        Text(
            text = "%02d".format(items[it]),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center
        )
    }

}