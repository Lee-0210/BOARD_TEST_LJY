package com.aloha.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aloha.post.domain.Page;
import com.aloha.post.domain.Posts;

@Mapper
public interface PostMapper {

    // 목록
    public List<Posts> list(Page Page) throws Exception;
    // 조회
    public Posts select(Integer no) throws Exception;
    // 등록
    public int insert(Posts post) throws Exception;
    // 수정
    public int update(Posts post) throws Exception;
    // 삭제
    public int delete(Integer no) throws Exception;

    public long count() throws Exception;
}
