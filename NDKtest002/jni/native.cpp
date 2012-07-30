#include <jni.h>  
#include <string.h>   
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>  
#include <sys/mman.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <errno.h>
#include <fcntl.h>
#include <linux/fb.h>
//#include "pixelflinger.h"

#include <sstream>

#ifdef __cplusplus
#define __STDC_CONSTANT_MACROS
#ifdef _STDINT_H
#undef _STDINT_H
#endif
# include <stdint.h>
#endif

#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, "native-activity", __VA_ARGS__))

struct BMPHeader {
     char type1;
     char type2;
     long filesize;
     char reserved[2];
     long headersize;
     long infoSize;
     long width;
     long height;
     short biPlanes;
     short bits;
     long biCompression;
     long biSizeImage;
     long biXPelsPerMeter;
     long biYPelsPerMeter;
     long biClrUsed;
     long biClrImportant;
};

struct Img {
	BMPHeader header;
	unsigned char * data;
};

extern "C" {
	#include "capturescr.c"
	//#include "libavcodec/avcodec.h"
}

#include <EGL/egl.h>
#include <GLES/gl.h>

//#define PIXEL_COUNT (320*240)

/*void Java_com_tuxronnow_ndktest002_TimerT_memoryMap(JNIEnv * env, jobject obj, jstring filename)  {
	const char *file = (env)->GetStringUTFChars(filename, NULL);
	save_screenshot(file);
	LOGW("screen saved");
}*/

extern "C" void Java_com_tuxronnow_ndktest002_MainActivity_test(JNIEnv * env, jobject obj) {
	LOGW("test called");
}

extern "C" jbyteArray Java_com_tuxronnow_ndktest002_ScreenShotService_memoryMap(JNIEnv * env, jobject obj, jstring filename)  {
	const char *file = (env)->GetStringUTFChars(filename, NULL);
	Img screenshot = save_screenshot(file);

	jbyteArray result;
	int headerSize = screenshot.header.headersize;
	int completeSize = screenshot.header.filesize;
	result = env->NewByteArray(completeSize);
	if (result == NULL) {
		LOGW("Out of memory");
	    return NULL; /* out of memory error thrown */
	}

	//LOGW(reinterpret_cast<const char*>(screenshot.data));

	char head[sizeof(char)*56];

	char * charIte = head;
	
	charIte = &screenshot.header.type1;
	/*charIte++;
	charIte = &screenshot.header.type2;
	charIte++;
	long * longIte = (long *) charIte;
	longIte = &screenshot.header.filesize;
	longIte++;
	charIte = (char*) longIte;
	*charIte = 0;
	charIte++;
	*charIte = 0;
	charIte++;
	longIte = (long *) charIte;
	longIte = &screenshot.header.headersize;
	longIte++;
	longIte = &screenshot.header.infoSize;
	longIte++;
	longIte = &screenshot.header.width;
	longIte++;
	longIte = &screenshot.header.height;
	longIte++;
	short * shortIte = (short *) longIte;
	shortIte = &screenshot.header.biPlanes;
	shortIte++;
	shortIte = &screenshot.header.bits;
	shortIte++;
	longIte = (long *) shortIte;
	longIte = &screenshot.header.biCompression;
	longIte++;
	longIte = &screenshot.header.biSizeImage;
	longIte++;
	longIte = &screenshot.header.biXPelsPerMeter;
	longIte++;
	longIte = &screenshot.header.biYPelsPerMeter;
	longIte++;
	longIte = &screenshot.header.biClrUsed;
	longIte++;
	longIte = &screenshot.header.biClrImportant;
	longIte++;*/

	jbyte finalhead[sizeof(char)*56];

	for (int i = 0; i<headerSize; i++) {
		*(finalhead+i) = *(charIte+i);
	}

	jbyte finaldata[completeSize-headerSize];
	for (int i = 0; i<headerSize; i++) {
		*(finaldata+i) = *(screenshot.data+i);
	}


	std::stringstream ss2;
	for (int i = 0; i<headerSize; i++) {
		ss2 << *(finaldata+i);
	}
	//ss2 << *finalhead;
	LOGW("Hello?");
	LOGW(ss2.str().c_str());

	env->SetByteArrayRegion(result, 0, headerSize, finalhead);
	//env->SetByteArrayRegion(result, headerSize+1, completeSize, reinterpret_cast<signed const char*>(&screenshot.data));

	std::stringstream ss;
	ss << "screen saved " << screenshot.header.width << " " << screenshot.header.height;
	std::string output = ss.str();

	LOGW(output.c_str());

	return result;
}
extern "C" void Java_com_tuxronnow_ndktest002_MainActivity_memoryMap(JNIEnv * env, jobject obj, jstring filename)  {
	const char *file = (env)->GetStringUTFChars(filename, NULL);
	Img screenshot = save_screenshot(file);
	LOGW("screen saved");

	//avcodec_init();

	//AVCodec *codec;
	//AVCodecContext *c= NULL;

	/*int file = open("/dev/graphics/fb0", O_RDWR);

	//int file = open("/sdcard/frame.raw", O_RDWR);

	struct fb_var_screeninfo varInfo;
	struct fb_fix_screeninfo finfo;
	void * bits;

	if (file == -1) {
		LOGW("Error opening file");
		return;
	}

	if(ioctl(file, FBIOGET_FSCREENINFO, &finfo) < 0) {
		LOGW("failed to get fb0 info");
		return;
	}

	if(ioctl(file, FBIOGET_VSCREENINFO, &varInfo) < 0) {
		LOGW("failed to get fb0 info");
		return;
	}


	else {
		LOGW("Opened file");
		bits = mmap(0, finfo.smem_len, PROT_READ | PROT_WRITE, MAP_SHARED, file, 0);
		if(bits == MAP_FAILED) {
			LOGW("failed to mmap framebuffer");
			return;
		}

		fb->version = sizeof(*fb);
		fb->width = vi.xres;
		fb->height = vi.yres;
		fb->stride = fi.line_length / (vi.bits_per_pixel >> 3);
		fb->data = bits;
		fb->format = GGL_PIXEL_FORMAT_RGB_565;
	}*/

}

extern "C" void Java_com_tuxronnow_ndktest002_MainActivity_getString(JNIEnv * env, jobject obj, jint value1, jint value2)  {

}  





