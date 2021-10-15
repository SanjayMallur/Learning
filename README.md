<p align="center">  
RoundUp is a small demo application based on modern Android application tech-stacks and MVVM architecture.<br>
The application take all the our going transactions for the past week and rounds up to the nearest pound. And on click of a button at the bottom os the screen this amount will be transfered to their savings goal account.
</p>

## Screenshots 

<img src="https://user-images.githubusercontent.com/7569605/137411337-ee16bcd2-2988-402d-b1a1-cc76bb8207f6.png" width="40%"/>     <img src="https://user-images.githubusercontent.com/7569605/137411360-2f4e86e6-f80d-4677-b8a8-21425ea0c35f.png" width="40%"/>


## Download release apk
[Click here to download](https://github.com/SanjayMallur/RoundUp/releases)

## Tech stack & Open-source libraries
- Minimum SDK level 23
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [Koin](https://github.com/InsertKoinIO/koin) for dependency injection.
- JetPack
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Android DataBinding kit for notifying data changes to UI layers.
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Moshi](https://github.com/square/moshi/) - A modern JSON library for Kotlin and Java.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.
- [Navigation](https://developer.android.com/guide/navigation) - The Navigation component also ensures a consistent and predictable user experience by adhering to an established set of principles.

## Asumptions made while developing this application
- Because of short development time, I wanted to focus on mainly three things: The clean code, achitecture and unit testing.
- The tatal saved amount is calculated for direction OUT which I assumed spending as somewhere.
- The feed transaction api is made for the past week from the time of the request.
- Only the direction and amount for transaction is displayed on the feed item.
- There is no UI provided for creating the goal, instead the goal is created on the fly with some static request parameters and the money is then tranfered to created goal and and created goal will be returned back from api response.
- Savings goal UI is very basic consisting of Name, Target amount and saved amount in pounds.
- Not all properties are used from api responses, instead used whatever needed to show the basic UI.
- Since we are skipping the login and account related part, please replace accountId, categoryId and token in Prefs.kt class. The fields to be replaced are   ACCOUNT_ID_VALUE, CATEGORY_ID_VALUE and TOKEN_VALUES.

## Things I want to improve if I had more time for development
- Create the multi module application by creating library components, for example UI commons, Networking, Feature X etc.. so that application can be extensible for further features.
- Improve authentication and token storage part, currently all the tokens are stored as plain fields(We can improve this using gradle fields and generated build onfig file)
- Create a tablet version of the app.
- Create a beautiful UI's and display the relevant images for the items using libraries like Glide, Coil and Fresco.
- Create E2E test cases for regression suite. 


## MAD Score
![summary](https://user-images.githubusercontent.com/7569605/137406601-9e6579c3-7e3c-413d-bfbb-4e846d249952.png)
![kotlin](https://user-images.githubusercontent.com/7569605/137406641-27afa407-76f5-4bbd-8a4f-319d1403d9f1.png)
[MadScore Link](https://madscorecard.withgoogle.com/scorecards/2523066045/)

## Architecture
RoundUp is based on MVVM architecture and a repository pattern.

![architecture](https://user-images.githubusercontent.com/24237865/77502018-f7d36000-6e9c-11ea-92b0-1097240c8689.png)

