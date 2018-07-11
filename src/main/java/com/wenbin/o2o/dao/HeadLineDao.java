package com.wenbin.o2o.dao;

import java.util.List;

import com.wenbin.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;


public interface HeadLineDao {

    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);

    HeadLine queryHeadLineById(long lineId);

    List<HeadLine> queryHeadLineByIds(List<Long> lineIdList);

    int insertHeadLine(HeadLine headLine);

    int updateHeadLine(HeadLine headLine);

    int deleteHeadLine(long headLineId);

    int batchDeleteHeadLine(List<Long> lineIdList);
}
