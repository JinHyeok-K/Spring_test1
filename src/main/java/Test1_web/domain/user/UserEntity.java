package Test1_web.domain.user;


import Test1_web.domain.BaseTime;
import Test1_web.domain.board.BoardEntity;
import lombok.*;

//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name="user")
//@Getter @Setter @AllArgsConstructor @NoArgsConstructor
//@ToString @Builder
//
//public class UserEntity extends BaseTime {
//
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int mno;
//    private String mid;
//    private String mpassword;
//
//    @Builder.Default    // 빌더 사용시 초기값 설정
//    @OneToMany( mappedBy ="memberEntity" , cascade = CascadeType.ALL)  // 1:M
//    List<BoardEntity> boardEntityList = new ArrayList<>();
//
//
//
//}
