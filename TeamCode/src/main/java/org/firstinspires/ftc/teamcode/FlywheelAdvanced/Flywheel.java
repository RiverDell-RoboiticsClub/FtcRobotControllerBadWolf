package org.firstinspires.ftc.teamcode.FlywheelAdvanced;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Flywheel {
    public DcMotorEx nectarFlywheel;
    public DcMotorEx pollenFlywheel;

    //Clicks per rotation on encoder
    private double encoderCPM = 28; //change depending on motor later
    private double gearRatio = 1; //change depending on actual gearratio for badwolf

    //velocity feed forward constant, static friction, and proportional constant
    private double kV, kS, kP; //will tune later -> after tuning set each value equal to the tuned value



    //initialize motors again
    public void init() {
        nectarFlywheel = hardwareMap.get(DcMotorEx.class, "flywheel_motor_nectar");
        pollenFlywheel = hardwareMap.get(DcMotorEx.class, "flywheel_motor_pollen");
        //Because we're building custom loop, we don't want encoder.
        //This allows us to still read the motor encoder, but no run at speed it wants
        nectarFlywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pollenFlywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //tell our motors how fast to go:
    public void setMotorPower(double power, DcMotor motor) {
        motor.setPower(power);
    }

    public void setMotorRPM(double targetRPM, DcMotorEx motor) {
        //should call this every loop
        double error = targetRPM - getRPM(motor);
        double ff = (kV * targetRPM) + kS; // feedforward
        double fb = error * kP;
        double power = ff + fb;
        setMotorPower(power, motor);
    }

    //how fast we going
    public double getTicksPerSec(DcMotorEx motor) {
        return motor.getVelocity();
    };

    //calculating total RPM then ticks per second
    public double getRPM(DcMotorEx motor) {
        return((getTicksPerSec(motor)/encoderCPM) * 60) / gearRatio; //37:50 in video
    }

}
