package com.kech.business.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kech.common.entity.pms.PmsComment;
import com.kech.business.goods.mapper.PmsCommentMapper;
import com.kech.business.goods.service.IPmsCommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品评价表 服务实现类
 * </p>
 *
 * 
 */
@Service
public class PmsCommentServiceImpl extends ServiceImpl<PmsCommentMapper, PmsComment> implements IPmsCommentService {

}
