package jumdo12.mottomotto.domain;

import lombok.Getter;

@Getter
public class Participant {

    private final Member member;

    private Participant(Member member) {
        this.member = member;
    }

    public static Participant create(Member member) {
        return new Participant(member);
    }
}
