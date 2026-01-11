package jumdo12.mottomotto.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KarutaGameEvent {

    private final String roomId;
}
