package jumdo12.mottomotto.domain;

import jumdo12.mottomotto.common.exception.MottoException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class KarutaGameRoom {

    private static final int MAX_PARTICIPANTS = 4;

    private final String roomId;
    private final String title;
    private final List<Participant> participants;

    private KarutaGameRoom(String roomId, String title, List<Participant> participants) {
        this.roomId = roomId;
        this.title = title;
        this.participants = participants;
    }

    public static KarutaGameRoom create(String roomId, String title) {
        return new KarutaGameRoom(roomId, title, new ArrayList<>());
    }

    public Participant join(Member member) {
        if(participants.size() > MAX_PARTICIPANTS) {
            throw new MottoException("정원을 초과하였습니다.");
        }

        if(isParticipatant(member)) {
            throw new MottoException("이미 참여 중인 방입니다.");
        }

        Participant participant = Participant.create(member);

        participants.add(participant);

        return participant;
    }

    public Participant leave(Member member) {
        Participant participant = findParticipant(member);
        participants.remove(participant);

        return participant;
    }

    public Participant findParticipant(Member member) {
        return participants.stream()
                .filter(p -> p.getMember().getId().equals(member.getId()))
                .findFirst()
                .orElseThrow(() -> new MottoException("방에 참여 중이지 않습니다."));
    }

    public boolean isParticipatant(Member member) {
        return this.participants.stream()
                .anyMatch(p -> p.getMember().getId().equals(member.getId()));
    }

    public boolean isEmpty() {
        return participants.isEmpty();
    }
}
