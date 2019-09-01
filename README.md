## 多张图片生成GIF图片

### GetFiles 
- getFiles 用于读取某目录下的多张图片（按照帧的顺序排序）

### CompressPic
- compressPic 对图片进行压缩处理

### GenerateGif
- generatePreview 用于生成Gif预览图，并存储到临时目录下
- parse 把PNG素材转成BufferedImage格式
- parseCompressed 把PNG素材进行等比压缩并转成BufferedImage格式

### Main
- main 最终的调用

### 参考文档
- [参考链接](https://github.com/rtyley/animated-gif-lib-for-java)

### jar包下载
- [animated-gif-lib-1.4.jar](http://www.java2s.com/Code/Jar/a/Downloadanimatedgiflib10jar.htm)
- [commons-codec-1.11.jar](https://commons.apache.org/proper/commons-codec/download_codec.cgi)

### 多张PNG图片生成GIF图片算法（可抽帧压缩）
```java
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
```
### 把PNG素材压缩并转成BufferedImage
```java
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

```
### 对PNG图片进行等比压缩处理
```java
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
                imgCompressed = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
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
```

### 单张图片压缩算法（与上述方法无关）
```java
     /**
     * 压缩图片
     * @param file
     * @param qality 参数qality是取值0~1范围内  代表压缩的程度
     * @return
     * @throws IOException
     */
    public static File compressPictureByQality(File file, float qality) throws IOException {
        BufferedImage src = null;
        FileOutputStream out = null;
        ImageWriter imgWrier;
        ImageWriteParam imgWriteParams;
        System.out.println("开始设定压缩图片参数");
        // 指定写图片的方式为 jpg
        imgWrier = ImageIO.getImageWritersByFormatName("png").next();
        imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(
                null);
        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
        imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT);
        // 这里指定压缩的程度，参数qality是取值0~1范围内，
        imgWriteParams.setCompressionQuality(qality);
        imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);
        ColorModel colorModel =ImageIO.read(file).getColorModel();// ColorModel.getRGBdefault();
        imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
                colorModel, colorModel.createCompatibleSampleModel(32, 32)));
        System.out.println("结束设定压缩图片参数");
        if (!file.exists()) {
            System.out.println("Not Found Img File,文件不存在");
            throw new FileNotFoundException("Not Found Img File,文件不存在");
        } else {
            System.out.println("图片转换前大小"+file.length()/1024+"kb");
            src = ImageIO.read(file);
            out = new FileOutputStream(file);
            imgWrier.reset();
            // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
            // OutputStream构造
            imgWrier.setOutput(ImageIO.createImageOutputStream(out));
            // 调用write方法，就可以向输入流写图片
            imgWrier.write(null, new IIOImage(src, null, null),
                    imgWriteParams);
            out.flush();
            out.close();
            System.out.println("图片转换后大小"+file.length()/1024+"kb");
            return file;
        }
    }
```
### 项目源码参考
[项目链接](https://github.com/guangxush/GenerateGIF)
