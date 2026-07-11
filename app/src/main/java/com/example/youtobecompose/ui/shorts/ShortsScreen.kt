package com.example.youtobecompose.ui.shorts

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil3.compose.AsyncImage
import com.example.youtobecompose.R
import com.example.youtobecompose.model.ShortModel
import com.example.youtobecompose.ui.theme.YoutobeComposeTheme

@Composable
fun ShortsRoute(
    modifier: Modifier = Modifier,
    viewModel: ShortsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    HideSystemBarsEffect()

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    ShortsScreen(
        modifier = modifier,
        state = state,
        onBackClick = {
            viewModel.onEvent(ShortsEvent.BackClick(short = it))
        },
        onLikeClick = {
            viewModel.onEvent(ShortsEvent.LikeClick(short = it))
        },
        onDislikeClick = {
            viewModel.onEvent(ShortsEvent.DislikeClick(short = it))
        },
        onCommentClick = {
            viewModel.onEvent(ShortsEvent.CommentClick(short = it))
        },
        onShareClick = {
            viewModel.onEvent(ShortsEvent.ShareClick(short = it))
        },
        onMoreClick = {
            viewModel.onEvent(ShortsEvent.MoreClick(short = it))
        },
        onSoundClick = {
            viewModel.onEvent(ShortsEvent.SoundClick(short = it))
        },
        onSubscribeClick = {
            viewModel.onEvent(ShortsEvent.SubscribeClick(short = it))
        },
    )

    LaunchedEffect(viewModel.effect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is ShortsEffect.BackClickSuccess -> {
                        Toast.makeText(
                            context,
                            "BackClickSuccess: ${effect.shortTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                        onBackClick()
                    }

                    is ShortsEffect.LikeClickSuccess -> {
                        Toast.makeText(
                            context,
                            "LikeClickSuccess: ${effect.shortTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is ShortsEffect.DislikeClickSuccess -> {
                        Toast.makeText(
                            context,
                            "DislikeClickSuccess: ${effect.shortTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is ShortsEffect.CommentClickSuccess -> {
                        Toast.makeText(
                            context,
                            "CommentClickSuccess: ${effect.shortTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is ShortsEffect.ShareClickSuccess -> {
                        Toast.makeText(
                            context,
                            "ShareClickSuccess: ${effect.shortTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is ShortsEffect.MoreClickSuccess -> {
                        Toast.makeText(
                            context,
                            "MoreClickSuccess: ${effect.shortTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is ShortsEffect.SoundClickSuccess -> {
                        Toast.makeText(
                            context,
                            "SoundClickSuccess: ${effect.shortTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is ShortsEffect.SubscribeClickSuccess -> {
                        Toast.makeText(
                            context,
                            "SubscribeClickSuccess: ${effect.shortTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}

@Composable
fun ShortsScreen(
    modifier: Modifier = Modifier,
    state: ShortsState,
    onBackClick: (ShortModel) -> Unit,
    onLikeClick: (ShortModel) -> Unit,
    onDislikeClick: (ShortModel) -> Unit,
    onCommentClick: (ShortModel) -> Unit,
    onShareClick: (ShortModel) -> Unit,
    onMoreClick: (ShortModel) -> Unit,
    onSoundClick: (ShortModel) -> Unit,
    onSubscribeClick: (ShortModel) -> Unit,
) {
    val pagerState = rememberPagerState(
        pageCount = { state.shorts.size },
    )

    VerticalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize(),
        beyondViewportPageCount = 1,
    ) { page ->
        ShortVideoItem(
            short = state.shorts[page],
            onBackClick = onBackClick,
            onLikeClick = onLikeClick,
            onDislikeClick = onDislikeClick,
            onCommentClick = onCommentClick,
            onShareClick = onShareClick,
            onMoreClick = onMoreClick,
            onSoundClick = onSoundClick,
            onSubscribeClick = onSubscribeClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ShortsScreenPreview() {
    YoutobeComposeTheme {
        ShortsScreen(
            state = ShortsState(
                shorts = listOf(
                    ShortModel(
                        id = "short-poly-amazing-race",
                        thumbnailUrl = "https://picsum.photos/id/1002/720/1280",
                        title = "\"Poly Amazing Race\" san choi day thu vi moi danh cho sinh...",
                        channelAvatarUrl = "https://i.pravatar.cc/200?img=11",
                        channelName = "@ChannelName",
                        likeCount = "2.3k",
                        dislikeLabel = "Dislike",
                        commentCount = "44",
                        shareLabel = "Share",
                        soundThumbnailUrl = "https://i.pravatar.cc/200?img=11",
                        isSubscribed = false,
                    )
                )
            ),
            onBackClick = {},
            onLikeClick = {},
            onDislikeClick = {},
            onCommentClick = {},
            onShareClick = {},
            onMoreClick = {},
            onSoundClick = {},
            onSubscribeClick = {},
        )
    }
}

@Composable
fun ShortVideoItem(
    short: ShortModel,
    onBackClick: (ShortModel) -> Unit,
    onLikeClick: (ShortModel) -> Unit,
    onDislikeClick: (ShortModel) -> Unit,
    onCommentClick: (ShortModel) -> Unit,
    onShareClick: (ShortModel) -> Unit,
    onMoreClick: (ShortModel) -> Unit,
    onSoundClick: (ShortModel) -> Unit,
    onSubscribeClick: (ShortModel) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val (thumbnailUrlId, backId, videoShortActionId, videoShortInfoId) = createRefs()

        AsyncImage(
            model = short.thumbnailUrl,
            error = painterResource(R.drawable.ic_background_error),
            placeholder = painterResource(R.drawable.ic_background_error),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(thumbnailUrlId) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentScale = ContentScale.Crop,
        )

        IconButton(
            modifier = Modifier
                .statusBarsPadding()
                .size(48.dp)
                .constrainAs(backId) {
                    top.linkTo(parent.top, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                },
            onClick = {
                onBackClick(short)
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "",
                modifier = Modifier.size(30.dp),
                tint = Color.White,
            )
        }

        VideoShortAction(
            modifier = Modifier
                .navigationBarsPadding()
                .constrainAs(videoShortActionId) {
                    end.linkTo(parent.end, margin = 8.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                },
            likeCount = short.likeCount,
            dislikeLabel = short.dislikeLabel,
            commentCount = short.commentCount,
            shareLabel = short.shareLabel,
            soundThumbnailUrl = short.soundThumbnailUrl,
            onLikeClick = {
                onLikeClick(short)
            },
            onDislikeClick = {
                onDislikeClick(short)
            },
            onCommentClick = {
                onCommentClick(short)
            },
            onShareClick = {
                onShareClick(short)
            },
            onMoreClick = {
                onMoreClick(short)
            },
            onSoundClick = {
                onSoundClick(short)
            },
        )

        VideoShortInfo(
            modifier = Modifier
                .navigationBarsPadding()
                .constrainAs(videoShortInfoId) {
                    start.linkTo(parent.start, margin = 24.dp)
                    end.linkTo(videoShortActionId.start, margin = 12.dp)
                    bottom.linkTo(parent.bottom, margin = 32.dp)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                },
            short = short,
            onSubscribeClick = {
                onSubscribeClick(short)
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ShortVideoItemPreview() {
    YoutobeComposeTheme {
        ShortVideoItem(
            short = ShortModel(
                id = "short-poly-amazing-race",
                thumbnailUrl = "https://picsum.photos/id/1002/720/1280",
                title = "\"Poly Amazing Race\" san choi day thu vi moi danh cho sinh...",
                channelAvatarUrl = "https://i.pravatar.cc/200?img=11",
                channelName = "@ChannelName",
                likeCount = "2.3k",
                dislikeLabel = "Dislike",
                commentCount = "44",
                shareLabel = "Share",
                soundThumbnailUrl = "https://i.pravatar.cc/200?img=11",
                isSubscribed = false,
            ),
            onBackClick = {},
            onLikeClick = {},
            onDislikeClick = {},
            onCommentClick = {},
            onShareClick = {},
            onMoreClick = {},
            onSoundClick = {},
            onSubscribeClick = {},
        )
    }
}

@Composable
private fun VideoShortInfo(
    modifier: Modifier = Modifier,
    short: ShortModel,
    onSubscribeClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = short.title,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.labelLarge,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            AsyncImage(
                model = short.channelAvatarUrl,
                error = painterResource(R.drawable.ic_profile_avatar),
                placeholder = painterResource(R.drawable.ic_profile_avatar),
                contentDescription = "",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )

            Text(
                text = short.channelName,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = stringResource(R.string.shorts_subscribe),
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .clickable(onClick = onSubscribeClick)
                    .padding(horizontal = 28.dp, vertical = 10.dp),
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black,
                maxLines = 1,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF222222)
@Composable
private fun VideoShortInfoPreview() {
    YoutobeComposeTheme {
        VideoShortInfo(
            modifier = Modifier.padding(16.dp),
            short = ShortModel(
                id = "short-poly-amazing-race",
                thumbnailUrl = "https://picsum.photos/id/1002/720/1280",
                title = "\"Poly Amazing Race\" san choi day thu vi moi danh cho sinh...",
                channelAvatarUrl = "https://i.pravatar.cc/200?img=11",
                channelName = "@ChannelName",
                likeCount = "2.3k",
                dislikeLabel = "Dislike",
                commentCount = "44",
                shareLabel = "Share",
                soundThumbnailUrl = "https://i.pravatar.cc/200?img=11",
                isSubscribed = false,
            ),
            onSubscribeClick = {},
        )
    }
}

@Composable
fun VideoShortAction(
    modifier: Modifier = Modifier,
    likeCount: String,
    dislikeLabel: String,
    commentCount: String,
    shareLabel: String,
    soundThumbnailUrl: String,
    onLikeClick: () -> Unit,
    onDislikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    onMoreClick: () -> Unit,
    onSoundClick: () -> Unit,
) {
    Column(
        modifier = modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        VideoShortItem(
            iconId = R.drawable.ic_like,
            itemLabel = likeCount,
            onClick = onLikeClick,
        )

        VideoShortItem(
            iconId = R.drawable.ic_dislike,
            itemLabel = dislikeLabel,
            onClick = onDislikeClick,
        )

        VideoShortItem(
            iconId = R.drawable.ic_comment,
            itemLabel = commentCount,
            onClick = onCommentClick,
        )

        VideoShortItem(
            iconId = R.drawable.ic_share,
            itemLabel = shareLabel,
            onClick = onShareClick,
        )

        VideoShortIconButton(
            iconId = R.drawable.ic_more_horizontal,
            onClick = onMoreClick,
        )

        VideoShortSoundThumbnail(
            soundThumbnailUrl = soundThumbnailUrl,
            onClick = onSoundClick,
        )
    }
}

@Composable
fun VideoShortItem(
    iconId: Int,
    itemLabel: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.width(64.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(
            modifier = Modifier.size(48.dp),
            onClick = onClick,
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "",
                modifier = Modifier.size(32.dp),
                tint = Color.White,
            )
        }

        Text(
            text = itemLabel,
            modifier = Modifier.width(64.dp),
            style = MaterialTheme.typography.labelLarge,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun VideoShortIconButton(
    iconId: Int,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier.size(48.dp),
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "",
            modifier = Modifier.size(32.dp),
            tint = Color.White,
        )
    }
}

@Composable
private fun VideoShortSoundThumbnail(
    soundThumbnailUrl: String,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier.size(52.dp),
        onClick = onClick,
    ) {
        AsyncImage(
            model = soundThumbnailUrl,
            error = painterResource(R.drawable.ic_background_short),
            placeholder = painterResource(R.drawable.ic_background_short),
            contentDescription = "",
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black.copy(alpha = 0.35f)),
            contentScale = ContentScale.Crop,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF222222)
@Composable
private fun VideoShortActionPreview() {
    YoutobeComposeTheme {
        VideoShortAction(
            likeCount = "2.3k",
            dislikeLabel = "Dislike",
            commentCount = "44",
            shareLabel = "Share",
            soundThumbnailUrl = "https://i.pravatar.cc/200?img=11",
            onLikeClick = {},
            onDislikeClick = {},
            onCommentClick = {},
            onShareClick = {},
            onMoreClick = {},
            onSoundClick = {},
        )
    }
}

@Composable
fun HideSystemBarsEffect() {
    val context = LocalContext.current
    val activity = context as? Activity ?: return
    val window = activity.window

    DisposableEffect(window) {
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        onDispose {
            controller.show(WindowInsetsCompat.Type.systemBars())
        }
    }
}
