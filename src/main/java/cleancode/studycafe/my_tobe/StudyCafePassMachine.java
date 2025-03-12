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

            enrollStudyCafe(studyCafePassType);

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void enrollStudyCafe(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> passes = getStudyCafePasses(studyCafePassType);

        showPassList(passes);
        StudyCafePass selectedPass = inputHandler.getSelectPass(passes);

        StudyCafeLockerPass studyCafeLockerPass = null;

        if (studyCafePassType == StudyCafePassType.FIXED) {
            studyCafeLockerPass = getStudyCafeLockerPass(selectedPass);
        }

        outputHandler.showPassOrderSummary(selectedPass, studyCafeLockerPass);
    }

    private static List<StudyCafePass> getStudyCafePasses(StudyCafePassType passType) {
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
        List<StudyCafePass> filteredPasses = studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == passType)
                .toList();
        return filteredPasses;
    }

    private static StudyCafeLockerPass getStudyCafeLockerPasses(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
        StudyCafeLockerPass lockerPass = lockerPasses.stream()
                .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                                && option.getDuration() == selectedPass.getDuration()
                )
                .findFirst()
                .orElse(null);
        return lockerPass;
    }

    private boolean isLockerSelection(StudyCafeLockerPass lockerPass) {
        boolean lockerSelection = false;
        if (lockerPass != null) {
            outputHandler.askLockerPass(lockerPass);
            lockerSelection = inputHandler.getLockerSelection();
        }
        return lockerSelection;
    }

    private StudyCafeLockerPass getStudyCafeLockerPass(StudyCafePass selectedPass) {
        StudyCafeLockerPass lockerPass = getStudyCafeLockerPasses(selectedPass);

        boolean lockerSelection = isLockerSelection(lockerPass);

        if (lockerSelection) {
            return lockerPass;
        }

        return null;
    }


    private void showPassList(List<StudyCafePass> hourlyPasses) {
        outputHandler.showPassListForSelection(hourlyPasses);
    }

}
