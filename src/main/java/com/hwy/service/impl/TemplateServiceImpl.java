package com.hwy.service.impl;

import com.github.pagehelper.PageHelper;
import com.hwy.dto.request.TemplateGenReqDto;
import com.hwy.dto.response.TemplateGenResDto;
import com.hwy.enums.DataStatusEnum;
import com.hwy.mapper.TemplateMapper;
import com.hwy.dto.Page;
import com.hwy.dto.request.TemplateQueryReqDto;
import com.hwy.dto.request.TemplateReqDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.response.TemplateResDto;
import com.hwy.model.TemplateGroupModel;
import com.hwy.model.TemplateModel;
import com.hwy.service.TemplateGroupService;
import com.hwy.service.TemplateService;
import com.hwy.utils.CollectionUtil;
import com.hwy.utils.LangUtils;
import com.hwy.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 模板服务
 * @date 2018/8/13 10:10
 **/
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
        List<TemplateResDto> result = CollectionUtil.newArrayList();
        Example example = new Example(TemplateModel.class);
        setCondition(example, reqDto);
        int total = templateMapper.selectCountByExample(example);
        Page page = reqDto.to(total);
        if (total > 0) {
            Map<Long, TemplateGroupModel> groupMap = getGroupMap();
            PageHelper.startPage(page.getPage(), page.getLimit());
            List<TemplateModel> models = templateMapper.selectByExample(example);
            if (CollectionUtil.isNotEmpty(models)) {
                for (TemplateModel model : models) {
                    TemplateGroupModel group = groupMap.get(model.getGroupId());
                    result.add(TemplateResDto.get(model)
                            .setTemplateGroupName(group.getGroupName())
                            .setFilePath(group.getMainPackage(), group.getModuleName()));
                }
            }
        }
        return PageUtil.getPageInfo(result, page);
    }

    private void setCondition(Example example, TemplateQueryReqDto reqDto) {
        Example.Criteria criteria = example.createCriteria();
        criteria.andNotEqualTo("dataStatus", DataStatusEnum.DELETED.getType());
        if (null != reqDto.getTemplate()) {
            criteria.andLike("templateName", "%" + reqDto.getTemplate() + "%");
        }
        if (LangUtils.nvl(reqDto.getType()) > 0) {
            criteria.andEqualTo("templateType", reqDto.getType());
        }
        if (LangUtils.nvl(reqDto.getGroupId()) > 0) {
            criteria.andEqualTo("groupId", reqDto.getGroupId());
        }
    }

    private Map<Long, TemplateGroupModel> getGroupMap() {
        Map<Long, TemplateGroupModel> result = CollectionUtil.newHashMap();
        List<TemplateGroupModel> models = templateGroupService.listAllModel();
        for (TemplateGroupModel model : models) {
            result.put(model.getId(), model);
        }
        return result;
    }

    /**
    * 查询
    */
    @Override
    public TemplateResDto get(TemplateReqDto reqDto) {
        TemplateModel model = templateMapper.selectOne(reqDto.to());
        return TemplateResDto.getWithContext(model);
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

    /**
     * 通过模板组id查模板列表
     * @param groupId
     * @return
     */
    @Override
    public List<TemplateModel> listByGroupId(Long groupId) {
        return templateMapper.select(TemplateModel.builder().groupId(groupId).build());
    }

    /**
     * 通过模板组id查模板列表，生成代码使用
     * @param reqDto
     * @return
     */
    @Override
    public List<TemplateGenResDto> genList(TemplateGenReqDto reqDto) {
        TemplateGroupModel groupModel = templateGroupService.getById(reqDto.getGroupId());
        List<TemplateModel> templateModels = templateMapper.select(
                TemplateModel.builder().groupId(reqDto.getGroupId()).dataStatus(DataStatusEnum.ENABLE.getType()).build());
        List<TemplateGenResDto> result = CollectionUtil.newArrayList();
        if (null != groupModel
                && CollectionUtil.isNotEmpty(templateModels)
                && CollectionUtil.isNotEmpty(reqDto.getTableNameList())) {
            for (String tableName : reqDto.getTableNameList()) {
                for (TemplateModel templateModel : templateModels) {
                    TemplateGenResDto resDto = TemplateGenResDto.get(templateModel);
                    resDto.setTableName(tableName);
                    resDto.setTablePrefix(groupModel.getTablePrefix());
                    resDto.setFilePath(groupModel.getMainPackage(), groupModel.getModuleName());
                    resDto.setFileRealName();
                    result.add(resDto);
                }
            }
        }
        return result;
    }

}
