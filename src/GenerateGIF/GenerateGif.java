package GenerateGIF;

import com.madgag.gif.fmsware.AnimatedGifEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GenerateGif {

    /**
     * 生成GIF图片
     * @param files 原始PNG图片
     * @param isPressed 是否被压缩，默认true
     * @return
     */
    public static File generatePreview(File[] files, Boolean isPressed) {
        try {
            BufferedImage[] images = null;
            if(isPressed)
                images = parseCompressed(files);
            else
                images = parse(files);
            Path path = Files.createTempFile("preview_", ".gif");
            System.out.println(path);
            AnimatedGifEncoder encoder = new AnimatedGifEncoder();
            // 设置循环模式，0为无限循环 这里没有使用源文件的播放次数
            encoder.setRepeat(0);

            encoder.start(new FileOutputStream(path.toFile()));
            int count = 1;
            //采样频率，数字越大，文件越小，丢失的帧越多，设置为1可保持原帧
            int frequency = 2;
            for (BufferedImage image : images) {
                if((++count)%frequency==0){
                    encoder.setDelay(50*frequency);
                    encoder.addFrame(image);
                }
            }
            encoder.finish();
            System.out.print("GIF创建成功： ");
            return path.toFile();
        } catch (IOException e) {
            System.out.println("failed to generate preview file");
            throw new RuntimeException("failed to generate preview file");
        }
    }

    /**
     * 把PNG素材压缩并转成BufferedImage
     *
     * @param files png文件
     * @return BufferedImage[]
     */
    private static BufferedImage[] parseCompressed(File[] files) {
        BufferedImage[] bi = new BufferedImage[files.length];
        try {
            for (int index = 0; index < files.length; index++) {
                bi[index] = CompressPic.compressPic(ImageIO.read(files[index]));
            }
        } catch (IOException e) {
            System.out.println("fail to parse template");
            throw new RuntimeException("fail to parse template", e);
        }
        return bi;
    }

    /**
     * 把PNG素材原图转成BufferedImage
     *
     * @param files png文件
     * @return BufferedImage[]
     */
    private static BufferedImage[] parse(File[] files) {
        BufferedImage[] bi = new BufferedImage[files.length];
        try {
            for (int index = 0; index < files.length; index++) {
                bi[index] = ImageIO.read(files[index]);
            }
        } catch (IOException e) {
            System.out.println("fail to parse template");
            throw new RuntimeException("fail to parse template", e);
        }
        return bi;
    }
}
