# Video Feed Swipe Handling

This document explains how swipe gestures are detected and how the page changes when navigating vertically through the video feed.

## Gesture Detection

`TiktokVerticalVideoPager` uses Jetpack Compose's `VerticalPager` API to track drag gestures. The pager's state is created via `rememberPagerState`:

```kotlin
val pagerState = rememberPagerState(
    initialPage = initialPage ?: 0,
    pageCount = { videos.size }
)
```

Compose's pager automatically registers vertical drag gestures. It calculates the swipe velocity and displacement to decide when to settle on the next or previous page. The `flingBehavior` is configured with `PagerDefaults.flingBehavior` and `PagerSnapDistance.atMost(1)` so that a single fling scrolls at most one page:

```kotlin
val fling = PagerDefaults.flingBehavior(
    state = pagerState,
    pagerSnapDistance = PagerSnapDistance.atMost(1)
)
```

## Page Change Logic

`VerticalPager` exposes the `settledPage` property inside `PagerState`. Each `VideoPlayer` checks whether its page index matches `pagerState.settledPage` before creating an `ExoPlayer` instance:

```kotlin
if (pagerState.settledPage == pageIndex) {
    val exoPlayer = remember(context) { ExoPlayer.Builder(context).build() }
    // ...
}
```

When the user swipes up or down, `VerticalPager` updates `settledPage` after the scroll animation completes. The currently visible page initializes its video player, while the previous one disposes of its player via `DisposableEffect` when it leaves the screen:

```kotlin
DisposableEffect(key1 = AndroidView(...)) {
    onDispose {
        exoPlayer.release()
    }
}
```

Thus, upward or downward swipes trigger `VerticalPager` to settle on a new page, which in turn starts or stops the correct video playback.
