package jumdo12.mottomotto.infra;

import jumdo12.mottomotto.domain.event.KarutaGameChatEvent;
import jumdo12.mottomotto.domain.event.KarutaGameJoinEvent;
import jumdo12.mottomotto.domain.event.KarutaGameLeaveEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Async
@RequiredArgsConstructor
public class KarutaGameEventListener {

    private final static String CHAT_EVENT_DESCRIPTION = "CHAT";
    private final static String JOIN_EVENT_DESCRIPTION = "JOIN";
    private final static String LEAVE_EVENT_DESCRIPTION = "LEAVE";

    private final SseProvider sseProvider;

    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleKarutaGameJoinEvent(KarutaGameJoinEvent karutaGameJoinEvent) {
        sseProvider.broadcast(
                karutaGameJoinEvent.getRoomId(),
                JOIN_EVENT_DESCRIPTION,
                karutaGameJoinEvent.getParticipant());
    }

    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleKarutaGameLeaveEvent(KarutaGameLeaveEvent karutaGameLeaveEvent) {
        sseProvider.broadcast(
                karutaGameLeaveEvent.getRoomId(),
                LEAVE_EVENT_DESCRIPTION,
                karutaGameLeaveEvent.getParticipant());
    }

    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleKarutaGameChatEvent(KarutaGameChatEvent karutaGameChatEvent) {
        sseProvider.broadcast(
                karutaGameChatEvent.getRoomId(),
                CHAT_EVENT_DESCRIPTION,
                karutaGameChatEvent.getChatMessage());
    }
}
