#include "_log.h"
#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <pthread.h>
#include <sys/prctl.h>

#define THREAD_NUM 5

JavaVM *gjvm = NULL;
jobject *gobj = NULL;

const char *namePrefix = "thread";

void *threadFunc(void *arg) {
    // set thread name
    char thrName[10];
    snprintf(thrName, sizeof(thrName), "%s-%d", namePrefix, (int)(long)arg);
    prctl(PR_SET_NAME, (unsigned long)thrName);
    sleep(3);

    // child thread attachs to main thread to get JNIenv pointer
    // after this operation, thread name will be changed as Thread-n
    JNIEnv *env = NULL;
    if ((*gjvm)->AttachCurrentThread(gjvm, &env, NULL) != JNI_OK) {
        LOGE("AttachCurrentThread failed!");
        pthread_exit(0);
    }

    LOGI("I'm thread [%d]", gettid());

    // get class
    jclass clazz;
    clazz = (*env)->GetObjectClass(env, gobj);
    if (clazz == NULL) {
        LOGE("GetObjectClass failed!");
        goto err;
    }

    // get static method
    jmethodID invoke = (*env)->GetStaticMethodID(env, clazz, "invoke", "(I)V");
    if (invoke == NULL) {
        LOGE("GetStaticMethodID failed!");
        goto err;
    }

    // call static method
    (*env)->CallStaticVoidMethod(env, clazz, invoke, (jobjectArray)arg);

err:
    if ((*gjvm )->DetachCurrentThread(gjvm) != JNI_OK) {
        LOGE("DetachCurrentThread failed!");
    }
    pthread_exit(0);
}

void Java_com_young_ApiDemo_ndk_jni_InvokeActivity_globalizeVar(JNIEnv *env, jobject obj) {
    LOGI("trigger downcall! (%s)", __func__);
    (*env)->GetJavaVM(env, &gjvm);
    gobj = (*env)->NewGlobalRef(env, obj);
}

void Java_com_young_ApiDemo_ndk_jni_InvokeActivity_mainThread(JNIEnv *env, jobject obj) {
    LOGI("trigger downcall! (%s)", __func__);
    long i;
    pthread_t pt[5];

    // create child thread
    for (i = 0; i < THREAD_NUM; i++) {
        pthread_create(&pt[i], NULL, &threadFunc, (void*)i);
    }

    // wait for child thread exit
    for (i = 0; i < THREAD_NUM; i++) {
        pthread_join(pt[i], NULL);
    }
}

int JNI_OnLoad(JavaVM *vm, void *reserved) {
    // get JNIenv pointer for main thread
    JNIEnv *env = NULL;
    if ((*vm)->GetEnv(vm, (void**)&env, JNI_VERSION_1_4) != JNI_OK) {
        LOGE("GetEnv failed!");
        return JNI_ERR;
    }

    return JNI_VERSION_1_4;
}
