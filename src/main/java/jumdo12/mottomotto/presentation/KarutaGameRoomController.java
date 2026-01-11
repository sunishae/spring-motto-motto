package jumdo12.mottomotto.presentation;

import jumdo12.mottomotto.application.KarutaGameRoomChatService;
import jumdo12.mottomotto.application.KarutaGameRoomService;
import jumdo12.mottomotto.domain.KarutaGameRoom;
import jumdo12.mottomotto.domain.Member;
import jumdo12.mottomotto.infra.SseProvider;
import jumdo12.mottomotto.presentation.dto.KarutaGameChatReqeust;
import jumdo12.mottomotto.presentation.dto.KarutaGameRoomCreateRequest;
import jumdo12.mottomotto.presentation.dto.KarutaGameRoomInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class KarutaGameRoomController {

    private final KarutaGameRoomService karutaGameRoomService;
    private final KarutaGameRoomChatService karutaGameRoomChatService;
    private final SseProvider sseProvider;

    @GetMapping
    public ResponseEntity<List<KarutaGameRoomInfoResponse>> getAllRooms() {
        List<KarutaGameRoomInfoResponse> karutaGameRoomInfoResponses = karutaGameRoomService.getAllKarutaGameRooms().stream()
                .map(KarutaGameRoomInfoResponse::from)
                .toList();

        return ResponseEntity.ok(karutaGameRoomInfoResponses);
    }

    @PostMapping
    public ResponseEntity<KarutaGameRoomInfoResponse> createRoom(
            Member member,
            @RequestBody KarutaGameRoomCreateRequest request) {
        KarutaGameRoom karutaGameRoom = karutaGameRoomService.createKarutaGameRoom(member, request.title());

        return ResponseEntity.ok(KarutaGameRoomInfoResponse.from(karutaGameRoom));
    }

    @PostMapping("/{roomId}/join")
    public ResponseEntity<Void> joinRoom(
            @PathVariable("roomId") String roomId,
            Member member) {
        karutaGameRoomService.joinKarutaGameRoom(roomId, member);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{roomId}/leave")
    public ResponseEntity<Void> leaveRoom(
            @PathVariable("roomId") String roomId,
             Member member) {
        karutaGameRoomService.leaveKarutaGameRoom(roomId, member);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{roomId}/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(
            @PathVariable("roomId") String roomId,
            Member member
    ) {
        karutaGameRoomService.validateParticipation(roomId, member);

        return sseProvider.connect(roomId);
    }

    @PostMapping("/{roomId}/chat")
    public ResponseEntity<Void> sendChat(
            @PathVariable("roomId") String roomId,
            Member member,
            @RequestBody KarutaGameChatReqeust request) {

        karutaGameRoomChatService.sendChat(roomId, member, request.message());

        return ResponseEntity.ok().build();
    }
}
