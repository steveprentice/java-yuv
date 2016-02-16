# Introduction #

This simple java class can open any yuv 4:2:0 file, and also can write black and white 4:2:0 file.


# How to use #

simply by creating object of ReadYUV class and use startReading function and than you can read the frames by using nextImage function.

this is example source code.

```
BufferedImage currentImage;

ReadYUV ryuv = new ReadYUV(176, 144); //read the qcif yuv
ryuv.startReading("akiyo_qcif.yuv");

WriteYUV wyuv = new WriteYUV(176, 144);
wyuv.startWriting("test_qcif.yuv");

while((currentImage=ryuv.nextImageYOnly())!=null)
{
	wyuv.writeImageYOnly(currentImage);
}

wyuv.endWriting();
ryuv.endReading();

```


or if you want to read/write colored image, you can use


```
BufferedImage currentImage;

ReadYUV ryuv = new ReadYUV(176, 144); //read the qcif yuv
ryuv.startReading("akiyo_qcif.yuv");

WriteYUV wyuv = new WriteYUV(176, 144);
wyuv.startWriting("test_qcif.yuv");

while((currentImage=ryuv.nextImage())!=null)
{
	wyuv.writeImage(currentImage);
}

wyuv.endWriting();
ryuv.endReading();

```