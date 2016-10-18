#include "com_apress_example_MainActivity.h"

#include "my_log.h"

JNIEXPORT void JNICALL Java_com_apress_example_MainActivity_logMessages(JNIEnv* env, jclass clazz) {
    MY_LOG_VERBOSE("The native method is called.");

    MY_LOG_DEBUG("env=%p thiz=%p", env, thiz);

    MY_LOG_ASSERT(0 != env, "JNIEnv cannot be NULL.");
}
