package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


@Autonomous(name="redONLYball", group="Ball")
@Disabled
public class redBall extends LinearOpMode {

    //declare vuforia recognizing engine
    VuforiaLocalizer vuforia;
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    //declare drive motors
    private DcMotor leftBack;
    private DcMotor rightBack;
    private DcMotor leftFront;
    private DcMotor rightFront;
    // declare dump servo
    private Servo leftDump;
    private Servo rightDump;
    // declare color servo
    private Servo colorServo;
    private Servo flickServo;
    private String colorid;
    // declare color sensor
    private ColorSensor colorFront;
    private DcMotor ramp;

    //use the two variables in two color sensors situation
//    ColorSensor colorFront;
//    ColorSensor colorBack;

    final double currentRatio = 1.3; //ratio set for red/blue, for color id function

    @Override
    public void runOpMode() throws InterruptedException {

        //mapping drive motors to configuration
        leftBack = hardwareMap.get(DcMotor.class, "LB");
        rightBack = hardwareMap.get(DcMotor.class, "RB");
        leftFront = hardwareMap.get(DcMotor.class, "LF");
        rightFront = hardwareMap.get(DcMotor.class, "RF");

        //mapping dump servos to configuration
        leftDump = hardwareMap.get(Servo.class, "LD");
        rightDump = hardwareMap.get(Servo.class, "RD");

        ramp = hardwareMap.dcMotor.get("ramp");


        //mapping color servo to configuration
        colorServo = hardwareMap.get(Servo.class, "COLORSERVO");
        flickServo = hardwareMap.get(Servo.class, "FLICKSERVO");

        //mapping color sensor to configuration
        colorFront = hardwareMap.get(ColorSensor.class, "CSF");

        //use the two mapping where there are two color sensors
//        colorFront  = hardwareMap.get(ColorSensor.class, "CSF");
//        colorBack = hardwareMap.get(ColorSensor.class, "CSB");

        //drive motor directions
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        //enable image tracking


        runtime.reset();


        // run until the end of the match (driver presses STOP)h

        while (opModeIsActive()) {

            arm(.1); // put arm down
            sleep(2000);

            colorid = checkColor(colorFront, currentRatio);

            telemetry.addLine(colorid);
            telemetry.update();

            sleep(1000);

            if (colorid == "RED") {
                flicker(0);
            } else if (checkColor(colorFront, .4) == "BLUE") {
                flicker(1);
            }

            sleep(1000);
            arm(.8); // put arm up
            sleep(1500);


            idle();

            break;
        }

    }


    private void flicker(double position) {
        //setting the flicker servo to an input value
        flickServo.setPosition(position);
        sleep(2000);
        flickServo.setPosition(0.5);

    }

    private void arm(double position) {
        //setting the color servo to an input value
        colorServo.setPosition(position);
    }

    private void sleep(int i) {
        //initial time takes the current hardware time in milliseconds
        long initial_time = System.currentTimeMillis();
        //inside the while loop cpu will stop working when the input time is more than the time passed in this loop
        //cpu will be back working when the loop reaches the target time
        while (System.currentTimeMillis() - initial_time < i) {

        }
    }

    private String checkColor(ColorSensor sensor, double ratio) {
        double redOverBlue = (sensor.red() + 1) / (sensor.blue() + 1);
        if (redOverBlue >= ratio) {
            //if it is greater than ratio, it is red
            return "RED";
        } else if (redOverBlue <= ratio) {
            //if it is less than ratio, it is blue
            return "BLUE";
        } else {
            //if nothing is detected, return not defined
            return "UNDEF";
        }
    }
}