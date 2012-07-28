#!/bin/bash

rm dayzmap/512x512/*.png
rm dayzmap/128x128/*.png
rm dayzmap/256x256/*.png
rm dayzmap/1024x1024/*.png


convert 50percent.png -crop 512x512  dayzmap/512x512/map_%04d.png &
convert 25percent.png -crop 256x256  dayzmap/256x256/map_%04d.png &
convert 12.5percent.png -crop 128x128  dayzmap/128x128/map_%04d.png &
convert MaxHighresChernarusLootV1-cropped.png -crop 1024x1024 dayzmap/1024x1024/map_%04d.png

adb push ./dayzmap/ /mnt/sdcard/dayzmap
