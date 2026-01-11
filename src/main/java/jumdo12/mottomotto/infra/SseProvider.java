package jumdo12.mottomotto.infra;

import jumdo12.mottomotto.common.exception.MottoException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SseProvider {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 10;
    private final Map<String, List<SseEmitter>> gameRooms = new ConcurrentHashMap<>();

    public SseEmitter connect(String roomId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        gameRooms.computeIfAbsent(roomId, k -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> removeEmitter(roomId, emitter));
        emitter.onTimeout(() -> removeEmitter(roomId, emitter));
        emitter.onError((e) -> removeEmitter(roomId, emitter));

        return emitter;
    }

    public void broadcast(String roomId, String type, Object data) {
        if(!gameRooms.containsKey(roomId)) {
            throw new MottoException("방을 찾을 수 없습니다.");
        }

        List<SseEmitter> emitters = gameRooms.get(roomId);

        emitters.forEach(emitter -> sendToClient(emitter, type, data));
    }

    private void sendToClient(SseEmitter emitter, String eventName, Object data) {
        try {
            emitter.send(SseEmitter.event().name(eventName).data(data));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }

    private void removeEmitter(String roomId, SseEmitter emitter) {
        if(!gameRooms.containsKey(roomId)) {
            throw new MottoException("방을 찾을 수 없습니다.");
        }

        List<SseEmitter> emitters = gameRooms.get(roomId);
        emitters.remove(emitter);

        if (emitters.isEmpty()) {
            gameRooms.remove(roomId);
        }
    }

}
