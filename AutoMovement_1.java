package org.firstinspires.ftc.teamcode;

// Import necessary FTC libraries
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "AutoMovement_1")
public class AutoMovement_1 extends LinearOpMode {
    
    // Declare hardware components
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
    private DcMotor leftSlide;
    private DcMotor rightSlide;
    private Servo claw;

    // Movement controller instance
    private MovementController movement;
    private SlideController leftSlideController;
    private SlideController rightSlideController;
    private ClawController clawController;
    //private ArmController armController;

    @Override
    public void runOpMode() {
        // Initialize hardware
        leftFront = hardwareMap.get(DcMotor.class, "front left");
        rightFront = hardwareMap.get(DcMotor.class, "front right");
        leftBack = hardwareMap.get(DcMotor.class, "rear left");
        rightBack = hardwareMap.get(DcMotor.class, "rear right");
        leftSlide = hardwareMap.get(DcMotor.class, "left slide");
        rightSlide = hardwareMap.get(DcMotor.class, "right slide");
       // armMotor = hardwareMap.get(DcMotor.class, "arm");
        claw = hardwareMap.get(Servo.class, "claw");

        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        //armMotor.setDirection(DcMotor.Direction.FORWARD);
        claw.setDirection(Servo.Direction.FORWARD);

        // Initialize controller classes
        movement = new MovementController(leftFront, rightFront, leftBack, rightBack, this);
        leftSlideController = new SlideController(leftSlide, this);
        rightSlideController = new SlideController(rightSlide, this);
        clawController = new ClawController(claw);
        //armController = new ArmController(armMotor, this);

        // Wait for the game to start
        waitForStart();
        if (opModeIsActive()) {
            movement.right(0.3, 0.5);
            movement.forward(0.85);
            movement.back(0.55);
            movement.right(0.3);
            movement.turnRight(0.8, 0.5);
            // going to first block
            movement.forward(1.25, 0.6);
            sleep(125);
            movement.left(0.45, 0.6);
            movement.turnRight(0.1,0.5);
            movement.back(1.4, 0.6);
            movement.right(0.5, 0.6);
            movement.turnLeft(0.1, 0.6);
            // going to 2nd block
            movement.forward(1.2);
            movement.left(0.3);
            sleep(125);
            movement.turnRight(0.1, 0.6);
            sleep(125);
            movement.back(1.5,0.6);
            sleep(125);
            // going to 3rd block
            movement.right(0.5, 0.6);
            movement.forward(1.1);
            sleep(125);
            movement.left(1.1, 0.6);
            sleep(125);
            movement.back(1.5, 0.6);
            sleep(125);
            movement.forward(0.2);
            sleep(125);
            movement.right(0.3);
            sleep(125);
            movement.turnRight(0.4);
            sleep(125);
            movement.forward(0.5);
            movement.right(0.5);
            sleep(125);
            movement.forward(1.8);
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
            setPowerAll(power.length > 0 ? power[0] : 0.75);
            opMode.sleep((long) (seconds * 1000));
            stopAll();
        }

        public void back(double seconds, Double... power) {
            setPowerAll(power.length > 0 ? -power[0] : -0.75);
            opMode.sleep((long) (seconds * 1000));
            stopAll();
        }

        public void left(double seconds, Double... power) {
            double p = power.length > 0 ? power[0] : 0.75;
            leftFront.setPower(-p);
            rightFront.setPower(p);
            leftBack.setPower(p);
            rightBack.setPower(-p);
            opMode.sleep((long) (seconds * 1000));
            stopAll();
        }

        public void right(double seconds, Double... power) {
            double p = power.length > 0 ? power[0] : 0.75;
            leftFront.setPower(p);
            rightFront.setPower(-p);
            leftBack.setPower(-p);
            rightBack.setPower(p);
            opMode.sleep((long) (seconds * 1000));
            stopAll();
        }

        public void turnLeft(double seconds, Double... power) {
            double p = power.length > 0 ? power[0] : 0.75;
            leftFront.setPower(-p);
            rightFront.setPower(p);
            leftBack.setPower(-p);
            rightBack.setPower(p);
            opMode.sleep((long) (seconds * 1000));
            stopAll();
        }

        public void turnRight(double seconds, Double... power) {
            double p = power.length > 0 ? power[0] : 0.75;
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
            claw.setPosition(0.0); // Set the close position (adjust as needed)
            }
        }
    }
