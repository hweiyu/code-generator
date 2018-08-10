package com.hwy.service.impl;

import com.github.pagehelper.PageHelper;
import com.hwy.dao.TemplateMapper;
import com.hwy.dto.Page;
import com.hwy.dto.request.TemplateQueryReqDto;
import com.hwy.dto.request.TemplateReqDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.response.TemplateGroupSelectResDto;
import com.hwy.dto.response.TemplateResDto;
import com.hwy.model.TemplateModel;
import com.hwy.service.TemplateGroupService;
import com.hwy.service.TemplateService;
import com.hwy.utils.CollectionUtil;
import com.hwy.utils.LangUtils;
import com.hwy.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private TemplateGroupService templateGroupService;

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
        if (LangUtils.nvl(reqDto.getType()) > 0) {
            criteria.andEqualTo("templateType", reqDto.getType());
        }
        int total = templateMapper.selectCountByExample(example);
        Page page = reqDto.to(total);
        if (total > 0) {
            Map<Long, String> groupMap = getGroupMap();
            PageHelper.startPage(page.getPage(), page.getLimit());
            List<TemplateModel> models = templateMapper.selectByExample(example);
            if (CollectionUtil.isNotEmpty(models)) {
                for (TemplateModel model : models) {
                    result.add(TemplateResDto.get(model)
                            .setTemplateGroupName(
                                    groupMap.get(model.getGroupId())));
                }
            }
        }
        return PageUtil.getPageInfo(result, page);
    }

    private Map<Long, String> getGroupMap() {
        Map<Long, String> result = CollectionUtil.newHashMap();
        List<TemplateGroupSelectResDto> resDtos = templateGroupService.listAll();
        if (CollectionUtil.isNotEmpty(resDtos)) {
            for (TemplateGroupSelectResDto resDto : resDtos) {
                result.put(resDto.getId(), resDto.getGroupName());
            }
        }
        return result;
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

    @Override
    public List<TemplateModel> listByGroupId(Long groupId) {
        return templateMapper.select(TemplateModel.builder().build());
    }

}
