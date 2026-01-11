package jumdo12.mottomotto.application;

import jumdo12.mottomotto.common.exception.MottoException;
import jumdo12.mottomotto.domain.Member;
import jumdo12.mottomotto.domain.MemberRepository;
import jumdo12.mottomotto.presentation.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signup(SignupRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new MottoException("이미 사용 중인 이메일입니다.");
        }

        if (memberRepository.existsByNickname(request.nickname())) {
            throw new MottoException("이미 사용 중인 닉네임입니다.");
        }

        if (!request.isPasswordMatching()) {
            throw new MottoException("비밀번호가 일치하지 않습니다.");
        }

        Member member = Member.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .build();

        return memberRepository.save(member).getId();
    }

    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MottoException("존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new MottoException("비밀번호가 일치하지 않습니다.");
        }

        return member;
    }
}