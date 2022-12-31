package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtNewsExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtNewsMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtNews;
import com.tdtu.webproject.model.NewsSearchCondition;
import com.tdtu.webproject.mybatis.mapper.NewsSupportMapper;
import com.tdtu.webproject.mybatis.result.NewsSearchResult;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
@ComponentScan
public class NewsRepositoryImp implements NewsRepository {
    private final TdtNewsMapper tdtNewsMapper;
    private final NewsSupportMapper newsSupportMapper;

    @Override
    public List<TdtNews> selectAll() {
        TdtNewsExample example = new TdtNewsExample();
        TdtNewsExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        return tdtNewsMapper.selectByExample(example);
    }

    @Override
    public int create(TdtNews record) {
        return tdtNewsMapper.insertSelective(record);
    }

    @Override
    public int update(TdtNews record, BigDecimal newsId) {
        TdtNewsExample example = new TdtNewsExample();
        TdtNewsExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(newsId).ifPresent(criteria::andNewsIdEqualTo);
        criteria.andIsDeletedEqualTo(false);
        return tdtNewsMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int delete(BigDecimal newsId) {
        TdtNewsExample example = new TdtNewsExample();
        TdtNewsExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(newsId).ifPresent(criteria::andNewsIdEqualTo);
        criteria.andIsDeletedEqualTo(false);
        return tdtNewsMapper.updateByExampleSelective(
                TdtNews.builder()
                        .isDeleted(true)
                        .build(),
                example);
    }

    @Override
    public Long countNews(NewsSearchCondition condition) {
        return newsSupportMapper.count(condition);
    }

    @Override
    public List<NewsSearchResult> searchNews(NewsSearchCondition condition) {
        return newsSupportMapper.select(condition);
    }
}
