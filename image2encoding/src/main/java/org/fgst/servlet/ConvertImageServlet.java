package org.fgst.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fgst.exec.AnimatedGifEncoder;
import org.fgst.exec.Image2Encoding;

public class ConvertImageServlet extends HttpServlet {
	private static Log log = LogFactory.getLog(ConvertImageServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getAttribute("message") == null) {
			req.getRequestDispatcher("index.jsp").forward(req, resp);
			return;
		}
		Map<String, Object> message = (Map<String, Object>) req
				.getAttribute("message");
		String filename = (String) message.get("address");
		String extName = filename.substring(filename.indexOf("."),
				filename.length());
		String projectpath = this.getServletContext().getRealPath("/images");
		Image2Encoding encoding = new Image2Encoding();
		File readFile = new File(projectpath + "\\image\\" + filename);
		String encodingfilename = projectpath + "\\encoding\\" + filename;

		if (".gif".equals(extName)) {
			try {
				List<BufferedImage> images = encoding.openGif(readFile);
				AnimatedGifEncoder encoder = new AnimatedGifEncoder();
				encoder.setRepeat(0);
				encoder.start(encodingfilename);

				for (BufferedImage bufferedImage : images) {
					BufferedImage temp = encoding.draw(bufferedImage);
					encoder.addFrame(temp);
				}
				encoder.finish();
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		} else {
			try {
				BufferedImage bufferedImage = ImageIO.read(readFile);
				BufferedImage encodingImage = encoding.draw(bufferedImage);
				ImageIO.write(encodingImage, extName.substring(1),
						new File(encodingfilename));
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		message.put("encoding", filename);
		req.setAttribute("message", message);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
}
