<p align="center">  
Learning is a small demo application based on modern Android application tech-stacks and MVVM architecture.<br>
Also fetching data from the network and integrating persisted data in the database via repository pattern.
</p>

## Screenshots 

### Tablet version
<img src="https://user-images.githubusercontent.com/7569605/121689845-0b2c1480-cabd-11eb-9e6a-9f5e70cdb656.png" align="right" width="100%"/>

### Mobile version
<img src="https://user-images.githubusercontent.com/7569605/121689963-2dbe2d80-cabd-11eb-857a-f3188978ae4a.jpeg" align="left" width="50%"/>
<img src="https://user-images.githubusercontent.com/7569605/121690004-39a9ef80-cabd-11eb-9960-0c8ea5172de3.jpeg" align="right" width="50%"/>


## Download release apk
[Click here to download](https://github.com/SanjayMallur/Learning/releases)

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [Koin](https://github.com/InsertKoinIO/koin) for dependency injection.
- JetPack
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct a database using the abstract layer.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Android DataBinding kit for notifying data changes to UI layers.
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Moshi](https://github.com/square/moshi/) - A modern JSON library for Kotlin and Java.
- [Glide](https://github.com/bumptech/glide) - loading images.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.


## MAD Score
![summary](https://user-images.githubusercontent.com/7569605/121675892-e8ddcb00-caab-11eb-8cbc-c6e1a9f48c6d.png)
![kotlin](https://user-images.githubusercontent.com/7569605/121675983-0317a900-caac-11eb-8315-76cff6c75202.png)
[MadScore Link](https://madscorecard.withgoogle.com/scorecards/3578495028/)

## Architecture
Learning is based on MVVM architecture and a repository pattern.

![architecture](https://user-images.githubusercontent.com/24237865/77502018-f7d36000-6e9c-11ea-92b0-1097240c8689.png)

## Open API
<img src="https://user-images.githubusercontent.com/24237865/83422649-d1b1d980-a464-11ea-8c91-a24fdf89cd6b.png" align="right" width="21%"/>

Learning using the [PokeAPI](https://pokeapi.co/) for constructing RESTful API.<br>
PokeAPI provides a RESTful API interface to highly detailed objects built from thousands of lines of data related to Pokémon.
