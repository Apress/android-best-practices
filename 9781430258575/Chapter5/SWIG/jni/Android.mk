LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := unix

LOCAL_SRC_FILES := com_apress_example_Unix.c

include $(BUILD_SHARED_LIBRARY)

