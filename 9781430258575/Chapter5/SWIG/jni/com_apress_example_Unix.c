#include "com_apress_example_Unix.h"

#include <unistd.h>

JNIEXPORT jstring JNICALL Java_com_apress_example_Unix_getlogin(JNIEnv* env, jclass clazz) {
    jstring loginString = 0;

    const char* login = getlogin();
    if (0 != login) {
        loginString = (*env)->NewStringUTF(env, login);
    }

    return loginString;
}
