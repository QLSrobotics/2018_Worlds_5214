package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="HOUSTON_Manual_v1", group="Team5214")
//@Disabled
public class HOUSTON_Manual_v1 extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    //declares motors
    private DcMotor leftBack;
    private DcMotor rightBack;
    private DcMotor leftFront;
    private DcMotor rightFront;

    private DcMotor liftMotor;
    private DcMotor relicMotor;

    private DcMotor lBelt;
    private DcMotor rBelt;

    private Servo colSer;
    private Servo knckSer;


    private Servo rDum;
    private Servo lDum;
    private Servo cDum;
    private Servo wrist;
    private Servo finger;


    private int ticks;
    private int position2move2;


    long startTime = 0;
    long startTime2 = 0;
    long startTime3 = 0;


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //hooks up all of these motors with the config file
        leftBack = hardwareMap.get(DcMotor.class, "LB");
        rightBack = hardwareMap.get(DcMotor.class, "RB");
        leftFront = hardwareMap.get(DcMotor.class, "LF");
        rightFront = hardwareMap.get(DcMotor.class, "RF");

        cDum = hardwareMap.servo.get("CD");
        rDum = hardwareMap.servo.get("RD");
        lDum = hardwareMap.servo.get("LD");
        colSer = hardwareMap.servo.get("COLORSERVO");
        knckSer = hardwareMap.servo.get("FLICKSERVO");
        wrist = hardwareMap.servo.get("WRIST");
        finger = hardwareMap.servo.get("FINGER");

        lBelt = hardwareMap.dcMotor.get("LBELT");
        rBelt = hardwareMap.dcMotor.get("RBELT");

        liftMotor = hardwareMap.dcMotor.get("LIFT");
        relicMotor = hardwareMap.dcMotor.get("RELICMOTOR");


        // knckSer.setPosition(.5);
        // colSer.setPosition(.2);

        waitForStart();


        while (opModeIsActive()) {

            //   colSer.setPosition(.2);
            // knckSer.setPosition(.5);
            //game pad one cotrls

            if (gamepad1.dpad_up) {
                lBelt.setPower(-1);
                rBelt.setPower(1);
            }
            if (gamepad1.dpad_left) {
                lBelt.setPower(0.5);
                rBelt.setPower(-0.5);
            }
            if (gamepad1.dpad_down) {
                lBelt.setPower(1);
                rBelt.setPower(-1);
            }
            if (gamepad1.dpad_right) {
                lBelt.setPower(0);
                rBelt.setPower(0);
            }
            telemetry.update();
            bButton();
            xButton();

            rBumpGp1();

//            if(gamepad1.right_bumper){lDum.setPosition(0.73); rDum.setPosition(.21); sleep(500); cDum.setPosition(0.25); lBelt.setPower(1); rBelt.setPower(-1);}
            if(gamepad1.x){
                lDum.setPosition(0.61);
                cDum.setPosition(0.33);}

            if(gamepad1.y){
                lDum.setPosition(0.15);
                cDum.setPosition(0.8); }

            if(gamepad1.b){
                lDum.setPosition(0.53);
                cDum.setPosition(0.33);
                lBelt.setPower(0); rBelt.setPower(0);}

////            TARGET DOESNT WORK IF MOTOR IS STUCK AND PRESSED IN WRONG DIRECTION FIX IT
////            if (gamepad1.x){
////                motorWithEncoder(liftMotor,.5,8);
////            }
////            if (gamepad1.b){
////                motorWithEncoder(liftMotor,-.5,8);
////            }
//            if (gamepad1.x){
//                liftMotor.setPower(.5);
//                sleep(200);
//                liftMotor.setPower(0);
//            }
//            if (gamepad1.b){
//                liftMotor.setPower(-.5);
//                sleep(400);
//                liftMotor.setPower(0);
//            }

            //this is for game pad one olny right now -- hima -- 21:43 -- SAT feb 17

            /*if(gamepad2.a){
                elbow.setPosition(.75);
            }
            if (gamepad2.y){
                elbow.setPosition(0.25);
            }

            bButton();
            xButton();

            if (gamepad2.b){
                elbow.setPosition(0.50);
            }

            if (gamepad2.left_bumper) {
                hand.setPosition(0.25);
            }

            if (gamepad2.right_bumper) {
                hand.setPosition(0.75);
            }

            if (gamepad2.dpad_down) {
                worm.setPower(-.75);
            }

            if (gamepad2.dpad_right) {
                worm.setPower(0);

            }
            if (gamepad2.dpad_up) {
                worm.setPower(1);
            }
        */
            /*telemetry.update();
            leftBack.setPower(
                    .45*((gamepad1.left_stick_y + gamepad1.left_stick_x + (.5*gamepad1.right_stick_y)) + (.75 * -(gamepad1.right_stick_x))
                            + (.5 * (gamepad1.right_trigger)) + -.5 * (gamepad1.left_trigger))
                            + .9*((gamepad2.left_stick_y + gamepad2.left_stick_x + (.5*gamepad2.right_stick_y)) + (.75 * -(gamepad2.right_stick_x))
                            + (.5 * (gamepad2.right_trigger)) + -.5 * (gamepad2.left_trigger)));

            leftFront.setPower(
                    .45*((gamepad1.left_stick_y - gamepad1.left_stick_x+ (.5*gamepad1.right_stick_y) + (.75 * -(gamepad1.right_stick_x))
                            + (-.5 * (gamepad1.right_trigger)) + .5 * (gamepad1.left_trigger)))
                            +.9*((gamepad2.left_stick_y - gamepad2.left_stick_x+ (.5*gamepad2.right_stick_y) + (.75 * -(gamepad2.right_stick_x))
                            + (-.5 * (gamepad2.right_trigger)) + .5 * (gamepad2.left_trigger))));

            rightBack.setPower(
                    .45*((-gamepad1.left_stick_y + gamepad1.left_stick_x+ (-.5*gamepad1.right_stick_y) + (.75 * -(gamepad1.right_stick_x))
                            + (.5 * (gamepad1.right_trigger)) + -.5 * (gamepad1.left_trigger)))
                            +.9*((-gamepad2.left_stick_y + gamepad2.left_stick_x+ (-.5*gamepad2.right_stick_y) + (.75 * -(gamepad2.right_stick_x))
                            + (.5 * (gamepad2.right_trigger)) + -.5 * (gamepad2.left_trigger))));

            rightFront.setPower(
                    .45*((-gamepad1.left_stick_y - gamepad1.left_stick_x+ (-.5*gamepad1.right_stick_y) + (.75 * -(gamepad1.right_stick_x))
                            + (-.5 * (gamepad1.right_trigger)) + .5 * (gamepad1.left_trigger)))
                            +.9*((-gamepad2.left_stick_y - gamepad2.left_stick_x+ (-.5*gamepad2.right_stick_y) + (.75 * -(gamepad2.right_stick_x))
                            + (-.5 * (gamepad2.right_trigger)) + .5 * (gamepa
                            d2.left_trigger))));

            */


            //gamepad 2 contrls

            if(gamepad2.b){wrist.setPosition(.9);}
            if(gamepad2.x){wrist.setPosition(.07);}
            if(gamepad2.a){finger.setPosition(.78);}
            relicRELEASE();

            if(gamepad2.dpad_right){relicMotor.setPower(-.25); }

            else if(gamepad2.dpad_left) {relicMotor.setPower(.25);}

            else if(gamepad2.dpad_down){motorWithEncoder(relicMotor,1,27);
            }

            else if(gamepad2.dpad_up){
                wrist.setPosition(.07);
                finger.setPosition(.2);
                motorWithEncoder(relicMotor,1,-42);

            }

            else if(gamepad2.right_bumper) {motorWithEncoder(relicMotor,1,15);}
            else if(gamepad2.left_bumper) {relicMotor.setPower(0);}
            else{relicMotor.setPower(0);}




            telemetry.update();
            leftBack.setPower(
                    ((gamepad1.left_stick_y + gamepad1.left_stick_x + (.5*gamepad1.right_stick_y)) + (.75 * -(gamepad1.right_stick_x))
                            + (.6 * (gamepad1.right_trigger)) + -.6 * (gamepad1.left_trigger))
                            + .5*((gamepad2.left_stick_y + gamepad2.left_stick_x + (.5*gamepad2.right_stick_y)) + (.75 * -(gamepad2.right_stick_x))
                            + (.75 * (gamepad2.right_trigger)) + -.75 * (gamepad2.left_trigger)));

            leftFront.setPower(
                    ((gamepad1.left_stick_y - gamepad1.left_stick_x+ (.5*gamepad1.right_stick_y) + (.75 * -(gamepad1.right_stick_x))
                            + (-.6 * (gamepad1.right_trigger)) + .6 * (gamepad1.left_trigger)))
                            +.5*((gamepad2.left_stick_y - gamepad2.left_stick_x+ (.5*gamepad2.right_stick_y) + (.75 * -(gamepad2.right_stick_x))
                            + (-.75 * (gamepad2.right_trigger)) + .75 * (gamepad2.left_trigger))));

            rightBack.setPower(
                    ((-gamepad1.left_stick_y + gamepad1.left_stick_x+ (-.5*gamepad1.right_stick_y) + (.75 * -(gamepad1.right_stick_x))
                            + (.6 * (gamepad1.right_trigger)) + -.6 * (gamepad1.left_trigger)))
                            +.5*((-gamepad2.left_stick_y + gamepad2.left_stick_x+ (-.5*gamepad2.right_stick_y) + (.75 * -(gamepad2.right_stick_x))
                            + (.75 * (gamepad2.right_trigger)) + -.75 * (gamepad2.left_trigger))));

            rightFront.setPower(
                    ((-gamepad1.left_stick_y - gamepad1.left_stick_x+ (-.5*gamepad1.right_stick_y) + (.75 * -(gamepad1.right_stick_x))
                            + (-.6 * (gamepad1.right_trigger)) + .6 * (gamepad1.left_trigger)))
                            +.5*((-gamepad2.left_stick_y - gamepad2.left_stick_x+ (-.5*gamepad2.right_stick_y) + (.75 * -(gamepad2.right_stick_x))
                            + (-.75 * (gamepad2.right_trigger)) + .75 * (gamepad2.left_trigger))));
        }






    }



    private void motorWithEncoder(DcMotor motorName, double power, int inches) {
        motorName.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorName.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        ticks = (int) (inches * 1680 / (1.4 * 3.14159)); //converts inches to ticks
        telemetry.addData("target position: ", ticks);
        telemetry.addData("motor current position: ", motorName.getCurrentPosition());
        telemetry.update();

        //modifies moveto position based on starting ticks position, keeps running tally
        motorName.setTargetPosition(ticks);
        motorName.setPower(power);

        while(motorName.isBusy()){

            //prints out the current position of the motor
            telemetry.addData("target position: ", ticks);
            telemetry.addData("motor current position: ", motorName.getCurrentPosition());
            telemetry.update();


            leftBack.setPower(
                    ((gamepad1.left_stick_y + gamepad1.left_stick_x + (.5*gamepad1.right_stick_y)) + (.75 * -(gamepad1.right_stick_x))
                            + (.5 * (gamepad1.right_trigger)) + -.5 * (gamepad1.left_trigger))
                            + .45*((gamepad2.left_stick_y + gamepad2.left_stick_x + (.5*gamepad2.right_stick_y)) + (.75 * -(gamepad2.right_stick_x))
                            + (.75 * (gamepad2.right_trigger)) + -.75 * (gamepad2.left_trigger)));

            leftFront.setPower(
                    ((gamepad1.left_stick_y - gamepad1.left_stick_x+ (.5*gamepad1.right_stick_y) + (.75 * -(gamepad1.right_stick_x))
                            + (-.5 * (gamepad1.right_trigger)) + .5 * (gamepad1.left_trigger)))
                            +.45*((gamepad2.left_stick_y - gamepad2.left_stick_x+ (.5*gamepad2.right_stick_y) + (.75 * -(gamepad2.right_stick_x))
                            + (-.75 * (gamepad2.right_trigger)) + .75 * (gamepad2.left_trigger))));

            rightBack.setPower(
                    ((-gamepad1.left_stick_y + gamepad1.left_stick_x+ (-.5*gamepad1.right_stick_y) + (.75 * -(gamepad1.right_stick_x))
                            + (.5 * (gamepad1.right_trigger)) + -.5 * (gamepad1.left_trigger)))
                            +.45*((-gamepad2.left_stick_y + gamepad2.left_stick_x+ (-.5*gamepad2.right_stick_y) + (.75 * -(gamepad2.right_stick_x))
                            + (.75 * (gamepad2.right_trigger)) + -.75 * (gamepad2.left_trigger))));

            rightFront.setPower(
                    ((-gamepad1.left_stick_y - gamepad1.left_stick_x+ (-.5*gamepad1.right_stick_y) + (.75 * -(gamepad1.right_stick_x))
                            + (-.5 * (gamepad1.right_trigger)) + .5 * (gamepad1.left_trigger)))
                            +.45*((-gamepad2.left_stick_y - gamepad2.left_stick_x+ (-.5*gamepad2.right_stick_y) + (.75 * -(gamepad2.right_stick_x))
                            + (-.75 * (gamepad2.right_trigger)) + .75 * (gamepad2.left_trigger))));
            //prints out the current position of the motor
            telemetry.update();
            if(gamepad2.left_bumper){
                motorName.setPower(0);
                break;
            };

        }

        motorName.setPower(0);
        motorName.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorName.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //prints out the current position of the motor
        telemetry.update();





    }

    private void bButton(){
        if (gamepad1.right_bumper){
            startTime = System.currentTimeMillis();
            liftMotor.setPower(.5);
        }
        setSpe(750,700,0,startTime, liftMotor);

    }

    private void xButton(){
        if (gamepad1.left_bumper){
            startTime = System.currentTimeMillis();
            liftMotor.setPower(-.5);
        }
        setSpe(1800,1900,0,startTime, liftMotor);

    }
//              if(gamepad2.b){wrist.setPosition(.9);}
//            if(gamepad2.x){wrist.setPosition(.07);}
//            if(gamepad2.a){finger.setPosition(.78);}
//            if(gamepad2.y){finger.setPosition(.2);}

    public void relicRELEASE(){
        if(gamepad2.y){
            startTime3 = System.currentTimeMillis();
        }

        setPos(1,50,.78,startTime3,finger);
        setPos(51,150,.73,startTime3,finger);
        setPos(151,250,.65,startTime3,finger);
        setPos(251,350,.6,startTime3,finger);
        setPos(351,450,.55,startTime3,finger);
        setPos(451,600,.2,startTime3,finger);
    }

//    private void gp2X(){
//        if(gamepad2.x){
//            startTime = System.currentTimeMillis();
//            elbow.setPosition(.55);
//        }
//        setPos(1000,1100,.5, startTime, elbow);
//    }

    public void rBumpGp1(){
        if(gamepad1.a){
            startTime2 = System.currentTimeMillis();
        }
        setPos(1,50,.40,startTime2,lDum);
        setPos(51,150,.50,startTime2,lDum);
        setPos(151,250,.55,startTime2,lDum);
        setPos(251,350,.6,startTime2,lDum);
        setPos(351,450,.65,startTime2,lDum);
        setPos(451,600,.71,startTime2,lDum);
        setPos(401,600,.33,startTime2,cDum);
        setSpe(401, 600, 1,startTime2,lBelt);
        setSpe(401, 600, -1,startTime2,rBelt);
    }

    // if(gamepad1.right_bumper)
    // {lDum.setPosition(0.73); rDum.setPosition(.21);
    // sleep(500);
    // cDum.setPosition(0.25); lBelt.setPower(1); rBelt.setPower(-1);}


    private void setSpe(long in, long out, double power, long sTime, DcMotor mot ){
        if((System.currentTimeMillis() >= (in+sTime)) && (System.currentTimeMillis() <= (out+sTime))){
            mot.setPower(power);
        }

    }

    private void setPos(long in, long out, double pos, long sTime, Servo ser ){
        if((System.currentTimeMillis() >= (in+sTime)) && (System.currentTimeMillis() <= (out+sTime))){
            ser.setPosition(pos);
        }
    }



//    private void xButton(){
//        if (gamepad1.x){
//            startTime = System.currentTimeMillis();
//            liftMotor.setPower(.5);
//        }
//
//        setPos(100,300,.5,startTime);
//        setPos(300,500,.6,startTime);
//        setPos(500,700,.45,startTime);
//        setPos(900,1500,.8,startTime);
//
//
//
//    }
//
//    private void setPos(long in, long out, double alignInput, long sTime ) {
//        if ((System.currentTimeMillis() >= (in + sTime)) && (System.currentTimeMillis() <= (out + sTime))) {
//            align.setPosition(alignInput);
//        }
//    }
}