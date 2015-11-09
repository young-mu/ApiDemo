#include "_log.h"
#include <string.h>
#include <jni.h>
#include <dlfcn.h>
#include <sys/ptrace.h>

#define NELEM(x) ((int)(sizeof(x) / sizeof(x[0])))

jstring DowncallOnloadActivity_downcallOnloadMtd1(JNIEnv *env, jobject obj)
{
    LOGI("trigger downcall! (%s)", __func__);
    return (*env)->NewStringUTF(env, "Here is in downcall onload method 1");
}

jstring DowncallOnloadActivity_downcallOnloadMtd2(JNIEnv *env, jobject obj)
{
    LOGI("trigger downcall! (%s)", __func__);

    void *handle = NULL;
    void (*callfunc)();
    const char *err = NULL;

    handle = dlopen("/data/data/com.young.ApiDemo/lib/libcallee.so", RTLD_NOW);
    if (!handle) {
        LOGI("dlopen failed! (%s)", dlerror());
        return (*env)->NewStringUTF(env, "Error: dlopen");
    }

    callfunc = dlsym(handle, "callee");
    // NOTE: dlerror returns 'Symbol not found' though dlsym returns valid address
    if (callfunc == NULL) {
        LOGI("dlsym failed! (%s)", dlerror());
        return (*env)->NewStringUTF(env, "Error: dlsym");
    }

    callfunc();
    dlclose(handle);

    return (*env)->NewStringUTF(env, "Here is in downcall onload method 2");
}

void enable_antidebug(void)
{
    ptrace(PTRACE_TRACEME, 0, NULL, NULL);
}

void fork_antidebug_process_for_SELinux(void)
{
    pid_t child;
    child = fork();
    if (0 == child) {
        ptrace(PTRACE_TRACEME, 0, NULL, NULL);
        LOGI("I will sleep for 20s");
        sleep(20);
        // do something secret in this child process
    } else {
        wait(NULL);
        LOGI("the child has exited");
    }
}

// const char *name, const char *signature, void *fnPtr
static JNINativeMethod gMethods[] = {
    {"downcallOnloadMtd1", "()Ljava/lang/String;", (void*)DowncallOnloadActivity_downcallOnloadMtd1},
    {"downcallOnloadMtd2", "()Ljava/lang/String;", (void*)DowncallOnloadActivity_downcallOnloadMtd2},
};

// called when System.loadLiabray executes
int JNI_OnLoad(JavaVM *vm, void *reserved)
{
    LOGI("%s...", __func__);
    JNIEnv *env = NULL;
    if ((*vm)->GetEnv(vm, (void**)&env, JNI_VERSION_1_4) != JNI_OK) {
        LOGE("GetEnv failed!");
        return JNI_ERR;
    }

    jclass clazz;
    clazz = (*env)->FindClass(env, "com/young/ApiDemo/ndk/jni/DowncallOnloadActivity");
    if (clazz == NULL) {
        LOGE("FindClass failed!");
        return JNI_ERR;
    }

    LOGI("call RegisterNatives");
    if ((*env)->RegisterNatives(env, clazz, gMethods, NELEM(gMethods)) < 0) {
        LOGE("RegisterNatives failed!");
        return JNI_ERR;
    }

    //LOGI("fork an anti-debug process");
    enable_antidebug();
    //fork_antidebug_process_for_SELinux();

    return JNI_VERSION_1_4;
}

// called when the classloader is garbage collected
void JNI_OnUnload(JavaVM *vm, void *reserved)
{
    LOGI("%s...", __func__);
    JNIEnv *env = NULL;
    if ((*vm)->GetEnv(vm, (void**)&env, JNI_VERSION_1_4) != JNI_OK) {
        LOGE("GetEnv failed!");
        return;
    }

    jclass clazz;
    clazz = (*env)->FindClass(env, "com/young/ApiDemo/ndk/jni/DowncallOnloadActivity");
    if (clazz == NULL) {
        LOGE("FindClass failed!");
        return;
    }

    LOGI("call UnregisterNatives");
    if ((*env)->UnregisterNatives(env, clazz) < 0) {
        LOGE("UnregisterNatives failed!");
        return;
    }
}
