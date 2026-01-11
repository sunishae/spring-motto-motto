package jumdo12.mottomotto.application;

import jumdo12.mottomotto.common.exception.MottoException;
import jumdo12.mottomotto.domain.*;
import jumdo12.mottomotto.domain.event.KarutaGameJoinEvent;
import jumdo12.mottomotto.domain.event.KarutaGameLeaveEvent;
import jumdo12.mottomotto.infra.KarutaGameRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KarutaGameRoomService {

    private final KarutaGameRoomRepository karutaGameRoomRepository;
    private final ApplicationEventPublisher eventPublisher;

    public KarutaGameRoom createKarutaGameRoom(Member member, String title) {
        KarutaGameRoom karutaGameRoom = karutaGameRoomRepository.save(title);

        karutaGameRoom.join(member);

        return karutaGameRoom;
    }

    public void joinKarutaGameRoom(String roomId, Member member) {
        KarutaGameRoom karutaGameRoom = findKarutaGameRoom(roomId);

        Participant participant = karutaGameRoom.join(member);

        eventPublisher.publishEvent(new KarutaGameJoinEvent(roomId, participant));
    }

    public void validateParticipation(String roomId, Member member) {
        KarutaGameRoom karutaGameRoom = findKarutaGameRoom(roomId);

        if(!karutaGameRoom.isParticipatant(member)) {
            throw new MottoException("방에 참가중이지 않습니다.");
        }
    }

    public void leaveKarutaGameRoom(String roomId, Member member) {
        KarutaGameRoom karutaGameRoom = findKarutaGameRoom(roomId);

        Participant participant = karutaGameRoom.leave(member);

        if(karutaGameRoom.isEmpty()) {
            karutaGameRoomRepository.remove(roomId);

            return;
        }

        eventPublisher.publishEvent(new KarutaGameLeaveEvent(roomId, participant));
    }

    public List<KarutaGameRoom> getAllKarutaGameRooms() {
        return karutaGameRoomRepository.findAll();
    }

    private KarutaGameRoom findKarutaGameRoom(String roomId) {
        return karutaGameRoomRepository.findById(roomId)
                .orElseThrow(() -> new MottoException("방을 찾을 수 없습니다."));
    }
}
