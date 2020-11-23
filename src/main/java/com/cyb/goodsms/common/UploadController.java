package com.cyb.goodsms.common;

import com.cyb.authority.base.BaseController;
import com.cyb.common.tips.Tips;
import com.cyb.goodsms.config.MyWebAppConfiguration;
import com.cyb.goodsms.utils.MyUtils;
import com.cyb.common.result.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

@Controller
@RequestMapping(value="/upload")
public class UploadController extends BaseController {



	@Value("${web.upload-path}")
	private String uploadPath;

	private static String IMG_SUFFIX = "png,jpg";

	@PostMapping(value = "/image")
	//@PostMapping(value = "/image", headers="content-type=multipart/form-data")
	@ResponseBody
	public Tips image(@RequestParam("file") MultipartFile file) {

		super.validLogined();
		if(isLogined){
			tips = new Tips("文件上传失败", true, false);
			R validateImage = validateImage(file);
			if (validateImage.isSuccess()) {
				String savePath = "image/" + getSavePaht(file);
				R save = saveFile(savePath, file);
				if(save.isSuccess()){
					tips = new Tips(save.getMsg(), true, save.isSuccess());
					tips.setData(MyWebAppConfiguration.V_PATH + savePath);
				}else{
					tips.setMsg(save.getMsg());
				}
			}else{
				tips.setMsg(validateImage.getMsg());
			}
		}
		return tips;
	}

	/**
	 * 获取文件保存路径
	 * @param file
	 * @return
	 */
	private String getSavePaht(MultipartFile file){
		String fileName = file.getOriginalFilename();
		String fileType = MyUtils.getFileTypeByFullName(fileName);
		String savePath =  MyUtils.getPrimaryKey()+fileType;
		return savePath;
	}

	private R validateImage(MultipartFile file){
		R isEmpty = isEmpty(file);
		if(isEmpty.isSuccess()){
			String originalFilename = file.getOriginalFilename();
			String suffix = originalFilename.split("[.]")[1];
			if(IMG_SUFFIX.indexOf(suffix) <= 0){
				return R.fail("此文件不是图片");
			}
			return R.success("文件校验成功");
		}
		return R.fail(isEmpty.getMsg());
	}

	/**
	 * 非空判断
	 * @param file
	 * @return
	 */
	private R isEmpty(MultipartFile file){
		if (StringUtils.isEmpty(uploadPath)) {
			return R.fail("文件保存路径为空");
		}else if(null == file || StringUtils.isEmpty(file.getOriginalFilename())){
			return R.fail("文件是空的");
		}
		return R.success("success");
	}

	/**
	 * 保存文件
	 * @param savePath
	 * @param file
	 * @return
	 */
	private R saveFile(String savePath, MultipartFile file){
		try{
			File dest = new File(uploadPath + savePath);
			if(dest.exists()){
				dest.delete();
			}
			dest.createNewFile();
			file.transferTo(dest);
			return R.success("文件保存成功");
		}catch (Exception e){
			return R.fail(e.getMessage());
		}
	}
}
