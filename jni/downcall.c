#include "_log.h"
#include <string.h>
#include <jni.h>
#include <android/log.h>

jstring Java_com_young_jniinterface_DowncallActivity_downcall(JNIEnv *env, jobject obj)
{
    LOGI("downcall ...");
    return (*env)->NewStringUTF(env, "Here is in downcall");
}
