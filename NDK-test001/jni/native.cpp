#include <jni.h>  
#include <string.h>   
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>  

#include <EGL/egl.h>
#include <GLES/gl.h>

#define DEBUG_TAG "MainActivity"  

extern "C" {
	void Java_com_example_ndktest001_MainActivity_helloLog(JNIEnv * env, jobject obj, jstring logThis);
	jstring Java_com_example_ndktest001_MainActivity_getString(JNIEnv * env, jobject obj, jint value1, jint value2);
}


void Java_com_example_ndktest001_MainActivity_helloLog(JNIEnv * env, jobject obj, jstring logThis)  
{  
	jboolean isCopy;
	const char * szLogThis = (env)->GetStringUTFChars(logThis, &isCopy);
  
	__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NDK:LC: [%s]", szLogThis);
  
	(env)->ReleaseStringUTFChars(logThis, szLogThis);
}  

jstring Java_com_example_ndktest001_MainActivity_getString(JNIEnv * env, jobject obj, jint value1, jint value2)
{  
	char *szFormat = "The sum of the two numbers is: %i";
	    char *szResult;

	    // add the two values
	    jlong sum = value1+value2;

	    // malloc room for the resulting string
	    szResult = (char *) malloc(sizeof(szFormat) + 20);

	    // standard sprintf
	    sprintf(szResult, szFormat, sum);

	    // get an object string
	    jstring result = (env)->NewStringUTF(szResult);

	    // cleanup
	    free(szResult);

	    return result;
}  





