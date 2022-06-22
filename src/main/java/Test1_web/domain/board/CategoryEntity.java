package Test1_web.domain.board;

import Test1_web.domain.BaseTime;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Builder @Table(name="category")
public class CategoryEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;
    private String cname;

    @Builder.Default
    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

}
