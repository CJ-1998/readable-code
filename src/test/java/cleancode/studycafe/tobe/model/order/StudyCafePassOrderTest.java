package cleancode.studycafe.tobe.model.order;

import static org.assertj.core.api.Assertions.assertThat;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyCafePassOrderTest {

    @DisplayName("고정권이 아닌 패스의 금액은 패스의 금액과 할인율에 따라 계산된다.")
    @Test
    void calculateTotalPriceInNonFixedPass() {
        //given
        int passPrice = 4000;
        StudyCafeSeatPass studyCafeHourlySeatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 2, passPrice, 0.0);

        StudyCafePassOrder passOrder = StudyCafePassOrder.of(studyCafeHourlySeatPass, null);

        //when
        int totalPrice = passOrder.getTotalPrice();

        //then
        assertThat(totalPrice).isEqualTo(passPrice);
    }

    @DisplayName("고정권이 아닌 패스의 금액은 패스의 금액과 할인율에 따라 계산된다.")
    @Test
    void calculateTotalPriceInFixedPass() {
        //given
        StudyCafeSeatPass studyCafeFixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 700000, 0.15);
        StudyCafeLockerPass studyCafeLockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 10000);

        StudyCafePassOrder passOrder = StudyCafePassOrder.of(studyCafeFixedSeatPass, studyCafeLockerPass);

        //when
        int totalPrice = passOrder.getTotalPrice();

        int answerPrice = 700000 + 10000 - (int) (700000 * 0.15);

        //then
        assertThat(totalPrice).isEqualTo(answerPrice);
    }

    @DisplayName("가지고 있는 패스의 할인 해주는 금액은 패스의 금액과 할인율을 곱하는 것이다.")
    @Test
    void calculateDiscountPriceOfPass() {
        //given
        StudyCafeSeatPass studyCafeFixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 700000, 0.15);
        StudyCafeLockerPass studyCafeLockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 10000);

        StudyCafePassOrder passOrder = StudyCafePassOrder.of(studyCafeFixedSeatPass, studyCafeLockerPass);

        //when
        int discountPrice = passOrder.getDiscountPrice();

        int answerDiscountPrice = (int) (700000 * 0.15);

        //then
        assertThat(discountPrice).isEqualTo(answerDiscountPrice);
    }

}