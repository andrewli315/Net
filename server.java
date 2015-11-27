import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Scanner;
import ezprivacy.protocol.IntegrityCheckException;
import ezprivacy.toolkit.CipherUtil;
class server{
	
	Socket client=null;
	
	byte[] key = "oiEAitVbJHJFdkjW".getBytes();
	byte[] iv = "0101010101010101".getBytes();
	
	
	ServerSocket serv=null;
	PrintStream output;
	BufferedReader buf;
	BufferedInputStream inputStream;
	DataOutputStream outfile;
	DataInputStream infile;
	server(){
		Scanner scanner = new Scanner(System.in);
		System.out.printf("input the port: ");
		//int port = scanner.nextInt();
		int port = 3333;
		connect(port);
	}
	void connect(int port){
		try{
			serv = new ServerSocket(port);
			System.out.println("server is listening ...");
			boolean f =true;
			while(f){
				client = serv.accept();
				buf= new BufferedReader(new InputStreamReader(client.getInputStream()));
				boolean flag = true;
				while(flag){
					String str = buf.readLine();
					if("upload".equals(str)){
						System.out.println("recieving the data...");
						String file_name =new String(buf.readLine());
						try{
						recieve(file_name);
						System.out.println("complete.");
						}catch(Exception e){
							System.out.println("recieving error");
							e.printStackTrace();
						}
					}
					else if("download".equals(str)){
						System.out.println("transmit datas...");
						String file_name =new String(buf.readLine());
						try{
						transmission(file_name);
						System.out.println("complete.");
						}catch(Exception e){
							System.out.println("transmission error");
							e.printStackTrace();
						}
						
					}
					else if(str==null||"".equals(str)){
						flag = false;
					}
					else{
						if("bye".equals(str)){
							f = false;
							flag = false;
						}
						else{
							for(int i=0;i<str.length();i++){
								//byte[] temp = str.getBytes();
								//System.out.printf("%d ",temp[i]);
							}
						}
					}
				}	
				client.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	int recieve(String file_name)throws Exception{
			int temp,index=0;
			int count =0;
			byte[] cipher = new byte[32];
			byte[] decrypt= new byte[1];
			System.out.println(file_name);
			
			File doc = new File("server");
			if(!doc.exists())
				doc.mkdir();
			
			File fout = new File("server"+File.separator+file_name);
			
			inputStream = new BufferedInputStream(client.getInputStream());
			outfile = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fout)));
			while(true)//((temp = inputStream.read())!= 65533)
			{
				temp = inputStream.read();
				cipher[index] = (byte)temp;
				if(index == 31){
					decrypt = CipherUtil.authDecrypt(key,iv,cipher);
					if(decrypt[0] == (byte)-1){
						break;
					}
					else{
						outfile.write(decrypt);
					}
					index =0;
				}
				else{
					index++;
				}
			}

			System.out.println("recieve the data...");
			
			outfile.flush();
			outfile.close();
			return 1;
			
	}
	int transmission(String file)throws Exception{
		int temp,index=0;
		byte[] plain = new byte[1];
		byte[] cipher;
		byte[] eof = new byte[1];
		eof[0] = (byte)-1;
		
		String route = "server"+File.separator+file;
		
		File fout = new File(route);
		
		if(!fout.exists()){
			output.println("file does not exist");
			return 0;
		}
		
		output = new PrintStream(client.getOutputStream());
		infile = new DataInputStream(new BufferedInputStream(new FileInputStream(fout)));
		
		while((temp = infile.read())!=-1){
			plain[index] = (byte)temp;
			cipher = CipherUtil.authEncrypt(key,iv,plain);
			output.write(cipher);
		}
		cipher = CipherUtil.authEncrypt(key,iv,eof);
		output.write(cipher);
		infile.close();
		System.out.println("file transmission succeed");
		return 1;
	}
}