#include "_log.h"
#include <string.h>
#include <jni.h>
#include <dlfcn.h>

jstring Java_com_young_jniinterface_DowncallActivity_downcallMtd1(JNIEnv *env, jobject obj) {
    LOGI("trigger downcall! (%s)", __func__);
    return (*env)->NewStringUTF(env, "Here is in downcall method 1");
}

jstring Java_com_young_jniinterface_DowncallActivity_downcallMtd2(JNIEnv *env, jobject obj) {
    LOGI("trigger downcall! (%s)", __func__);

    void *handle = NULL;
    void (*callfunc)();
    const char *err = NULL;

    handle = dlopen("/data/data/com.young.jniinterface/lib/libcallee.so", RTLD_NOW);
    if (!handle) {
        LOGI("dlopen failed! (%s)", dlerror());
        return (*env)->NewStringUTF(env, "Error: dlopen");
    }

    callfunc = dlsym(handle, "callee");
    // NOTE: dlerror returns 'Symbol not found' though dlsym returns valid address
    if (callfunc != NULL) {
        callfunc();
    }

    dlclose(handle);

    return (*env)->NewStringUTF(env, "Here is in downcall method 2");
}
