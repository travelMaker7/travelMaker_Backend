package travelMaker.backend.mypage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelMaker.backend.mypage.model.BookMark;

public interface BookMarkRepository extends JpaRepository<BookMark, Long>, BookMarkRepositoryCustom {
}
