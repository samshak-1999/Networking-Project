import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class server {
	
	public static void main(String[] args) throws IOException {
		
		int port = 1025;
		
		try (ServerSocket serverSocket = new ServerSocket(port)){
			System.out.println("Listening on port: " + port);
			
			while(true) {
				
				Socket socket = serverSocket.accept();
				System.out.println("New client connected");
				OutputStream output = socket.getOutputStream();
				PrintWriter writer = new PrintWriter(output, true);
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				String str = "";
				str = input.readLine();
				System.out.println("You selected " + str);
				
				//int i = Integer.parseInt(str);
				
				switch(Integer.parseInt(str)) {
				
				case 1:
					writer.println(new Date().toString());
					socket.close();
					break;
				
				case 2:
					Process uptime1 = Runtime.getRuntime().exec("uptime");
					BufferedReader reader = new BufferedReader(new InputStreamReader(uptime1.getInputStream()));
					String uptime = "";
					uptime = reader.readLine();
					writer.println(uptime);
					socket.close();
					break;
					
				case 3: 
					Runtime memory = Runtime.getRuntime();
					writer.println("Total memory: " + memory.maxMemory() + "MB");
					writer.println("Free memory: " + memory.freeMemory() + "MB");
					writer.println("Used memory: " + (memory.maxMemory() - memory.freeMemory()) + "MB");
					socket.close();
					break;
					
				case 4: 
					Process netStat = Runtime.getRuntime().exec("netstat -a");
					BufferedReader netStat_Reader = new BufferedReader(new InputStreamReader(netStat.getInputStream()));
					String line = "";
					while((line = netStat_Reader.readLine()) != null) {
						writer.println(line);
					}
					socket.close();
					break;
					
				case 5:
					Process currentUser = Runtime.getRuntime().exec("who -a");
					BufferedReader current = new BufferedReader(new InputStreamReader(currentUser.getInputStream()));
					String line1 = "";
					while((line1 = current.readLine()) != null){
						writer.println(line1);
					}
					socket.close();
					break;
				
				case 6:
					Process activeProcess = Runtime.getRuntime().exec("ps -A");
					BufferedReader processReader = new BufferedReader(new InputStreamReader(activeProcess.getInputStream()));
					String line2;
					while((line2 = processReader.readLine()) != null) {
						writer.println(line2);
					}
					socket.close();
					break;
					
				case 7:
					System.out.println("Exited");
					socket.close();
					break;
				default:
					System.out.println("Incorrect choice");
					socket.close();
				}
		}	
	}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
}