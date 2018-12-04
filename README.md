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

