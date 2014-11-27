LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := eng
LOCAL_PRELINK_MODULE := false
LOCAL_MODULE := libdowncall
LOCAL_SRC_FILES := downcall.c
LOCAL_LDFLAGS := -llog

include $(BUILD_SHARED_LIBRARY)
