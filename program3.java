/*
Write a program to store attendance record of 'n' students. Accept rollno,name,class,subject
and total number of attended lectures for n students. Declare Total_conducted as a constant.
Calculate percentage attendance and display the marks of all students out of 10 based on
following conditions.

Table : attendance(rollno,name,class,subject,total attended)

_________________________________________________
|percentage Attendance  |    Marks out of 10    |
|90%=100%               | 10                    |
|80%-89.99%             | 9                     |
|Upto                                           |
|1%-9.99%               | 1                     |
|_______________________________________________|__
 */
import java.io.*;
import java.sql.*;
import java.lang.Math;

class program3
{
  static Connection conn=null;
  static Statement stmt=null;
  static ResultSet rs=null;

 public static void main(String args[])throws Exception
 {  
     BufferedReader bobj = new BufferedReader(new InputStreamReader(System.in));
     try
     {
      Class.forName("org.postgresql.Driver");

      conn=DriverManager.getConnection("jdbc:postgresql://192.168.16.1/ty8844","ty8844","");
      stmt=conn.createStatement();

      if(conn==null)
      {
        System.out.println("connection failed");
      }
      else
      {
       System.out.println("connection successful..");
      }
      int n,i=0;
      System.out.println("Ente the Number of student : ");
      n=Integer.parseInt(bobj.readLine());
      
      for(i=0;i<n;i++)
      { 
       Accept();  
      }
      for(i=0;i<n;i++)
      {
        Marks();
      }
     }
     catch(Exception e)
     {
     System.out.println(e);
     }
 }

 public static void Accept()throws Exception
 {
  BufferedReader bobj = new BufferedReader(new InputStreamReader(System.in));
  int roll_no,total;
  String name,subject,class_,sql_query,table_name="attendance";
  System.out.println("enter the name : ");
  name=bobj.readLine();
  System.out.println("enter the roll no : ");
  roll_no=Integer.parseInt(bobj.readLine());
  System.out.println("enter the total number of attended lectures : ");
  total=Integer.parseInt(bobj.readLine());
  System.out.println("enter the subject : ");
  subject=bobj.readLine();
  System.out.println("enter the class name : ");
  class_=bobj.readLine();
  
  sql_query=" INSERT INTO "+table_name+" values(" +roll_no+",'"+name+"','"+subject+"',"+total+");";

  int p=stmt.executeUpdate(sql_query);
  if(p==1)
  {
   System.out.println("Data Added");
  }
 }

 public static void Marks()
 {
  int total_con=100;
  float per;
  String sql_query="select name,total_att from attendance;";
  try{
   rs=stmt.executeQuery(sql_query);
   System.out.println("name\tmarks\n");
   while(rs.next())
   {
    per=rs.getInt("total_att");
    per=per/100;
    per=per*100;
    if(per >=90 && per <= 100)
    {
     per=10;
    }
    else if(per >=80 && per <= 89.99)
    {
     per=9;
    }
    else if(per >=70 && per <= 79.99)
    {
     per=8;
    }
     else if(per >=60 && per <= 69.99)
    {
     per=7;
    }
     else if(per >=50 && per <= 59.99)
    {
     per=6;
    }
     else if(per >=40 && per <= 49.99)
    {
     per=5;
    }
     else if(per >=30 && per <= 39.99)
    {
     per=4;
    }
     else if(per >=20 && per <= 29.99)
    {
     per=3;
    } 
     else if(per >=10 && per <= 19.99)
    {
     per=2;
    }
    else if(per >=1 && per <= 9.99)
    {
     per=9;
    }

    System.out.println(rs.getString("name")+"      "+per);
   }
  }
  catch(Exception e)
  {
   System.out.println(e);
  }
 }
}
