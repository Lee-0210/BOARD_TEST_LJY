package com.aloha.post.service;

import java.util.List;

import com.aloha.post.domain.Page;
import com.aloha.post.domain.Posts;

public interface PostService {

    // 목록
    public List<Posts> list(Page page) throws Exception;

    // 조회
    public Posts select(Integer no) throws Exception;

    // 등록
    public boolean insert(Posts post) throws Exception;

    // 수정
    public boolean update(Posts post) throws Exception;

    // 삭제
    public boolean delete(Integer no) throws Exception;
}
