package jumdo12.mottomotto.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jumdo12.mottomotto.application.MemberService;
import jumdo12.mottomotto.domain.Member;
import jumdo12.mottomotto.presentation.dto.LoginRequest;
import jumdo12.mottomotto.presentation.dto.SignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest signupRequest) {
        Long memberId = memberService.signup(signupRequest);
        return ResponseEntity.created(URI.create("/api/members/" + memberId)).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletRequest request
    ) {
        Member member = memberService.login(loginRequest.email(), loginRequest.password());

        HttpSession session = request.getSession();
        session.setAttribute("LOGIN_MEMBER", member.getId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        return ResponseEntity.ok(!memberService.isEmailDuplicate(email));
    }
}
