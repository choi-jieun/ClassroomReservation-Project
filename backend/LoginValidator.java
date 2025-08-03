package backend;

// 로그인 시 사용자의 입력이 유효한지 검사 후 로그인 완료/오류 메세지 출력하는 프로그램
public class LoginValidator {

    // 학번 유효성 검사 메서드
    public static void validateLoginId(String loginId) throws InvalidLoginIdException {
        // 정수만으로 구성되어 있는지 확인 후 아닐 시 예외 처리
        if (!loginId.matches("\\d+")) {
            throw new InvalidLoginIdException("숫자만 입력해주세요.");
        }

        // 7자리인지 확인 후 아닐 시 예외 처리
        if (loginId.length() != 7) {
            throw new InvalidLoginIdException("학번은 7자리여야 합니다.");
        }
    }

    // 로그인 처리 메서드
    public static void login(String loginId) {
        try {
            validateLoginId(loginId); // 메서드 이름도 맞게 수정
            System.out.println("로그인 완료");
        } catch (InvalidLoginIdException e) {
            System.out.println("로그인 오류: " + e.getMessage());
        }
    }
}
