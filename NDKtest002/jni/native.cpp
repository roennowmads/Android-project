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

#ifdef __cplusplus
#define __STDC_CONSTANT_MACROS
#ifdef _STDINT_H
#undef _STDINT_H
#endif
# include <stdint.h>
#endif

extern "C" {
	#include "capturescr.c"
	//#include "libavcodec/avcodec.h"
}

#include <EGL/egl.h>
#include <GLES/gl.h>

extern "C" {
	void Java_com_tuxronnow_ndktest002_MainActivity_memoryMap(JNIEnv * env, jobject obj);
}

//#define PIXEL_COUNT (320*240)

#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, "native-activity", __VA_ARGS__))

  
void Java_com_tuxronnow_ndktest002_MainActivity_memoryMap(JNIEnv * env, jobject obj)  {  
	save_screenshot();
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

void Java_com_tuxronnow_ndktest002_MainActivity_getString(JNIEnv * env, jobject obj, jint value1, jint value2)  {  

}  





