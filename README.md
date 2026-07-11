# YoutobeCompose

Android app clone giao diện YouTube bằng Kotlin, Jetpack Compose, Navigation Compose và Hilt. Project đang tập trung vào các màn chính: Home, Shorts, Subscription và Library.

## UI Preview

Các ảnh dưới đây là lightweight mock preview trong repo để README hiển thị được ngay. Khi Đoàn có screenshot thật từ emulator, chỉ cần thay file trong `docs/screenshots/`.

| Home | Shorts |
| --- | --- |
| <img src="docs/screenshots/home.svg" width="240" alt="Home screen preview" /> | <img src="docs/screenshots/shorts.svg" width="240" alt="Shorts screen preview" /> |

| Subscription | Library |
| --- | --- |
| <img src="docs/screenshots/subscription.svg" width="240" alt="Subscription screen preview" /> | <img src="docs/screenshots/library.svg" width="240" alt="Library screen preview" /> |

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Hilt
- Coil
- Kotlin Serialization

## Current Screens

- Home feed with video cards, filters and Shorts section
- Shorts full-screen vertical pager with action rail and channel info
- Subscription feed with channel row, filter chips and video cards
- Library screen with recent videos, menu items and playlists

## Run

```bash
./gradlew :app:assembleDebug
```

For quick Kotlin compile check:

```bash
./gradlew :app:compileDebugKotlin
```

## Project Notes

- UI follows `Route -> Screen -> Item` composable structure.
- ViewModels expose `StateFlow` for UI state and `SharedFlow` for one-shot effects.
- Mock data is currently loaded inside ViewModels while screens are being built.
- `.kotlin/sessions/` is a local Gradle/Kotlin artifact and should not be committed.
