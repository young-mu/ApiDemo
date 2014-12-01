#include "_log.h"
#include <string.h>
#include <jni.h>

#define NELEM(x) ((int)(sizeof(x) / sizeof(x[0])))

jstring DowncallOnloadActivity_downcallOnloadMtd1(JNIEnv *env, jobject obj) {
    LOGI("trigger downcall! (%s)", __func__);
    return (*env)->NewStringUTF(env, "Here is in downcall onload method 1");
}

jstring DowncallOnloadActivity_downcallOnloadMtd2(JNIEnv *env, jobject obj) {
    LOGI("trigger downcall! (%s)", __func__);
    return (*env)->NewStringUTF(env, "Here is in downcall onload method 2");
}

static JNINativeMethod gMethods[] = {
    {"downcallOnloadMtd1", "()Ljava/lang/String;", (void*)DowncallOnloadActivity_downcallOnloadMtd1},
    {"downcallOnloadMtd2", "()Ljava/lang/String;", (void*)DowncallOnloadActivity_downcallOnloadMtd2},
};

int JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    if ((*vm)->GetEnv(vm, (void**)&env, JNI_VERSION_1_4) != JNI_OK) {
        LOGE("GetEnv failed!");
        return JNI_ERR;
    }

    jclass clazz;
    clazz = (*env)->FindClass(env, "com/young/jniinterface/DowncallOnloadActivity");
    if (clazz == NULL) {
        LOGE("FindClass failed!");
        return JNI_ERR;
    }

    LOGI("call RegisterNatives");
    if ((*env)->RegisterNatives(env, clazz, gMethods, NELEM(gMethods)) < 0) {
        LOGE("RegisterNatives failed!");
        return JNI_ERR;
    }

    return JNI_VERSION_1_4;
}
