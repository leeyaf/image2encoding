package org.fgst.exec;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

/**
 * 黑色 -16777216 白色-1
 */
public class Image2Encoding {
	private int fontSize = 8;
	private static HashMap<String, Integer> map;

	public static void main(String[] args) {
		
	}

	public void convert(String inputfilename, String savefilename,boolean isgif) {
		if(isgif){
			try {
				Image2Encoding image = new Image2Encoding();
				List<BufferedImage> images = image.openGif(inputfilename);
				System.out.println(images.size());

				AnimatedGifEncoder encoder = new AnimatedGifEncoder();
				encoder.setRepeat(0);
				encoder.start(savefilename);

				for (BufferedImage bufferedImage : images) {
					BufferedImage temp = image.draw(bufferedImage);
					encoder.addFrame(temp);
				}
				encoder.finish();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}else {
			try {
				Image2Encoding image = new Image2Encoding();
				File file = new File(inputfilename);
				BufferedImage bufferedImage = ImageIO.read(file);
				BufferedImage encodingImage = image.draw(bufferedImage);
				File file2 = new File(savefilename);
				ImageIO.write(encodingImage, "jpg", file2);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * 打开gif图片
	 * @return 动态图片的帧集合
	 */
	public List<BufferedImage> openGif(String filename) throws IOException {
		File file = new File(filename);
		Iterator readers = ImageIO.getImageReadersByFormatName("gif");
		ImageReader reader = (ImageReader) readers.next();
		reader.setInput(ImageIO.createImageInputStream(file));
		List<BufferedImage> images = new ArrayList<BufferedImage>();
		for (int i = 0; true; i++) {
			try {
				BufferedImage bufferedImage = reader.read(i);
				images.add(bufferedImage);
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("openGif failed: " + e.getMessage());

				break;
			}
		}
		return images;
	}

	/**
	 * 转化一个图片成编码图片
	 */
	public BufferedImage draw(BufferedImage bufferedImage) throws IOException {
		if (bufferedImage.getWidth() < 100 && bufferedImage.getHeight() < 100) {
			bufferedImage = zoomImage(bufferedImage, 6);
		} else if (bufferedImage.getWidth() < 300
				&& bufferedImage.getHeight() < 300) {
			bufferedImage = zoomImage(bufferedImage, 4);
		} else if (bufferedImage.getWidth() < 500
				&& bufferedImage.getHeight() < 500) {
			bufferedImage = zoomImage(bufferedImage, 2);
		}
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		System.out.println(width + "*" + height + "px");

		BufferedImage encodingImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D) encodingImage.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("微软雅黑", Font.ITALIC, fontSize));

		for (int i = 0; i < height; i += fontSize) {
			for (int j = 0; j < width; j += fontSize) {
				try {
					int[] temp = bufferedImage.getRGB(j, i, fontSize, fontSize,
							null, 0, fontSize);
					HashMap<String, Integer> mosts = mostEle(ints2Strings(temp));
					Set<Entry<String, Integer>> entitys = mosts.entrySet();
					for (Entry<String, Integer> entry : entitys) {
						int dip = Integer.parseInt(entry.getKey());
						Color color = new Color(dip);
						graphics.setColor(color);
						// graphics.fillRect(j, i, fontSize, fontSize);
						if (-1000000 < dip) {
							continue;
						} else if (-2000000 < dip) {
							graphics.drawString(".", j, i);
						} else if (-3000000 < dip) {
							graphics.drawString(":", j, i);
						} else if (-4000000 < dip) {
							graphics.drawString(";", j, i);
						} else if (-5000000 < dip) {
							graphics.drawString("|", j, i);
						} else if (-6000000 < dip) {
							graphics.drawString("(", j, i);
						} else if (-7000000 < dip) {
							graphics.drawString("L", j, i);
						} else if (-8000000 < dip) {
							graphics.drawString("T", j, i);
						} else if (-9000000 < dip) {
							graphics.drawString("K", j, i);
						} else if (-10000000 < dip) {
							graphics.drawString("E", j, i);
						} else if (-11000000 < dip) {
							graphics.drawString("B", j, i);
						} else if (-12000000 < dip) {
							graphics.drawString("$", j, i);
						} else if (-13000000 < dip) {
							graphics.drawString("&", j, i);
						} else if (-14000000 < dip) {
							graphics.drawString("%", j, i);
						} else if (-15000000 < dip) {
							graphics.drawString("W", j, i);
						} else {
							graphics.drawString("@", j, i);
						}
						break;
					}
				} catch (Exception e) {
					System.out.println("GET dip failed:" + e.getMessage());
				}
			}
		}

		graphics.dispose();

		System.out.println("draw image done!");
		return encodingImage;
	}

	/**
	 * 把一个图片放大N倍
	 */
	private BufferedImage zoomImage(BufferedImage bufferedImage, int times) {
		int width = bufferedImage.getWidth() * times;
		int height = bufferedImage.getHeight() * times;
		BufferedImage newImage = new BufferedImage(width, height,
				bufferedImage.getType());
		Graphics2D graphics2d = (Graphics2D) newImage.getGraphics();
		graphics2d.drawImage(bufferedImage, 0, 0, width, height, null);
		graphics2d.dispose();
		return newImage;
	}

	/**
	 * 把int数组转换成string数组
	 */
	private String[] ints2Strings(int[] ints) {
		String[] strings = new String[ints.length];
		for (int i = 0; i < ints.length; i++) {
			strings[i] = ints[i] + "";
		}
		return strings;
	}

	/**
	 * 计算一个数组中出现最多的元素以及出现次数
	 * 
	 * @return 当几个出现最多次数的元素出现的次数相等时，返回多个元素
	 */
	public static HashMap<String, Integer> mostEle(String[] strArray) {
		map = new HashMap<String, Integer>();

		String str = "";
		int count = 0;
		int result = 0;

		for (int i = 0; i < strArray.length; i++)
			str += strArray[i];

		for (int i = 0; i < strArray.length; i++) {
			String temp = str.replaceAll(strArray[i], "");
			count = (str.length() - temp.length()) / strArray[i].length();

			if (count > result) {
				map.clear();
				map.put(strArray[i], count);
				result = count;
			} else if (count == result)
				map.put(strArray[i], count);
		}
		return map;
	}
}
