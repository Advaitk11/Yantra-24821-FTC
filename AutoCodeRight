package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Right Side AutoCode")
public class AutoCodeRight extends LinearOpMode {

    // Declare hardware variables
    private DcMotor leftslide, rightslide;
    private DcMotor leftpivot, rightpivot;
    private DcMotor frontleft, frontright, rearleft, rearright;
    private CRServo leftservo, rightservo;

    // Timer for autonomous actions
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
        int position_leftslide = leftslide.getCurrentPosition();
        int position_rightslide = rightslide.getCurrentPosition();

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

            // Move left for 3 seconds (from 2s to 5s)
            while (opModeIsActive() && timer.seconds() <= 5) {
                frontleft.setPower(1);
                frontright.setPower(-1);
                rearleft.setPower(1);
                rearright.setPower(-1);
            }

            // Rotate left for 0.5 seconds (from 5s to 5.5s)
            while (opModeIsActive() && timer.seconds() <= 5.5) {
                frontleft.setPower(-1);
                frontright.setPower(1);
                rearleft.setPower(-1);
                rearright.setPower(1);
            }

            // Move forward to the bucket for 3.5 seconds (from 5.5s to 9s)
            while (opModeIsActive() && timer.seconds() <= 9) {
                frontleft.setPower(1);
                frontright.setPower(1);
                rearleft.setPower(1);
                rearright.setPower(1);
            }

            // Activate pivots for 2 seconds (from 9s to 11s)
            while (opModeIsActive() && timer.seconds() <= 11) {
                leftpivot.setPower(1);
                rightpivot.setPower(1);
            }

            // Activate slide for 2 seconds (from 11s to 13s)
            while (opModeIsActive() && timer.seconds() <= 13) {
                leftslide.setPower(1);
                rightslide.setPower(1);
            }

            // Move servos for 2 seconds (from 13s to 15s)
            while (opModeIsActive() && timer.seconds() <= 15) {
                leftservo.setPower(-1);
                rightservo.setPower(1);
            }

            // Unextend slide for 2 seconds (from 15s to 17s)
            while (opModeIsActive() && timer.seconds() <= 17) {
                leftslide.setPower(-1);
                rightslide.setPower(-1);
            }

            // Park robot in the observation zone for 6 seconds (from 17s to 23s)
            while (opModeIsActive() && timer.seconds() <= 23) {
                frontleft.setPower(-1);
                frontright.setPower(-1);
                rearleft.setPower(-1);
                rearright.setPower(-1);
            }

            // Adjust robot for parking position for 5 seconds (from 23s to 28s)
            while (opModeIsActive() && timer.seconds() <= 28) {
                frontleft.setPower(1);
                frontright.setPower(-1);
                rearleft.setPower(-1);
                rearright.setPower(1);
            }
        }
    }
}
