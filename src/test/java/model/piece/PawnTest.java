package model.piece;

import model.position.Moving;
import model.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PawnTest {

    @DisplayName("이동할 수 없는 경로면 예외를 발생시킨다.")
    @ParameterizedTest
    @MethodSource("cantMovableParameterProvider")
    void invalidRoute(final Moving moving) {
        final Pawn pawn = new BlackPawn();

        assertAll(
                () -> assertThat(pawn.canMovable(moving)).isFalse(),
                () -> assertThatThrownBy(() -> pawn.getMoveRoute(moving))
                        .isInstanceOf(IllegalArgumentException.class)
        );

    }

    static Stream<Arguments> cantMovableParameterProvider() {
        return Stream.of(
                Arguments.of(new Moving(Position.from("a6"), Position.from("a4"))),
                Arguments.of(new Moving(Position.from("a7"), Position.from("a4"))),
                Arguments.of(new Moving(Position.from("a7"), Position.from("a8")))
        );
    }

    @DisplayName("이동할 수 있다면 경로를 반환한다.")
    @ParameterizedTest
    @MethodSource("canMovableParameterProvider")
    void canMovable(final Moving moving) {
        final Pawn pawn = new BlackPawn();

        assertThat(pawn.canMovable(moving)).isTrue();
    }

    static Stream<Arguments> canMovableParameterProvider() {
        return Stream.of(
                Arguments.of(new Moving(Position.from("a7"), Position.from("a5"))),
                Arguments.of(new Moving(Position.from("a6"), Position.from("a5")))
        );
    }
}
