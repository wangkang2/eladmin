package com.wk.controller.system;

import com.wk.entity.system.dto.MenuDto;
import com.wk.entity.system.vo.MenuVo;
import com.wk.service.system.SysMenuService;
import com.wk.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "系统：菜单管理")
@RequestMapping("/api/menus")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping(value = "/build")
    @ApiOperation("获取前端所需菜单")
    public ResponseEntity<List<MenuVo>> buildMenus(){

        List<MenuDto> menuDtoList = sysMenuService.findByUserId(SecurityUtils.getCurrentUserId());
        List<MenuDto> menuDtos = sysMenuService.buildTree(menuDtoList);
        List<MenuVo> menuVos = sysMenuService.buildMenus(menuDtos);
        return ResponseEntity.ok(menuVos);
    }

}
