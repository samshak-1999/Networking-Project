import java.util.*;
import java.io.*;
import java.net.*;

public class client extends Thread {
	//Global Variables
	
	long elapsedTime;
	ArrayList<String> serverOut;
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	String command;
	Thread thread;
	
	//Constructor
	
	public client(String host, int port, String command) throws IOException {
		
		this.elapsedTime = 0;
		this.serverOut = new ArrayList<String>();
		this.socket = new Socket(host, port);
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.command = command;
		this.thread = new Thread(this);
		
		System.out.println("Client Created. ");
	}
	
	
	
	public void run(){
		try {
			long time = System.currentTimeMillis();
			
			String str = "";
			
			out.println(command);
			
			while((str = in.readLine()) != null) {
				serverOut.add(str);
			}
			
			elapsedTime = System.currentTimeMillis() - time;
			
			serverOut.add("Elapsed Time: " + elapsedTime + " MS"); 
			serverOut.add("------------------------------------------------------------------------------------------");
	
			while(serverOut.isEmpty() != true) {
				System.out.println(serverOut.remove(0));
			}
			
			socket.close();
		}
		catch(IOException ex){
			System.out.println("Server Exception");
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the host address: ");
		String host1 = input.next();
		System.out.println("Enter the port: ");
		int port1 = input.nextInt();
		System.out.println("How many Clients?: ");
		int clients1 = input.nextInt();

		System.out.print("Request\n"+
				"1. Date and Time\n"+
				"2. Uptime\n"+
				"3. Memory Use\n"+
				"4. Netstat\n"+
				"5. Current Users\n"+
				"6. Running Processes\n"+
				"7. Exit\n");
		
		String command = input.next();
		client[] clientObject = new client[clients1];
		CreateClient(clientObject, clients1, command, host1, port1);
		
		input.close();
	}
	
	public static void CreateClient(client[] clientObject, int clients, String command, String host, int port) throws IOException{
		
	
		for(int l = 0; l < clients; l++) {
			clientObject[l] = new client(host, port, command);
		}
		
		for(int k = 0; k < clients; k++) {
			clientObject[k].start();
		}
		
		for(int j = 0; j < clients; j++) {
			try {
			clientObject[j].join();
			}
			catch(InterruptedException ex) {
				ex.getMessage();
			}
		}
		
		long timeTotal = 0;
		
		for(int i = 0; i < clients; i++) {
			timeTotal += clientObject[i].elapsedTime;
		}
		
		System.out.println("Average time: " + (timeTotal/clients) + " ms");
	}
	

	

}
