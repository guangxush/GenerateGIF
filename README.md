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
