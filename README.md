# wattsnextbackend

This is the Spring Boot backend of the online multiplayer implementation of the board game Watts Next. 
The frontend can be found [here](https://github.com/Roundabout49/wattsnext-frontend).

See `DEPLOYMENT.md` for how the app is deployed to the production server.

## What is Watts Next?

Watts Next is a cooperative board game on the energy transition.
Visit the [website](https://www.enzo.kit.edu/wattsnext) for more information.

## About Gradle

This project uses [Gradle](https://gradle.org/).
To build and run the application, use the *Gradle* tool window by clicking the Gradle icon in the right-hand toolbar,
or run it directly from the terminal:

* Run `./gradlew bootRun` to build and run the application.
* Run `./gradlew build` to only build the application.
* Run `./gradlew check` to run all checks, including tests.
* Run `./gradlew clean` to clean all build outputs.

Note the usage of the Gradle Wrapper (`./gradlew`).
This is the suggested way to use Gradle in production projects.

[Learn more about the Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html).

[Learn more about Gradle tasks](https://docs.gradle.org/current/userguide/command_line_interface.html#common_tasks).

This project follows the suggested multi-module setup and consists of the `app` and `model` subprojects.
The shared build logic was extracted to a convention plugin located in `buildSrc`.

This project uses a version catalog (see `gradle/libs.versions.toml`) to declare and version dependencies
and both a build cache and a configuration cache (see `gradle.properties`).

## Open Issues

- Offer separate game modi for standard and expert. Recycling, stacking for small technologies and single-use supply are implemented but disabled in standard mode. Expert mode still needs a way to actually turn them on.
- Set `gameBeforeEffect` in `ProgressCardData.kt`. It is currently always `null`.
- Add the technology energy matrix to the GameData telling the current energy supply totals.
- possibly more ...