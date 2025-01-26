package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name = "OUR_MAIN_DRIVE1 (Blocks to Java)")
public class OUR_MAIN_DRIVE1 extends LinearOpMode {

  private Servo armservo;
  private DcMotor frontleft;
  private DcMotor rearleft;
  private DcMotor leftslide;
  private DcMotor frontright;
  private DcMotor rearright;
  private Servo claw;
  private DcMotor winch;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    int cor_toggle;
    double denominator;
    float y;
    float rx;
    double x;
    boolean b_pressed;
    int n;

    armservo = hardwareMap.get(Servo.class, "arm servo");
    frontleft = hardwareMap.get(DcMotor.class, "front left");
    rearleft = hardwareMap.get(DcMotor.class, "rear left");
    leftslide = hardwareMap.get(DcMotor.class, "left slide");
    frontright = hardwareMap.get(DcMotor.class, "front right");
    rearright = hardwareMap.get(DcMotor.class, "rear right");
    claw = hardwareMap.get(Servo.class, "claw");
    winch = hardwareMap.get(DcMotor.class, "winch");

    armservo.setDirection(Servo.Direction.FORWARD);
    frontleft.setDirection(DcMotor.Direction.REVERSE);
    rearleft.setDirection(DcMotor.Direction.REVERSE);
    leftslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    leftslide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    leftslide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rearleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rearright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    frontleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    frontright.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    rearleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    rearright.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    cor_toggle = 0;
    waitForStart();
    armservo.setPosition(0.5);
    while (opModeIsActive()) {
      // Remember, Y stick value is reversed
      y = -gamepad1.left_stick_y;
      // Factor to counteract imperfect strafing
      x = gamepad1.left_stick_x * 1.1;
      rx = gamepad1.right_stick_x;
      // Denominator is the largest motor power (absolute value) or 1.
      // This ensures all powers maintain the same ratio, but only if one is outside of the range [-1, 1].
      denominator = JavaUtil.maxOfList(JavaUtil.createListWith(JavaUtil.sumOfList(JavaUtil.createListWith(Math.abs(y), Math.abs(x), Math.abs(rx))), 1));
      // Make sure your ID's match your configuration
      frontleft.setPower((y + x + rx) / denominator);
      rearleft.setPower(((y - x) + rx) / denominator);
      frontright.setPower(((y - x) - rx) / denominator);
      rearright.setPower(((y + x) - rx) / denominator);
      // all left side is reversed becasue of mototr mounting
      // Left slide code
      if (gamepad2.dpad_up || gamepad2.dpad_down) {
        leftslide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      }
      if (gamepad2.dpad_down) {
        leftslide.setPower(-0.6);
      } else {
        if (gamepad2.dpad_up) {
          leftslide.setPower(1);
        } else {
          leftslide.setPower(0);
        }
      }
      if (gamepad2.y && armservo.getPosition() <= 0.5) {
        armservo.setPosition(armservo.getPosition() + 0.02);
      }
      if (gamepad2.a && armservo.getPosition() >= 0.1 && armservo.getPosition() <= 1) {
        armservo.setPosition(armservo.getPosition() - 0.02);
      }
      if (gamepad2.left_bumper) {
        claw.setPosition(1);
      }
      if (gamepad2.right_bumper) {
        claw.setPosition(0.6);
      }
      if (gamepad2.b) {
        if (!b_pressed) {
          if (cor_toggle == 0) {
            cor_toggle = 1;
          } else {
            cor_toggle = 0;
          }
          b_pressed = true;
        }
      } else {
        b_pressed = false;
      }
      if (gamepad2.dpad_up || gamepad2.dpad_down) {
        n = leftslide.getCurrentPosition();
      }
      if (cor_toggle == 0) {
        if (!(gamepad2.dpad_up || gamepad2.dpad_down)) {
          if (Math.abs(n - leftslide.getCurrentPosition()) > 25) {
            leftslide.setTargetPosition(n);
            leftslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftslide.setTargetPosition(n);
            leftslide.setPower(1);
          }
        }
      }
      if (gamepad1.a) {
        armservo.setPwmDisable();
        winch.setPower(1);
      }
      if (gamepad1.b) {
        armservo.setPwmDisable();
        winch.setPower(-1);
      }
      telemetry.addData("arm position", armservo.getPosition());
      telemetry.addData("cor toggle", cor_toggle);
      telemetry.addData("n", n);
      telemetry.addData("leftslide actual pos", leftslide.getCurrentPosition());
      telemetry.addData("front left pos", frontleft.getCurrentPosition());
      telemetry.addData("front right pos", frontright.getCurrentPosition());
      telemetry.addData("rear left pos", rearleft.getCurrentPosition());
      telemetry.addData("rear right pos", rearright.getCurrentPosition());
      telemetry.update();
    }
  }
}
