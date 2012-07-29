LOCAL_PATH := $(call my-dir)
  
include $(CLEAR_VARS)  
  
LOCAL_MODULE    := ndk1  
LOCAL_SRC_FILES := native.cpp 
LOCAL_LDLIBS    := -llog -landroid -lEGL -lGLESv1_CM 
LOCAL_CPP_FEATURES += exceptions

  
include $(BUILD_SHARED_LIBRARY)  

$(call import-module,android/native_app_glue)
