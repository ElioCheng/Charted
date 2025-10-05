## Architecture

1. ![Architecture.jpg](/images/Architecture.jpg)
2. Our architecture will be MVVM, because it will allow modular design and it’s easy to test and debug. We wish for our application to have dynamic changes in display that reflect the user's choices in the game or their deliberate changes (removing certain music, reranking, etc.). There will also be frequent updates to the universal ranking given the totality of all personal rankings being reflected through it so MVVM works best here.
3. We will use Firebase for cloud services.
4. Our app allows users to register an account and to connect it to their Spotify account. We don’t want to leak users' passwords and Spotify information, so we’re going to use Firebase authentication and look into ways to ensure that their Spotify details when our app is using their ID to find tracks / albums from their account is secure. Being online means that we need to be more strict on privacy and security aspect of our application.

## Application Features

1. Our application will target Android version 7.0, which runs on 97% of Android devices.
2. We will test with virtual emulators using Android Studio or set up an Android plugin in IntelliJ and use an emulator that way. We will rely more on integration tests rather than unit tests given the scope of the application and their features.
3. Features that we don't know how to implement yet: music recommendations, discussion forum for album match-ups. We will investigate libraries or other tools for implementing these features. These are mainly advanced features that we will implement after our main features are completed and do not impact the main functionalities of our application, so they should cause no problems even if it results in us omitting them.
4. [lowdef-prototype.pdf](/images/lowdef-prototype.pdf)

## External Dependencies

For local dependencies and libraries, we plan on using the Spotify API to get song/album data. We will also use Firebase library for cloud services. We will also possibly need to use an iFrame embedding library to display Spotify players on screen.