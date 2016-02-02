#include "_log.h"
#include <string.h>
#include <jni.h>
#include <dlfcn.h>
#include <stdio.h>
#include <fcntl.h>

void __attribute__((constructor(150))) init_func1(void) // 0-100 is for system
{
    LOGI("I'm constructor [%s] with priority 150", __func__);
}

void __attribute__((constructor(200))) init_func2(void)
{
    LOGI("I'm constructor [%s] with priority 200", __func__);
}

void __attribute__((constructor())) init_func3(void)
{
    LOGI("I'm constructor [%s] with priority null", __func__);
}

void __attribute__((destructor())) fini_func1(void)
{
    LOGI("I'm destructor [%s] with priority null", __func__);
}

void __attribute__((destructor(200))) fini_func2(void)
{
    LOGI("I'm destructor [%s] with priority 200", __func__);
}

void __attribute__((destructor(150))) fini_func3(void)
{
    LOGI("I'm destructor [%s] with priority 150", __func__);
}

jstring Java_com_young_ApiDemo_ndk_jni_DowncallActivity_simpleDowncall(JNIEnv *env, jobject obj) {
    LOGI("enter downcall [%s]", __func__);

    int numUpcall = (int)(sizeof(**env) / sizeof(long));
    LOGI("number of upcalls = %d", numUpcall);

    LOGI("[stack] addr of &env = %p", &env);
    LOGI("[heap] addr of env = %p", env);
    LOGI("[libart.so rodata] addr of (*env) = %p", (*env));
    LOGI("[libart.so code] addr of (*env)->GetVersion = %p", (*env)->GetVersion);

    return (*env)->NewStringUTF(env, "Here is in downcall method 1");
}

jboolean Java_com_young_ApiDemo_ndk_jni_DowncallActivity_paramTest(JNIEnv *env, jobject obj, jint i1, jlong i2, jfloat i3) {
    LOGI("enter downcall [%s]", __func__);
    LOGI("i1 = %d, i2 = %llx, i3 = %f\n", i1, i2, i3);
    return ((i1 == -1) && (i2 == (jlong)0x1234567890abcdefL) && (i3 == (jfloat)-3.14F));
}

int my_read(void *cookie, char *data, int n)
{
    return read((int)(long)cookie, data, n);
}

jstring Java_com_young_ApiDemo_ndk_jni_DowncallActivity_funopenCallback(JNIEnv *env, jobject obj) {
    LOGI("enter downcall [%s]", __func__);

    int fd;
    FILE *fp;
    int i;
    char buf[5];
    int ret;

    fd = open("/data/data/com.young.ApiDemo/files/test", O_RDONLY);
    if (fd == -1) {
        LOGE("open failed!");
        goto out1;
    }

    fp = funopen((void*)(long)fd, my_read, NULL, NULL, NULL);
    if (fp == NULL) {
        LOGE("funopen failed!!");
        goto out1;
    }

    ret = fread(buf, sizeof(char), 5, fp);
    if (ret != 5) {
        LOGE("fread failed!");
        goto out2;
    }

    for (i = 0; i < 5; i++) {
        LOGI("buf[%d] = %c", i, buf[i]);
    }

out2:
    fclose(fp);
out1:
    close(fd);

    return (*env)->NewStringUTF(env, "Here is in downcall method 3");
}

int (*p_read)(void *, char *, int);

int my_read2(void *cookie, char *data, int n)
{
    return p_read(cookie, data, n);
}

jstring Java_com_young_ApiDemo_ndk_jni_DowncallActivity_fgetsCallback(JNIEnv *env, jobject obj) {
    LOGI("enter downcall [%s]", __func__);

    do {
        FILE *fp = fopen("/data/data/com.young.ApiDemo/files/test", "r");
        if (fp == NULL) {
            LOGE("open failed!");
            break;
        }

        // change callback
        p_read = fp->_read;
        fp->_read = my_read2;

        char buf[20];

        while (fgets(buf, 10, fp) != NULL) {
            LOGI("buf: %s", buf);
        }
    } while (0);

    return (*env)->NewStringUTF(env, "Here is in downcall method 4");
}

jstring Java_com_young_ApiDemo_ndk_jni_DowncallActivity_dlopenLib(JNIEnv *env, jobject obj) {
    LOGI("enter downcall [%s]", __func__);

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

    Dl_info dlinfo;
    int found = dladdr((void*)callfunc, &dlinfo);
    if (found) {
        LOGI("symbol [name] = %s, [address] = %p, [library] = %s, [library baseaddr] = %p\n", dlinfo.dli_sname, dlinfo.dli_saddr, dlinfo.dli_fname, dlinfo.dli_fbase);
    }

    callfunc();
    dlclose(handle);

    return (*env)->NewStringUTF(env, "Here is in downcall method 5");
}

jboolean Java_com_young_ApiDemo_ndk_jni_DowncallActivity_perfTest(JNIEnv *env, jobject obj, jint i1, jint i2) {
    return (i1 == 100 && i2 == 200);
}
