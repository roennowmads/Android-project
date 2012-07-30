
#include <stdlib.h>
#include <unistd.h>

#include <fcntl.h>
#include <stdio.h>

#include <sys/ioctl.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <time.h>

#include <linux/fb.h>
#include <linux/kd.h>

#include "pixelflinger.h"


//copyright text
char cprght[255]="Copyright(C)2009 Motisan Radu , All rights reserved.\n radu.motisan@gmail.com";
//surface pointer
static GGLSurface gr_framebuffer[2];
//handler
static int gr_fb_fd = -1;
//v screen info
static struct fb_var_screeninfo vi;
//f screen info
struct fb_fix_screeninfo fi;

static void dumpinfo(struct fb_fix_screeninfo *fi,
                     struct fb_var_screeninfo *vi);

static int get_framebuffer(GGLSurface *fb)
{
    int fd;
    void *bits;

    fd = open("/dev/graphics/fb0", O_RDWR);
    if(fd < 0) {
        perror("cannot open fb0");
        return -1;
    }

    if(ioctl(fd, FBIOGET_FSCREENINFO, &fi) < 0) {
        perror("failed to get fb0 info");
        return -1;
    }

    if(ioctl(fd, FBIOGET_VSCREENINFO, &vi) < 0) {
        perror("failed to get fb0 info");
        return -1;
    }

    //dumpinfo(&fi, &vi);

    bits = mmap(0, fi.smem_len, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    if(bits == MAP_FAILED) {
        perror("failed to mmap framebuffer");
        return -1;
    }
	
    fb->version = sizeof(*fb);
    fb->width = vi.xres;
    fb->height = vi.yres;
    fb->stride = fi.line_length / (vi.bits_per_pixel >> 3);
    fb->data = (GGLubyte*) bits;
    fb->format = GGL_PIXEL_FORMAT_RGB_565;

    fb++;

    fb->version = sizeof(*fb);
    fb->width = vi.xres;
    fb->height = vi.yres;
    fb->stride = fi.line_length / (vi.bits_per_pixel >> 3);
    fb->data = (GGLubyte*) (((unsigned) bits) + vi.yres * vi.xres * 2);
    fb->format = GGL_PIXEL_FORMAT_RGB_565;

    return fd;
}


static void dumpinfo(struct fb_fix_screeninfo *fi, struct fb_var_screeninfo *vi)
{
    fprintf(stderr,"vi.xres = %d\n", vi->xres);
    fprintf(stderr,"vi.yres = %d\n", vi->yres);
    fprintf(stderr,"vi.xresv = %d\n", vi->xres_virtual);
    fprintf(stderr,"vi.yresv = %d\n", vi->yres_virtual);
    fprintf(stderr,"vi.xoff = %d\n", vi->xoffset);
    fprintf(stderr,"vi.yoff = %d\n", vi->yoffset);
    fprintf(stderr, "vi.bits_per_pixel = %d\n", vi->bits_per_pixel);

    fprintf(stderr, "fi.line_length = %d\n", fi->line_length);

}


Img save_screenshot(const char * filename)
{
  //get screen capture
  gr_fb_fd = get_framebuffer(gr_framebuffer);
  if (gr_fb_fd <= 0) exit(1);

  int w = vi.xres, h = vi.yres, depth = vi.bits_per_pixel;
  	
  //convert pixel data
  uint8_t *rgb24;
  if (depth == 16)
  {
	rgb24 = (uint8_t *)malloc(w * h * 3); 
	int i = 0;
	for (;i<w*h;i++)
	{
		uint16_t pixel16 = ((uint16_t *)gr_framebuffer[0].data)[i];
		// RRRRRGGGGGGBBBBBB -> RRRRRRRRGGGGGGGGBBBBBBBB
		// in rgb24 color max is 2^8 per channel (*255/32 *255/64 *255/32)
		rgb24[3*i+2]   = (255*(pixel16 & 0x001F))/ 32; 		//Blue
		rgb24[3*i+1]   = (255*((pixel16 & 0x07E0) >> 5))/64;	//Green
		rgb24[3*i]     = (255*((pixel16 & 0xF800) >> 11))/32; 	//Red
	}
  }
  else
  if (depth == 24) //exactly what we need
  {
  	rgb24 = (uint8_t *) gr_framebuffer[0].data;
  }
  else 
  if (depth == 32) //skip transparency channel
  {
	rgb24 = (uint8_t *) malloc(w * h * 3);
	int i=0;
	for (;i<w*h;i++)
	{
		uint32_t pixel32 = ((uint32_t *)gr_framebuffer[0].data)[i];
		// in rgb24 color max is 2^8 per channel 
		rgb24[3*i+0]   =  pixel32 & 0x000000FF; 		//Blue
		rgb24[3*i+1]   = (pixel32 & 0x0000FF00) >> 8;	//Green
		rgb24[3*i+2]   = (pixel32 & 0x00FF0000) >> 16; 	//Red
	}
  }
  else
  {
  	//free
        close(gr_fb_fd);
	exit(2);
  };


  //save RGB 24 Bitmap
  int bytes_per_pixel = 3;
  BMPHeader bh;
  memset ((char *)&bh,0,sizeof(BMPHeader)); // sets everything to 0
  //bh.filesize  =   calculated size of your file (see below)
  //bh.reserved  = two zero bytes
  bh.type1 = 'B';
  bh.type2 = 'M';
  bh.headersize  = 54L;			// for 24 bit images
  bh.infoSize  =  0x28L;		// for 24 bit images
  bh.width     = w;			// width of image in pixels
  bh.height    = h;			// height of image in pixels
  bh.biPlanes  = 1;			// for 24 bit images
  bh.bits      = 8 * bytes_per_pixel;	// for 24 bit images
  bh.biCompression = 0L;		// no compression
  int bytesPerLine;
  bytesPerLine = w * bytes_per_pixel;  	// for 24 bit images
  //round up to a dword boundary 
  if (bytesPerLine & 0x0003) 
  {
    	bytesPerLine |= 0x0003;
    	++bytesPerLine;
  }
  bh.filesize = bh.headersize + (long)bytesPerLine * bh.height;

  Img img;
  img.header = bh;
  img.data = rgb24;

  return img;

  /*
  FILE * bmpfile;
  //printf("Bytes per line : %d\n", bytesPerLine);
  bmpfile = fopen(filename, "wb");
  if (bmpfile == NULL)
  {
	close(gr_fb_fd);
	exit(3);
  }
  fwrite("BM",1,2,bmpfile);
  fwrite((char *)&bh, 1, sizeof (bh), bmpfile);
  //fwrite(rgb24,1,w*h*3,bmpfile);
  char *linebuf;   
  linebuf = (char *) calloc(1, bytesPerLine);
  if (linebuf == NULL)
  {
     	fclose(bmpfile);
	close(gr_fb_fd);
	exit(4);
  }
  int line,x;
  for (line = h-1; line >= 0; line --)
  {
    	// fill line linebuf with the image data for that line 
	for( x =0 ; x < w; x++ )
  	{
   		*(linebuf+x*bytes_per_pixel) = *(rgb24 + (x+line*w)*bytes_per_pixel+2);
   		*(linebuf+x*bytes_per_pixel+1) = *(rgb24 + (x+line*w)*bytes_per_pixel+1);
   		*(linebuf+x*bytes_per_pixel+2) = *(rgb24 + (x+line*w)*bytes_per_pixel+0);
  	}
	// remember that the order is BGR and if width is not a multiple
       	// of 4 then the last few bytes may be unused
	fwrite(linebuf, 1, bytesPerLine, bmpfile);
  }
  fclose(bmpfile);
  close(gr_fb_fd);
  return 0;*/
}
