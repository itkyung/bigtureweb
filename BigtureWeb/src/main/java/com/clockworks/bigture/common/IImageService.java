package com.clockworks.bigture.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.OsType;
import com.clockworks.bigture.entity.TempImage;
import com.clockworks.bigture.entity.User;

public interface IImageService {
	
	BufferedImage resize(BufferedImage src, int width, int height); 
	BufferedImage resize(BufferedImage src, int width);
	BufferedImage read(byte[] imageData);
	BufferedImage read(InputStream inputStream) throws IOException;
	TempImage saveImage(User user,MultipartFile file,int thumbnailWidth,int thumbnailHeight,String localPath) throws IOException;
	ImageInfo transferFile(TempImage tmp,ImagePathType type) throws IOException;
	ImageInfo transferFile(Artwork artwork,ImagePathType type) throws IOException;
	String saveDrawingFile(User user,MultipartFile file,OsType osType) throws IOException;
	
}
