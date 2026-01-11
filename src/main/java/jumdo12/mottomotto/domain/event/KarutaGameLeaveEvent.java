package jumdo12.mottomotto.domain.event;

import jumdo12.mottomotto.domain.Participant;
import lombok.Getter;

@Getter
public class KarutaGameLeaveEvent extends KarutaGameEvent {

    private final Participant participant;

    public KarutaGameLeaveEvent(String roomId, Participant participant) {
        super(roomId);

        this.participant = participant;
    }
}
