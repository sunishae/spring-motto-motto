package jumdo12.mottomotto.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class KarutaGameChatMessage {

    private final String message;
    private final Participant sender;
    private final String karutaRoomId;
    private final LocalDateTime sentAt;

    private KarutaGameChatMessage(String message,
                                  Participant sender,
                                  String karutaRoomId,
                                  LocalDateTime sentAt) {
        this.message = message;
        this.sender = sender;
        this.karutaRoomId = karutaRoomId;
        this.sentAt = sentAt;
    }

    public static KarutaGameChatMessage create(String message,
                                               Participant sender,
                                               String karutaRoomId) {
        return new KarutaGameChatMessage(
                message,
                sender,
                karutaRoomId,
                LocalDateTime.now()
        );
    }
}
