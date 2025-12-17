# RadioDebug

Tool to investigate `RadioManager` capabilities.

## Setup

### adjust repo

First you need to instruct repo to add `RadioDebug` project to your AOSP sources.

Copy `local_manifests/*.xml` into your AOSP's `.root/local_manifests`.

```shell
AOSP_ROOT=/sources/aosp

mkdir -p $AOSP_ROOT/.repo/local_manifests
cp ./local_manifests/*.xml $AOSP_ROOT/.repo/local_manifests/
```

### sync repo

Sync sources, at least this project, e.g.
```shell
repo sync vendor/foqus/RadioDebug
```

## Building

```shell
mmm vendor/foqus/RadioDebug
```
## Integrating into your app

In your app's `Android.bp` add
```
    static_libs: [
        ...
        "RadioDebug",
    ],
```

Then use it in code
```kotlin
    val radioManager = RadioManagerExt(context)
    lifecycleScope.launch {
        // open first tuner (index = 0)
        radioManager.openTuner(index = 0).collect {
            // do sth with provided program
        }
    }
```
