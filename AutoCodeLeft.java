package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Left Side AutoCode")
public class AutoCodeLeft extends LinearOpMode {

    // Declare hardware variables
    private DcMotor leftslide, rightslide;
    private DcMotor leftpivot, rightpivot;
    private DcMotor frontleft, frontright, rearleft, rearright;
    private CRServo leftservo, rightservo;

    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {

        // Initialize hardware
        leftslide = hardwareMap.get(DcMotor.class, "left slide");
        rightslide = hardwareMap.get(DcMotor.class, "right slide");
        leftpivot = hardwareMap.get(DcMotor.class, "left pivot");
        rightpivot = hardwareMap.get(DcMotor.class, "right pivot");
        frontleft = hardwareMap.get(DcMotor.class, "front left");
        frontright = hardwareMap.get(DcMotor.class, "front right");
        rearleft = hardwareMap.get(DcMotor.class, "rear left");
        rearright = hardwareMap.get(DcMotor.class, "rear right");

        leftservo = hardwareMap.get(CRServo.class, "left servo");
        rightservo = hardwareMap.get(CRServo.class, "right servo");

        // Reverse the right side motors
        frontright.setDirection(DcMotor.Direction.FORWARD);
        rearright.setDirection(DcMotor.Direction.REVERSE);
        rightservo.setDirection(CRServo.Direction.REVERSE);

        // Set initial positions for slide motors (Optional)
        int positionLeftSlide = leftslide.getCurrentPosition();
        int positionRightSlide = rightslide.getCurrentPosition();

        // Wait for the game to start
        waitForStart();

        if (opModeIsActive()) {
            timer.reset();

            // Reset encoders for slide motors
            leftslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // Move robot forward for 2 seconds
            while (opModeIsActive() && timer.seconds() <= 2) {
                frontleft.setPower(1);
                frontright.setPower(1);
                rearleft.setPower(1);
                rearright.setPower(1);
            }

            // Rotate left for 0.5 seconds (from 2s to 2.5s)
            while (opModeIsActive() && timer.seconds() <= 2.5) {
                frontleft.setPower(-1);
                frontright.setPower(1);
                rearleft.setPower(-1);
                rearright.setPower(1);
            }

            // Move forward to bucket for 0.5 seconds (from 2.5s to 3s)
            while (opModeIsActive() && timer.seconds() <= 3) {
                frontleft.setPower(1);
                frontright.setPower(1);
                rearleft.setPower(1);
                rearright.setPower(1);
            }

            // Activate pivots for 0.5 seconds (from 9s to 9.5s)
            while (opModeIsActive() && timer.seconds() <= 9.5) {
                leftpivot.setPower(1);
                rightpivot.setPower(1);
            }

            // Extend slide for 1.5 seconds (from 9.5s to 11s)
            while (opModeIsActive() && timer.seconds() <= 11) {
                leftslide.setPower(1);
                rightslide.setPower(1);
            }

            // Activate servos for 1 second (from 11s to 12s)
            while (opModeIsActive() && timer.seconds() <= 12) {
                leftservo.setPower(-1);
                rightservo.setPower(-1);
            }

            // Unextend slide for 2 seconds (from 12s to 14s)
            while (opModeIsActive() && timer.seconds() <= 14) {
                leftslide.setPower(-1);
                rightslide.setPower(-1);
            }

            // Park robot in the observation zone for 6 seconds (from 14s to 20s)
            while (opModeIsActive() && timer.seconds() <= 20) {
                frontleft.setPower(-1);
                frontright.setPower(-1);
                rearleft.setPower(-1);
                rearright.setPower(-1);
            }

            // Adjust parking position for 5 seconds (from 20s to 25s)
            while (opModeIsActive() && timer.seconds() <= 25) {
                frontleft.setPower(1);
                frontright.setPower(-1);
                rearleft.setPower(-1);
                rearright.setPower(1);
            }
        }
    }
}
