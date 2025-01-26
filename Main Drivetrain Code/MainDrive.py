from com.qualcomm.robotcore.eventloop.opmode import LinearOpMode, TeleOp
from com.qualcomm.robotcore.hardware import DcMotor, Servo
from org.firstinspires.ftc.robotcore.external import JavaUtil

@TeleOp(name="OUR_MAIN_DRIVE1 (Blocks to Python)")
class OUR_MAIN_DRIVE1(LinearOpMode):

    def runOpMode(self):
        cor_toggle = 0
        n = 0
        b_pressed = False

        # Initialize hardware
        armservo = self.hardwareMap.servo.get("arm servo")
        frontleft = self.hardwareMap.dcMotor.get("front left")
        rearleft = self.hardwareMap.dcMotor.get("rear left")
        leftslide = self.hardwareMap.dcMotor.get("left slide")
        frontright = self.hardwareMap.dcMotor.get("front right")
        rearright = self.hardwareMap.dcMotor.get("rear right")
        claw = self.hardwareMap.servo.get("claw")
        winch = self.hardwareMap.dcMotor.get("winch")

        # Servo and motor setup
        armservo.direction = Servo.Direction.FORWARD
        frontleft.direction = DcMotor.Direction.REVERSE
        rearleft.direction = DcMotor.Direction.REVERSE

        leftslide.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        leftslide.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        leftslide.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        motors = [frontleft, frontright, rearleft, rearright]
        for motor in motors:
            motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        self.waitForStart()

        armservo.position = 0.5

        while self.opModeIsActive():
            # Joystick controls
            y = -self.gamepad1.left_stick_y  # Y stick value is reversed
            x = self.gamepad1.left_stick_x * 1.1  # Strafing adjustment
            rx = self.gamepad1.right_stick_x

            # Normalize motor powers
            denominator = max(abs(y) + abs(x) + abs(rx), 1)
            frontleft_power = (y + x + rx) / denominator
            rearleft_power = (y - x + rx) / denominator
            frontright_power = (y - x - rx) / denominator
            rearright_power = (y + x - rx) / denominator

            # Set motor powers
            frontleft.power = frontleft_power
            rearleft.power = rearleft_power
            frontright.power = frontright_power
            rearright.power = rearright_power

            # Left slide control
            if self.gamepad2.dpad_up or self.gamepad2.dpad_down:
                leftslide.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

            if self.gamepad2.dpad_down:
                leftslide.power = -0.6
            elif self.gamepad2.dpad_up:
                leftslide.power = 1
            else:
                leftslide.power = 0

            # Arm servo control
            if self.gamepad2.y and armservo.position <= 0.5:
                armservo.position += 0.02
            if self.gamepad2.a and 0.1 <= armservo.position <= 1:
                armservo.position -= 0.02

            # Claw servo control
            if self.gamepad2.left_bumper:
                claw.position = 1
            if self.gamepad2.right_bumper:
                claw.position = 0.6

            # Toggle correction mode
            if self.gamepad2.b:
                if not b_pressed:
                    cor_toggle = 1 - cor_toggle
                    b_pressed = True
            else:
                b_pressed = False

            # Left slide position correction
            if self.gamepad2.dpad_up or self.gamepad2.dpad_down:
                n = leftslide.currentPosition

            if cor_toggle == 0 and not (self.gamepad2.dpad_up or self.gamepad2.dpad_down):
                if abs(n - leftslide.currentPosition) > 25:
                    leftslide.targetPosition = n
                    leftslide.mode = DcMotor.RunMode.RUN_TO_POSITION
                    leftslide.power = 1

            # Winch and arm servo disable
            if self.gamepad1.a:
                armservo.pwmDisable()
                winch.power = 1
            if self.gamepad1.b:
                armservo.pwmDisable()
                winch.power = -1

            # Telemetry data
            self.telemetry.addData("arm position", armservo.position)
            self.telemetry.addData("cor toggle", cor_toggle)
            self.telemetry.addData("n", n)
            self.telemetry.addData("leftslide actual pos", leftslide.currentPosition)
            self.telemetry.addData("front left pos", frontleft.currentPosition)
            self.telemetry.addData("front right pos", frontright.currentPosition)
            self.telemetry.addData("rear left pos", rearleft.currentPosition)
            self.telemetry.addData("rear right pos", rearright.currentPosition)
            self.telemetry.update()
