package Test1_web.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity,Integer> {



    @Query( value = "select * from board where cno = :cno and btitle like %:keyword%" , nativeQuery = true )
    Page<BoardEntity> findBybtitle(int cno , @Param("keyword")  String keyword, Pageable pageable);
    //List 대신 Page 쓰는 이유 : 페이징  관련 메소드를 사용하기 위해서
    // 2. 내용 검색
    @Query( value = "select * from board where cno = :cno and bcontent like %:keyword%" , nativeQuery = true )
    Page<BoardEntity> findBybcontent(  int cno ,    @Param("keyword") String keyword ,Pageable pageable );
//    // 3. 작성자 검색
//    @Query( value = "select * from board where cno = :cno and mno = :#{#memberEntity.mno}", nativeQuery = true  )
//    Page<BoardEntity> findBymno(   int cno ,    @Param("memberEntity") MemberEntity memberEntity,Pageable pageable  );



}
