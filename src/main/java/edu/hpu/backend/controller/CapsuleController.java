package edu.hpu.backend.controller;

import edu.hpu.backend.common.Result;
import edu.hpu.backend.dto.CapsuleDTO;
import edu.hpu.backend.entity.Capsule;
import edu.hpu.backend.service.CapsuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/capsule")
@CrossOrigin(origins = "*")
public class CapsuleController {

    @Autowired
    private CapsuleService capsuleService;

    // 埋下胶囊
    @PostMapping("/add")
    public Result<String> add(@RequestHeader("X-User-Id") Long userId, @RequestBody CapsuleDTO dto) {
        // 我们通过请求头获取 userId (前端需要传)
        capsuleService.create(userId, dto);
        return Result.success("胶囊已埋下，静待花开");
    }

    // 获取我的胶囊列表
    @GetMapping("/list")
    public Result<List<Capsule>> list(@RequestHeader("X-User-Id") Long userId) {
        return Result.success(capsuleService.getMyCapsules(userId));
    }
}