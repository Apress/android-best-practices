LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := logger

# Define the log tag
MY_LOG_TAG := \"module\"

# Define the default logging level based build type
ifeq ($(APP_OPTIM),release)
  MY_LOG_LEVEL := MY_LOG_LEVEL_ERROR
else
  MY_LOG_LEVEL := MY_LOG_LEVEL_VERBOSE
endif

# Appending the compiler flags
LOCAL_CFLAGS += -DMY_LOG_TAG=$(MY_LOG_TAG)
LOCAL_CFLAGS += -DMY_LOG_LEVEL=$(MY_LOG_LEVEL)

LOCAL_SRC_FILES := com_apress_example_MainActivity.cpp

# Dynamically linking with the log library
LOCAL_LDLIBS += -llog

include $(BUILD_SHARED_LIBRARY)

