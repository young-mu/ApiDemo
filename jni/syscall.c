#include "_log.h"
#include <stdio.h>
#include <jni.h>
#include <time.h>

jstring Java_com_young_ApiDemo_ndk_SyscallActivity_SyscallTest1(JNIEnv *env, jobject obj)
{
    struct timespec ts;
    ts.tv_sec = 1;
    ts.tv_nsec = 500000000;
    int ret;
    ret = nanosleep(&ts, NULL);

    char retstr[50];
    snprintf(retstr, sizeof(retstr), "nanosleep for 1.5s, return %d", ret);

    return (*env)->NewStringUTF(env, retstr);
}
