# RadioDebug

Tool to investigate `RadioManager` capabilities.

## Setup

### adjust repo

First you need to instruct repo to add `RadioDebug` project to your AOSP sources.

Copy `local_manifests/*.xml` into your AOSP's `.root/local_manifests` adjusting `0_remote_local_fs.xml` making `fetch` pointing the folder containing this project, e.g. for `/mnt/sources_root/RadioDebug` you can execute

```shell
AOSP_ROOT=/sources/aosp

mkdir -p $AOSP_ROOT/local_manifests
cp ./local_manifests/*.xml $AOSP_ROOT/local_manifests/

cat local_manifests/0_remote_local_fs.xml | sed 's%##LOCAL_FS_ROOT##%"file:///sources"%' > $AOSP_ROOT/.repo/local_manifests/0_remote_local_fs.xml
```

### sync repo

Sync sources, at least this project, e.g.
```
repo sync vendor/foqus/RadioDebug
```

## Building (TODO)

