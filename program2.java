/*
Write a menu driven program to create following tables

Teacher(Teacher_id,Teacher_name)
Workload(subject_code,subject,date,time,class).
Relationship between Teacher table and Workload table is-One:Many
And perform the following operations.

a.Add Record
b.Display date wise lecture details for a specific teacher (accept the teacher name)
c.Display class wise time table (accept class name)
d.Modify Record of teacher(only date,time and class).
 
 */
import java.io.*;
import java.util.*;
import java.sql.*;
class program2{
	public static void main(String args[]) throws  ClassNotFoundException, SQLException{
		Scanner sc = new Scanner(System.in);
		int choice; 
		char opt='y';
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://192.168.16.1/ty8859", "ty8859", "");
			if(conn==null){
				System.out.println(" Connection failed !!!");
			}
			else{
				System.out.println("Connection successful!!!");
				st=conn.createStatement();
				st.execute("drop table workload");
				st.execute("drop table Teacher");
				st.execute("create table Teacher(Teacher_id int primary key, Teacher_name varchar(20))");
				st.execute("create table workload(subject_code int primary key, subject varchar(20), Day varchar(10), Time varchar(10) , class_ varchar(20), Teacher_id int references Teacher(Teacher_id) on delete cascade)");
				while(opt=='y'){
					System.out.println("--------------------MENU--------------------");
					System.out.println(" 1. Add record ");
					System.out.println(" 2. Display date wise lecture details of a teacher ");
					System.out.println(" 3. Display class wise timetable ");
					System.out.println(" 4. Modify record of a teacher ");
					System.out.println(" 5. EXIT ");
					System.out.println(" Enter your choice  : " );
					choice = sc.nextInt();
					switch(choice){
						case 1:
							System.out.println("\n\n -------------ADD NEW RECORD------------");
							System.out.println(" Select the table to which record is to be added :  ");
							System.out.println(" 1. TEACHER " );
							System.out.println(" 2. WORKLOAD " );
							int _c;
							System.out.println("Enter your choice :");
							_c=sc.nextInt();
							switch(_c){
								case 1 :
									System.out.println(" Enter the teacher ID :");
									int t_id;
									t_id=sc.nextInt();
									System.out.println(" Enter the teacher name : ");
									String name = sc.next();
									st.execute("insert into Teacher values("+t_id+",'"+name+"'"+")");
									break;
								case 2:
									System.out.println(" Enter the subject code : ");
									int s_code = sc.nextInt();
									System.out.println("Enter the subject : ");
									String sub = sc.next();
									System.out.println(" Enter the day : ");
									String d = sc.next();
									System.out.println(" Enter the time :");
									String t = sc.next();
									System.out.println(" Enter the class : ");
									String cl = sc.next();
									System.out.println("Enter the teacher ID : ");
									int tid = sc.nextInt();
									String query= "insert into workload values(" + s_code + ",'"+sub+"','"+d+"','"+t+"','"+cl+"',"+tid+")";
									st.execute(query);
									break;	
							}
							break;
						case 2: 
							System.out.println("Enter the name of the  Teacher : ");
							String ti=sc.next();
							System.out.println("\n\t Day wise timetable\n\n ");
							System.out.println("\n TEACHER NAME : " + ti+"\n");
							String days[]={"MONDAY","TUESDAY","WEDNESDAY","THURSDAY", "FRIDAY","SATURDAY"};
							for(int i=0;i<6;i++){
								System.out.println("\n--- "+days[i]+" ---");
								rs=st.executeQuery(" select w.class_ , w.subject_code, w.subject,w.time from workload w ,Teacher t where t.Teacher_id=w.Teacher_id and t.Teacher_name='"+ti+"' and w.Day='"+days[i]+"'");
								int count=0;
								while(rs.next()){
									System.out.println("\t class - "+rs.getString(1)+"        "+rs.getString(2)+" - "+rs.getString(3)+"\t Time : "+rs.getString(4));
									count++;
								}
								if(count==0){
									System.out.println(" \t\t\t TAKE REST !!\n ");
								}
							}
							break;
						case 3:
							System.out.println("Enter the class :");
							String cl=sc.next();
							String dayss[]={"MONDAY","TUESDAY","WEDNESDAY","THURSDAY", "FRIDAY","SATURDAY"};
                                                        for(int i=0;i<6;i++){
                                                                System.out.println("\n--- "+dayss[i]+" ---");
                                                                rs=st.executeQuery(" select t.Teacher_name, w.subject_code, w.subject,w.time from workload w ,Teacher t where t.Teacher_id=w.Teacher_id and w.class_ ='"+cl+"' and w.Day='"+dayss[i]+"'");
                                                                int count=0;
                                                                while(rs.next()){
									 System.out.println("\t "+rs.getString(2)+"--  "+rs.getString(3)+"  ( "+rs.getString(1)+")\t Time : "+rs.getString(4));
                                                                        count++;
                                                                }
                                                                if(count==0){
                                                                        System.out.println(" \t\t\t TAKE REST !! \n ");
                                                                }
                                                        }
                                                        break;
						case 4: 
							System.out.println(" Enter the teacher id :");
							int t=sc.nextInt();
							System.out.println(" Enter the day and time of the class to be modified : ");
							System.out.println(" Class : ");
							String c=sc.next();
							System.out.println(" Day : ");
							String day=sc.next();
							System.out.println(" Time :");
							String tim=sc.next();
							System.out.println("\n Existing data : \n");
							rs=st.executeQuery(" select subject_code, subject,Day, time, class_ from workload w  where Teacher_id = "+t+" and w.class_ ='"+c+"' and  w.Day='"+day+"' and w.time = '"+tim+"'");
							while(rs.next()){
								System.out.println(" Subject code : "+ rs.getString(1));
								System.out.println(" Subject :"+ rs.getString(2));
								System.out.println(" Day : "+ rs.getString(3) + " Time : " +rs.getString(4));
								System.out.println(" Class : " + rs.getString(5));
							}
							System.out.println(" Enter the new values : ");
							System.out.println(" Class : ");
							String c_=sc.next();
							System.out.println(" Day : ");
							String day_ = sc.next();
							System.out.println(" Time : ");
							String tim_=sc.next();
							st.executeUpdate(" update workload set class_ ='"+ c_+"' , time = '"+tim_+"', Day ='"+ day_+"' where class_='"+c+"' and time='"+tim+"' and Day = '"+day+"'"); 
							break;			
			
			
					}	
					System.out.println("Do you want to continue ? : ");
					opt=sc.next().charAt(0);
				}
			}
		}
		catch(Exception e ){
			System.out.println("Exception : " + e);
		}
		finally{
			conn.close();
			st.close();
			rs.close();
		}
	}
}
