package com.example.androidseries2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.*;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Net;
import org.opencv.imgproc.Imgproc;

import org.opencv.dnn.Dnn;
import org.opencv.utils.Converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    CameraBridgeViewBase cameraBridgeViewBase;
    BaseLoaderCallback baseLoaderCallback;
    //105033110
    boolean startYolo = false;
    boolean firstTimeYolo = false;
    Net tinyYolo;

    //105033209
    private Button mbtnColor;
    boolean startColor = true;
    boolean scancontrol = false;
    boolean sendcontrol = false;
    int i_cd;
    double x_p, y_p;
    double[] x_pc = new double[4];
    double[] y_pc = new double[4];
    double[] area = new double[4];
    boolean target_get = false, obstacle_get = false, target_right = false, target_left = false, scan = false;


    //hsv, canny edge, gaussian blur
    Mat frame_hsv, blurimg;
    Mat mat_green, edge_green;
    Mat mat_blue, mat_blue1, edge_blue;
    Mat mat_red, mat_red1, edge_red;

    //contours material
    String Label;
    Mat edge_detect, hovIMG;
//    MatOfPoint2f approxCurve;

    /*Mat edge_green_c, hovIMG_g;
    Mat edge_blue_c, hovIMG_b;
    Mat edge_red_c, hovIMG_r;*/

    //color
    Scalar scalar_low_g, scalar_high_g;
    Scalar scalar_low_b1, scalar_high_b1;
    Scalar scalar_low_b2, scalar_high_b2;
    Scalar scalar_low_r1, scalar_high_r1;
    Scalar scalar_low_r2, scalar_high_r2;

    //105033110
    private TBlue tBlue;
    private int skipped;
    private String btAddress = "98:D3:81:FD:88:8F";
    private String sCommand;

    private Button addressBn, disconnectBn, commandBn;
    private Button num1Bn, num2Bn, num3Bn, num4Bn, num5Bn;
    private EditText addressEt, commandEt;
    private TextView messagesTv, terminalTv;

    //105033110
    Handler samHandler = new Handler();
    boolean findCat = false;
    boolean findDog = false;
    String dir ="R";

/* //part4
    boolean startCanny = false;
*/


    public void YOLO(View Button) {

        if (startYolo == false) {

            startYolo = true;

            if (firstTimeYolo == false) {

                firstTimeYolo = true;
                String tinyYoloCfg = Environment.getExternalStorageDirectory() + "/dnns/yolov3-tiny.cfg";
                String tinyYoloWeights = Environment.getExternalStorageDirectory() + "/dnns/yolov3-tiny.weights";

                tinyYolo = Dnn.readNetFromDarknet(tinyYoloCfg, tinyYoloWeights);
            }

        }
        else {
            startYolo = false;
        }
    }


/* //part4
    public void Canny(View Button) {

        if (startCanny == false) {
            startCanny = true;
        }
        else {
            startCanny = false;
        }

    }
*/

/* //part3
    int counter = 0;
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraBridgeViewBase = (JavaCameraView)findViewById(R.id.CameraView);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);

        //105033209
        mbtnColor = (Button)findViewById(R.id.btnColor);
        mbtnColor.setOnClickListener(clickColor);

        //green
        scalar_low_g = new Scalar(35, 43, 46);
        scalar_high_g = new Scalar(77, 255, 255);

        //blue
        scalar_low_b1 = new Scalar(100, 43, 46);
        scalar_high_b1 = new Scalar(124, 255, 255);

        //another blue for project
        scalar_low_b2 = new Scalar(78, 43, 46);
        scalar_high_b2 = new Scalar(99, 255, 255);

        //red
        scalar_low_r1 = new Scalar(0, 43, 46);
        scalar_high_r1 = new Scalar(10, 255, 255);
        scalar_low_r2 = new Scalar(156, 43, 46);
        scalar_high_r2 = new Scalar(180, 255, 255);

        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        baseLoaderCallback = new BaseLoaderCallback(this) {
            @Override
            public void onManagerConnected(int status) {
                super.onManagerConnected(status);

                switch (status) {

                    case BaseLoaderCallback.SUCCESS:
                        cameraBridgeViewBase.enableView();
                        break;
                    default:
                        super.onManagerConnected(status);
                        break;
                }

            }
        };

        //105033110
        initGUI();
        try { actionController(); } catch(Exception e) {msgMessage("Action Error!\n");}

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat frame = inputFrame.rgba();
        //Mat dst = inputFrame.rgba();

        //color detection
        //105033209
        if (startColor == true) {
            if (scancontrol == true) {
                scan = true;
            }

            //get input frame (rgb)

            //guassian blur, hsv to canny edge, dilate
            Imgproc.GaussianBlur(frame, blurimg, new Size(5, 5), 2, 2);
            Imgproc.cvtColor(blurimg, frame_hsv, Imgproc.COLOR_RGB2HSV);

            //green
            Core.inRange(frame_hsv, scalar_low_g, scalar_high_g, mat_green);
            Imgproc.Canny(mat_green, edge_green, 60, 180);
            Imgproc.dilate(edge_green, edge_green, new Mat(), new Point(-1, 1), 1);

            //blue
            Core.inRange(frame_hsv, scalar_low_b1, scalar_high_b1, mat_blue1);
            Core.inRange(frame_hsv,scalar_low_b2, scalar_high_b2, mat_blue);
            Core.addWeighted(mat_blue1, 1.0, mat_blue, 1.0, 0.0, mat_blue);
            Imgproc.Canny(mat_blue, edge_blue, 60, 180);
            Imgproc.dilate(edge_blue, edge_blue, new Mat(), new Point(-1, 1), 1);

            //red
            Core.inRange(frame_hsv, scalar_low_r1, scalar_high_r1, mat_red1);
            Core.inRange(frame_hsv, scalar_low_r2, scalar_high_r2, mat_red);
            Core.addWeighted(mat_red1, 1.0, mat_red, 1.0, 0.0, mat_red);
//        Core.bitwise_or(mat_red1, mat_red, mat_red);
            Imgproc.Canny(mat_red, edge_red, 60, 180);
            Imgproc.dilate(edge_red, edge_red, new Mat(), new Point(-1, 1), 1);

            //test
//        edge_green_c = edge_red.clone();

            //detect green, blue and red separately
            //i_cd = 2, skip green
            for (i_cd = 2; i_cd <= 3; i_cd++){
                if (i_cd == 1){
//                edge_detect = edge_green.clone();
//                Label = "Green";
                } else if (i_cd == 2){
                    edge_detect = edge_blue.clone();
                    Label = "Blue";
                } else if (i_cd == 3){
                    edge_detect = edge_red.clone();
                    Label = "Red";
                }

                //get contours
                List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

                //RETR_EXTERNAL gives outer contour
                //compresses horizontal, vertical, and diagonal segments and leaves only their end points. For example, an up-right rectangular contour is encoded with 4 points
                Imgproc.findContours(edge_detect, contours, hovIMG, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

                //for each MatOfPoint in the contours, for each contour
                for (MatOfPoint cnt : contours) {
                    if(sendcontrol == true){
                        setLabel(frame, Label, cnt, i_cd);
                    }
                    /*MatOfPoint2f curve = new MatOfPoint2f(cnt.toArray());

                    //approxPolyDP() allows the approximation of polygons, so if your image contains polygons, they will be quite accurately detected
                    Imgproc.approxPolyDP(curve, approxCurve, 0.02 * Imgproc.arcLength(curve, true), true);

                    //sides of polygon
                    int numberVertices = (int) approxCurve.total();

                    //contour area
                    double contourArea = Imgproc.contourArea(cnt);

                    //absolute value of an area is used because area may be positive or negative
                    //continue, skip this contour without breaking for loop
                    if (Math.abs(contourArea) < 100) {
                        continue;
                    }

                    //Rectangle detected
                    if (numberVertices == 4) {
                        List<Double> cos = new ArrayList<>();

                        for (int j = 2; j < numberVertices + 1; j++) {

                            //helper function angle, help calculate cosine
                            cos.add(angle(approxCurve.toArray()[j % numberVertices], approxCurve.toArray()[j - 2], approxCurve.toArray()[j - 1]));
                        }

                        //Sort from small to big
                        Collections.sort(cos);

                        double mincos = cos.get(0);
                        double maxcos = cos.get(cos.size() - 1);

                        //square contours should have 4 vertices after approximation
                        //cosine is small when angle is near 90 degrees
                        if (mincos >= -0.1 && maxcos <= 0.3) {

                            //helper function setLabel
                            setLabel(frame, Label, cnt, i_cd);
                        }

                    }*/
                }
            }
            if(scan == true){
                scanning();
            }
            //return dst;
        }


        if (startYolo == true) {

            //105033110
            if (findCat == false && findDog == false) {
                //尋找目標障礙的動作
                //sendBT("F");

                scanning2();

                /*if (dir=="R") {
                    dir = "L";
                } else {
                    dir = "R";
                }

                sendBT(dir);
*//*                samHandler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                sendBT("S");
                            }
                        }, 300
                );*//*
                samHandler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                sendBT("F");
                            }
                        }, 325
                );
                samHandler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                sendBT("S");
                            }
                        }, 350
                );*/

            } else {
                findCat = false;
                findDog = false;
            }

            Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGBA2RGB);

            Mat ImgBlob = Dnn.blobFromImage(frame, 0.00392, new Size(416,416), new Scalar(0, 0, 0), /*swapRB*/false, /*crop*/false);

            tinyYolo.setInput(ImgBlob);

            java.util.List<Mat> result = new java.util.ArrayList<Mat>(2);

            List<String> outBlobNames = new java.util.ArrayList<>();
            outBlobNames.add(0, "yolo_16");
            outBlobNames.add(1, "yolo_23");

            tinyYolo.forward(result,outBlobNames);

            float confThreshold = 0.3f;

            List<Integer> clsIds = new ArrayList<>();
            List<Float> confs = new ArrayList<>();
            List<Rect> rects = new ArrayList<>();

            for (int i = 0; i < result.size(); ++i)
            {

                Mat level = result.get(i);

                for (int j = 0; j < level.rows(); ++j)
                {
                    Mat row = level.row(j);
                    Mat scores = row.colRange(5, level.cols());

                    Core.MinMaxLocResult mm = Core.minMaxLoc(scores);

                    float confidence = (float)mm.maxVal;

                    Point classIdPoint = mm.maxLoc;

                    if (confidence > confThreshold)
                    {
                        int centerX = (int)(row.get(0,0)[0] * frame.cols());
                        int centerY = (int)(row.get(0,1)[0] * frame.rows());
                        int width   = (int)(row.get(0,2)[0] * frame.cols());
                        int height  = (int)(row.get(0,3)[0] * frame.rows());

                        int left    = centerX - width  / 2;
                        int top     = centerY - height / 2;

                        clsIds.add((int)classIdPoint.x);
                        confs.add((float)confidence);

                        rects.add(new Rect(left, top, width, height));

                        //105033110
                        //Log.i("fb", "(((((((((((((((((((("+ frame.cols() + ", " + frame.rows() + ")))))))))))))");
                        //frame.cols()=1408, frame.rows()=792

                    }
                }
            }
            int ArrayLength = confs.size();

            if (ArrayLength>=1) {
                // Apply non-maximum suppression procedure.
                float nmsThresh = 0.2f;

                MatOfFloat confidences = new MatOfFloat(Converters.vector_float_to_Mat(confs));

                Rect[] boxesArray = rects.toArray(new Rect[0]);

                MatOfRect boxes = new MatOfRect(boxesArray);

                MatOfInt indices = new MatOfInt();

                Dnn.NMSBoxes(boxes, confidences, confThreshold, nmsThresh, indices);

                // Draw result boxes:
                int[] ind = indices.toArray();
                for (int i = 0; i < ind.length; ++i) {

                    int idx = ind[i];
                    Rect box = boxesArray[idx];

                    int idGuy = clsIds.get(idx);

                    float conf = confs.get(idx);

                    List<String> cocoNames = Arrays.asList("a person", "a bicycle", "a motorbike", "an airplane", "a bus", "a train", "a truck", "a boat", "a traffic light", "a fire hydrant", "a stop sign", "a parking meter", "a car", "a bench", "a bird", "a cat", "a dog", "a horse", "a sheep", "a cow", "an elephant", "a bear", "a zebra", "a giraffe", "a backpack", "an umbrella", "a handbag", "a tie", "a suitcase", "a frisbee", "skis", "a snowboard", "a sports ball", "a kite", "a baseball bat", "a baseball glove", "a skateboard", "a surfboard", "a tennis racket", "a bottle", "a wine glass", "a cup", "a fork", "a knife", "a spoon", "a bowl", "a banana", "an apple", "a sandwich", "an orange", "broccoli", "a carrot", "a hot dog", "a pizza", "a doughnut", "a cake", "a chair", "a sofa", "a potted plant", "a bed", "a dining table", "a toilet", "a TV monitor", "a laptop", "a computer mouse", "a remote control", "a keyboard", "a cell phone", "a microwave", "an oven", "a toaster", "a sink", "a refrigerator", "a book", "a clock", "a vase", "a pair of scissors", "a teddy bear", "a hair drier", "a toothbrush");

                    int intConf = (int) (conf * 100);

                    Imgproc.putText(frame,cocoNames.get(idGuy) + " " + intConf + "%",box.tl(),Core.FONT_HERSHEY_SIMPLEX, 2, new Scalar(255,255,0),2);

                    Imgproc.rectangle(frame, box.tl(), box.br(), new Scalar(255, 0, 0), 2);

                    //105033110
                    if (cocoNames.get(idGuy) == "a cat") {
                        Log.i("fb", "((((((((((CAT(((((((((("+ box.x + ", " + box.y + ")))))))))))))");

                        findCat = true;
                        if (box.x > 804) {
                            sendBT("R");
                            samHandler.postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            sendBT("S");
                                        }
                                    }, 100
                            );
                        } else if (box.x < 404) {
                            sendBT("L");
                            samHandler.postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            sendBT("S");
                                        }
                                    }, 100
                            );
                        } else {
                           sendBT("S"); //應該仍要前進？
                        }

                    } else if (cocoNames.get(idGuy) == "a dog") {
                        Log.i("fb", "((((((((((DOG(((((((((("+ box.x + ", " + box.y + ")))))))))))))");

                        findDog = true;
                        if (box.x > 804) {
                            sendBT("L");
                            samHandler.postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            sendBT("S");
                                        }
                                    }, 100
                            );
                        } else if (box.x < 404) {
                            sendBT("R");
                            samHandler.postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            sendBT("S");
                                        }
                                    }, 100
                            );
                        } else {
                            sendBT("S");  //應該仍要前進？
                        }

                    }

                }
            }
        }


/* //part4
        if (startCanny == true) {

            Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGBA2GRAY);
            Imgproc.Canny(frame, frame, 100, 80);
        }
*/

/* //part3
        if (counter % 2 == 0) {

            Core.flip(frame, frame, 1);
            Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGBA2GRAY);

        }

        counter = counter + 1;
*/
        //if(startColor == false){
            return frame;
        //} else {
        //    return dst;
        //}
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

        //105033209
        if(startColor == true){
            frame_hsv = new Mat(width, height, CvType.CV_8UC4);
            blurimg = new Mat(width, height, CvType.CV_8UC4);

            //green
            mat_green = new Mat(width, height, CvType.CV_8UC4);
            edge_green = new Mat(width, height, CvType.CV_8UC4);
//        edge_green_c = new Mat();
//        hovIMG_g = new Mat();

            //blue
            mat_blue = new Mat(width, height, CvType.CV_8UC4);
            mat_blue1 = new Mat(width, height, CvType.CV_8UC4);
            edge_blue = new Mat(width, height, CvType.CV_8UC4);
//        edge_blue_c = new Mat();
//        hovIMG_b = new Mat();

            //red
            mat_red = new Mat(width, height, CvType.CV_8UC4);
            mat_red1 = new Mat(width, height, CvType.CV_8UC4);
            edge_red = new Mat(width, height, CvType.CV_8UC4);
//        edge_red_c = new Mat();
//        hovIMG_r = new Mat();

            hovIMG = new Mat();
            edge_detect = new Mat();
//            approxCurve = new MatOfPoint2f();
        }


        if (startYolo == true){

            String tinyYoloCfg = Environment.getExternalStorageDirectory() + "/dnns/yolov3-tiny.cfg" ;
            String tinyYoloWeights = Environment.getExternalStorageDirectory() + "/dnns/yolov3-tiny.weights";

            tinyYolo = Dnn.readNetFromDarknet(tinyYoloCfg, tinyYoloWeights);

        }

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!OpenCVLoader.initDebug()) {
            Toast.makeText(getApplicationContext(),"There's a problem, yo!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            baseLoaderCallback.onManagerConnected(baseLoaderCallback.SUCCESS);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraBridgeViewBase != null) {
            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraBridgeViewBase != null) {
            cameraBridgeViewBase.disableView();
        }
    }


    //105033110
    public void initGUI() {
        addressBn = (Button)findViewById(R.id.addressBn);
        disconnectBn = (Button)findViewById(R.id.disconnectBn);
        commandBn = (Button)findViewById(R.id.commandBn);
        num1Bn = (Button)findViewById(R.id.num1Bn);
        num2Bn = (Button)findViewById(R.id.num2Bn);
        num3Bn = (Button)findViewById(R.id.num3Bn);
        num4Bn = (Button)findViewById(R.id.num4Bn);
        num5Bn = (Button)findViewById(R.id.num5Bn);
        addressEt = (EditText) findViewById(R.id.addressEt);
        commandEt = (EditText) findViewById(R.id.commandEt);
        messagesTv = (TextView) findViewById(R.id.messagesTv);
        terminalTv = (TextView) findViewById(R.id.terminalTv);

        messagesTv.setText(""); terminalTv.setText("");
        commandEt.setText(""); addressEt.setText(btAddress);
    }

    public void actionController() throws Exception {

        // 建立 Connect Button 的點擊監聽物件
        addressBn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                msgMessage("Bluetooth initialization... \n");
                try {initBluetooth(); } catch(Exception e) {}
            }
        });

        // 建立 DisConnect Button 的點擊監聽物件
        disconnectBn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {closeBluetooth();} catch(Exception e) {};
            }
        });

        // 建立 Send Button 的點擊監聽物件
        commandBn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { sendBT(commandEt.getText().toString());}
        });

        // 建立 Number Button 的點擊監聽物件
        num1Bn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { sendBT("F");}
        });
        num2Bn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { sendBT("B");}
        });

        num3Bn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { sendBT("L"); }
        });

        num4Bn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { sendBT("R"); }
        });

        num5Bn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { sendBT("S"); }
        });

    }

    /*** Bluetooth ***/

    void initBluetooth() throws Exception {
        skipped=0;

        btAddress = addressEt.getText().toString();

        tBlue=new TBlue(btAddress);
        if (tBlue.streaming()) {
            msgMessage("Bluetooth Connected. \n");
        } else {
            msgMessage("Error: Bluetooth connection failed. \n");
            closeBluetooth();
        }
    }

    void closeBluetooth()
    {
        msgMessage("Bluetooth Disconnect... \n");
        tBlue.close();
    }

    void receiveBT() {
        String inString=tBlue.read();
        msgTerminal("->: " + inString + "\n");    //receive signal from Arduino
    }

    void sendBT(String s)
    {
        if (skipped>=10) tBlue.connect();
        if (tBlue.streaming()) {    //send signal to Arduino
            Log.i("fb", "Clear to send, sending... ");
            tBlue.write(s);
            msgTerminal("<-: " + s + "\n");
            skipped=0;
        } else {    //skip
            Log.i("fb", "Not ready, skipping send. in: \""+ s +"\"");
            skipped++;
            msgTerminal(".\n");
            return;
        }
        //new CountDownTimer(1000,1000){
        //    public void onTick(long ms){}
        //    public void onFinish(){receiveBT();}    //receive feedback from Arduino
        //}.start();

    }

    public void msgMessage(String s)
    {
        Log.i("FB", s);
        if (5<=messagesTv.getLineCount()) messagesTv.setText("");
        messagesTv.append(s);
    }

    public void msgTerminal(String s)
    {
        Log.i("FB", s);
        if (16<terminalTv.getLineCount()) terminalTv.setText("");
        terminalTv.append(s);
    }

    //105033209
    //find a cosine of angle between two vectors, formula a dot b = a * b * cosine
    private static double angle(Point pt1, Point pt2, Point pt0) {
        double dx1 = pt1.x - pt0.x;
        double dy1 = pt1.y - pt0.y;
        double dx2 = pt2.x - pt0.x;
        double dy2 = pt2.y - pt0.y;
        return (dx1 * dx2 + dy1 * dy2) / Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2) + 1e-10);
    }

    //show color name and center point to screen
    //control code for bluetooth arduino car is here
    //c = 1 = green
    //c = 2 = blue
    //c = 3 = red
    private void setLabel(Mat im, String label, MatOfPoint contour, int c) {
        int fontface = Core.FONT_HERSHEY_COMPLEX;
        double scale = 1;
        int thickness = 2;
        int[] baseline = new int[1];
        Rect r = Imgproc.boundingRect(contour);
        x_p = r.x + r.width / 2;
        y_p = r.y + r.height / 2;
        double area_pick = Math.abs(r.area());

        //point should not be at the top of the screen, y at the top is smaller
        //point should not be at the side of the screen
        if (y_p > 300 && y_p < 650 && x_p < 1000 && x_p > 300 && area_pick > 40000){
            if(c == 3 && Math.abs(x_pc[2] - x_p) <= 250){
                label = "";
            }else {
                x_pc[c] = x_p;
                y_pc[c] = y_p;
                area[c] = area_pick;
                //label = label + ": " + Double.toString(x_p) + ", " + Double.toString(y_p);
                label = label + ": " + Double.toString(area[c]);
            }

            //find blue rect (c == 2), or red rect (c == 3), or nothing
            //these are the most prior boolean
            if (c == 2){
                scan = false;
                target_get = true;

                //test
                //label = "target get";

            } else if(c == 3){
                obstacle_get = true;
                //scan = false;

                //test
                //label = "obstacle get";
            }

            //find out whether target is on the left or right of the obstacle
            /*if(target_get == true){
                if(obstacle_get == true){
                    if(x_pc[2] >= x_pc[3]){
                        target_right = true;
                        target_left = false;
                    }else {
                        target_left = true;
                        target_right = false;
                    }
                }else {
                    target_left = false;
                    target_right = false;
                }
            }*/
        } else {
            label = "";
        }

        //test
//        label = label + Integer.toString(c);

        Size text = Imgproc.getTextSize(label, fontface, scale, thickness, baseline);
        Point pt = new Point(x_p - text.width / 2, y_p + text.height / 2);
        Imgproc.putText(im, label, pt, fontface, scale, new Scalar(164, 198, 57), thickness);

        //this is where I put and send control code
        //this code is written for handling one target and goal at a time
        //scanning motion

        if(target_get == true){

            //still far from the target
            if(area[2] <= 300000){
                //for blue: goal area
                if (x_pc[2] > 550 && x_pc[2] < 750){
                    //if center of blue rect is in the middle of screen
                    //maybe mace this more accurate, range smaller
                    //go forward
                    sendBT("F");

                } else if(x_pc[2] < 550){
                    //turn right
                    sendBT("R");

                } else if(x_pc[2] > 750){
                    //turn left
                    sendBT("L");

                }
            }else {
                sendBT("S");
            }

        }else if (obstacle_get == true){
            //if too close to obstacle
            if (area[3] > 200000){

                //if center of red rect is in the middle of the screen
                //more to the left
                if (x_pc[3] > 400 && x_pc[3] < 650){
                    //turn right to avoid
                    sendBT("R");


                    //more to the right
                }else if(x_pc[3] >= 650 && x_pc[3] < 900){
                    //turn left to avoid
                    sendBT("L");

                } else{
                    sendBT("F");
                }
            }
        }
        /*
        if(scan == true){

            if(obstacle_get == true){

                //if too close to obstacle
                if (area[3] > 250000){

                    //if center of red rect is in the middle of the screen
                    //more to the left
                    if (x_pc[3] > 450 && x_pc[3] < 650){
                        //turn right to avoid
                        sendBT("R");


                        //more to the right
                    }else if(x_pc[3] >= 650 && x_pc[3] < 850){
                        //turn left to avoid
                        sendBT("L");

                    } else{
                        sendBT("F");
                    }
                }
            }
        } else {

            //for red: avoid area
            if(obstacle_get == true){
                if (x_pc[3] > 550 && x_pc[3] < 750){

                    //if center of red rect is in the middle of the screen
                    if(target_right == true){
                        //blue is on red's right
                        //turn right
                        //go forward
                        sendBT("R");

                        if(area[2] <= 330000) {
                            //for blue: goal area
                            if (x_pc[2] > 550 && x_pc[2] < 750) {
                                //if center of blue rect is in the middle of screen
                                //maybe mace this more accurate, range smaller
                                //go forward
                                sendBT("F");

                            }else {
                                sendBT("S");
                            }
                        }

                    } else if(target_left == true){
                        //turn left
                        //go forward
                        sendBT("L");


                        if(area[2] <= 330000) {
                            //for blue: goal area
                            if (x_pc[2] > 550 && x_pc[2] < 750) {
                                //if center of blue rect is in the middle of screen
                                //maybe mace this more accurate, range smaller
                                //go forward
                                sendBT("F");

                            }
                        }else {
                            sendBT("S");
                        }
                    }
                }
            } else{
                if (target_get == true){

                    //still far from the target
                    if(area[2] <= 330000){
                        //for blue: goal area
                        if (x_pc[2] > 550 && x_pc[2] < 750){
                            //if center of blue rect is in the middle of screen
                            //maybe mace this more accurate, range smaller
                            //go forward
                            sendBT("F");

                        } else if(x_pc[2] < 550){
                            //turn right
                            sendBT("R");

                        } else if(x_pc[2] > 750){
                            //turn left
                            sendBT("L");

                        }
                    }else {
                        sendBT("S");
                    }
                }
            }
        }*/

        target_get = false;
        obstacle_get = false;
        target_right = false;
        target_left = false;
    }

    private View.OnClickListener clickColor = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (startColor == true) {
                startColor = false;
                scancontrol = false;
            } else {
                scancontrol = true;
                startColor = true;
                sendcontrol = true;
                //scan = true;
            }
        }
    };

    public Integer count = 1;
    public void scanning() {
        //Integer count = 1;
        if (count == 1) {
            sendBT("F");
            count++;
        } else if (count == 2) {
            sendBT("F");
            count++;
        }else if (count == 3) {
            sendBT("L");
            count++;
        }else if (count == 4) {
            sendBT("L");
            count++;
        }else if (count == 5) {
            sendBT("L");
            count++;
        }else if (count == 6) {
            sendBT("L");
            count++;
        }else if (count == 7) {
            sendBT("L");
            count++;
        }else if (count == 8) {
            sendBT("L");
            count++;
        }else if (count == 9) {
            sendBT("L");
            count++;
        }else if (count == 10) {
            sendBT("L");
            count++;
        }else if (count == 11) {
            sendBT("L");
            count++;
        }else if (count == 12) {
            sendBT("L");
            count++;
        } else if (count == 13) {
            sendBT("R");
            count++;
        }else if (count == 14) {
            sendBT("R");
            count++;
        } else if (count == 15) {
            sendBT("R");
            count++;
        }else if (count == 16) {
            sendBT("R");
            count++;
        }else if (count == 17) {
            sendBT("R");
            count++;
        }else if (count == 18) {
            sendBT("R");
            count++;
        }else if (count == 19) {
            sendBT("R");
            count++;
        }else if (count == 20) {
            sendBT("R");
            count++;
        }else if (count == 21) {
            sendBT("R");
            count++;
        }else if (count == 22) {
            sendBT("R");
            count++;
        }else if (count == 23) {
            sendBT("F");
            count = 1;
        }else if (count == 24) {
            sendBT("F");
            count = 1;
        };

    }


    public Integer j=1;
    public void scanning2() {
        if (j==1) {
            sendBT("F");
            j++;
        } else if (j==2) {
            sendBT("F");
            j++;
        } else if (j==3) {
            sendBT("L");
            j++;
        } else if (j==4) {
            sendBT("L");
            j++;
        } else if (j==5) {
            sendBT("R");
            j++;
        } else if (j==6) {
            sendBT("R");
            j=1;
        }
    }


}

