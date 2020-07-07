package com.example.android.studentdatabasesqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editTextId,editName,editEmail,editCC;
    Button btnAdd,btnGetData,btnUpdate,btnDelete,btnViewall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb=new DatabaseHelper(this);

        editTextId = findViewById(R.id.editText_id);
        editName = findViewById(R.id.editText_name);
        editEmail = findViewById(R.id.editText_email);
        editCC = findViewById(R.id.editText_CC);

        btnAdd=findViewById(R.id.button_add);
        btnGetData=findViewById(R.id.button_view);
        btnUpdate=findViewById(R.id.button_update);
        btnDelete=findViewById(R.id.button_delete);
        btnViewall=findViewById(R.id.button_viewAll);

        addData();
        getData();
        getAllData();
        updateData();
        deleteData();

    }

    public void addData(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name= editName.getText().toString();
                String email=editEmail.getText().toString();
                String cc=editCC.getText().toString();

                boolean isInserted = myDb.insertData(name,email,cc);
                if(isInserted == true){
                    Toast.makeText(MainActivity.this,"Data inserted",Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(MainActivity.this,"Not inserted",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getData(){
        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=editTextId.getText().toString();
                if(id.equals(String.valueOf(""))){
                    editTextId.setError("Enter Id");
                    return;
                }
                Cursor cursor=myDb.getData(id);
                String data=null;

                if(cursor.moveToNext()){
                    data="ID: "+cursor.getString(0)+"\n" +
                            "Name: "+cursor.getString(1)+"\n" +
                            "Email: "+cursor.getString(2)+"\n" +
                            "Course Count: "+cursor.getString(3);
                }
                showMessage("Data",data);
            }
        });
    }

    public void getAllData(){
        btnViewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer buffer = new StringBuffer();
                Cursor cursor = myDb.getAllData();
                if (cursor.getCount() == 0) {
                    showMessage("Error", "No Data in Database");
                }
                while (cursor.moveToNext()) {
                    buffer.append("ID: "+cursor.getString(0)+"\n");
                    buffer.append("Name: "+cursor.getString(1)+"\n");
                    buffer.append("Email: "+cursor.getString(2)+"\n");
                    buffer.append("Course Count: "+cursor.getString(3)+"\n\n");
                }
                showMessage("All Data",buffer.toString());
            }
        });
    }

    public void updateData(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate=myDb.updateData(editTextId.getText().toString(),
                        editName.getText().toString(),
                        editEmail.getText().toString(),
                        editCC.getText().toString());

                if(isUpdate==true){
                    Toast.makeText(MainActivity.this,"Successfully Updated",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Not Updated",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=editTextId.getText().toString();
                Integer deleted=myDb.deleteData(id);
                if(id.equals(String.valueOf(""))){
                    editTextId.setError("Enter Id");
                }
                if(deleted>0){
                    Toast.makeText(MainActivity.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Nothing is Deleted",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showMessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
