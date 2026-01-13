package jumdo12.mottomotto.domain;

import jakarta.persistence.*;
import jumdo12.mottomotto.common.BaseEntity;
import jumdo12.mottomotto.common.exception.MottoException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Builder
    private Member(String email, String password, String nickname) {
        Assert.hasText(email, "이메일은 필수입니다.");
        Assert.hasText(nickname, "닉네임은 필수입니다.");

        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
