package org.fgst.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UploadFileServlet extends HttpServlet {
	private static Log log = LogFactory.getLog(UploadFileServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 用于存放输出的信息
		Map<String, Object> message = new HashMap<String, Object>();

		// 在自己的项目中构造出一个用于存放用户照片的文件夹
		String projectpath = this.getServletContext().getRealPath("/images");
		// 如果此文件夹不存在，则构造此文件夹
		File f = new File(projectpath);
		if (!f.exists()) {
			f.mkdir();
		}
		// 构造出文件工厂，用于存放JSP页面中传递过来的文件
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置缓存大小，如果文件大于缓存大小时，则先把文件放到缓存中
		factory.setSizeThreshold(4 * 1024);
		// 设置上传文件的保存路径
		factory.setRepository(f);

		// 产生Servlet上传对象
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设置可以上传文件大小的上界4MB
		upload.setSizeMax(4 * 1024 * 1024);
		try {
			// 取得所有上传文件的信息
			List<FileItem> list = upload.parseRequest(req);
			Iterator<FileItem> iter = list.iterator();
			while (iter.hasNext()) {
				FileItem item = iter.next();
				// 如果接收到的参数不是一个普通表单(例text等)的元素，那么执行下面代码
				if (!item.isFormField()) {
					String fieldName = item.getFieldName();// 获得此表单元素的name属性
					String fileName = item.getName();// 获得文件的完整路径
					// 从文件的完整路径中截取出文件名
					fileName = fileName.substring(
							fileName.lastIndexOf("\\") + 1, fileName.length());

					// 判断是否有图片上传
					if (!("".equals(fileName)) && !(fileName == null)) {

						// 如果上传的文件不是图片，那么不上传
						String allImgExt = ".jpg|.jpeg|.gif|.bmp|.png|";
						String extName = fileName.substring(
								fileName.indexOf("."), fileName.length());
						if (allImgExt.indexOf(extName + "|") == -1) {
							message.put("error", "该文件类型不允许上传。请上传 " + allImgExt
									+ " 类型的文件，当前文件类型为" + extName);
							break;
						}

						String filepath = projectpath + "\\" + fieldName;
						File uf = new File(filepath);
						// 更改文件的保存路径，以防止文件重名的现象出现
						if (!uf.exists()) {
							uf.mkdir();
						}
						String tempfilename = Long.toString(new Date()
								.getTime()) + extName;
						// 此输出路径为保存到数据库中photo字段的路径
						String insertDB = filepath + "\\" + tempfilename;
						log.info("文件路径：" + insertDB + ":" + insertDB.length());

						File uploadedFile = new File(filepath, tempfilename);

						try {
							// 如果在该文件夹中已经有相同的文件，那么将其删除之后再重新创建（只适用于上传一张照片的情况）
							if (uploadedFile.exists()) {
								uploadedFile.delete();
							}
							item.write(uploadedFile);
							message.put("address", tempfilename);

						} catch (Exception e) {
							log.error(e.getMessage());
						}

					} else {
						// 取得普通的对象（对于像文本框这种类型的使用）
						// 对于普通类型的对象暂不做处理
						// return ;
						System.out.println("false");
					}
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			message.put("error", "文件的内容过大，请上传小于4MB的文件");
			log.error(e.getMessage());
		}

		req.setAttribute("message", message);
		req.getRequestDispatcher("convert_image").forward(req, resp);
	}
}
