package jumdo12.mottomotto.domain.event;

import jumdo12.mottomotto.domain.Participant;
import lombok.Getter;

@Getter
public class KarutaGameJoinEvent extends KarutaGameEvent {

    private final Participant participant;

    public KarutaGameJoinEvent(String roomId, Participant participant) {
        super(roomId);

        this.participant = participant;
    }
}
