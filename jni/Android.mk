LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := downcall
LOCAL_SRC_FILES := downcall.c
LOCAL_LDFLAGS := -llog
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := downcall_onload
LOCAL_SRC_FILES := downcall_onload.c
LOCAL_LDFLAGS := -llog
include $(BUILD_SHARED_LIBRARY)

