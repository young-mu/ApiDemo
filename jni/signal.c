#include "_log.h"
#include <jni.h>
#include <signal.h>
#include <assert.h>

void sigHandler1(int signo) {
    switch (signo) {
        case SIGUSR1:
            LOGI("catch a signal - SIGUSR1\n");
            break;
        case SIGUSR2:
            LOGI("catch a signal - SIGUSR2\n");
            break;
        default:
            LOGI("wrong signo!\n");
            break;
    }
}

jstring Java_com_young_jniinterface_SignalActivity_downcallMtd1(JNIEnv *env, jobject obj) {
    LOGI("trigger downcall! (%s)", __func__);

    signal(SIGUSR1, sigHandler1);
    signal(SIGUSR2, sigHandler1);

    LOGI("raise SIGUSR1");
    raise(SIGUSR1);
    LOGI("raise SIGUSR2");
    raise(SIGUSR2);

    // TODO
    // 1. SIGUSR1 no response
    // 2. show this message on activity
    return (*env)->NewStringUTF(env, "Here is in signal method using raise");
}
