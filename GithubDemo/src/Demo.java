import java.sql.*;
import java.util.Scanner;
public class Demo{
	public static void main(String[] args) throws InstantiationException,IllegalAccessException, ClassNotFoundException, SQLException {
		try 
		{
			int chioce;
			student s = new student();
			do {
			System.out.println("Select an option \n1.Registration \n2.Update Password \n3.Delete a record \n4.Fetch a record \n5.Exit");
			Scanner sc = new Scanner(System.in);
			chioce = sc.nextInt();
		    switch(chioce)
		    {
		    case 1:
		    	s.get_studentdetails();
		    	s.Insert_studentDetails();
		    	break;
		    case 2:
		    	s.Update_studentDetails();
		    	break;
		    case 3:
		    	s.Delete_studentDetails();
		    	break;
		    case 4:
		    	s.Fetch_studentDetails();
		    	break;
		    case 5:
		    	System.out.println("Exiting the Application");
		    default	:
		    	System.out.println("Select a correct option");
		    	break;
		    }
			}while(chioce!=5);
			System.out.println("Thanks for using our software");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
class student
{
	private String name;
	private String password;
	private String country;
	private int mark;
	public void get_studentdetails(){
		System.out.println("Enter the details:");
		Scanner input = new Scanner(System.in);
		System.out.println("Enter Name : ");
		name = input.nextLine();
		System.out.println("Enter Password : ");
		password =  input.nextLine();
		System.out.println("Enter Country : ");
		country = input.nextLine();
		System.out.println("Enter Marks : ");
		mark = input.nextInt();
	}
	
	//Inserting a record
	
	public void Insert_studentDetails() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql://localhost:3306/vit","root","");
		Connection con  = dbmsconnect.get_connection();
		String sql = "insert into student values(?,?,?,?);";
		PreparedStatement pr = con.prepareStatement(sql);
		pr.setString(1,name);
		pr.setString(2, password);
		pr.setString(3, country);
		pr.setInt(4,mark);
		pr.execute();
		System.out.println("Record inserted successfully");
		dbmsconnect.closeconnection(con, pr);
	}
	
	//Updating the record.
	
   public void Update_studentDetails() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		try
		{
		dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql://localhost:3306/vit","root","");
		Connection con  = dbmsconnect.get_connection();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the password of the record to be Updated: ");
		String newpass = sc.nextLine();
		System.out.println("Enter the name of the record to be Updated: ");
		String nam = sc.nextLine();
		String sql = "update student set password = ? where name = ?;";
		PreparedStatement pr = con.prepareStatement(sql);
		pr.setString(1,newpass);
		pr.setString(2, nam);
		int i = pr.executeUpdate();
		if(i>0)
		{
			System.out.println("Record updated successfully");
		}
		else
		{
			System.out.println("No such record found in database");
		}
		
		dbmsconnect.closeconnection(con, pr);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
 }

   // Deleting the record.
   
   public void Delete_studentDetails() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql://localhost:3306/vit","root","");
		Connection con  = dbmsconnect.get_connection();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the record to be deleted:");
		String nam = sc.nextLine();
		String sql = "Delete from  student  where name = ?;";
		PreparedStatement pr = con.prepareStatement(sql);
		pr.setString(1, name);
		int i = pr.executeUpdate();
		if(i>0)
		{
			System.out.println("Record deleted successfully");
		}
		else
		{
			System.out.println("No such record found in database");
		}
		
		dbmsconnect.closeconnection(con, pr);
     }

     //Fetch the record
   
   public void Fetch_studentDetails() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql://localhost:3306/vit","root","");
		Connection con  = dbmsconnect.get_connection();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the record to be fetched: ");
		String nam = sc.nextLine();
		String sql = "Select from  student  where name = ?;";
		PreparedStatement pr = con.prepareStatement(sql);
		pr.setString(1, name);
		int i  = pr.executeUpdate();
		if(i>0)
		{
			System.out.println("Record fetchted successfully");
		}
		else
		{
			System.out.println("No such record found in database");
		}
		dbmsconnect.closeconnection(con, pr);
    }

}

class dbmsconnection
{
	String url;
	String username;
	String password;
	public dbmsconnection(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}
	public Connection get_connection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection con = null;
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(url,username,password);
		System.out.println("Connection established successfully");
		return con;
	}
	public void closeconnection(Connection con , PreparedStatement  pr) throws SQLException {
		pr.close();
		con.close();
		System.out.println("Connection closed successfully");
	}
}

