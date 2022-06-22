package Test1_web.dto;


import Test1_web.domain.BaseTime;
import Test1_web.domain.board.BoardEntity;
import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Builder
public class BoardDto extends BaseTime {

    private int bno;                // 번호
    private String btitle;         // 제목
    private String bcontent;    // 내용
    private int bview;              // 조회수
    private int blike;              // 좋아요 수

    private String category;    // 카데고리

    // DTO -> ENTITY
    public BoardEntity toentity(){
        return BoardEntity.builder()
                .bno( this.bno )
                .btitle( this.btitle)
                .blike( this.bview)
                .blike( this.blike)
                .build();
    }


}
