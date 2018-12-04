package GenerateGIF;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CompressPic {

    private static boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)
    private static int outputWidth = 115; // 默认输出图片宽
    private static int outputHeight = 90; // 默认输出图片高

    /**
     * 对图片进行压缩处理
     * @param img
     * @return
     */
    public static BufferedImage compressPic(BufferedImage img) {
        BufferedImage imgCompressed = null;
        try {
            if (img.getWidth(null) == -1) {
                //打印错误日志
                System.out.println("图片格式错误！");
                return imgCompressed;
            } else {
                int newWidth;
                int newHeight;
                // 判断是否是等比缩放
                if (proportion == true) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) img.getWidth(null))
                            / (double) outputWidth * 1.0;
                    double rate2 = ((double) img.getHeight(null))
                            / (double) outputHeight * 1.0;
                    // 根据缩放比率大的进行缩放控制
                    double rate = rate1 > rate2 ? rate1 : rate2;
                    newWidth = (int) (((double) img.getWidth(null)) / rate);
                    newHeight = (int) (((double) img.getHeight(null)) / rate);
                } else {
                    newWidth = outputWidth; // 输出的图片宽度
                    newHeight = outputHeight; // 输出的图片高度
                }
                imgCompressed = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
                /*
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的优先级比速度高 生成的图片质量比较好 但速度慢
                 */
                imgCompressed.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH)
                        , 0, 0, null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return imgCompressed;
    }
}
