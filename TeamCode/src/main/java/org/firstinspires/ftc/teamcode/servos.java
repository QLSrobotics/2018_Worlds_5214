package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by hima on 3/9/18.
 */

@Autonomous(name="servo2", group="safe")
@Disabled

public class servos extends LinearOpMode {

    private Servo colorServo;
    private Servo FLICKSERVO;

    @Override
    public void runOpMode(){

        colorServo = hardwareMap.servo.get("COLORSERVO");
        FLICKSERVO = hardwareMap.servo.get("FLICKSERVO");

        while (opModeIsActive()){
            colorServo.setPosition(.5);
            FLICKSERVO.setPosition(.5);
            sleep(5000);

        }
    }


}
