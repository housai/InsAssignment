package com.klein.controller;

import java.io.File;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.klein.util.JSONUtils;
import com.klein.util.Maps;
import com.klein.util.MyUtil;

@Controller  
@RequestMapping("/fileUploadControllerAPI")  
public class FileUploadControllerAPI{
	
		@ResponseBody
	 	@RequestMapping(value="/upload",method=RequestMethod.POST)
	    private String fildUpload(HttpSession session,@RequestParam(value="file",required=false) MultipartFile file,  
	            HttpServletRequest request)throws Exception{
			Map<String, Object> map = Maps.newHashMap();
			String pathRoot = request.getSession().getServletContext().getRealPath("/");
			String newPath = MyUtil.uploadString(pathRoot);
			System.out.println(file.getSize()+"");
			System.out.println(request.getParameter("description"));
			System.out.println(file.getOriginalFilename());
			System.out.println(pathRoot);
	        File temp=new File(newPath);
	        if (!temp.isDirectory()) {
				temp.mkdir();
			}
	        if(!file.isEmpty()){
	        	try {
	        		String prefix = System.currentTimeMillis()+"";//时间戳作为文件前缀
	 	            String originalImageName=file.getOriginalFilename();//获得上传文件的文件名（前缀+后缀）
	 	            String suffix=originalImageName.substring(originalImageName.lastIndexOf(".")+1);//文件的后缀名
	 	            String newImageName=prefix+"."+suffix;
	 	            File localFile = new File(newPath+newImageName);
	 	            System.out.println("最后的名字："+newPath+newImageName);
	 	            file.transferTo(localFile);
	 	            map.put("resultMsg", "成功");
	 	            map.put("resultCode", 200);
	 	            map.put("fileName", newImageName);//返回给移动端文件名字
	 	            return JSONUtils.toJson(map);
				} catch (Exception e) {
					e.printStackTrace();
					map.put("resultMsg", "上传文件失败");
			        map.put("resultCode", 404);
			        return JSONUtils.toJson(map);
				}
	        }else{
	        	 map.put("resultMsg", "文件不能为空");
		         map.put("resultCode", 404);
		         return JSONUtils.toJson(map);
	        }

	    }
}
