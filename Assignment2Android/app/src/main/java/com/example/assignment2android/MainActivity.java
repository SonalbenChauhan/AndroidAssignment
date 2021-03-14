
package com.example.assignment2android;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Create Instance
    DatabaseHelper MyDataBase;

    EditText Id, FirstName, LastName, Marks;
    Button Add;
    RadioButton RB1, RB2,RB3;


    Button  ViewByData, SearchByID, SearchByProgCode, Delete, Update;
    ListView ListViewCourses;
    RadioGroup Credits;
    ArrayAdapter<String> adapter;
    private String[] CourseSList = { "PROG 8110", "PROG 8215", "PROG 1815" };
    String Prog ="" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create New instance

        MyDataBase = new DatabaseHelper(this); //this constroctor will call and create table becacuse in main class we have mentioned to create table in arguments
        Id =(EditText) findViewById(R.id.txtId);
        FirstName = (EditText) findViewById(R.id.txtName);
        LastName = (EditText) findViewById(R.id.txtLast);
        Marks = (EditText) findViewById(R.id.txtMarks);
        Add = (Button) findViewById(R.id.buttonAdd);
        ViewByData = (Button) findViewById(R.id.buttonViewData);
        Update =(Button) findViewById(R.id.buttonUpdate);
        Delete =(Button) findViewById(R.id.buttonDelete);
        ListViewCourses = (ListView) findViewById(R.id.LVCourses);
        Credits=(RadioGroup) findViewById(R.id.CreditsRadioGroup);
        RB1 = (RadioButton) findViewById(R.id.radio2);
        RB2 = (RadioButton) findViewById(R.id.radio3);
        RB3 = (RadioButton) findViewById(R.id.radio4);
        SearchByID=(Button)findViewById(R.id.buttonSearchByID);
        SearchByProgCode=(Button)findViewById(R.id.buttonSearchByProgCode);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, CourseSList);

        ListViewCourses.setAdapter(adapter);
        // ListView on item selected listener.
        ListViewCourses.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this,"Selected  "+ListViewCourses.getItemAtPosition(position),Toast.LENGTH_LONG).show();
                // TODO Auto-generated method stub
                Prog = CourseSList[position];
                for (int i = 0; i < ListViewCourses.getChildCount(); i++) {
                    if(position == i ){
                        ListViewCourses.getChildAt(i).setBackgroundColor(Color.BLUE);
                    }else{
                        ListViewCourses.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
                // Toast.makeText(MainActivity.this, courseList[position], Toast.LENGTH_SHORT).show();
            }
        });

        ListViewCourses.setAdapter(adapter);
        add(); //This method will call Add method and will add data.
        ViewbyData();//This method will call ViewbyData method and will Show data.
        UpdateData();//This method will call UpdateData method and will Update data.
        DeleteInfo();//This method will call Deleteinformation method and will Delete data.
    }

    public void add() {
        Add.setOnClickListener(
                new View.OnClickListener() {
                    String firstName = FirstName.getText().toString();
                    String lastName = LastName.getText().toString();
                    String marks = Marks.getText().toString();
                    String credit = GetCreditScore();

                    @Override
                    public void onClick(View v) {
                        boolean isAdded = MyDataBase.InsertData(FirstName.getText().toString(), LastName.getText().toString(), Marks.getText().toString(),Prog,"1");
                        if (isAdded == true)
                            Toast.makeText(MainActivity.this, "Data added", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not added", Toast.LENGTH_LONG).show();


                    }
                }
        );
    }

    public void ViewbyData() {


        ViewByData.setOnClickListener(

                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Cursor Ans = MyDataBase.getAlldata();
                        //if there is no data then if statment will show

                        if (Ans.getCount() == 0) {

                            ShowMessage("Exception", "Add some data there is no data");
                            return;

                        }

                        StringBuffer buffer = new StringBuffer();
                        while (Ans.moveToNext()) {
                            //it will append data
                            buffer.append("Id:" + Ans.getString(0) + "\n");
                            buffer.append("First Name:" + Ans.getString(1) + "\n");
                            buffer.append("Last Name:" + Ans.getString(2) + "\n");
                            buffer.append("Marks:" + Ans.getString(3) + "\n");
                            buffer.append("Course:" + Ans.getString(4) + "\n");
                            buffer.append("Credit:" + Ans.getString(5) + "\n");


                        }
                        ShowMessage("database", buffer.toString());
                    }
                }
        );
    }




    public void ShowMessage(String title, String message) {
        //create new instance so using that we can create alert dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        //show method will show our dialog
        AlertDialog show = builder.show();


    }
    public void UpdateData(){
        Update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //isUpdate is true then it will update data
                         boolean isUpdate = MyDataBase.updateData(Id.getText().toString(),FirstName.getText().toString(),LastName.getText().toString(),Marks.getText().toString(),Prog.toString(),Credits.toString());
                         if(isUpdate== true)
                          Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        else
                             Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    public void DeleteInfo(){
        Delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer DestoryedRows = MyDataBase.DeleteInformation(Id.getText().toString());
                        if(DestoryedRows >0)
                            Toast.makeText(MainActivity.this, "Information Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Information Not Deleted", Toast.LENGTH_LONG).show();


                    }
                }
        );
        SearchByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = Id.getText().toString();
                if (id.equals("")) {
                    Toast.makeText(MainActivity.this, "write Id to find data of that ID", Toast.LENGTH_SHORT).show();
                    return;
                }


                Cursor data = MyDataBase.showTableDataByID(id);

                if (data.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No data", Toast.LENGTH_SHORT).show();
                    return;
                }
                // setting control values using the result
                while (data.moveToNext()) {
                    FirstName.setText(data.getString(1));
                    LastName.setText(data.getString(2));
                    Marks.setText(data.getString(3));
                    switch(data.getString(4)) {
                        case "2":
                            RB1.setChecked(true);
                            break;
                        case "3":
                            RB2.setChecked(true);
                            break;
                        case "4":
                            RB3.setChecked(true);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        SearchByProgCode = (Button) findViewById(R.id.buttonSearchByProgCode);
        SearchByProgCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Prog.equals("")) {
                    Toast.makeText(MainActivity.this, "Choose one Course", Toast.LENGTH_SHORT).show();
                    return;
                }
                // getting all data using showTableDataByCode using Course code in DatabaseHelper function
                Cursor data = MyDataBase.showTableDataByCode(Prog);

                if (data.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                    return;
                }

                // adding the data information to string buffer
                StringBuffer buffer = new StringBuffer();
                buffer.append("The students in course : "+ Prog+"\n");
                while (data.moveToNext()) {
                    buffer.append("ID: " + data.getString(0) + "\n");
                    buffer.append("FirstName: " + data.getString(1) + "\n");
                    buffer.append("LastName: " + data.getString(2) + "\n");
                    buffer.append("Course: " + data.getString(4) + "\n");
                    buffer.append("Credits: " + data.getString(5) + "\n");
                    buffer.append("Marks: " + data.getString(3) + "\n");
                }


                // displaying data using toast.
                Toast.makeText(MainActivity.this, buffer.toString(), Toast.LENGTH_SHORT).show();
            }});
    }


    public String GetCreditScore() {

        String credit = null;
        if(RB1.isChecked()){
            credit = "2";
        } else if(RB2.isChecked()){
            credit = "3";
        } else if(RB3.isChecked()){
            credit = "4";
        } else {
            Toast.makeText(getApplicationContext(), "Please select a credit", Toast.LENGTH_SHORT).show();
        }
        return credit;
    }

}

