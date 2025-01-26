package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutoCodePark")
public class AutoCodePark extends LinearOpMode {

    // Declare hardware variables
    private DcMotor frontleft, frontright, rearleft, rearright;

    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {

        // Initialize hardware
        frontleft = hardwareMap.get(DcMotor.class, "front left");
        frontright = hardwareMap.get(DcMotor.class, "front right");
        rearleft = hardwareMap.get(DcMotor.class, "rear left");
        rearright = hardwareMap.get(DcMotor.class, "rear right");


        // Reverse the side motors
        frontleft.setDirection(DcMotor.Direction.REVERSE);
        rearleft.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start
        waitForStart();

        if (opModeIsActive()) {
            timer.reset();


            while (opModeIsActive() && timer.seconds() <= 8) {
                frontleft.setPower(0.5);
                frontright.setPower(-0.5);
                rearleft.setPower(-0.5);
                rearright.setPower(0.5);
            }
            while (opModeIsActive() && timer.seconds() <= 12 && timer.seconds() > 8) {
                frontleft.setPower(-0.5);
                frontright.setPower(-0.5);
                rearleft.setPower(-0.5);
                rearright.setPower(-0.5);
            }
        }
    }
}