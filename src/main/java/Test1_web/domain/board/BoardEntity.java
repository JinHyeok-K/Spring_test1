package Test1_web.domain.board;


import Test1_web.domain.BaseTime;
//import Test1_web.domain.user.UserEntity;
import lombok.*;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder @ToString( exclude = { "categoryEntity"})
@Table(name="board")
@Entity
public class BoardEntity  extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;
    private String bid;
    private String btitle;
    private String bcontent;
    private String bpassword;
    private int bview;
    private int blike;


//    @ManyToOne
//    @JoinColumn(name = "mno")
//    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name="cno")
    private CategoryEntity categoryEntity;

}
