package com.hwy.service.impl;

import com.hwy.dao.TemplateMapper;
import com.hwy.dto.Page;
import com.hwy.dto.request.TemplateQueryReqDto;
import com.hwy.dto.request.TemplateReqDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.response.TemplateResDto;
import com.hwy.model.TemplateModel;
import com.hwy.service.TemplateService;
import com.hwy.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板服务
 *
 * @author hweiyu
 * @date 2018-08-10 09:47:28
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateMapper templateMapper;

    /**
    * 列表
    */
    @Override
    public PageResDto<TemplateResDto> select(TemplateQueryReqDto reqDto) {
        List<TemplateResDto> result = new ArrayList<>(20);
        Example example = new Example(TemplateModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (null != reqDto.getTemplate()) {
            criteria.andLike("templateName", "%" + reqDto.getTemplate() + "%");
        }
        int total = templateMapper.selectCountByExample(example);
        Page page = reqDto.to(total);
        if (total > 0) {
            List<TemplateModel> models = templateMapper.selectByExampleAndRowBounds(example,
                    PageUtil.getRowBounds(page));
            if (null != models) {
                for (TemplateModel model : models) {
                    result.add(TemplateResDto.get(model));
                }
            }
        }
        return PageUtil.getPageInfo(result, page);
    }

    /**
    * 查询
    */
    @Override
    public TemplateResDto get(TemplateReqDto reqDto) {
        TemplateModel model = templateMapper.selectOne(reqDto.to());
        return TemplateResDto.get(model);
    }

    /**
    * 添加
    */
    @Override
    public int insert(TemplateReqDto reqDto) {
        return templateMapper.insertSelective(reqDto.to());
    }

    /**
    * 修改
    */
    @Override
    public int update(TemplateReqDto reqDto) {
        return templateMapper.updateByPrimaryKeySelective(reqDto.to());
    }

    /**
    * 删除
    */
    @Override
    public int delete(TemplateReqDto reqDto) {
        return templateMapper.delete(reqDto.to());
    }

}
