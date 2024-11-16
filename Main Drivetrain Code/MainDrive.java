package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name = "OurmainDrivetrainforeverdontchangewithoutcopy (Blocks to Java)")
public class OurmainDrivetrainforeverdontchangewithoutcopy extends LinearOpMode {

  private DcMotor frontright;
  private DcMotor rearright;
  private DcMotor frontleft;
  private DcMotor rearleft;
  private DcMotor rightslide;
  private DcMotor rightpivot;
  private DcMotor leftpivot;
  private CRServo leftservo;
  private CRServo rightservo;
  private DcMotor leftslide;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    double denominator;
    float y;
    float rx;
    double x;

    frontright = hardwareMap.get(DcMotor.class, "front right");
    rearright = hardwareMap.get(DcMotor.class, "rear right");
    frontleft = hardwareMap.get(DcMotor.class, "front left");
    rearleft = hardwareMap.get(DcMotor.class, "rear left");
    rightslide = hardwareMap.get(DcMotor.class, "right slide");
    rightpivot = hardwareMap.get(DcMotor.class, "right pivot");
    leftpivot = hardwareMap.get(DcMotor.class, "left pivot");
    leftservo = hardwareMap.get(CRServo.class, "left servo");
    rightservo = hardwareMap.get(CRServo.class, "right servo");
    leftslide = hardwareMap.get(DcMotor.class, "left slide");

    // Reverse the right side motors.  This may be wrong for your setup.
    // If your robot moves backwards when commanded to go forwards, reverse the left side instead.
    frontright.setDirection(DcMotor.Direction.FORWARD);
    rearright.setDirection(DcMotor.Direction.REVERSE);
    waitForStart();
    while (opModeIsActive()) {
      telemetry.addData("power", (y + x + rx) / denominator);
      // Remember, Y stick value is reversed
      y = -gamepad1.left_stick_y;
      // Factor to counteract imperfect strafing
      x = gamepad1.left_stick_x * 1.1;
      rx = gamepad1.right_stick_x;
      // Denominator is the largest motor power (absolute value) or 1.
      // This ensures all powers maintain the same ratio, but only if one is outside of the range [-1, 1].
      denominator = JavaUtil.maxOfList(JavaUtil.createListWith(JavaUtil.sumOfList(JavaUtil.createListWith(Math.abs(y), Math.abs(x), Math.abs(rx))), 1));
      // Make sure your ID's match your configuration
      frontleft.setPower(((y + x) - rx) / denominator);
      rearleft.setPower(((y - x) + rx) / denominator);
      frontright.setPower(((y - x) - rx) / denominator);
      rearright.setPower((y + x + rx) / denominator);
      // all left side is reversed becasue of mototr mounting
      // Right slide code
      if (gamepad1.dpad_down) {
        rightslide.setPower(1);
      } else {
        if (gamepad1.dpad_up) {
          rightslide.setPower(-1);
        } else {
          rightslide.setPower(0);
        }
      }
      // Right pivot code
      if (gamepad1.dpad_right) {
        rightpivot.setPower(0.5);
      } else {
        rightpivot.setPower(0);
      }
      if (gamepad1.dpad_left) {
        rightpivot.setPower(0.5);
      } else {
        rightpivot.setPower(0);
      }
      // Left pivot code
      if (gamepad1.dpad_right) {
        leftpivot.setPower(1);
      } else {
        leftpivot.setPower(0);
      }
      if (gamepad1.dpad_left) {
        leftpivot.setPower(1);
      } else {
        leftpivot.setPower(0);
      }
      // intake claw code
      leftservo.setDirection(CRServo.Direction.FORWARD);
      rightservo.setDirection(CRServo.Direction.FORWARD);
      if (gamepad1.left_bumper) {
        leftservo.setPower(1000);
      } else if (gamepad1.right_bumper) {
        leftservo.setPower(-1000);
      } else {
        leftservo.setPower(0);
      }
      if (gamepad1.left_bumper) {
        rightservo.setPower(-1000);
      } else if (gamepad1.right_bumper) {
        rightservo.setPower(1000);
      } else {
        rightservo.setPower(0);
      }
    }
  }
}
