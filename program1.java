/*
Write a menu driven program(Command line interface) to perform the following operations
on Employee table(empid,emp_name,salary)

a.Insert
b.Modify
c.Delete
d.Search
e.View All
f.Exit.

 */
import java.sql.*;
import java.util.*;
import java.util.Scanner;
class program1
{
  public static void main(String main[]) throws SQLException 
  {
        Connection conn = null;
        Statement stat = null;
        ResultSet res = null;
        PreparedStatement insert = null,update = null;
        Scanner input = new Scanner(System.in);
        try
        {
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://192.168.16.1/ty8844","ty8844","");
        insert = conn.prepareStatement("insert into employee values(?,?,?)");
        update = conn.prepareStatement("update employee set salary = ? where emp_id = ?");
        if(conn==null)
        {
                System.out.println("Connection failed");
        }
        else
        {
                System.out.println("Connetion succesful..");
        }
        stat = conn.createStatement();

       }
       catch(Exception e){System.out.print(e);}
        int ch;
        do
        {
        System.out.println("1. Insert || 2. Modify || 3. Delete || 4 Search || 5. ViewAll || 0. Exit");
        System.out.println("Enter your choice : ");
        ch = input.nextInt();

        switch(ch)
        {
                case 1:
                        {
                        System.out.print("\n Enter Id : ");
                        int id = input.nextInt();
                        System.out.print("\n Enter Name : ");
                        String name = input.next();
                        System.out.print("\n Enter salary : ");
                        float salary = input.nextFloat();

                        insert.setInt(1,id);
                        insert.setString(2,name);
                        insert.setFloat(3,salary);
                        insert.executeUpdate();
                        }
			break;
	                
		case 2:
			{
			System.out.print("Enter Id : ");
			int id = input.nextInt();
			System.out.print("enter updated salary : ");
			float salary = input.nextFloat();
			update.setInt(2,id);
			update.setFloat(1,salary);
			update.executeUpdate();
			break;
			}
		case 3:
			{
			System.out.print("Enter ID to Delete : ");
			int id = input.nextInt();
			stat.executeUpdate("delete from employee where emp_id= "+id);
				break;
			}
		case 4:
			{
			System.out.print("Enter Id to search : ");
			int id = input.nextInt();
			res = stat.executeQuery("select * from employee where emp_id= "+id);
			if(res.next())
			{
				System.out.print("Emp id = "+res.getInt(1));
				System.out.print("Emp Name = "+res.getString(2));
				System.out.print("Emp Salary = "+res.getFloat(3));
			}
			else
			{
				System.out.print("not found");
			}
			break;
		}
	
		case 5: 
			{
				res = stat.executeQuery("select * from employee");
				while(res.next())
				{
				System.out.print("\n Emp id = "+res.getInt(1));
				System.out.print("\n Emp name = "+res.getString(2));
				System.out.print("\n Emp salary =  "+res.getFloat(3));		
				}
			break;

			}
		}
}while(ch!=0);
}
}
                                                               
