#!/bin/bash

# build a whole Android application
# wrapper of android/ndk-build/ant/adb tools

if [[ $# -ne 1 ]] && [[ $# -ne 2 ]]; then
    echo "Usage: ./make <id> <release>"
    echo "example: ./make android-23 1"
else
    targetId=${1}
    ver=debug
    if [[ $# -eq 2 ]]; then
        if [[ ${2} -eq 1 ]]; then
            ver=release
        fi
    fi
    curPath=`pwd`
    apkName=`basename ${curPath}`
    echo "1. generate build configuration files ..."
    android update project -n ${apkName} -p . -t ${targetId} 1> /dev/null
    echo "2. build NDK libraries ..."
    if [[ -d ./jni ]]; then
        ndk-build clean 1> /dev/null
        ndk-build 1> /dev/null
    fi
    echo "3. build APK ..."
    ant clean 1> /dev/null
    ant ${ver} 1> /dev/null
    echo "4. install APK ..."
    echo "----------"
    adb devices | grep -w "device" 1> /dev/null
    if [[ $? -eq 0 ]]; then
        if [[ "${ver}" == "debug" ]]; then
            adb install -r bin/${apkName}-debug.apk
        else
            echo ">>> NOTE: type the passowrd as requested: 122333\n"
            jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore my.key bin/${apkName}-${ver}-unsigned.apk test
            zipalign -v 4 bin/${apkName}-${ver}-unsigned.apk ${apkName}-signed.apk
            adb install -r ${apkName}-signed.apk
        fi
    else
        echo "Error: device NOT found!"
    fi
fi
