# TikTok-Compose

TikTok clone app built with Jetpack Compose following latest modern android development trend. Data shown in the app is from local data source, you can replace that with your own server api.

<p align="center">
<img src="screenshots/tiktokcompose_screenshot.jpg"  />
</p>

<br/>

Click download button to get latest TikTok compose app

<a href="https://github.com/puskal-khadka/TikTok-Compose/releases/download/1.0.0/tiktok-compose.apk"><img src="screenshots/download_button.png" width=170></a>

<br/>

## TechStack
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material 3](https://m3.material.io/)
- [Coroutine](https://kotlinlang.org/docs/coroutines-overview.html)
- [Accompanist](https://google.github.io/accompanist/)
- [Hilt](https://dagger.dev/hilt/)
- [Coil](https://coil-kt.github.io/coil/)
- [Multi Module](https://developer.android.com/topic/modularization)
- [Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
- [Media 3](https://developer.android.com/guide/topics/media/media3)
- [CameraX](https://developer.android.com/training/camerax) and so on

<br/>

## Architecture
- Clean Architecture
- MVVM
- UDF (Unidirectional Data Flow)

<br/>

## Modularization
To achieve low coupling, high cohesion with maximum reusability & scalable code base, this project is modularized.
This app consist of following modules:

- app
- feature
- data
- domain
- common
- core

<img src="screenshots/tiktokcompose_modularization.jpg" width=760  />

<br/>

## Contribution
You are always welcome to contribute to this project. For contributing, feel free to [create an issue](https://github.com/puskal-khadka/TikTok-Compose/issues/new/choose) or submit a pull request.

<br/>

## License
[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

    Copyright 2023 Puskal khadka

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
