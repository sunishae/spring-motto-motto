package jumdo12.mottomotto.infra;

import jumdo12.mottomotto.domain.KarutaGameRoom;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class KarutaGameRoomRepository {

    private final Map<String, KarutaGameRoom> karutaGameRooms = new ConcurrentHashMap<>();

    public KarutaGameRoom save(String title) {
        String roomId = UUID.randomUUID().toString();
        KarutaGameRoom karutaGameRoom = KarutaGameRoom.create(roomId, title);

        karutaGameRooms.put(roomId, karutaGameRoom);

        return karutaGameRoom;
    }

    public Optional<KarutaGameRoom> findById(String roomId) {
        return Optional.ofNullable(karutaGameRooms.get(roomId));
    }

    public List<KarutaGameRoom> findAll() {
        return new ArrayList<>(karutaGameRooms.values());
    }

    public void remove(String roomId) {
        karutaGameRooms.remove(roomId);
    }
}
