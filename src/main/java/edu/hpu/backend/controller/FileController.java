package edu.hpu.backend.controller;

import edu.hpu.backend.common.Result;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/file")
@CrossOrigin(origins = "*")
public class FileController {

    // 图片存储路径：项目根目录/images/
    private final String UPLOAD_DIR = "/home/time_capsule/images/";
    // 1. 上传接口
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();

            // 生成唯一文件名，防止重名覆盖
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + suffix;

            // 保存文件
            file.transferTo(new File(UPLOAD_DIR + newFileName));

            // 返回可访问的 URL (对应下面的 view 接口)
            String fileUrl = "http://localhost:8081/api/file/view/" + newFileName;
            return Result.success("上传成功", fileUrl);
        } catch (IOException e) {
            return Result.error("上传失败：" + e.getMessage());
        }
    }

    // 2. 查看图片接口 (用于前端 img src 访问)
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> view(@PathVariable String fileName) {
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // 简单起见，统一识别为图片
                        .body(resource);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }
}