from com.qualcomm.robotcore.eventloop.opmode import LinearOpMode, TeleOp
from com.qualcomm.robotcore.hardware import DcMotor

@TeleOp(name="OurmainDrivetrainforeverdontchangewithoutcopy (Blocks to Python)")
class OurmainDrivetrainforeverdontchangewithoutcopy(LinearOpMode):

    def runOpMode(self):
        # Initialize hardware variables
        self.frontright = self.hardwareMap.get(DcMotor, "front right")
        self.rearright = self.hardwareMap.get(DcMotor, "rear right")
        self.frontleft = self.hardwareMap.get(DcMotor, "front left")
        self.rearleft = self.hardwareMap.get(DcMotor, "rear left")
        self.rightslide = self.hardwareMap.get(DcMotor, "right slide")
        self.leftslide = self.hardwareMap.get(DcMotor, "left slide")
        self.rightpivot = self.hardwareMap.get(DcMotor, "right pivot")
        self.leftpivot = self.hardwareMap.get(DcMotor, "left pivot")

        # Reverse directions if necessary
        self.frontright.setDirection(DcMotor.Direction.FORWARD)
        self.rearright.setDirection(DcMotor.Direction.REVERSE)

        # Wait for the game to start (driver presses PLAY)
        self.waitForStart()

        while self.opModeIsActive():
            # Gamepad input processing
            y = -self.gamepad1.left_stick_y  # Remember, Y stick value is reversed
            x = self.gamepad1.left_stick_x * 1.1  # Counteract imperfect strafing
            rx = self.gamepad1.right_stick_x
            
            # Compute the denominator for scaling powers
            denominator = max(abs(y) + abs(x) + abs(rx), 1)

            # Set power for each wheel
            self.frontleft.setPower((y + x - rx) / denominator)
            self.rearleft.setPower((y - x + rx) / denominator)
            self.frontright.setPower((y - x - rx) / denominator)
            self.rearright.setPower((y + x + rx) / denominator)

            # Slide motor controls
            if self.gamepad1.dpad_down:
                self.rightslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER)
                self.rightslide.setPower(10000)
                self.leftslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER)
                self.leftslide.setPower(10000)
            elif self.gamepad1.dpad_up:
                self.rightslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER)
                self.rightslide.setPower(-10000)
                self.leftslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER)
                self.leftslide.setPower(-10000)
            else:
                self.rightslide.setPower(0)
                self.leftslide.setPower(0)

            # Pivot motor controls
            if self.gamepad1.dpad_right:
                self.rightpivot.setPower(1)
                self.leftpivot.setPower(-1)
            elif self.gamepad1.dpad_left:
                self.rightpivot.setPower(-1)
                self.leftpivot.setPower(1)
            else:
                self.rightpivot.setPower(0)
                self.leftpivot.setPower(0)
