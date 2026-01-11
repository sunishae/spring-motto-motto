package jumdo12.mottomotto.application;

import jumdo12.mottomotto.common.exception.MottoException;
import jumdo12.mottomotto.domain.*;
import jumdo12.mottomotto.domain.event.KarutaGameChatEvent;
import jumdo12.mottomotto.infra.KarutaGameRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KarutaGameRoomChatService {

    private final KarutaGameRoomRepository karutaGameRoomRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void sendChat(String roomId, Member member, String content) {

        KarutaGameRoom karutaGameRoom = findKarutaGameRoom(roomId);

        Participant participant = karutaGameRoom.findParticipant(member);
        KarutaGameChatMessage chatMessage = KarutaGameChatMessage.create(
                content,
                participant,
                roomId
        );

        eventPublisher.publishEvent(new KarutaGameChatEvent(roomId, chatMessage));
    }

    private KarutaGameRoom findKarutaGameRoom(String roomId) {
        return karutaGameRoomRepository.findById(roomId)
                .orElseThrow(() -> new MottoException("방을 찾을 수 없습니다."));
    }
}
