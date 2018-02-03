package com.stecon.patipan_on.diarycar;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.database.DatabaseUser;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;
import com.stecon.patipan_on.diarycar.model.PinCodeStatic;

import org.xmlpull.v1.XmlSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.XMLFormatter;

public class PinCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitlePin;
    private TextView tvMessagePin;
    private ImageView imgPinTitle;

    private Button btnPin1,btnPin2,btnPin3,btnPin4,btnPin5,btnPin6,btnPin7,btnPin8,btnPin9,btnPin0;
    private Button btnPinDel;
    private Button btnPinSpace;
    private ImageView imgCode1,imgCode2,imgCode3,imgCode4;

    private int pinSize = 4;
    private int pinCheckCorrect = 0; //4 Correct
    private char pinResult[] = new char[pinSize];
    private char pinTempAraay[] = new char[pinSize];


    private ArrayList<Character> pinTemp = new ArrayList<>();
    private ArrayList<Character> pinInput = new ArrayList<>();
    private ArrayList<ImageView> imagePin = new ArrayList<>();

    private int mode = 0; //0 apply , 1 add , 3 change


    public static final String PIN_NUMBER = "pin_number";
    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;


    private int pin_apply = 0;
    private int pin_add = 1;

    private int pin_change = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);

        myDbHelper = new MyDbHelper(PinCodeActivity.this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        bindWidGet();
        Bundle bundle = getIntent().getExtras();
        onSetMode(bundle);
        imgSetList();

        myTestPinCustomResult();

        buttonOnClick();

        testXml();


    }

    private void testXml() {
        String xmlData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<ArrayOfUser xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://microsoft.com/webservices/\">\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>P00434</LoginName>\n" +
                "        <FirstName>สมโชค</FirstName>\n" +
                "        <LastName>อุทัยทอง</LastName>\n" +
                "        <FirstNameEng>SOMCHOCK</FirstNameEng>\n" +
                "        <LastNameEng>AUTHAITHONG</LastNameEng>\n" +
                "        <DisplayName>นายสมโชค  อุทัยทอง</DisplayName>\n" +
                "        <EmpNo>P00434</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>P00601</LoginName>\n" +
                "        <FirstName>พันธุ์</FirstName>\n" +
                "        <LastName>ไหวดี</LastName>\n" +
                "        <FirstNameEng>PUN</FirstNameEng>\n" +
                "        <LastNameEng>WAIDEE</LastNameEng>\n" +
                "        <DisplayName>นายพันธุ์ ไหวดี</DisplayName>\n" +
                "        <EmpNo>P00601</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>P01008</LoginName>\n" +
                "        <FirstName>ปริญญา</FirstName>\n" +
                "        <LastName>อาจเทศ</LastName>\n" +
                "        <FirstNameEng>PRINYA</FirstNameEng>\n" +
                "        <LastNameEng>ARJTHET</LastNameEng>\n" +
                "        <DisplayName>นายปริญญา อาจเทศ</DisplayName>\n" +
                "        <EmpNo>P01008</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>P01640</LoginName>\n" +
                "        <FirstName>สุพจน์</FirstName>\n" +
                "        <LastName>บำรุง</LastName>\n" +
                "        <FirstNameEng>SUPOCH</FirstNameEng>\n" +
                "        <LastNameEng>BUMRUNG</LastNameEng>\n" +
                "        <DisplayName>นายสุพจน์  บำรุง</DisplayName>\n" +
                "        <EmpNo>P01640</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>P01670</LoginName>\n" +
                "        <FirstName>เกียรติภูมิ</FirstName>\n" +
                "        <LastName>สมใจ</LastName>\n" +
                "        <FirstNameEng>KIATIPHUM</FirstNameEng>\n" +
                "        <LastNameEng>SOMJAI</LastNameEng>\n" +
                "        <DisplayName>นายเกียรติภูมิ  สมใจ</DisplayName>\n" +
                "        <EmpNo>P01670</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>P01717</LoginName>\n" +
                "        <FirstName>ภาสกร</FirstName>\n" +
                "        <LastName>สุวรดี</LastName>\n" +
                "        <FirstNameEng>PASSAKORN</FirstNameEng>\n" +
                "        <LastNameEng>SUWORADEE</LastNameEng>\n" +
                "        <DisplayName>นายภาสกร สุวรดี</DisplayName>\n" +
                "        <EmpNo>P01717</EmpNo>\n" +
                "        <EmailAddress>phassakorn_su@stecon.co.th</EmailAddress>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00400</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>P01718</LoginName>\n" +
                "        <FirstName>วิชัย</FirstName>\n" +
                "        <LastName>บุญกว้าง</LastName>\n" +
                "        <FirstNameEng>WICHAI</FirstNameEng>\n" +
                "        <LastNameEng>BOONKWANG</LastNameEng>\n" +
                "        <DisplayName>นายวิชัย  บุญกว้าง</DisplayName>\n" +
                "        <EmpNo>P01718</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>P01792</LoginName>\n" +
                "        <FirstName>สมชาย</FirstName>\n" +
                "        <LastName>ธนะภาษี</LastName>\n" +
                "        <FirstNameEng>SOMCHAI</FirstNameEng>\n" +
                "        <LastNameEng>THANAPASEE</LastNameEng>\n" +
                "        <DisplayName>นายสมชาย ธนะภาษี</DisplayName>\n" +
                "        <EmpNo>P01792</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>P01814</LoginName>\n" +
                "        <FirstName>สุชาติ</FirstName>\n" +
                "        <LastName>พองาม</LastName>\n" +
                "        <FirstNameEng>Suchart</FirstNameEng>\n" +
                "        <LastNameEng>Porngam</LastNameEng>\n" +
                "        <DisplayName>นายสุชาติ พองาม</DisplayName>\n" +
                "        <EmpNo>P01814</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>P01848</LoginName>\n" +
                "        <FirstName>สพชัย</FirstName>\n" +
                "        <LastName>ขยันหา</LastName>\n" +
                "        <FirstNameEng>SUPCHAI</FirstNameEng>\n" +
                "        <LastNameEng>KAYUNHA</LastNameEng>\n" +
                "        <DisplayName>นายสพชัย  ขยันหา</DisplayName>\n" +
                "        <EmpNo>P01848</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>P02169</LoginName>\n" +
                "        <FirstName>สุชิน</FirstName>\n" +
                "        <LastName>ฉิมเกิด</LastName>\n" +
                "        <FirstNameEng>CHUSHIN</FirstNameEng>\n" +
                "        <LastNameEng>CHIMKERD</LastNameEng>\n" +
                "        <DisplayName>นายสุชิน ฉิมเกิด</DisplayName>\n" +
                "        <EmpNo>P02169</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00430</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>P02227</LoginName>\n" +
                "        <FirstName>ปณโชติ์</FirstName>\n" +
                "        <LastName>ปิโย</LastName>\n" +
                "        <FirstNameEng>PANACHOT</FirstNameEng>\n" +
                "        <LastNameEng>PIYO</LastNameEng>\n" +
                "        <DisplayName>นายปณโชติ์  ปิโย</DisplayName>\n" +
                "        <EmpNo>P02227</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>P03160</LoginName>\n" +
                "        <FirstName>สมหมาย</FirstName>\n" +
                "        <LastName>บุตรดี</LastName>\n" +
                "        <FirstNameEng>SOMMAI</FirstNameEng>\n" +
                "        <LastNameEng>BOOTRDEE</LastNameEng>\n" +
                "        <DisplayName>นายสมหมาย บุตรดี</DisplayName>\n" +
                "        <EmpNo>P03160</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>P03315</LoginName>\n" +
                "        <FirstName>เอกรินทร์</FirstName>\n" +
                "        <LastName>ศิริวราศิลป์</LastName>\n" +
                "        <FirstNameEng>EAKARIN</FirstNameEng>\n" +
                "        <LastNameEng>SIRIWARASIN</LastNameEng>\n" +
                "        <DisplayName>นายเอกรินทร์ ศิริวราศิลป์</DisplayName>\n" +
                "        <EmpNo>P03315</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>T08013</LoginName>\n" +
                "        <FirstName>สง่า</FirstName>\n" +
                "        <LastName>พรมบ้านสังข์</LastName>\n" +
                "        <FirstNameEng>SANGA</FirstNameEng>\n" +
                "        <LastNameEng>POMBANSUNG</LastNameEng>\n" +
                "        <DisplayName>นายสง่า พรมบ้านสังข์</DisplayName>\n" +
                "        <EmpNo>T08013</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>T08718</LoginName>\n" +
                "        <FirstName>วิโรจน์</FirstName>\n" +
                "        <LastName>หาสูงเนิน</LastName>\n" +
                "        <FirstNameEng>VIROJ</FirstNameEng>\n" +
                "        <LastNameEng>HARSUNGNOEN</LastNameEng>\n" +
                "        <DisplayName>นายวิโรจน์ หาสูงเนิน</DisplayName>\n" +
                "        <EmpNo>T08718</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>T08731</LoginName>\n" +
                "        <FirstName>วิทยา</FirstName>\n" +
                "        <LastName>ละมูล</LastName>\n" +
                "        <FirstNameEng>WITTHAYA</FirstNameEng>\n" +
                "        <LastNameEng>LAMOOL</LastNameEng>\n" +
                "        <DisplayName>นายวิทยา ละมูล</DisplayName>\n" +
                "        <EmpNo>T08731</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>0037</JobCode>\n" +
                "        <JobThai>ขับรถยนต์</JobThai>\n" +
                "        <JobEng>ขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00195</LoginName>\n" +
                "        <FirstName>เลิศพร</FirstName>\n" +
                "        <LastName>ไทธะนุ</LastName>\n" +
                "        <FirstNameEng>LOETPORN</FirstNameEng>\n" +
                "        <LastNameEng>THAITHANU</LastNameEng>\n" +
                "        <DisplayName>นายเลิศพร ไทธะนุ</DisplayName>\n" +
                "        <EmpNo>W00195</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>25080</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00198</LoginName>\n" +
                "        <FirstName>ธนบูลย์</FirstName>\n" +
                "        <LastName>นุ่มเนียม</LastName>\n" +
                "        <FirstNameEng>TANABUL</FirstNameEng>\n" +
                "        <LastNameEng>NUMNEAM</LastNameEng>\n" +
                "        <DisplayName>นายธนบูลย์ นุ่มเนียม</DisplayName>\n" +
                "        <EmpNo>W00198</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00202</LoginName>\n" +
                "        <FirstName>ชัชวาล</FirstName>\n" +
                "        <LastName>ขจรล่า</LastName>\n" +
                "        <FirstNameEng>CHATCHAWAN</FirstNameEng>\n" +
                "        <LastNameEng>KACHONLE</LastNameEng>\n" +
                "        <DisplayName>นายชัชวาล ขจรล่า</DisplayName>\n" +
                "        <EmpNo>W00202</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>25080</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00203</LoginName>\n" +
                "        <FirstName>สันต์</FirstName>\n" +
                "        <LastName>บุญเข็ม</LastName>\n" +
                "        <FirstNameEng>SAN</FirstNameEng>\n" +
                "        <LastNameEng>BUNKEM</LastNameEng>\n" +
                "        <DisplayName>นายสันต์ บุญเข็ม</DisplayName>\n" +
                "        <EmpNo>W00203</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00239</LoginName>\n" +
                "        <FirstName>ประเสริฐ</FirstName>\n" +
                "        <LastName>แก่นสุวรรณ</LastName>\n" +
                "        <FirstNameEng>PRASERT</FirstNameEng>\n" +
                "        <LastNameEng>KAENSUWAN</LastNameEng>\n" +
                "        <DisplayName>นายประเสริฐ แก่นสุวรรณ</DisplayName>\n" +
                "        <EmpNo>W00239</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00250</LoginName>\n" +
                "        <FirstName>ธนิตย์</FirstName>\n" +
                "        <LastName>หฤทัยสิริรัตน์</LastName>\n" +
                "        <FirstNameEng>THANIT</FirstNameEng>\n" +
                "        <LastNameEng>HARUETHAISIRIRAT</LastNameEng>\n" +
                "        <DisplayName>นายธนิตย์ หฤทัยสิริรัตน์</DisplayName>\n" +
                "        <EmpNo>W00250</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00293</LoginName>\n" +
                "        <FirstName>กุศล</FirstName>\n" +
                "        <LastName>โพธิ์สมร</LastName>\n" +
                "        <FirstNameEng>KUSON</FirstNameEng>\n" +
                "        <LastNameEng>PHOSAMON</LastNameEng>\n" +
                "        <DisplayName>นายกุศล โพธิ์สมร</DisplayName>\n" +
                "        <EmpNo>W00293</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00294</LoginName>\n" +
                "        <FirstName>รังสรรณ์</FirstName>\n" +
                "        <LastName>คุ้มถนอม</LastName>\n" +
                "        <FirstNameEng>RANGSUN</FirstNameEng>\n" +
                "        <LastNameEng>KOOMTANOM</LastNameEng>\n" +
                "        <DisplayName>นายรังสรรณ์ คุ้มถนอม</DisplayName>\n" +
                "        <EmpNo>W00294</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00296</LoginName>\n" +
                "        <FirstName>กิจกาล</FirstName>\n" +
                "        <LastName>สมมาศ</LastName>\n" +
                "        <FirstNameEng>KITJAKAN</FirstNameEng>\n" +
                "        <LastNameEng>SOMMAS</LastNameEng>\n" +
                "        <DisplayName>นายกิจกาล สมมาศ</DisplayName>\n" +
                "        <EmpNo>W00296</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00299</LoginName>\n" +
                "        <FirstName>เกรียงศักดิ์</FirstName>\n" +
                "        <LastName>ตังยะรัตน์</LastName>\n" +
                "        <FirstNameEng>KREANGSAK</FirstNameEng>\n" +
                "        <LastNameEng>TANGYARAT</LastNameEng>\n" +
                "        <DisplayName>นายเกรียงศักดิ์ ตังยะรัตน์</DisplayName>\n" +
                "        <EmpNo>W00299</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00302</LoginName>\n" +
                "        <FirstName>พนา</FirstName>\n" +
                "        <LastName>เหล่าอัน</LastName>\n" +
                "        <FirstNameEng>PHANA</FirstNameEng>\n" +
                "        <LastNameEng>LAO-AN</LastNameEng>\n" +
                "        <DisplayName>นายพนา เหล่าอัน</DisplayName>\n" +
                "        <EmpNo>W00302</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00305</LoginName>\n" +
                "        <FirstName>จักรพงษ์</FirstName>\n" +
                "        <LastName>วงศ์กิติพรชัย</LastName>\n" +
                "        <FirstNameEng>JAKAPONG</FirstNameEng>\n" +
                "        <LastNameEng>WONGKITIPORNCHAI</LastNameEng>\n" +
                "        <DisplayName>นายจักรพงษ์ วงศ์กิติพรชัย</DisplayName>\n" +
                "        <EmpNo>W00305</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00308</LoginName>\n" +
                "        <FirstName>สราวุฒิ</FirstName>\n" +
                "        <LastName>ชีวินวิวัฒน์</LastName>\n" +
                "        <FirstNameEng>SARAWOOT</FirstNameEng>\n" +
                "        <LastNameEng>CHEWINWIWAT</LastNameEng>\n" +
                "        <DisplayName>นายสราวุฒิ ชีวินวิวัฒน์</DisplayName>\n" +
                "        <EmpNo>W00308</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00309</LoginName>\n" +
                "        <FirstName>ประจักษ์</FirstName>\n" +
                "        <LastName>ศรีนรจันทร์</LastName>\n" +
                "        <FirstNameEng>PACHAK</FirstNameEng>\n" +
                "        <LastNameEng>SEENORACHAN</LastNameEng>\n" +
                "        <DisplayName>นายประจักษ์ ศรีนรจันทร์</DisplayName>\n" +
                "        <EmpNo>W00309</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00311</LoginName>\n" +
                "        <FirstName>สนธยา</FirstName>\n" +
                "        <LastName>ภูทอง</LastName>\n" +
                "        <FirstNameEng>SONTHAYA</FirstNameEng>\n" +
                "        <LastNameEng>PHUTHONG</LastNameEng>\n" +
                "        <DisplayName>นายสนธยา ภูทอง</DisplayName>\n" +
                "        <EmpNo>W00311</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00312</LoginName>\n" +
                "        <FirstName>เจตนา</FirstName>\n" +
                "        <LastName>ดวนใหญ่</LastName>\n" +
                "        <FirstNameEng>JETTANA</FirstNameEng>\n" +
                "        <LastNameEng>DUANYAI</LastNameEng>\n" +
                "        <DisplayName>นายเจตนา ดวนใหญ่</DisplayName>\n" +
                "        <EmpNo>W00312</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00313</LoginName>\n" +
                "        <FirstName>ศุภรักษ์</FirstName>\n" +
                "        <LastName>ส.วรเนตร</LastName>\n" +
                "        <FirstNameEng>SUPARUK</FirstNameEng>\n" +
                "        <LastNameEng>S.VORANATE</LastNameEng>\n" +
                "        <DisplayName>นายศุภรักษ์ ส.วรเนตร</DisplayName>\n" +
                "        <EmpNo>W00313</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "    <User>\n" +
                "        <UserId>0</UserId>\n" +
                "        <LoginName>W00314</LoginName>\n" +
                "        <FirstName>สุรสิทธิ์</FirstName>\n" +
                "        <LastName>สมพงษ์</LastName>\n" +
                "        <FirstNameEng>SURASIT</FirstNameEng>\n" +
                "        <LastNameEng>SOMPONG</LastNameEng>\n" +
                "        <DisplayName>นายสุรสิทธิ์ สมพงษ์</DisplayName>\n" +
                "        <EmpNo>W00314</EmpNo>\n" +
                "        <Department>063</Department>\n" +
                "        <JobNo>00000</JobNo>\n" +
                "        <AuthenType>2</AuthenType>\n" +
                "        <PositionCode>5520</PositionCode>\n" +
                "        <PositionThai>พนักงานขับรถยนต์</PositionThai>\n" +
                "        <JobCode>5520</JobCode>\n" +
                "        <JobThai>พนักงานขับรถยนต์</JobThai>\n" +
                "        <JobEng>พนักงานขับรถยนต์</JobEng>\n" +
                "    </User>\n" +
                "</ArrayOfUser>";


    }

    private void onSetMode(Bundle bundle) {
//        if (bundle != null) {
//            mode = bundle.getInt(PIN_NUMBER);
//        }
//        mode = pin_apply;
        if (mode == pin_add) {
            tvMessagePin.setText(getResources().getString(R.string.txt_pin_add_input));
        } else if (mode == pin_change) {
            tvMessagePin.setText(getResources().getString(R.string.txt_pin_new));
        } else if (mode == pin_apply) {
            tvMessagePin.setText(getResources().getString(R.string.txt_pin_input));
        }
    }

    private void myTestPinCustomResult() {
        PinCodeStatic.setPinNumber("2010");
        String temp = PinCodeStatic.getPinNumber();
        if (temp != null) {
            if (temp.length() == 4) {
                for (int i = 0 ; i < temp.length(); i++) {
                    pinResult[i] = temp.charAt(i);
                }
            }
            Log.d("pin result test = > ", Arrays.toString(pinResult));
        } else { // value is PinCodeStatic is Not value

            Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
        }
    }

    private void imgSetList() {
        imagePin.add(imgCode1);
        imagePin.add(imgCode2);
        imagePin.add(imgCode3);
        imagePin.add(imgCode4);
    }

    private void buttonOnClick() {
        btnPin0.setOnClickListener(this);
        btnPin1.setOnClickListener(this);
        btnPin2.setOnClickListener(this);
        btnPin3.setOnClickListener(this);
        btnPin4.setOnClickListener(this);
        btnPin5.setOnClickListener(this);
        btnPin6.setOnClickListener(this);
        btnPin7.setOnClickListener(this);
        btnPin8.setOnClickListener(this);
        btnPin9.setOnClickListener(this);

        btnPinDel.setOnClickListener(this);
        btnPinSpace.setOnClickListener(this);


    }

    private void bindWidGet() {
        btnPin0 = (Button) findViewById(R.id.btnPin0);
        btnPin1 = (Button) findViewById(R.id.btnPin1);
        btnPin2 = (Button) findViewById(R.id.btnPin2);
        btnPin3 = (Button) findViewById(R.id.btnPin3);
        btnPin4 = (Button) findViewById(R.id.btnPin4);
        btnPin5 = (Button) findViewById(R.id.btnPin5);
        btnPin6 = (Button) findViewById(R.id.btnPin6);
        btnPin7 = (Button) findViewById(R.id.btnPin7);
        btnPin8 = (Button) findViewById(R.id.btnPin8);
        btnPin9 = (Button) findViewById(R.id.btnPin9);

        btnPinDel = (Button) findViewById(R.id.btnPinDel);
        btnPinSpace = (Button) findViewById(R.id.btnPinSpace);
        imgCode1 = (ImageView) findViewById(R.id.imgCode1);
        imgCode2 = (ImageView) findViewById(R.id.imgCode2);
        imgCode3 = (ImageView) findViewById(R.id.imgCode3);
        imgCode4 = (ImageView) findViewById(R.id.imgCode4);

        tvTitlePin = (TextView) findViewById(R.id.txtPinTitle);
        imgPinTitle = (ImageView) findViewById(R.id.imgPinTitle);
        tvMessagePin = (TextView) findViewById(R.id.tvMessagePin);
    }

    @Override
    public void onClick(View v) {
        if (v == btnPin1) {
            processSetPin('1');
        } else if (v == btnPin2) {
            processSetPin('2');
        } else if (v == btnPin3) {
            processSetPin('3');
        } else if (v == btnPin4) {
            processSetPin('4');
        } else if (v == btnPin5) {
            processSetPin('5');
        } else if (v == btnPin6) {
            processSetPin('6');
        } else if (v == btnPin7) {
            processSetPin('7');
        } else if (v == btnPin8) {
            processSetPin('8');
        } else if (v == btnPin9) {
            processSetPin('9');
        } else if (v == btnPin0) {
            processSetPin('0');
        } else if (v == btnPinDel) {
            processDeletePin();
        } else if (v == btnPinSpace) {
            processClearPin();
        }

        if (pinInput.size() == pinSize) {
            processCheckPinCode();
        }
    }

    private void processCheckPinCode() {

        if (mode == pin_add || mode == pin_change) { // 1 add , 3 change
            if (pinTemp.size() == 0) {
                onSetTemp();
                tvMessagePin.setText(getResources().getString(R.string.txt_pin_confirm));
            }else{
                pinConfirmMode();
            }
        } else if (mode == pin_apply) { // 0
            pinApply();
        }

        processClearPin();
        pinCheckCorrect = 0;

    }

    private void onSetTemp() {
        pinTemp = new ArrayList<>(pinInput);
//        pinTemp.add(pinInput.get(0));
//        pinTemp.add(pinInput.get(1));
//        pinTemp.add(pinInput.get(2));
//        pinTemp.add(pinInput.get(3));
    }

    private void pinApply() {
        for(int i = 0 ; i < pinInput.size() ; i++) {
            if (pinInput.get(i).equals(pinResult[i])) {
                pinCheckCorrect++;
            }
        }
        pinCheckResult();
    }

    private void pinCheckResult() {

        if (pinCheckCorrect == pinSize) {
            tvMessagePin.setText(getResources().getString(R.string.txt_pin_correct));

            if (mode == pin_add) {
                onInsertPinData();
            } else if (mode == pin_change) {
                onEditPinData();
            }
        } else {
            tvMessagePin.setText(getResources().getString(R.string.txt_pin_error));
            //Toast.makeText(this, "Not Correct", Toast.LENGTH_SHORT).show();
        }

    }

    private void onEditPinData() {
        Toast.makeText(this, "Process Edit ", Toast.LENGTH_SHORT).show();
        //Edit Data
    }

    private void onInsertPinData() {
        Toast.makeText(this, "Process Insert ", Toast.LENGTH_SHORT).show();
        //Insert Data
    }


    private void pinConfirmMode() {
        for(int i = 0 ; i < pinInput.size() ; i++) {
            if (pinInput.get(i).equals(pinTemp.get(i))) {
                pinCheckCorrect++;

                Log.d("pinInput => ", pinInput.get(i) + " ");
                Log.d("pinTemp => ", pinTemp.get(i) + "");
            }
        }
        Toast.makeText(this, "pinCheckCorrect : " + pinCheckCorrect, Toast.LENGTH_SHORT).show();
        pinCheckResult();
    }

    private void cusGetPinFromDatabase() {
//        String strSql = "SELECT * FROM  " + DatabaseUser.TABLE_NAME + " WHERE" ;
//        Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);
        SharedPreferences sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        String temp = sharedPreferences.getString(MyAppConfig.pin_code, "");

    }

    private void processDeletePin() {

        if (pinInput.size() > 0) {
            pinInput.remove(pinInput.size() - 1);
            imagePin.get(pinInput.size()).setImageResource(R.drawable.custom_circle_pin);
            Log.d("pinInput => ", pinInput + " ");
            Log.d("pinSize => ", pinInput.size() + "");
        }
    }

    private void processSetPin(Character pinCode) {

        if (pinInput.size() < pinSize) {
            pinInput.add(pinCode);
            imagePin.get(pinInput.size() - 1).setImageResource(R.drawable.custom_circle_pin2);
            Log.d("pinInput => ", pinInput + " ");
            Log.d("pinSize => ", pinInput.size() + "");
        }
    }

    private void processClearPin() {

        if (pinInput.size() != 0) {
            for (int i = 0; i < pinInput.size(); i++) {
                imagePin.get(i).setImageResource(R.drawable.custom_circle_pin);
            }
        }
        pinInput.clear();
        Log.d("pinInput => ", pinInput + " ");
        Log.d("pinSize => ", pinInput.size() + "");
    }


}
