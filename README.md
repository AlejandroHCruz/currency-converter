# Currency Converter App
- 📼 Demo: https://www.youtube.com/watch?v=b25HvRB1Xig
- ⬇️ APK: https://www.dropbox.com/s/eb23xsu0mofhsjp/CurrencyConverter.apk?dl=0

# Architecture
MVVM
![](https://user-images.githubusercontent.com/1812129/68319008-e9d39d00-00bd-11ea-9245-ebedd2a2c067.png)

## ⚙️ Technologies used
- Room
- MVVM
- LiveData
- Retrofit2 + Moshi
- Coroutines
- Kotlin
- Dagger2
- Espresso
- RecyclerView
- Material Components
- Jetpack
- Lifecycle Extensions
- Junit 5
- Crashlytics

## 📱 Devices supported:
- Android 5 and up
- Phones, Chromebooks and Tablets

# 🚀 Definition of "shippable"
- App has an icon & splash screen
- Query the values every second (while the app is in the foreground)
- Offline support (with `Room` database)
- Orientation change
- Not using any experimental library or technology
- No `TODO`s left in the code
- Tests for meaningful parts of the code

# 🎇 Features
## ✨ Requested features
- Every currency has a flag icon & a consistant name
- Hint: Color and value in the EditText
- Typing in values (Removing the leading 0, can't input anything but positive numbers)
- Typing a lot of numbers on any one does not break the app
- Changin base currency by tapping on the row
- Any currency works as expected when using it as a base (KRW & IDR were challenging)
- While typing, the the values of the other currencies keep updating
- Tapping an item while typing works aswell

## 💖 Additional Features
- Bottom bar is the same color as the status bar
- Configuring TLSv1.3 or 1.2 on app start (when needed)
- Localized into German, Spanish & French
- On Scroll: dismiss the keyboard
- On app start: No EditText is selected automatically
- Setup working Crashlytics
- Loading screen (when no data is cached), relevant for slow internet connection
- Error screen (when no data is cached), relevant for when the user does not have internet and opens the app for the first time
- Error snack bars
- Handle focus when changing the base currency (select the top most instead of the one that was being edited)
- Don't update values in the UI while scrolling (for performance ⚡️)

# 🙏 Credits 

## 🏳 Vector files:
- https://www.vecteezy.com/vector-art/638109-collection-of-round-flags-most-popular-world-flags
- https://www.vecteezy.com/vector-art/447188-hong-kong-flag-on-round-and-square-frames
- https://www.vecteezy.com/vector-art/373767-mexico-flag-in-different-frame-designs
- https://www.vecteezy.com/vector-art/628988-made-in-philippines-flag-icon
- https://www.vecteezy.com/vector-art/444395-game-time-france-vs-croatia
- https://www.vecteezy.com/vector-art/603000-made-in-iceland-flag-icon
- https://www.vecteezy.com/vector-art/368211-flag-of-new-zealand-on-round-frame
- https://www.vecteezy.com/vector-art/626227-money-vector-icon

## 🤲 Open Source Projects
- StateLayout https://github.com/wangshouquan/StateLayout
