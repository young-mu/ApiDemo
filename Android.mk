LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := eng
LOCAL_SRC_FILES := $(call all-java-files-under, src)
LOCAL_PACKAGE_NAME := JNIitf

LOCAL_JNI_SHARED_LIBRARIES := libdowncall
include $(BUILD_PACKAGE)

include $(LOCAL_PATH)/jni/Android.mk
include $(call all-makefiles-under, $(LOCAL_PATH))
