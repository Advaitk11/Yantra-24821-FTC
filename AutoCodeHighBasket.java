/* package org.firstinspires.ftc.teamcode;

// Import necessary FTC libraries
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@Autonomous(name = "Auto Code High Basket")
public class AutoCodeHighBasket extends LinearOpMode {
    
    // Declare hardware components
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
    private DcMotor leftSlide;
    private DcMotor rightSlide;
    private Servo armservo;
    private Servo claw;

    // Movement controller instance
    private MovementController movement;
    private SlideController leftSlideController;
    private SlideController rightSlideController;
    private ClawController clawController;
    private ArmController armController;

    @Override
    public void runOpMode() {
        // Initialize hardware
        leftFront = hardwareMap.get(DcMotor.class, "front left");
        rightFront = hardwareMap.get(DcMotor.class, "front right");
        leftBack = hardwareMap.get(DcMotor.class, "rear left");
        rightBack = hardwareMap.get(DcMotor.class, "rear right");
        leftSlide = hardwareMap.get(DcMotor.class, "left slide");
        armservo = hardwareMap.get(Servo.class, "arm servo");
        claw = hardwareMap.get(Servo.class, "claw");
        
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
        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Initialize controller classes
        movement = new MovementController(leftFront, rightFront, leftBack, rightBack, this);
        leftSlideController = new SlideController(leftSlide, this);
        clawController = new ClawController(claw);

        int n = 0;

        int x = 1;

        // Wait for the game to start
        waitForStart();
        if (opModeIsActive()) {

            movement.right(0.4);
            movement.forward(0.2);
            movement.turnLeft(0.2);

            setSlideTarget(1000);
            enableRunToPosition();
            while (opModeIsActive() && leftSlide.isBusy()) {
            }
            disableRunToPosition();
            enableSlideCorrection();
            correctSlidePosition();

            sleep(125);

            armservo.setPosition();
            claw.open();
            sleep(500);
            claw.close();
            armservo.setPosition(0.65);
            sleep(125);
            disableSlideCorrection();
            sleep(125);
            SlideController.negative(2);

            movement.back(0.4);
            movement.turnRight(0.2);
            movement.left(0.5);
            movement.back(2);
        }
    }

    // Method to set the target position for the slide
    private void setSlideTarget(int n) {
        targetSlidePosition = n;
    }

    // Method to enable slide correction
    private void enableSlideCorrection() {
        isSlideCorrectionEnabled = true;
    }

    // Method to disable slide correction
    private void disableSlideCorrection() {
        isSlideCorrectionEnabled = false;
    }

    // Slide correction logic (runs while opModeIsActive() is true)
    private void correctSlidePosition() {
        while (opModeIsActive() && isSlideCorrectionEnabled) {
            // Correct the slide position as needed
            if (Math.abs(targetSlidePosition - leftSlide.getCurrentPosition()) > 25) {
                leftSlide.setTargetPosition(targetSlidePosition);
                leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftSlide.setPower(1);
            }
        }
    }

    // Class for robot movement
    public static class MovementController {
        private DcMotor leftFront, rightFront, leftBack, rightBack;
        private LinearOpMode opMode;

        public MovementController(DcMotor leftFront, DcMotor rightFront, DcMotor leftBack, DcMotor rightBack, LinearOpMode opMode) {
            this.leftFront = leftFront;
            this.rightFront = rightFront;
            this.leftBack = leftBack;
            this.rightBack = rightBack;
            this.opMode = opMode;
        }

        // Movement functions with optional power
        public void forward(double seconds, Double... power) {
            setPowerAll(power.length > 0 ? power[0] : 0.5);
            opMode.sleep((long) (seconds * 1000));
            stopAll();
        }

        public void back(double seconds, Double... power) {
            setPowerAll(power.length > 0 ? -power[0] : -0.5);
            opMode.sleep((long) (seconds * 1000));
            stopAll();
        }

        public void left(double seconds, Double... power) {
            double p = power.length > 0 ? power[0] : 0.5;
            leftFront.setPower(-p);
            rightFront.setPower(p);
            leftBack.setPower(p);
            rightBack.setPower(-p);
            opMode.sleep((long) (seconds * 1000));
            stopAll();
        }

        public void right(double seconds, Double... power) {
            double p = power.length > 0 ? power[0] : 0.5;
            leftFront.setPower(p);
            rightFront.setPower(-p);
            leftBack.setPower(-p);
            rightBack.setPower(p);
            opMode.sleep((long) (seconds * 1000));
            stopAll();
        }

        public void turnLeft(double seconds, Double... power) {
            double p = power.length > 0 ? power[0] : 0.5;
            leftFront.setPower(-p);
            rightFront.setPower(p);
            leftBack.setPower(-p);
            rightBack.setPower(p);
            opMode.sleep((long) (seconds * 1000));
            stopAll();
        }

        public void turnRight(double seconds, Double... power) {
            double p = power.length > 0 ? power[0] : 0.5;
            leftFront.setPower(p);
            rightFront.setPower(-p);
            leftBack.setPower(p);
            rightBack.setPower(-p);
            opMode.sleep((long) (seconds * 1000));
            stopAll();
        }

        // Helper methods
        private void setPowerAll(double power) {
            leftFront.setPower(power);
            rightFront.setPower(power);
            leftBack.setPower(power);
            rightBack.setPower(power);
        }

        private void stopAll() {
            setPowerAll(0);
        }
    }


private void setSlideTarget(int n) {
    targetSlidePosition = n;
    leftSlide.setTargetPosition(targetSlidePosition);  // Correctly set the target position
}

private void enableRunToPosition() {
    leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION); // Set the motor mode to RUN_TO_POSITION
    leftSlide.setPower(1.0);  // Set the motor power to move the slide
}

private void disableRunToPosition() {
    leftSlide.setPower(0);  // Stop the motor
    leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Change mode back to RUN_USING_ENCODER
}

    // Class for controlling slide motors
    public static class SlideController {
        private DcMotor slideMotor;
        private LinearOpMode opMode;

        public SlideController(DcMotor slideMotor, LinearOpMode opMode) {
            this.slideMotor = slideMotor;
            this.opMode = opMode;
        }

        public void positive(double seconds) {
            slideMotor.setPower(1.0);
            opMode.sleep((long) (seconds * 1000));
            slideMotor.setPower(0);
        }

        public void negative(double seconds) {
            slideMotor.setPower(-1.0);
            opMode.sleep((long) (seconds * 1000));
            slideMotor.setPower(0);
        }
    }

    // Class for controlling the claw
    public static class ClawController {
        private Servo claw;

        public ClawController(Servo claw) {
            this.claw = claw;
        }

        public void open() {
            claw.setPosition(1.0); // Set the open position (adjust as needed)
        }

        public void close() {
            claw.setPosition(0.6); // Set the close position (adjust as needed)
        }
        
*/