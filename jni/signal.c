#include "_log.h"
#include <jni.h>
#include <signal.h>
#include <string.h>
#include <pthread.h>

void sigHandler1(int signo) {
    switch (signo) {
        case SIGPIPE:
            LOGI("catch a signal - SIGPIPE");
            break;
        case SIGUSR2:
            LOGI("catch a signal - SIGUSR2");
            break;
        default:
            LOGI("wrong signo!");
            break;
    }
}

jstring Java_com_young_jniinterface_SignalActivity_SignalTest1(JNIEnv *env, jobject obj) {
    signal(SIGUSR2, sigHandler1); // call sigaction syscall
    LOGI("register signal SIGUSR2 (pid = %d)", getpid());
    return (*env)->NewStringUTF(env, "register signal SIGUSR2");
}

jstring Java_com_young_jniinterface_SignalActivity_SignalTest2(JNIEnv *env, jobject obj) {
    int pid = getpid();
    LOGI("send SIGUSR2 to self process (pid = %d)", getpid());
    tkill(pid, SIGUSR2);
    return (*env)->NewStringUTF(env, "send SIGUSR2 to self");
}

void *thrHandler3(void *arg)
{
    sigset_t set;
    sigemptyset(&set);
    sigaddset(&set, SIGUSR2);
    LOGI("[child %d] mask SIGUSR2 and suspend self", gettid());
    int ret = sigsuspend(&set);
    LOGI("[child %d] go on, sigsuspend returns %d", gettid(), ret);
}

jstring Java_com_young_jniinterface_SignalActivity_SignalTest3(JNIEnv *env, jobject obj) {
    // must register SIGPIPE, or else will not execute pending SIGUSR2 sigHandler
    signal(SIGPIPE, sigHandler1);
    signal(SIGUSR2, sigHandler1);
    pthread_t thr;
    int err = pthread_create(&thr, NULL, thrHandler3, NULL);
    if (err != 0) {
        LOGI("pthread_create failed (%s)", strerror(err));
        return (*env)->NewStringUTF(env, "Error: pthread_create");
    }
    LOGI("[parent %d] parent will sleep 1s, and tkill child SIGUSR2", gettid());
    sleep(1);
    pthread_kill(thr, SIGUSR2); // call tkill syscall
    LOGI("[parent %d] parent will sleep 1s, and tkill child SIGPIPE", gettid());
    sleep(1);
    pthread_kill(thr, SIGPIPE);
    pthread_join(thr, NULL);
    return (*env)->NewStringUTF(env, "child sigsuspends SIGUSR2, parent tkills SIGUSR2 and SIGPIPE");
}
