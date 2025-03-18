package cleancode.studycafe.tobe.model.pass;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyCafeSeatPassTest {

    @DisplayName("스터디 카페 패스가 고정권이 아닌 패스는 사물함을 사용할 수 없다.")
    @Test
    void cannotUseLocker() {
        //given
        StudyCafeSeatPass studyCafeHourlySeatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 2, 4000, 0.0);

        //when
        boolean isNotFixedPass = studyCafeHourlySeatPass.cannotUseLocker();

        //then
        assertThat(isNotFixedPass).isTrue();
    }

    @DisplayName("스터디 카페 패스가 고정권만이 사물함을 사용할 수 있다.")
    @Test
    void canUseLocker() {
        //given
        StudyCafeSeatPass studyCafeFixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 700000, 0.15);

        //when
        boolean isNotFixedPass = studyCafeFixedSeatPass.cannotUseLocker();

        //then
        assertThat(isNotFixedPass).isFalse();
    }

}