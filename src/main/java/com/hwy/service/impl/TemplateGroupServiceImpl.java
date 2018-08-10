package com.hwy.service.impl;

import com.github.pagehelper.PageHelper;
import com.hwy.dao.TemplateGroupMapper;
import com.hwy.dto.Page;
import com.hwy.dto.request.TemplateGroupQueryReqDto;
import com.hwy.dto.request.TemplateGroupReqDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.response.TemplateGroupResDto;
import com.hwy.model.TemplateGroupModel;
import com.hwy.service.TemplateGroupService;
import com.hwy.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库源服务
 *
 * @author hweiyu
 * @date 2018-08-10 09:47:28
 */
@Service
public class TemplateGroupServiceImpl implements TemplateGroupService {

    @Autowired
    private TemplateGroupMapper templateGroupMapper;

    /**
     * 列表
     */
    @Override
    public PageResDto<TemplateGroupResDto> select(TemplateGroupQueryReqDto reqDto) {
        List<TemplateGroupResDto> result = new ArrayList<>(20);
        Example example = new Example(TemplateGroupModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (null != reqDto.getGroupName()) {
            criteria.andLike("groupName", "%" + reqDto.getGroupName() + "%");
        }
        int total = templateGroupMapper.selectCountByExample(example);
        Page page = reqDto.to(total);
        if (total > 0) {
            PageHelper.startPage(page.getPage(), page.getLimit());
            List<TemplateGroupModel> models = templateGroupMapper.selectByExample(example);
            if (null != models) {
                for (TemplateGroupModel model : models) {
                    result.add(TemplateGroupResDto.get(model));
                }
            }
        }
        return PageUtil.getPageInfo(result, page);
    }

    /**
     * 查询
     */
    @Override
    public TemplateGroupResDto get(TemplateGroupReqDto reqDto) {
        TemplateGroupModel model = templateGroupMapper.selectOne(reqDto.to());
        return TemplateGroupResDto.get(model);
    }

    /**
     * 添加
     */
    @Override
    public int insert(TemplateGroupReqDto reqDto) {
        return templateGroupMapper.insertSelective(reqDto.to());
    }

    /**
     * 修改
     */
    @Override
    public int update(TemplateGroupReqDto reqDto) {
        return templateGroupMapper.updateByPrimaryKeySelective(reqDto.to());
    }

    /**
     * 删除
     */
    @Override
    public int delete(TemplateGroupReqDto reqDto) {
        return templateGroupMapper.delete(reqDto.to());
    }

}
