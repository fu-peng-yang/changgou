package com.itheima.controller;

import com.itheima.service.ItemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itemInfo")
@CrossOrigin
public class ItemInfoController {
    @Autowired
    private ItemInfoService itemInfoService;

    /**
     * εΊε­ιε
     * @param id
     * @param count
     * @return
     */
    @PostMapping(value = "/decrCount")
    public String decrCount(@RequestParam(value = "id")int id,@RequestParam(value = "count")int count){
        itemInfoService.decrCount(id,count);
        return "success";
    }
}
