package jumdo12.mottomotto.domain.event;

import jumdo12.mottomotto.domain.KarutaGameChatMessage;
import lombok.Getter;

@Getter
public class KarutaGameChatEvent extends KarutaGameEvent {

    private final KarutaGameChatMessage chatMessage;

    public KarutaGameChatEvent(String roomId, KarutaGameChatMessage karutaGameChatMessage) {
        super(roomId);

        this.chatMessage = karutaGameChatMessage;
    }
}
