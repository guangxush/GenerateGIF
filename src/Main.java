import GenerateGIF.GenerateGif;
import GenerateGIF.GetFiles;

import java.io.File;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
