package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "AutoEncoderTeamYantra_movesampleswithparking")
public class AutoEncoderTeamYantra_movesampleswithparking extends LinearOpMode {

    private DcMotor frontLeft, frontRight, backLeft, backRight, leftSlide;
    private Servo armservo, claw;

    static final double WHEEL_DIAMETER_INCHES = 2.95276;
    static final double TICKS_PER_REV = 1120;
    static final double GEAR_RATIO = (10 / 26);
    static final double COUNTS_PER_INCH = 46.4391; //(TICKS_PER_REV * GEAR_RATIO) / (Math.PI * WHEEL_DIAMETER_INCHES);

    private int targetSlidePosition = 0;
    private boolean isSlideCorrectionEnabled = false;

    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "front left");
        frontRight = hardwareMap.get(DcMotor.class, "front right");
        backLeft = hardwareMap.get(DcMotor.class, "rear left");
        backRight = hardwareMap.get(DcMotor.class, "rear right");
        leftSlide = hardwareMap.get(DcMotor.class, "left slide");
        armservo = hardwareMap.get(Servo.class, "arm servo");
        claw = hardwareMap.get(Servo.class, "claw");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();

        waitForStart();

        if (opModeIsActive()) {
            driveRight(5);
            driveForward(15);
            driveBackward(16);
            turnLeft(1.75);
            driveRight(55);
            driveForward(8);
            driveLeft(56);
            //start2
            driveBackward(7);
            driveRight(50);
            driveForward(11);
            driveLeft(43);
            driveBackward(7);
            driveRight(45);
            turnLeft(4);
            driveForward(9);
            driveLeft(38);
            driveBackward(8);
            turnRight(40);
            driveLeft(38);
            driveForward(25);
            armservo.setPosition(0.46);
            sleep(5);
        }
    }

    private void clawOpen() {
        claw.setPosition(1);
    }

    private void clawClose() {
        claw.setPosition(0.6);
    }

    private void driveForward(double inches) {
        driveInches(0.7, inches, inches, inches, inches);
    }

    private void driveBackward(double inches) {
        driveInches(0.7, -inches, -inches, -inches, -inches);
    }

    private void driveRight(double inches) {
        driveInches(0.7, inches, -inches, -inches, inches);
    }

    private void driveLeft(double inches) {
        driveInches(0.7, -inches, inches, inches, -inches);
    }

    private void turnRight(double inches) {
        driveInches(0.7, inches, -inches, inches, -inches);
    }

    private void turnLeft(double inches) {
        driveInches(0.7, -inches, inches, -inches, inches);
    }

    private void driveInches(double speed, double flInches, double frInches, double blInches, double brInches) {
        int flTarget = frontLeft.getCurrentPosition() + (int) (flInches * COUNTS_PER_INCH);
        int frTarget = frontRight.getCurrentPosition() + (int) (frInches * COUNTS_PER_INCH);
        int blTarget = backLeft.getCurrentPosition() + (int) (blInches * COUNTS_PER_INCH);
        int brTarget = backRight.getCurrentPosition() + (int) (brInches * COUNTS_PER_INCH);

        frontLeft.setTargetPosition(flTarget);
        frontRight.setTargetPosition(frTarget);
        backLeft.setTargetPosition(blTarget);
        backRight.setTargetPosition(brTarget);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);
        
        while (opModeIsActive() && ((Math.abs(frontLeft.getCurrentPosition() - flTarget) > 25) || (Math.abs(frontRight.getCurrentPosition() - frTarget) > 25) || (Math.abs(backLeft.getCurrentPosition() - blTarget) > 25) || (Math.abs(backRight.getCurrentPosition() - brTarget) > 25) )) {
            telemetry.addData("FL Position", frontLeft.getCurrentPosition());
            telemetry.addData("FR Position", frontRight.getCurrentPosition());
            telemetry.addData("BL Position", backLeft.getCurrentPosition());
            telemetry.addData("BR Position", backRight.getCurrentPosition());
            telemetry.update();
        }


        stopMotors();
        resetEncoders();
        sleep(5);
    }

    private void stopMotors() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    private void resetEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void setSlideTarget(int n) {
        targetSlidePosition = n;
        leftSlide.setTargetPosition(targetSlidePosition);
    }

    private void enableSlideCorrection() {
        isSlideCorrectionEnabled = true;
        while (opModeIsActive() && isSlideCorrectionEnabled) {
            if (Math.abs(targetSlidePosition - leftSlide.getCurrentPosition()) > 25) {
                leftSlide.setTargetPosition(targetSlidePosition);
                leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftSlide.setPower(1);
            }
        }
    }

    private void disableSlideCorrection() {
        isSlideCorrectionEnabled = false;
    }

    private void enableRunToPosition() {
        leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftSlide.setPower(1.0);
    }

    private void disableRunToPosition() {
        leftSlide.setPower(0);
        leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void slideUp(double power, double seconds) {
        leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftSlide.setPower(power);
        sleep((long) (seconds * 1000));
        leftSlide.setPower(0);
    }

    private void slideDown(double power, double seconds) {
        leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftSlide.setPower(-power);
        sleep((long) (seconds * 1000));
        leftSlide.setPower(0);
    }
}
