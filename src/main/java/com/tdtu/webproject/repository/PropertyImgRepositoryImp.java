package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtPropertyImgExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtPropertyImgMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtPropertyImg;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@AllArgsConstructor
@ComponentScan
public class PropertyImgRepositoryImp implements PropertyImgRepository {
    private final TdtPropertyImgMapper TdtPropertyImg;
    @Override
    public List<TdtPropertyImg> selectAll() {
        TdtPropertyImgExample example = new TdtPropertyImgExample();
        TdtPropertyImgExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        return TdtPropertyImg.selectByExample(example);
    }
}
