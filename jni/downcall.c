#include "_log.h"
#include <string.h>
#include <jni.h>
#include <dlfcn.h>
#include <stdio.h>
#include <fcntl.h>

jstring Java_com_young_jniinterface_DowncallActivity_downcallMtd1(JNIEnv *env, jobject obj, jint i1, jlong i2, jfloat i3) {
    LOGI("trigger downcall! (%s)", __func__);
    LOGI("env = %p", env);
    return (*env)->NewStringUTF(env, "Here is in downcall method 1");
}

jboolean Java_com_young_jniinterface_DowncallActivity_downcallMtd2(JNIEnv *env, jobject obj, jint i1, jlong i2, jfloat i3) {
    LOGI("trigger downcall! (%s)", __func__);
    LOGI("i1 = %d, i2 = %llx, i3 = %f\n", i1, i2, i3);
    return ((i1 == -1) && (i2 == (jlong)0x1234567890abcdef) && (i3 == (jfloat)3.14f));
}

int my_read(void *cookie, char *data, int n)
{
    return read((int)(long)cookie, data, n);
}

jstring Java_com_young_jniinterface_DowncallActivity_downcallMtd3(JNIEnv *env, jobject obj) {
    LOGI("trigger downcall! (%s)", __func__);

    int fd;
    FILE *fp;
    int i;
    char buf[5];
    int ret;

    fd = open("/data/young/test", O_RDONLY);
    if (fd == -1) {
        LOGE("open failed! (no /data/young/test file (content:12345))");
    }

    fp = funopen((void*)(long)fd, my_read, NULL, NULL, NULL);
    if (fp == NULL) {
        LOGE("funopen failed!!");
        close(fd);
    }

    ret = fread(buf, sizeof(char), 5, fp);
    if (ret != 5) {
        LOGE("fread failed!");
    }

    for (i = 0; i < 5; i++) {
        LOGI("(%d): %c", i, buf[i]);
    }

    close(fd);
    fclose(fp);

    return (*env)->NewStringUTF(env, "Here is in downcall method 3");
}

jstring Java_com_young_jniinterface_DowncallActivity_downcallMtd4(JNIEnv *env, jobject obj) {
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
    if (callfunc == NULL) {
        LOGI("dlsym failed! (%s)", dlerror());
        return (*env)->NewStringUTF(env, "Error: dlsym");
    }

    callfunc();
    dlclose(handle);

    return (*env)->NewStringUTF(env, "Here is in downcall method 4");
}

jboolean Java_com_young_jniinterface_DowncallActivity_downcallMtd5(JNIEnv *env, jobject obj, jint i1, jint i2) {
    return (i1 == 100 && i2 == 200);
}
