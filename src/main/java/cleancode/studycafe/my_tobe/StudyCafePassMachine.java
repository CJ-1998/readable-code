package cleancode.studycafe.my_tobe;

import cleancode.studycafe.my_tobe.exception.AppException;
import cleancode.studycafe.my_tobe.io.InputHandler;
import cleancode.studycafe.my_tobe.io.OutputHandler;
import cleancode.studycafe.my_tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.my_tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.my_tobe.model.StudyCafePass;
import cleancode.studycafe.my_tobe.model.StudyCafePassType;
import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    private static final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

            if (studyCafePassType == StudyCafePassType.HOURLY) {
                List<StudyCafePass> hourlyPasses = getStudyCafePasses(StudyCafePassType.HOURLY);

                showPassList(hourlyPasses);
                StudyCafePass selectedPass = inputHandler.getSelectPass(hourlyPasses);
                outputHandler.showPassOrderSummary(selectedPass, null);
                return;
            }
            if (studyCafePassType == StudyCafePassType.WEEKLY) {
                List<StudyCafePass> weeklyPasses = getStudyCafePasses(StudyCafePassType.WEEKLY);

                showPassList(weeklyPasses);
                StudyCafePass selectedPass = inputHandler.getSelectPass(weeklyPasses);
                outputHandler.showPassOrderSummary(selectedPass, null);
                return;
            }
            if (studyCafePassType == StudyCafePassType.FIXED) {
                List<StudyCafePass> fixedPasses = getStudyCafePasses(StudyCafePassType.FIXED);

                showPassList(fixedPasses);
                StudyCafePass selectedPass = inputHandler.getSelectPass(fixedPasses);

                List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
                StudyCafeLockerPass lockerPass = lockerPasses.stream()
                        .filter(option ->
                                option.getPassType() == selectedPass.getPassType()
                                        && option.getDuration() == selectedPass.getDuration()
                        )
                        .findFirst()
                        .orElse(null);

                boolean lockerSelection = false;
                if (lockerPass != null) {
                    outputHandler.askLockerPass(lockerPass);
                    lockerSelection = inputHandler.getLockerSelection();
                }

                if (lockerSelection) {
                    outputHandler.showPassOrderSummary(selectedPass, lockerPass);
                }
                outputHandler.showPassOrderSummary(selectedPass, null);

            }
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void showPassList(List<StudyCafePass> hourlyPasses) {
        outputHandler.showPassListForSelection(hourlyPasses);
    }

    private static List<StudyCafePass> getStudyCafePasses(StudyCafePassType passType) {
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
        List<StudyCafePass> filteredPasses = studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == passType)
                .toList();
        return filteredPasses;
    }

}
