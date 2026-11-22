# MD

A work-in-progress Minecraft Forge **1.12.2** mod adding some stuff from the Murder Drones franchise.
As of now only adds the disassembly drone morph with custom models and animations. Also a cool bloom effect.

## Build and run

Requires JDK 8 for the build and JDK 17-25 for gradle wrapper.

```sh
./gradlew build
```

The mod JAR is written to `build/libs/`.
The first build requires internet access to download Forge and other dependencies.

```sh
./gradlew runClient
```
To run a game client. You may need to download ForgeSideFix and put it into run/mods.


## Try the morph

With cheats enabled:

```text
/setmorph <player> DD
```
Check controls in ingame settings.

```text
/setoil <player> <amount>
```
You may need to give yourself some oil to test all the features.

