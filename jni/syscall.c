#include "_log.h"
#include <jni.h>
#include <time.h>

jstring Java_com_young_jniinterface_SyscallActivity_SyscallTest1(JNIEnv *env, jobject obj)
{
    struct timespec ts;
    ts.tv_sec = 1;
    ts.tv_nsec = 500000000;
    nanosleep(&ts, NULL);

    return (*env)->NewStringUTF(env, "nanosleep for 1.5s");
}
