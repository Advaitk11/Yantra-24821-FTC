// package org.firstinspires.ftc.teamcode;
// 
// import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
// import com.qualcomm.robotcore.hardware.DcMotor;
// import com.qualcomm.robotcore.hardware.Servo;
// 
// @Autonomous(name = "AutoEncoderPushbot_v1")
// public class AutoEncoderPushbot_v1 extends LinearOpMode {
// 
//     private DcMotor frontLeft, frontRight, backLeft, backRight, leftSlide;
//     private Servo armservo, claw;
// 
//     static final double WHEEL_DIAMETER_INCHES = 2.95276;
//     static final double TICKS_PER_REV = 1120;
//     static final double GEAR_RATIO = (10 / 26);
//     static final double COUNTS_PER_INCH = 46.4391; //(TICKS_PER_REV * GEAR_RATIO) / (Math.PI * WHEEL_DIAMETER_INCHES);
// 
//     private int targetSlidePosition = 0;
//     private boolean isSlideCorrectionEnabled = false;
// 
//     @Override
//     public void runOpMode() {
//         frontLeft = hardwareMap.get(DcMotor.class, "front left");
//         frontRight = hardwareMap.get(DcMotor.class, "front right");
//         backLeft = hardwareMap.get(DcMotor.class, "rear left");
//         backRight = hardwareMap.get(DcMotor.class, "rear right");
//         leftSlide = hardwareMap.get(DcMotor.class, "left slide");
//         armservo = hardwareMap.get(Servo.class, "arm servo");
//         claw = hardwareMap.get(Servo.class, "claw");
// 
//         frontLeft.setDirection(DcMotor.Direction.REVERSE);
//         backLeft.setDirection(DcMotor.Direction.REVERSE);
// 
//         resetEncoders();
// 
//         waitForStart();
// 
//         if (opModeIsActive()) {
//             driveBackward(3);
//             driveRight(55);
//             driveForward(10);
//             driveLeft(40);
//             turnLeft(1);
//             driveForward(18);
//             driveBackward(18);
//             turnLeft(1);
//             driveBackward(40);
//             driveRight(10);
//             driveForward(45);
//             driveBackward(45);
//             driveRight(8);
//             driveForward(40);
//             driveLeft(120);
//             driveForward(20);
//             stopMotors();
//         }
//     
//     }
// 
//     private void clawOpen() {
//         claw.setPosition(1);
//         sleep(125);
//     }
// 
//     private void clawClose() {
//         claw.setPosition(0.6);
//         sleep(125);
//     }
// 
//     private void driveForward(double speed, double inches) {
//         driveInches(speed, inches, inches, inches, inches);
//     }
// 
//     private void driveBackward(double speed, double inches) {
//         driveInches(speed, -inches, -inches, -inches, -inches);
//     }
// 
//     private void driveRight(double speed, double inches) {
//         driveInches(speed, inches, -inches, -inches, inches);
//     }
// 
//     private void driveLeft(double speed, double inches) {
//         driveInches(speed, inches, -inches, -inches, inches);
//     }
// 
//     private void turnRight(double speed, double inches) {
//         driveInches(speed, inches, -inches, inches, -inches);
//     }
// 
//     private void turnLeft(double speed, double inches) {
//         driveInches(speed, -inches, inches, -inches, inches);
//     }
// 
//     private void driveInches(double speed, double flInches, double frInches, double blInches, double brInches) {
//         int flTarget = frontLeft.getCurrentPosition() + (int) (flInches * COUNTS_PER_INCH);
//         int frTarget = frontRight.getCurrentPosition() + (int) (frInches * COUNTS_PER_INCH);
//         int blTarget = backLeft.getCurrentPosition() + (int) (blInches * COUNTS_PER_INCH);
//         int brTarget = backRight.getCurrentPosition() + (int) (brInches * COUNTS_PER_INCH);
// 
//         frontLeft.setTargetPosition(-flTarget);
//         frontRight.setTargetPosition(-frTarget);
//         backLeft.setTargetPosition(blTarget);
//         backRight.setTargetPosition(brTarget);
// 
//         frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//         frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//         backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//         backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
// 
//         frontLeft.setPower(speed);
//         frontRight.setPower(speed);
//         backLeft.setPower(speed);
//         backRight.setPower(speed);
// 
//         while (opModeIsActive()) {
//             telemetry.addData("FL Position", frontLeft.getCurrentPosition());
//             telemetry.addData("FR Position", frontRight.getCurrentPosition());
//             telemetry.addData("BL Position", backLeft.getCurrentPosition());
//             telemetry.addData("BR Position", backRight.getCurrentPosition());
//             telemetry.addData("fltarget", flTarget);
//             telemetry.addData("frtarget", flTarget);
//             telemetry.addData("bltarget", flTarget);
//             telemetry.addData("brtarget", flTarget);
// 
//             telemetry.update();
//         }
// 
// 
//         stopMotors();
//         resetEncoders();
//         sleep(50);
//     }
// 
//     private void stopMotors() {
//         frontLeft.setPower(0);
//         frontRight.setPower(0);
//         backLeft.setPower(0);
//         backRight.setPower(0);
//     }
// 
//     private void resetEncoders() {
//         frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//         frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//         backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//         backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
// 
//         frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//         frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//         backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//         backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//     }
// 
//     private void setSlideTarget(int n) {
//         targetSlidePosition = n;
//         leftSlide.setTargetPosition(targetSlidePosition);
//     }
// 
//     private void enableSlideCorrection() {
//         isSlideCorrectionEnabled = true;
//         while (opModeIsActive() && isSlideCorrectionEnabled) {
//             if (Math.abs(targetSlidePosition - leftSlide.getCurrentPosition()) > 25) {
//                 leftSlide.setTargetPosition(targetSlidePosition);
//                 leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                 leftSlide.setPower(1);
//             }
//         }
//     }
// 
//     private void disableSlideCorrection() {
//         isSlideCorrectionEnabled = false;
//     }
// 
//     private void enableRunToPosition() {
//         leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//         leftSlide.setPower(1.0);
//     }
// 
//     private void disableRunToPosition() {
//         leftSlide.setPower(0);
//         leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//     }
// 
//     private void slideUp(double power, double seconds) {
//         leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//         leftSlide.setPower(power);
//         sleep((long) (seconds * 1000));
//         leftSlide.setPower(0);
//     }
// 
//     private void slideDown(double power, double seconds) {
//         leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//         leftSlide.setPower(-power);
//         sleep((long) (seconds * 1000));
//         leftSlide.setPower(0);
//     }
// }
// 