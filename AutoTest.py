from com.qualcomm.robotcore.eventloop.opmode import Autonomous, LinearOpMode # type: ignore
from com.qualcomm.robotcore.hardware import DcMotor, Servo # type: ignore
from com.qualcomm.robotcore.util import ElapsedTime # type: ignore

@Autonomous(name="AutoTest 24/25")
class AutoTest(LinearOpMode):

    def runOpMode(self):
        # Initialize hardware variables

        #Slide
        self.leftslide = self.hardwareMap.get(DcMotor, "left slide")
        self.rightslide = self.hardwareMap.get(DcMotor, "right slide")
        
        #Pivot
        self.leftpivot = self.hardwareMap.get(DcMotor, "left pivot")
        self.rightpivot = self.hardwareMap.get(DcMotor, "right pivot")
        
        #Movement
        self.frontleft = self.hardwareMap.get(DcMotor, "front left")
        self.frontright = self.hardwareMap.get(DcMotor, "front right")
        self.rearleft = self.hardwareMap.get(DcMotor, "rear left")
        self.rearright = self.hardwareMap.get(DcMotor, "rear right")

        self.frontright.setDirection(DcMotor.Direction.FORWARD)
        self.rearright.setDirection(DcMotor.Direction.REVERSE)

        # Initialize the timer
        timer = ElapsedTime()

        # Wait for the game to start (driver presses PLAY)
        self.waitForStart()

        # Autonomous actions
        if self.opModeIsActive():
            # Reset timer and move robot forward
            timer.reset()
            while self.opModeIsActive() and timer.time() <= 1:
                self.frontleft.setPower(-1)
                self.frontright.setPower(1)
                self.rearleft.setPower(-1)
                self.rearright.setPower(1)

            # Continue moving in a different direction (Right)
            while self.opModeIsActive() and timer.time() <= 2:
                self.frontleft.setPower(-1)
                self.frontright.setPower(-1)
                self.rearleft.setPower(1)
                self.rearright.setPower(1)

            # Continue moving in a different direction (Left)
            while self.opModeIsActive() and timer.time() <= 3:
                self.frontleft.setPower(1)
                self.frontright.setPower(-1)
                self.rearleft.setPower(1)
                self.rearright.setPower(-1)

            # Move backwards to starting point
            while self.opModeIsActive() and timer.time() <= 4:
                self.frontleft.setPower(-1)
                self.frontright.setPower(-1)
                self.rearleft.setPower(1)
                self.rearright.setPower(1)
