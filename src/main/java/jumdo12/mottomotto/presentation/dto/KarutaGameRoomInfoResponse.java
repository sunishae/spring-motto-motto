package jumdo12.mottomotto.presentation.dto;

import jumdo12.mottomotto.domain.KarutaGameRoom;

public record KarutaGameRoomInfoResponse(
        String roomId,
        String title,
        int participantCount
) {

    public static KarutaGameRoomInfoResponse from(KarutaGameRoom room) {
        return new KarutaGameRoomInfoResponse(
                room.getRoomId(),
                room.getTitle(),
                room.getParticipants().size()
        );
    }
}
