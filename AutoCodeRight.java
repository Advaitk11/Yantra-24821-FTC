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


            // Adjust parking position for 5 seconds (from 20s to 25s)
            while (opModeIsActive() && timer.seconds() <= 5) {
                frontleft.setPower(1);
                frontright.setPower(-1);
                rearleft.setPower(-1);
                rearright.setPower(1);
            }
            while (opModeIsActive() && timer.seconds() <= 8 && timer.seconds() > 5) {
                frontleft.setPower(-1);
                frontright.setPower(-1);
                rearleft.setPower(-1);
                rearright.setPower(-1);
            }

            while (opModeIsActive() && timer.seconds() <= 10 && timer.seconds() > 8) {
                frontleft.setPower(1);
                frontright.setPower(1);
                rearleft.setPower(1);
                rearright.setPower(1);
            }
            while (opModeIsActive() && timer.seconds() <= 13 && timer.seconds() > 10) {
                frontleft.setPower(-1);
                frontright.setPower(-1);
                rearleft.setPower(-1);
                rearright.setPower(-1);
            }
        }
    }
}