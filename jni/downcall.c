#include "_log.h"
#include <string.h>
#include <jni.h>
#include <android/log.h>

jstring Java_com_young_jniinterface_DowncallActivity_downcallMtd1(JNIEnv *env, jobject obj) {
    LOGI("trigger downcall! (%s)", __func__);
    return (*env)->NewStringUTF(env, "Here is in downcall method 1");
}

jstring Java_com_young_jniinterface_DowncallActivity_downcallMtd2(JNIEnv *env, jobject obj) {
    LOGI("trigger downcall! (%s)", __func__);
    return (*env)->NewStringUTF(env, "Here is in downcall method 2");
}
