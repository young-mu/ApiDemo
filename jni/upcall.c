#include "_log.h"
#include <string.h>
#include <jni.h>

jstring Java_com_young_jniinterface_UpcallActivity_downcallMtd1(JNIEnv *env, jobject obj) {
    LOGI("trigger downcall! (%s)", __func__);

    // get class
    jclass clazz;
    clazz = (*env)->GetObjectClass(env, obj);
    if (clazz == NULL) {
        LOGE("GetObjectClass failed!");
        return NULL;
    }

    // get static method
    jmethodID staticUpcall = (*env)->GetStaticMethodID(env, clazz, "staticUpcall", "(Ljava/lang/String;)Ljava/lang/String;");
    if (staticUpcall == NULL) {
        LOGE("GetStaticMethodID failed!");
        (*env)->DeleteLocalRef(env, clazz);
        return NULL;
    }

    // call static method
    char *param = "static";
    jstring jparam = (*env)->NewStringUTF(env, param);
    return (jstring)(*env)->CallStaticObjectMethod(env, clazz, staticUpcall, jparam);
}

jstring Java_com_young_jniinterface_UpcallActivity_downcallMtd2(JNIEnv *env, jobject obj) {
    LOGI("trigger downcall! (%s)", __func__);

    // get class
    jclass clazz;
    clazz = (*env)->FindClass(env, "com/young/jniinterface/UpcallActivity");
    if (clazz == NULL) {
        LOGE("FindClass failed!");
        return NULL;
    }

    // get object
    jmethodID constructor = (*env)->GetMethodID(env, clazz, "<init>", "()V");
    if (constructor == NULL) {
        LOGE("GetMethodID failed!");
        (*env)->DeleteLocalRef(env, clazz);
        return NULL;
    }
    jobject jobj = (*env)->NewObject(env, clazz, constructor);
    if (jobj == NULL) {
        LOGE("NewObject failed!");
        (*env)->DeleteLocalRef(env, clazz);
        return NULL;
    }

    // get attribute
    jfieldID str = (*env)->GetFieldID(env, clazz, "str", "Ljava/lang/String;");
    if (str == NULL) {
        LOGE("GetFieldID failed!");
        return NULL;
    }

    // modify attribute
    char *new_str = "non-static";
    jstring jnew_str = (*env)->NewStringUTF(env, new_str);
    (*env)->SetObjectField(env, jobj, str, jnew_str);

    // get nonstatic method
    jmethodID nonstaticUpcall = (*env)->GetMethodID(env, clazz, "nonstaticUpcall", "()Ljava/lang/String;");
    if (nonstaticUpcall == NULL) {
        LOGE("GetMethodID failed!");
        (*env)->DeleteLocalRef(env, clazz);
        (*env)->DeleteLocalRef(env, jobj);
        return NULL;
    }

    // call nonstatic method
    return (jstring)(*env)->CallObjectMethod(env, jobj, nonstaticUpcall);
}

// NewObject
jint Java_com_young_jniinterface_UpcallActivity_downcallMtd3(JNIEnv *env, jobject obj)
{
    LOGI("trigger downcall! (%s)", __func__);

    // get class
    jclass clazz;
    clazz = (*env)->FindClass(env, (char*)"com/young/jniinterface/testClass");
    if (clazz == NULL) {
        LOGE("FindClass failed!");
        return -1;
    }

    // get object
    jmethodID constructor = (*env)->GetMethodID(env, clazz, "<init>", "(I)V");
    if (constructor == NULL) {
        LOGE("GetMethodID failed!");
        (*env)->DeleteLocalRef(env, clazz);
        return -1;
    }
    jobject jobj = (*env)->NewObject(env, clazz, constructor, 100);
    if (jobj == NULL) {
        LOGE("NewObject failed!");
        (*env)->DeleteLocalRef(env, clazz);
        return -1;
    }

    // get attribute
    jfieldID init = (*env)->GetFieldID(env, clazz, "init", "I");
    if (init == 0) {
        LOGE("GetFieldID failed!");
        return -1;
    }

    jint initVal = (*env)->GetIntField(env, jobj, init);

    return initVal;
}

// NewObjectV
jint Java_com_young_jniinterface_UpcallActivity_downcallMtd4(JNIEnv *env, jobject obj, ...)
{
    LOGI("trigger downcall! (%s)", __func__);

    // get class
    jclass clazz;
    clazz = (*env)->FindClass(env, (char*)"com/young/jniinterface/testClass");
    if (clazz == NULL) {
        LOGE("FindClass failed!");
        return -1;
    }

    // get object
    jmethodID constructor = (*env)->GetMethodID(env, clazz, "<init>", "(I)V");
    if (constructor == NULL) {
        LOGE("GetMethodID failed!");
        (*env)->DeleteLocalRef(env, clazz);
        return -1;
    }
    va_list args;
    va_start(args, obj);
    jobject jobj = (*env)->NewObjectV(env, clazz, constructor, args);
    if (jobj == NULL) {
        LOGE("NewObject failed!");
        (*env)->DeleteLocalRef(env, clazz);
        return -1;
    }
    va_end(args);

    // get attribute
    jfieldID init = (*env)->GetFieldID(env, clazz, "init", "I");
    if (init == 0) {
        LOGE("GetFieldID failed!");
        return -1;
    }

    jint initVal = (*env)->GetIntField(env, jobj, init);

    return initVal;
}

// NewObjectA
jint Java_com_young_jniinterface_UpcallActivity_downcallMtd5(JNIEnv *env, jobject obj)
{
    LOGI("trigger downcall! (%s)", __func__);

    // get class
    jclass clazz;
    clazz = (*env)->FindClass(env, (char*)"com/young/jniinterface/testClass");
    if (clazz == NULL) {
        LOGE("FindClass failed!");
        return -1;
    }

    // get object
    jmethodID constructor = (*env)->GetMethodID(env, clazz, "<init>", "(I)V");
    if (constructor == NULL) {
        LOGE("GetMethodID failed!");
        (*env)->DeleteLocalRef(env, clazz);
        return -1;
    }
    jvalue args;
    args.i = 100;
    jobject jobj = (*env)->NewObjectA(env, clazz, constructor, &args);
    if (jobj == NULL) {
        LOGE("NewObject failed!");
        (*env)->DeleteLocalRef(env, clazz);
        return -1;
    }

    // get attribute
    jfieldID init = (*env)->GetFieldID(env, clazz, "init", "I");
    if (init == 0) {
        LOGE("GetFieldID failed!");
        return -1;
    }

    jint initVal = (*env)->GetIntField(env, jobj, init);

    return initVal;
}
