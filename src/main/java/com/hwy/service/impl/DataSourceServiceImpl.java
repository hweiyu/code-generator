package com.hwy.service.impl;

import com.hwy.dao.DataSourceMapper;
import com.hwy.dto.Page;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.request.DataSourceQueryReqDto;
import com.hwy.dto.request.DataSourceReqDto;
import com.hwy.dto.response.DataSourceResDto;
import com.hwy.model.DataSourceModel;
import com.hwy.service.DataSourceService;
import com.hwy.utils.JdbcUtil;
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
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private DataSourceMapper dataSourceMapper;

    /**
    * 列表
    */
    @Override
    public PageResDto<DataSourceResDto> select(DataSourceQueryReqDto reqDto) {
        List<DataSourceResDto> result = new ArrayList<>(20);
        Example example = new Example(DataSourceModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (null != reqDto.getDataSourceName()) {
            criteria.andLike("dataSourceName", "%" + reqDto.getDataSourceName() + "%");
        }
        int total = dataSourceMapper.selectCountByExample(example);
        Page page = reqDto.to(total);
        if (total > 0) {
            List<DataSourceModel> models = dataSourceMapper.selectByExampleAndRowBounds(example,
                    PageUtil.getRowBounds(page));
            if (null != models) {
                for (DataSourceModel model : models) {
                    result.add(DataSourceResDto.get(model));
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
    public boolean connectTest(DataSourceReqDto reqDto) {
        return JdbcUtil.tryConnect(reqDto.to());
    }
}