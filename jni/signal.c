#include "_log.h"
#include <string.h>
#include <jni.h>
#include <dlfcn.h>
#include <stdio.h>
#include <fcntl.h>

jstring Java_com_young_jniinterface_SignalActivity_downcallMtd1(JNIEnv *env, jobject obj) {
    LOGI("trigger downcall! (%s)", __func__);
    return (*env)->NewStringUTF(env, "Here is in signal method 1");
}
