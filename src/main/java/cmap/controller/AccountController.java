package cmap.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cmap.model.AccountVM;
import cmap.services.Members;

//--- Sử dụng giao thức RestFul service chỉ trả về body dưới dạng json
@RestController
//--- Url mặc định cho controller này xiziu.com/data/account/{action}
@RequestMapping("/data/account")
public class AccountController {
	// --- Dependency Injection (cơ chế liên kết lỏng)
	@Autowired
	private Members mems;
	
	// --- Action lấy thông tin tài khoản đang đăng nhập
	// --- ResponseEntity đối tượng trả về dữ liệu và trạng thái
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<AccountVM> get(){
		try{
			// --- Lấy username member đang đăng nhập
			String username = AuthHelper.getUserName();
			// --- Get thông tin member
			AccountVM account = mems.findByUsername(username);
			// --- Trả về dữ liệu và trạng thái chập nhận
			return new ResponseEntity<AccountVM>(account, HttpStatus.ACCEPTED);
		}
		catch (Exception e){
			e.printStackTrace();
			// --- Trả về trạng khái không được chấp nhập
			return new ResponseEntity<AccountVM>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@RequestMapping(value = "/avatar/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getAvatar(@PathVariable("id") int id) {
		BufferedImage originalImage;
		try {
			originalImage = ImageIO.read(new File("avatar/"+id+".png"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, "png", baos);
			baos.flush();
			byte [] imageInByte = baos.toByteArray();
			baos.close();
			return imageInByte;
		} catch (IOException e) {
			try{
				originalImage = ImageIO.read(new File("avatar/no.gif"));
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(originalImage, "png", baos);
				baos.flush();
				byte [] imageInByte = baos.toByteArray();
				baos.close();
				return imageInByte;
			}
			catch(IOException er){
				er.printStackTrace();
			}
		}
		return null;
	}
	
}
