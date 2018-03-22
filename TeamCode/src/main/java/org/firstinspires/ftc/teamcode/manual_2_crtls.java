package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="manual_2_crtls", group="Team5214")
@Disabled
public class manual_2_crtls extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    //declares motors
    private DcMotor leftBack;
    private DcMotor rightBack;
    private DcMotor leftFront;
    private DcMotor rightFront;

    private DcMotor liftMotor;
    private DcMotor ramp;

    private DcMotor lBelt;
    private DcMotor rBelt;

    private Servo colSer;
    private Servo knckSer;

    private Servo align;

    private Servo rDum;
    private Servo lDum;

    private DigitalChannel limtTop;
    private DigitalChannel limtBot;


    boolean limtHit = true;
    boolean go;

    long startTime = 0;


    @Override
    public void runOpMode() throws InterruptedException{
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //hooks up all of these motors with the config file
        leftBack  = hardwareMap.get(DcMotor.class, "LB");
        rightBack = hardwareMap.get(DcMotor.class, "RB");
        leftFront  = hardwareMap.get(DcMotor.class, "LF");
        rightFront = hardwareMap.get(DcMotor.class, "RF");

        rDum = hardwareMap.servo.get("RD");
        lDum = hardwareMap.servo.get("LD");
        colSer =hardwareMap.servo.get("COLORSERVO");
        knckSer = hardwareMap.servo.get("FLICKSERVO");
        align = hardwareMap.servo.get("ALIGN1");

        lBelt = hardwareMap.dcMotor.get("LBELT");
        rBelt = hardwareMap.dcMotor.get("RBELT");

        liftMotor = hardwareMap.dcMotor.get("LIFT");
        ramp = hardwareMap.dcMotor.get("ramp");

        limtTop = hardwareMap.get(DigitalChannel.class, "touch_top");
        limtBot = hardwareMap.get(DigitalChannel.class, "touch_bot");

        align.setPosition(0);

        knckSer.setPosition(.5);
        colSer.setPosition(.9);

        waitForStart();



        while (opModeIsActive()) {

            colSer.setPosition(.9);
            knckSer.setPosition(.5);
            //game pad one cotrls

            //gp 1 dpad
            if(gamepad1.dpad_up){lBelt.setPower(-.75);rBelt.setPower(.75);}
            if(gamepad1.dpad_down){lBelt.setPower(.75);rBelt.setPower(-0.75);}
            if(gamepad1.dpad_left){colSer.setPosition(.9); knckSer.setPosition(.5);}
            if(gamepad1.dpad_right){lBelt.setPower(0);rBelt.setPower(0);}

            if(gamepad1.y){ramp.setPower(-0.5);
            } else if(gamepad1.x){ramp.setPower(0.5);
            } else{ramp.setPower(0);}

            if(gamepad1.left_bumper){lDum.setPosition(.15);rDum.setPosition(.85);}
            if(gamepad1.right_bumper){lDum.setPosition(.7);rDum.setPosition(.3);}


            xButton();
            bButton();

            //mecaum
            leftFront.setPower(curveSqr(((gamepad2.left_trigger - gamepad2.right_trigger)*-0.5)-gamepad1.left_stick_y + gamepad1.left_stick_x+ gamepad1.right_stick_x - gamepad2.left_stick_y + gamepad2.left_stick_x+ gamepad2.right_stick_x )*.5
                    +(-curveSqr(gamepad1.left_trigger)*.4 + curveSqr(gamepad1.right_trigger)*0.4));
            rightFront.setPower(curveSqr(((gamepad2.left_trigger - gamepad2.right_trigger)*-0.5)+gamepad1.left_stick_y + gamepad1.left_stick_x+ gamepad1.right_stick_x + gamepad2.left_stick_y + gamepad2.left_stick_x+ gamepad2.right_stick_x)*.5
                    -(-curveSqr(gamepad1.left_trigger)*.4 + curveSqr(gamepad1.right_trigger)*0.4));
            leftBack.setPower(curveSqr(((-gamepad2.left_trigger + gamepad2.right_trigger)*-0.5)-gamepad1.left_stick_y - gamepad1.left_stick_x+ gamepad1.right_stick_x - gamepad2.left_stick_y - gamepad2.left_stick_x+ gamepad2.right_stick_x)*.5
                    -(-curveSqr(gamepad1.left_trigger)*.4 + curveSqr(gamepad1.right_trigger)*0.4));
            rightBack.setPower(curveSqr(((-gamepad2.left_trigger + gamepad2.right_trigger)*-0.5)+gamepad1.left_stick_y - gamepad1.left_stick_x+ gamepad1.right_stick_x + gamepad2.left_stick_y - gamepad2.left_stick_x+ gamepad2.right_stick_x)*.5
                    +(-curveSqr(gamepad1.left_trigger)*.4 + curveSqr(gamepad1.right_trigger)*0.4));



            //elevator controls
            if((gamepad2.y && limtTop.getState()==true) || (gamepad2.y && limtBot.getState()==false)){
                liftMotor.setPower(-1);
            }
            else if ((gamepad2.a && limtBot.getState()==true) || (gamepad2.b && limtTop.getState()==false)){
                liftMotor.setPower(1);
            }
            else if(limtTop.getState()==false || limtBot.getState()==false){
                liftMotor.setPower(0);
            }
            else {
                liftMotor.setPower(0);
            }



            //game pd ywo d pad
            if(gamepad2.dpad_up){lBelt.setPower(-.75);rBelt.setPower(.75);}
            if(gamepad2.dpad_down){lBelt.setPower(.75);rBelt.setPower(-.75);}
            if(gamepad2.dpad_left){liftMotor.setPower(0);}
            if(gamepad2.dpad_right){lBelt.setPower(0);rBelt.setPower(0);}


            if(gamepad2.left_bumper){lDum.setPosition(.15);rDum.setPosition(.85);}
            if(gamepad2.right_bumper){lDum.setPosition(.7);rDum.setPosition(.3);}



        }
    }


    private double curveSqr (double in){
        double out = (in * Math.abs(in));
        return out;
    }
    private double curveCube(double in){
        double out = (in * in * in);
        return out;
    }
    private void sleep(int i){
        long initial_time = System.currentTimeMillis(); //creates variable that saves the current time in milliseconds
        while(System.currentTimeMillis()-initial_time <i){ //subtracts the initial time value from the current time to measure elapsed time

        }
    }

    private void setPos(long in, long out, double power, long sTime ){
        if((System.currentTimeMillis() >= (in+sTime)) && (System.currentTimeMillis() <= (out+sTime))){
            liftMotor.setPower(power);
        }
    }

    private void xButton(){
        if (gamepad1.a){
            startTime = System.currentTimeMillis();
            align.setPosition(0);
            lBelt.setPower(0);
            rBelt.setPower(0);
        }
        setPos(100,150,.5,startTime);

    }

    private void bButton(){
        if (gamepad1.b){
            startTime = System.currentTimeMillis();
            align.setPosition(0);
            lBelt.setPower(0);
            rBelt.setPower(0);
        }
        setPos(100,150,-.5,startTime);

    }

}
