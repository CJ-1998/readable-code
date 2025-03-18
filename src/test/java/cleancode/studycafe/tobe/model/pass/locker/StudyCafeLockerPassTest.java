package cleancode.studycafe.tobe.model.pass.locker;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyCafeLockerPassTest {

    @DisplayName("락커 패스의 패스 타입이 스터디 카페 패스 타입과 같은지 확인한다.")
    @Test
    void isSamePassType() {
        //given
        StudyCafeSeatPass studyCafeFixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 700000, 0.15);
        StudyCafeLockerPass studyCafeLockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 10000);

        //when
        boolean isSamePassType = studyCafeLockerPass.isSamePassType(studyCafeFixedSeatPass.getPassType());

        //then
        Assertions.assertThat(isSamePassType).isTrue();
    }

    @DisplayName("락커 패스의 패스 타입이 스터디 카페 패스 타입과 같지 않은지 확인한다.")
    @Test
    void isNotSamePassType() {
        //given
        StudyCafeSeatPass studyCafeHourlySeatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 2, 4000, 0.0);
        StudyCafeLockerPass studyCafeLockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 10000);

        //when
        boolean isSamePassType = studyCafeLockerPass.isSamePassType(studyCafeHourlySeatPass.getPassType());

        //then
        Assertions.assertThat(isSamePassType).isFalse();
    }

}