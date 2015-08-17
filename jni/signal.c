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

jstring Java_com_young_jniinterface_SignalActivity_SignalTest1(JNIEnv *env, jobject obj) {
    signal(SIGUSR2, sigHandler1);
    LOGI("register signal SIGUSR2 (pid = %d)", getpid());
    return (*env)->NewStringUTF(env, "register signal SIGUSR2");
}
