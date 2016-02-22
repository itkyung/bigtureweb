package com.clockworks.bigture.common;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.OsType;
import com.clockworks.bigture.entity.TempImage;
import com.clockworks.bigture.entity.User;
import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;
import com.mortennobel.imagescaling.AdvancedResizeOp.UnsharpenMask;

@Service
public class ImageService implements IImageService,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7404341154189506543L;

	@Autowired com.clockworks.bigture.IMasterDAO dao;
	
	@Value("${catalina.home}")
	private String bigtureHome;
	
	@Value("${uploadPath}")
	private String uploadPath;
	
	@Value("${fileAccessUrl}")
	private String fileAccessUrl;
	
	@Value("${uploadTempPath}")
	private String uploadTempDir;
	
	private String thumnailDir = "/thumbnail";
	private String imageDir = "/picture";

	@Override
	public BufferedImage resize(BufferedImage src, int width, int height) {
		ResampleOp resampleOp = new ResampleOp(width, height);
		resampleOp.setFilter(ResampleFilters.getLanczos3Filter());
		resampleOp.setUnsharpenMask(UnsharpenMask.Soft);
		
		return resampleOp.filter(src, null);
	}

	@Override
	public BufferedImage resize(BufferedImage src, int width) {
		return resize(src, width, width * src.getHeight() / src.getWidth());
	}

	@Override
	public BufferedImage read(byte[] imageData) {

		Image image = new ImageIcon(imageData).getImage();
		
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		
		BufferedImage buffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics g = buffImage.createGraphics();
		
		g.drawImage(image, 0, 0, width, height, 0, 0, width, height, null);
		g.dispose();
		
		return buffImage;
	}

	@Override
	public BufferedImage read(InputStream inputStream) throws IOException {
		return read(IOUtils.toByteArray(inputStream));
	}

	@Transactional
	@Override
	public TempImage saveImage(User user, MultipartFile file,int thumbnailWidth,int thumbnailHeight,String localPath) throws IOException {
		FileOutputStream out = null;
		try{
			
			
			File thumDir = new File(bigtureHome + uploadTempDir +  thumnailDir);
			if(!thumDir.exists()){
				thumDir.mkdirs();
			}
			
			File orgDir = new File(bigtureHome + uploadTempDir + imageDir);
			if(!orgDir.exists()){
				orgDir.mkdirs();
			}
			
			String fileName = makeFileName(user.getId());
			
			File orgImageFile = new File(orgDir,fileName);
			File thumbImageFile = new File(thumDir,fileName);
			file.transferTo(orgImageFile);
			
			BufferedImage orgImage = ImageIO.read(orgImageFile);
			
			BufferedImage thumbImage = null;
			
			if(thumbnailHeight == 0){
				thumbImage = resize(orgImage, thumbnailWidth);
			}else{
				thumbImage = resize(orgImage, thumbnailWidth, thumbnailHeight);
			}
			
			out = new FileOutputStream(thumbImageFile);
			this.write(thumbImage,out);
			
			String type = file.getContentType();
			
			String orgFileName = orgImageFile.getAbsolutePath();
			String thumbFileName = thumbImageFile.getAbsolutePath();
			
			TempImage tmpImage = new TempImage();
			
			tmpImage.setContentType(type);
			tmpImage.setPhoto(orgFileName);
			tmpImage.setThumbnail(thumbFileName);
			tmpImage.setOwner(user);
			tmpImage.setLocalImagePath(localPath);
			
			dao.saveTempImage(tmpImage);
			return tmpImage;
			
		}catch(IOException e){
			if(out != null){
				out.close();
			}
			throw e;
		}
		
	}

	public String makeFileName(Long id){
		UUID uid = UUID.randomUUID();
		return id + "_" + uid.toString();
	}
	
	public boolean write(BufferedImage image, OutputStream output) throws IOException {
		return write(image, output, 1f);
	}
	
	public boolean write(BufferedImage image, OutputStream output, float quality) throws IOException {

		ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
		
		ImageOutputStream ios = ImageIO.createImageOutputStream(output);
		writer.setOutput(ios);
		
		ImageWriteParam iwp = new JPEGImageWriteParam(Locale.getDefault());
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwp.setCompressionQuality(quality);
		
		writer.write(null, new IIOImage(image, null, null), iwp);
		
		ios.flush();
		writer.dispose();
		ios.close();
		
		return true;
	}

	
	@Override
	public ImageInfo transferFile(TempImage tmp, ImagePathType type)
			throws IOException {
		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH)+1;
		String datePrefix =  today.get(Calendar.YEAR) + File.separator + month + File.separator + today.get(Calendar.DATE) + File.separator ;
		
		String prefix = "";
		switch (type) {
		case NEWS:
			prefix = File.separator + "news";
			break;
		case ARTWORKS:
			prefix = "";
			break;
		case ARTCLASS:
			prefix = File.separator + "class";
			break;
		case STORY:
			prefix = File.separator + "story";
			break;
		 
		default:
			break;
		}
		String savedThumName = null;
		String savedOrgName = null;
		
		if(type.equals(ImagePathType.PROFILE)){
			//하나의 이미지만 처리한다. 
			savedThumName =  File.separator + "profile" + File.separator  + datePrefix;
			savedOrgName =  File.separator + "profile" + File.separator + datePrefix;
		}else{
			savedThumName =  prefix +  thumnailDir + File.separator + datePrefix ;
			savedOrgName =  prefix + imageDir + File.separator + datePrefix;
		}
		
		File thumDir = new File(bigtureHome + uploadPath + savedThumName);
		if(!thumDir.exists()){
			thumDir.mkdirs();
		}
		
		
		File orgDir = new File(bigtureHome + uploadPath + savedOrgName);
		if(!orgDir.exists()){
			orgDir.mkdirs();
		}
		String typeName = tmp.getContentType();
		
		String postfix = "";
		if(typeName != null){
			if(typeName.endsWith("jpeg") || typeName.endsWith("jpg")){
				postfix = ".jpg";
			}else if(typeName.endsWith("gif")){
				postfix = ".gif";
			}else{
				postfix = ".png";
			}
		}
		
		String fileName = makeFileName(tmp.getOwner().getId()) + postfix;
		
		if(type.equals(ImagePathType.PROFILE)){
			File newThumFile = new File(thumDir,fileName);
			fileCopy(new File(tmp.getThumbnail()),newThumFile);
		}else{
			File newThumFile = new File(thumDir,fileName);
			File newOrgFile = new File(orgDir,fileName);
			
			fileCopy(new File(tmp.getThumbnail()),newThumFile);
			fileCopy(new File(tmp.getPhoto()),newOrgFile);
		}
		
		
		ImageInfo info = new ImageInfo();
		info.setPhotoPath(datePrefix +  fileName);
		info.setThumbnailPath(datePrefix + fileName);
		
		return info;
	}

	
	/**
	 * 특정 artwork내의 이미지를 그 이름 그대로 type아래로 옮긴다.
	 * 
	 */
	@Override
	public ImageInfo transferFile(Artwork artwork, ImagePathType type)
			throws IOException {
		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH)+1;
		String datePrefix =  today.get(Calendar.YEAR) + File.separator + month + File.separator + today.get(Calendar.DATE) + File.separator ;
		String prefix = "";
		switch (type) {
		case NEWS:
			prefix = File.separator + "news";
			break;
		case ARTWORKS:
			prefix = "";
			break;
		case ARTCLASS:
			prefix = File.separator + "class";
			break;
		case STORY:
			prefix = File.separator + "story";
			break;
		default:
			break;
		}
		
		String savedThumName =  prefix +  thumnailDir + File.separator + datePrefix;
		String savedOrgName =  prefix + imageDir + File.separator + datePrefix;
		
		File thumDir = new File(bigtureHome + uploadPath + savedThumName);
		if(!thumDir.exists()){
			thumDir.mkdirs();
		}
		
		File orgDir = new File(bigtureHome + uploadPath + savedOrgName);
		if(!orgDir.exists()){
			orgDir.mkdirs();
		}
		
		String oldFile = artwork.getPhoto();
		String fileName = makeFileName(artwork.getUser().getId());
		
		
		File newThumFile = new File(thumDir,fileName);
		File newOrgFile = new File(orgDir,fileName);
		
		String artworkImgThumbPath = bigtureHome + uploadPath + thumnailDir + File.separator;
		String artworkImgPath = bigtureHome + uploadPath + imageDir + File.separator;
		
		fileCopy(new File(artworkImgThumbPath + oldFile),newThumFile);
		fileCopy(new File(artworkImgPath + oldFile),newOrgFile);
		
		ImageInfo info = new ImageInfo();
		info.setPhotoPath(datePrefix +  fileName);
		info.setThumbnailPath(datePrefix +  fileName);
		
		
		return info;
	}

	private void fileCopy(File srcFile,File targetFile) throws IOException{
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(targetFile);
		
		int data = 0;
		while((data = fis.read()) != -1){
			fos.write(data);
		}
		fis.close();
		fos.close();
	}

	@Override
	public String saveDrawingFile(User user, MultipartFile file, OsType osType)
			throws IOException {
		
		Calendar today = Calendar.getInstance();
		String datePrefix =  today.get(Calendar.YEAR) + File.separator + today.get(Calendar.MONTH+1) + File.separator;
		
		String prefix = File.separator + "drawing";

		String savedOrgName =  prefix + osType.name() + File.separator + datePrefix;
	
		File orgDir = new File(bigtureHome + uploadPath + savedOrgName);
		if(!orgDir.exists()){
			orgDir.mkdirs();
		}
		String typeName = file.getContentType();
		
		String postfix = ".xml";
		
		String fileName = makeFileName(user.getId()) + postfix;
	
		File newOrgFile = new File(orgDir,fileName);
		file.transferTo(newOrgFile);
		
		return datePrefix + fileName;
	}

	
}
