package ikaoyaner.util.file;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author bukaa
 */
@SuppressWarnings("restriction")
public class ImageUtils {
	
	public static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	
	public static BASE64Decoder decoder = new sun.misc.BASE64Decoder();
	
	public static final String JPG = "jpg";
	
	/**
	 * 获取指定图片流的缩略图
	 * @param in 文件流
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage getThumb(InputStream in, int width,int height) throws IOException{
		return Thumbnails.of(in).size(width, height).asBufferedImage();
	}
	
	/**
	 * 获取指定图片流的缩略图（BASE64）
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String getThumb2Base64(InputStream in, int width,int height) throws IOException{
		BufferedImage bi = getThumb(in, width, height);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bi, JPG, baos);
		byte[] bytes = baos.toByteArray();
		return encoder.encodeBuffer(bytes).trim();
	}
	
	/**
	 * 获取指定文件（图片）的缩略图
	 * @param in 文件流
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage getThumb(CommonsMultipartFile file, int width,int height) throws IOException{
		return getThumb(file.getInputStream(), width, height);
	}
	
	/**
	 * 获取指定文件（图片）的缩略图（BASE64）
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String getThumb2Base64(CommonsMultipartFile file, int width,int height) throws IOException{
		BufferedImage bi = getThumb(file.getInputStream(), width, height);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bi, JPG, baos);
		byte[] bytes = baos.toByteArray();
		return encoder.encodeBuffer(bytes).trim();
	}

}
