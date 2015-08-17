#include "_log.h"
#include <jni.h>
#include <signal.h>
#include <assert.h>

void sigHandler1(int signo) {
    switch (signo) {
        case SIGUSR2:
            LOGI("catch a signal - SIGUSR2\n");
            break;
        default:
            LOGI("wrong signo!\n");
            break;
    }
}

jstring Java_com_young_jniinterface_SignalActivity_downcallMtd1(JNIEnv *env, jobject obj) {
    signal(SIGUSR2, sigHandler1);
    return (*env)->NewStringUTF(env, "register signal SIGUSR2");
}
