# FastDFS-API
*FastDFS simple packge*

### features

- simple 
- verify size and format
- support extend

### dependencies

- SpringBoot 2.1.8.RELEASE
- fastdfs-client  1.29-SNAPSHOT
- JDK 1.8

### demo 

**video**

```
    /**
     * 视频类文件上传
     * @param file 视频类文件
     * @return ResponseWrapper
     */
    @PostMapping("/upload/video")
    public ResponseWrapper uploadVideo(@RequestParam("file") @ValidUploadFile(value ={FileFormatEnum.MP4}, maxSize = "100MB") MultipartFile file){
        FileDTO fileDTO = super.upload(file);
        if(fileDTO == null){
          return ResponseWrapper.error(DfsErrorStatusEnum.DFS_SERVICE_OPERATION_ERROR);
        }
        return ResponseWrapper.success(fileDTO);
    }
```

### Relevant 

*https://github.com/happyfish100/fastdfs*

