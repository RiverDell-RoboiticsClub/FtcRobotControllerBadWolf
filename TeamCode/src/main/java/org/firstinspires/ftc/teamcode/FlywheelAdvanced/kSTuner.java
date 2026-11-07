package org.firstinspires.ftc.teamcode.FlywheelAdvanced;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp
public class kSTuner extends OpMode {
    Flywheel flywheel = new Flywheel();

    public static double kS = 0;

    double[] increments = {0.000001, 0.00001, 0.0001, 0.001, 0.01};

    int incrementIdx = 4; //Start at 0.001
    //hlo
    @Override
    public void init() {
        flywheel.init();
    }

    @Override
    public void loop() {
        if (gamepad1.dpadRightWasPressed() && incrementIdx < 4) {
            incrementIdx++;
        } else if (gamepad1.dpadLeftWasPressed() && incrementIdx > 0) {
            incrementIdx--;
        }

        double currentStep = increments[incrementIdx];

        //Change kS
        if (gamepad1.dpadUpWasPressed()) { kS += currentStep; }
        if (gamepad1.dpadDownWasPressed()) { kS -= currentStep; }

        flywheel.setMotorPower(kS, flywheel.nectarFlywheel);

        telemetry.addData("Step", "%.6f", currentStep);
        telemetry.addData("kS", "%.6f", kS);
        telemetry.addData("RPM", flywheel.getRPM(flywheel.nectarFlywheel));
        telemetry.addData("Ticks per Sec", flywheel.getTicksPerSec(flywheel.nectarFlywheel));

    }
}
