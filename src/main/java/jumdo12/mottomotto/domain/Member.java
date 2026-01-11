package jumdo12.mottomotto.domain;

import jakarta.persistence.*;
import jumdo12.mottomotto.common.BaseEntity;
import lombok.Getter;

@Entity
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;
}
