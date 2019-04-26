package com.emerging.framework.core.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class ImageUtil {
	
	/**根据图片流生成图片
	 * @author hyp 
	 * @method Base64ToImage 
	 * @param imgStr
	 * @param imgFilePath
	 * @return 
	 * @return String 
	 * @date 2018年8月8日 下午8:12:44
	 */
	public static File Base64ToImage(String imgStr,File imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片
		if (StringUtils.isEmpty(imgStr)) // 图像数据为空
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return imgFilePath;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 本地图片转换成base64字符串
	 * @param imgFile	图片本地路径
	 * @return
	 *
	 * @author ZHANGJL
	 * @dateTime 2018-02-23 14:40:46
	 */
	public static String ImageToBase64ByLocal(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
	
	/**@author hyp 验证码类型转换
	 * @method yzmTyp 
	 * @param yzm_TYPE 00全部文字01红色02黄色03蓝色
	 * @return 
	 * @return String 
	 * @date 2018年8月10日 上午10:45:18 
	 */
	public static String yzmTyp(String yzm_TYPE) {
		if("00".equals(yzm_TYPE)){
			return "全部文字";
		}
		if("01".equals(yzm_TYPE)){
			return "红色";
		}
		if("02".equals(yzm_TYPE)){
			return "黄色";
		}
		if("03".equals(yzm_TYPE)){
			return "蓝色";
		}
		return null;
	}
	
    /**
     * 缩放图片
     * @param src
     * @return
     */
    public static BufferedImage zoomImage(String src) {
        BufferedImage result = null;
        try {
            File srcfile = new File(src);
            if (!srcfile.exists()) {
                return null;
            }
            BufferedImage im = ImageIO.read(srcfile);
            /* 原始图像的宽度和高度 */
            
            /* 原始图像的宽度和高度 */
			int width = im.getWidth();
			int height = im.getHeight();
			
			//压缩计算
			//float resizeTimes = 0f;  /*这个参数是要转化成的倍数,如果是1就是转化成1倍*/
			
			/* 调整后的图片的宽度和高度 */
			//int toWidth = (int) (width * resizeTimes);
			//int toHeight = (int) (height * resizeTimes);
 
			/* 新生成结果图片 */
			result = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			result.getGraphics().drawImage(
					im.getScaledInstance(width, height,
							java.awt.Image.SCALE_SMOOTH), 0, 0, null);
            /*try { 
                //将BufferedImage变量写入文件中。 
                ImageIO.write(result,"jpg",new File("f:/2.jpg")); 
            } catch (IOException e) { 
                // TODO Auto-generated catch block 
                e.printStackTrace(); 
            } */
        } catch (Exception e) {
            System.out.println("创建缩略图发生异常" + e.getMessage());
        }
        return result;
    }

    /***
     * 在一张大图张添加小图和文字
     * @param bigImgPath 大图的路径
     * @param smallImgPath 小图的路径
     * @param sx    小图在大图上x抽位置
     * @param sy    小图在大图上y抽位置
     * @param content   文字内容
     * @param cx    文字在大图上y抽位置
     * @param cy    文字在大图上y抽位置
     * @param outPathWithFileName 结果输出路径
     */
    public static void bigImgAddSmallImgAndText(File smallImgPath, int sx, int sy
            , String content, int cx, int cy
            , File outPathWithFileName) throws IOException {
    	BufferedImage image = new BufferedImage(120, 75,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics graphics = image.getGraphics();  
        graphics.setColor(Color.white);  
        graphics.fillRect(0, 0, 150, 150);  
        //小图片的路径
        File fileMin = new File(smallImgPath.getPath());
        BufferedImage imagemin = ImageIO.read(fileMin);
        //得到Image对象。
        //将小图片绘到大图片上,5,300 .表示你的小图片在大图片上的位置。
        graphics.drawImage(imagemin, sx, sy, null);
        //设置颜色。

        //最后一个参数用来设置字体的大小
        if (content != null) {
            Font f = new Font("微软雅黑", Font.PLAIN, 12);
            Color mycolor = Color.black;//new Color(0, 0, 255);
            graphics.setColor(mycolor);
            graphics.setFont(f);
            graphics.drawString("请输入", cx, cy); //表示这段文字在图片上的位置(cx,cy) .第一个是你设置的内容。
        }
        if (content != null) {
            Font f = new Font("微软雅黑", Font.PLAIN, 14);
            Color mycolor = Color.black;
            if("红色".equals(content)){
            	mycolor = Color.red;//new Color(0, 0, 255);
            }
            if("黄色".equals(content)){
            	mycolor = Color.yellow;//new Color(0, 0, 255);
            }
            if("蓝色".equals(content)){
            	mycolor = Color.BLUE;//new Color(0, 0, 255);
            }
            graphics.setColor(mycolor);
            graphics.setFont(f);
            if(content.length()==4){
            	String contSub = content.substring(0,2);
            	graphics.drawString(contSub, cx+38, cy); //表示这段文字在图片上的位置(cx,cy) .第一个是你设置的内容。
            }else{
            	graphics.drawString(content, cx+38, cy); //表示这段文字在图片上的位置(cx,cy) .第一个是你设置的内容。
            }
        }
       
	    if (content != null) {
	        Font f = new Font("微软雅黑", Font.PLAIN, 14);
	        Color mycolor = Color.black;//new Color(0, 0, 255);
	        graphics.setColor(mycolor);
	        graphics.setFont(f);
	        graphics.drawString("文字", cx+72, cy); //表示这段文字在图片上的位置(cx,cy) .第一个是你设置的内容。
	    }
	    graphics.dispose();
        
        ImageIO.write(image, "png", outPathWithFileName);
        
    }
    
    /**
	 * 图片裁切
	 * @param x1 选择区域左上角的x坐标
	 * @param y1 选择区域左上角的y坐标
	 * @param width 选择区域的宽度
	 * @param height 选择区域的高度
	 * @param sourcePath 源图片路径
	 * @param descpath 裁切后图片的保存路径
	 */
	public static void cut(int x1, int y1, int width, int height,
			String sourcePath, String descpath) {

		FileInputStream is = null;
		ImageInputStream iis = null;
		try {
			is = new FileInputStream(sourcePath);
			String fileSuffix = sourcePath.substring(sourcePath
					.lastIndexOf(".") + 1);
			Iterator<ImageReader> it = ImageIO
					.getImageReadersByFormatName(fileSuffix);
			ImageReader reader = it.next();
			iis = ImageIO.createImageInputStream(is);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x1, y1, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, fileSuffix, new File(descpath));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				is = null;
			}
			if (iis != null) {
				try {
					iis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				iis = null;
			}
		}

	}

}  
