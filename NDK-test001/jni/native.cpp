#include <jni.h>  
#include <string.h>   
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>  

#include <EGL/egl.h>
#include <GLES/gl.h>

/*#ifndef INT64_C
#define INT64_C(c) (c ## LL)
#define UINT64_C(c) (c ## ULL)
#endif

extern "C" {
#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
}  */

#define DEBUG_TAG "MainActivity"  

/*class Number {
private:	
	int integer;
public:
	Number(int integer);
	int getInteger();
	int operator+(Number other);
};

Number::Number(int integer) {
	this->integer = integer;
}

int Number::operator+(Number other) {
	return this->integer + other.getInteger();
}

int Number::getInteger() {
	return this->integer;
}*/

  
void Java_com_example_ndktest001_MainActivity_helloLog(JNIEnv * env, jobject obj, jstring logThis)  
{  

	//avcodec_register_all();

    //jboolean isCopy;  
    //const char * szLogThis = (*env)->GetStringUTFChars(logThis, &isCopy);  
  
   // __android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NDK:LC: [%s]", szLogThis);  
  
    //(*env)->ReleaseStringUTFChars(logThis, szLogThis);  
}  

void Java_com_example_ndktest001_MainActivity_getString(JNIEnv * env, jobject obj, jint value1, jint value2)  
{  
    /*std::string szFormat = "The sum of the two numbers is: ";  
    std::string szResult;  

	Number s1 = Number(value1);
	Number s2 = Number(value2);

	void (*pt)(unsigned int, int, unsigned int, void const*); 

	pt = glDrawElements;

	//*pt = log;

	//pt = 0;

	long sum = s1 + s2;
  
    long sum = value1+value2;  

	std::stringstream s;
	s << szFormat.c_str() << sum;
    szResult = s.str();  
  
    // get an object string  
    jstring result = env->NewStringUTF(szResult.c_str());  
  	*/
	
	//const char * s = "hej";

	//jstring result = NULL;//(*env)->NewStringUTF(s);

    //return void;  
}  





