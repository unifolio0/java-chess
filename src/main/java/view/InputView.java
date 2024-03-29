package view;

import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    private InputView() {

    }

    public static List<String> readCommandList() {
        String rawCommand = scanner.nextLine();
        validate(rawCommand);
        return List.of(rawCommand.split(" "));
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("유효하지 않은 입력입니다.");
        }
    }
}
