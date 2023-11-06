package travelMaker.backend.mypage.repository;

import travelMaker.backend.mypage.dto.response.BookMarkPlansDto;

import java.util.List;

public interface BookMarkRepositoryCustom {
    List<BookMarkPlansDto.BookMarkDto> bookMark(Long userId);
}
