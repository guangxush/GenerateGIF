import GenerateGIF.CompressPic;
import GenerateGIF.GenerateGif;
import GenerateGIF.GetFiles;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String filePath = "这里填写你需要生成GIF的多张png图片存放路径";
        File[] files = GetFiles.getFiles(filePath);
        //如果isPressed设置为true会对Gif图片进行压缩处理
        File preview = GenerateGif.generatePreview(files, true);
        try {
            preview.createNewFile();
            System.out.println("文件大小是："+preview.length()/1024+"KB");
            System.out.println(DigestUtils.md5Hex(new FileInputStream(preview)));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        String fileName = "jpg图片压缩测试";
//        File file = new File(fileName);
//        float qality = (float)0.4;
//        try {
//            CompressPic.compressPictureByQality(file,qality);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
