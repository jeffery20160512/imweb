package com.isoftstone.app.base.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 验证码工具类
 * @author husong
 *
 */
@SuppressWarnings("restriction")
public class verifyCodeUtil
{

    private static final String chars = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjklmnpqrstuvwxyz23456789";
    //验证码高度
    private static final int HEIGHT = 30;
    //验证码宽度
    private static final int WIDTH = 90;
    //验证码字符个数
    private static final int SIZE = 4;
    //验证码干扰线条数
    private static final int LINES = 4;
    //验证码字体大小
    private static final int FONT_HEIGHT = HEIGHT-2;
    
    public static Map<String, BufferedImage> createImage()
    {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = image.getGraphics();
        Random ran = new Random();
        graphic.setColor(Color.WHITE);
        graphic.fillRect(0, 0, WIDTH, HEIGHT);
       
        Font font = new Font("Fixedsys", Font.PLAIN, FONT_HEIGHT);
        graphic.setFont(font);

        graphic.setColor(Color.BLACK);
        graphic.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);

        graphic.setColor(Color.BLACK);
        for (int i = 0; i < LINES; i++) {
            graphic.setColor(getRandomColor());
            // graphic.drawLine(x1,y1,x2,y2) 在(x1,y1)与(x2,y2)之间画一条线； ran.nextInt（n）是从0到n的随机数
            graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT), ran.nextInt(WIDTH)+ran.nextInt(12), ran.nextInt(HEIGHT)+ran.nextInt(12));
        }
        
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < SIZE; i++)
        {
            int r = ran.nextInt(chars.length());
            graphic.setColor(getRandomColor());
            graphic.setFont(new Font("Fixedsys", Font.PLAIN, FONT_HEIGHT));
            graphic.drawString(chars.charAt(r) + "", (i * WIDTH / SIZE) + 5, HEIGHT - 5);
            sb.append(chars.charAt(r));
        }
        Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
        map.put(sb.toString(), image);
        return map;
    }

    public static Color getRandomColor()
    {
        Random ran = new Random();
        Color color = new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255));
        return color;
    }

	public static InputStream getInputStream(BufferedImage image) throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
        //encoder.encode(image);// 将图片压缩处理
        byte[] imageBts = bos.toByteArray();
        InputStream in = new ByteArrayInputStream(imageBts);
        return in;
    }

    // test
    /*public static void main(String[] args) throws Exception
    {
        Map<String, BufferedImage> map = createImage();
        Set<String> keys = map.keySet();
        for (String key : keys)
        {
            BufferedImage image = map.get(key);
            InputStream is = getInputStream(image);
            OutputStream os = new FileOutputStream(new File("d:/abc.jpeg"));
            ImageIO.write(image, "JPEG", os);
            os.close();
            is.close();
        }
    }*/
}
