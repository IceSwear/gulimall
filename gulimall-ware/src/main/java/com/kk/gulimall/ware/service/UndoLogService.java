package com.kk.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kk.common.utils.PageUtils;
import com.kk.gulimall.ware.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 15:22:31
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

