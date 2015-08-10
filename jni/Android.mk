LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := callee
LOCAL_SRC_FILES := callee.c
LOCAL_CFLAGS := -g -O0
LOCAL_LDLIBS := -llog
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := downcall
LOCAL_SRC_FILES := downcall.c
LOCAL_CFLAGS := -g -O0
LOCAL_LDLIBS := -llog -ldl
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := downcall_skia
LOCAL_SRC_FILES := downcall_skia.cpp
LOCAL_C_INCLUDES := /home/young/AOSP/Lollipop/frameworks/base/core/jni/android/graphics \
					/home/young/AOSP/Lollipop/external/skia/include/core \
					/home/young/AOSP/Lollipop/system/core/include
LOCAL_CFLAGS := -g -O0
LOCAL_LDLIBS := -llog -L /home/young/Debug/n9/symbols/system/lib -lskia -landroid_runtime -ljnigraphics
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := downcall_onload
LOCAL_SRC_FILES := downcall_onload.c
LOCAL_CFLAGS := -g -O0
LOCAL_LDLIBS := -llog -ldl
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := upcall
LOCAL_SRC_FILES := upcall.c
LOCAL_CFLAGS := -g -O0
LOCAL_LDLIBS := -llog
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := invoke
LOCAL_SRC_FILES := invoke.c
LOCAL_LDLIBS := -llog
include $(BUILD_SHARED_LIBRARY)
