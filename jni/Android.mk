LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := libdowncall
LOCAL_SRC_FILES := downcall.c
LOCAL_LDFLAGS := -llog

include $(BUILD_SHARED_LIBRARY)
