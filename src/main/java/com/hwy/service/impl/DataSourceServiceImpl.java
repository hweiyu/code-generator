package com.hwy.service.impl;

import com.github.pagehelper.PageHelper;
import com.hwy.dto.response.DataSourceSelectResDto;
import com.hwy.enums.DataStatusEnum;
import com.hwy.mapper.DataSourceMapper;
import com.hwy.dto.Page;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.request.DataSourceQueryReqDto;
import com.hwy.dto.request.DataSourceReqDto;
import com.hwy.dto.response.DataSourceResDto;
import com.hwy.model.DataSourceModel;
import com.hwy.service.DataSourceService;
import com.hwy.utils.CollectionUtil;
import com.hwy.utils.JdbcUtil;
import com.hwy.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 数据源服务
 * @date 2018/8/13 10:10
 **/
@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private DataSourceMapper dataSourceMapper;

    /**
    * 列表
    */
    @Override
    public PageResDto<DataSourceResDto> select(DataSourceQueryReqDto reqDto) {
        List<DataSourceResDto> result = CollectionUtil.newArrayList();
        Example example = new Example(DataSourceModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (null != reqDto.getDataSourceName()) {
            criteria.andLike("dataSourceName", "%" + reqDto.getDataSourceName() + "%");
        }
        int total = dataSourceMapper.selectCountByExample(example);
        Page page = reqDto.to(total);
        if (total > 0) {
            PageHelper.startPage(page.getPage(), page.getLimit());
            List<DataSourceModel> models = dataSourceMapper.selectByExample(example);
            if (null != models) {
                for (DataSourceModel model : models) {
                    result.add(DataSourceResDto.getWithPasswordHide(model));
                }
            }
        }
        return PageUtil.getPageInfo(result, page);
    }

    /**
    * 查询
    */
    @Override
    public DataSourceResDto get(DataSourceReqDto reqDto) {
        DataSourceModel model = dataSourceMapper.selectOne(reqDto.to());
        return DataSourceResDto.get(model);
    }

    @Override
    public DataSourceModel getById(Long id) {
        return dataSourceMapper.selectOne(DataSourceModel.builder()
                .id(id).dataStatus(DataStatusEnum.ENABLE.getType()).build());
    }

    /**
    * 添加
    */
    @Override
    public int insert(DataSourceReqDto reqDto) {
        return dataSourceMapper.insertSelective(reqDto.to());
    }

    /**
    * 修改
    */
    @Override
    public int update(DataSourceReqDto reqDto) {
        return dataSourceMapper.updateByPrimaryKeySelective(reqDto.to());
    }

    /**
    * 删除
    */
    @Override
    public int delete(DataSourceReqDto reqDto) {
        return dataSourceMapper.delete(reqDto.to());
    }

    @Override
    public void connectTest(DataSourceReqDto reqDto) {
        JdbcUtil.tryConnect(reqDto.toBean());
    }

    @Override
    public List<DataSourceSelectResDto> listAll() {
        List<DataSourceSelectResDto> result = CollectionUtil.newArrayList();
        List<DataSourceModel> models = dataSourceMapper.select(
                DataSourceModel.builder()
                        .dataStatus(DataStatusEnum.ENABLE.getType())
                        .build());
        if (null != models) {
            for (DataSourceModel model : models) {
                result.add(DataSourceSelectResDto.get(model));
            }
        }
        return result;
    }
}
