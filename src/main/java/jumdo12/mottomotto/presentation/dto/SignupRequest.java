package jumdo12.mottomotto.presentation.dto;

public record SignupRequest(
        String email,
        String password,
        String passwordConfirm,
        String nickname
) {
    public boolean isPasswordMatching() {
        return password != null && password.equals(passwordConfirm);
    }
}
