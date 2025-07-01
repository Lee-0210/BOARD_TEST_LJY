package com.aloha.post.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.aloha.post.domain.Page;
import com.aloha.post.domain.Posts;
import com.aloha.post.service.PostService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/list")
    public String list(Model model, Page page) throws Exception {
        List<Posts> list = postService.list(page);
        model.addAttribute("pagination", page);
        model.addAttribute("list", list);

        // Uri 빌더
        String pageUri = UriComponentsBuilder.fromPath("/posts/list")
                                            // .queryParam("page", Page.getPage())
                                            .queryParam("size", page.getSize())
                                            .queryParam("count", page.getCount())
                                            .build()
                                            .toUriString();

        model.addAttribute("pageUri", pageUri);

        return "posts/list";
    }

    @GetMapping("/read/{no}")
    public String read(@PathVariable("no") Integer no, Model model) throws Exception {
        // 데이터 요청
        Posts post = postService.select(no);
        // 모델 등록
        model.addAttribute("post", post);
        // 뷰 지정
        return "posts/read";
    }

    @GetMapping("/create")
    public String create(@ModelAttribute(value = "post") Posts post) {
        return "posts/insert";
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPostJSON(@Validated @RequestBody Posts post, BindingResult br) throws Exception {
        if (br.hasErrors()) {
            log.info("유효성 검사 실패");
            return ResponseEntity.badRequest().body(br.getFieldErrors());
        }

        log.info("그냥 통과됨");
        boolean result = postService.insert(post);
        if(result) return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);

        return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/update/{no}")
    public String update(@PathVariable("no") Integer no, Model model) throws Exception {
        // 데이터 요청
        Posts post = postService.select(no);
        // 모델 등록
        model.addAttribute("post", post);
        // 뷰 지정
        return "posts/update";
    }

    @PostMapping("/update")
    public String updatePostForm(Posts post) throws Exception {
        // 데이터 요청
        boolean result = postService.update(post);
        // 리다이렉트
        // ⭕ 데이터 처리 성공
        if(result) return"redirect:/posts/list";
        // ❌ 데이터 처리 실패
        return"redirect:/posts/update?error=true";
    }

    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePutJSON(@RequestBody Posts post) throws Exception {
        log.info("post : {}", post);
        // 데이터 요청
        boolean result = postService.update(post);
        // 리다이렉트
        if(result) return new ResponseEntity<>("SUCCESS", HttpStatus.OK);

        return  new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/delete/{no}")
    public String postDelete(@PathVariable("no") Integer no) throws Exception {
        // 데이터 요청
        boolean result = postService.delete(no);
        // 리다이렉트
        if(result) return "redirect:/posts/list";

        return "redirect:/posts/update/error=true";
    }

    @DeleteMapping("/{no}")
    public ResponseEntity<?> postDeleteJSON(@PathVariable("no") Integer no) throws Exception {
        // 데이터 요청
        boolean result = postService.delete(no);
        // 리다이렉트
        if(result) return new ResponseEntity<>("SUCCESS", HttpStatus.OK);

        return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
    }

}
