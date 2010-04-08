/*
 * ReadYUV.java
 * By Abdul Arfan
 * 20101204005
 */

import java.io.*;
import javax.swing.*;
import java.awt.image.*;
import java.util.*;

public class ReadYUV
{
	DataInputStream dis;
	DataOutputStream dos;
	byte[] oneFrame;
	int width;
	int height;
	String type= "4:2:0";
	int oneFrameLength;
	
	public ReadYUV(int width, int height)
	{
        this.width = width;
        this.height = height;
        this.type = "4:2:0"; //this version only support the 4:2:0 yuv
	}

	public void startWriting(String filename)
	{
		try
		{
			dos = new DataOutputStream(new BufferedOutputStream(
					new FileOutputStream(filename)));

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void writeImage(BufferedImage bi)
	{
		int w = bi.getWidth();
		int h = bi.getHeight();

		LinkedList<Byte> uBuffer = new LinkedList<Byte>();
		LinkedList<Byte> vBuffer = new LinkedList<Byte>();
		try
		{
			boolean s = false;

			for (int j = 0; j < h; j++)
			{
				for (int i = 0; i < w; i++)
				{
					int color = bi.getRGB(i, j);

					int alpha = color >> 24 & 0xff;
					int red = color >> 16 & 0xff;
					int green = color >> 8 & 0xff;
					int blue = color & 0xff;

					//int y = (int) ((0.257 * red) + (0.504 * green) + (0.098 * blue) + 16);
					//int v = (int) ((0.439 * red) - (0.368 * green) - (0.071 * blue) + 128);
					//int u = (int) (-(0.148 * red) - (0.291 * green) + (0.439 * blue) + 128);
					int y = red;
					//~ if(!s)
					//~ {
					//~ uBuffer.addLast((byte)(u & 0xff));
					//~ vBuffer.addLast((byte)(v & 0xff));
					//~ }
					dos.write(y);

					s = !s;
				}
			}


			int sampah = oneFrameLength - width * height;

			for (int i = 0; i < sampah; i++)
			{
				dos.write(128);
			}


		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void endWriting()
	{
		try
		{
			dos.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void startReading(String filename)
	{
		try
		{
			dis = new DataInputStream(new BufferedInputStream(
					new FileInputStream(filename)));
            
			double pengali = 1.5;

			if (type.equals("4:2:0"))
			{
				pengali = 1.5;
			} 
            /*else if (jenis.equals("4:4:4"))
			{
				pengali = 2;
			}*/

			oneFrameLength = (int) (width * height * (pengali));

			oneFrame = new byte[oneFrameLength];
			//File f=new File(filename);
		} 
        catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public BufferedImage nextImage()
	{
		try
		{
			int n = dis.read(oneFrame);
			//System.out.println(n);
			if (n != oneFrameLength)
			{
				return null;
			}

			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			double index = 0;

			for (int j = 0; j < height; j++)
			{
				for (int i = 0; i < width; i++)
				{
					int rc = 100;

					//index=Math.round(index);

					int satuPixel = 0;

					int indexTruncate = (int) index;
					int awal = unsignedByteToInt(oneFrame[indexTruncate]);

					//satuPixel = awal - 16; //16 itu dari perubahan yuv ke rgb
					satuPixel = awal;

					int rColor = (0xff << 24) | (satuPixel << 16) | (satuPixel << 8) | satuPixel;

					image.setRGB(i, j, rColor);
					index += 1;
				}
			}

			//ImageIO.write(image, "png", new File("result.png"));
			return image;
		} 
        catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
    public void endReading()
    {
        try {
            dis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

	public static int unsignedByteToInt(byte b)
	{
		return (int) b & 0xFF;
	}
    
    
    public static void main(String args[])
    {
        //String fileToRead = args[0];
        //String fileToWrite = args[1];
        
        BufferedImage currentImage;
        
        ReadYUV ryuv = new ReadYUV(176, 144); //read the qcif yuv
        
        ryuv.startReading("suzie_qcif.yuv");
        ryuv.startWriting("test_write_qcif.yuv");
        while((currentImage=ryuv.nextImage())!=null)
        {
            ryuv.writeImage(currentImage);
        }
        ryuv.endWriting();
        ryuv.endReading();
    }
}
