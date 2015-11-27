import java.io.*;
import java.net.*;
import java.util.*;
import ezprivacy.protocol.IntegrityCheckException;
import ezprivacy.toolkit.CipherUtil;
class client{
	
	Socket client;
	
	byte[] key = "oiEAitVbJHJFdkjW".getBytes();
	byte[] iv = "0101010101010101".getBytes();
	
	BufferedReader input = null;
	BufferedReader buf = null;
	BufferedInputStream inputStream;
	PrintStream out=null;
	DataInputStream tran;
	DataOutputStream outfile;
	client(){
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("input the IP:");
		String ip = "140.116.101.80";//scanner.nextLine();
		System.out.println("input the Port:");
		int port = 3333;//scanner.nextInt();
		connect(ip,port);
	}
	int recieve(String file_name)throws Exception{
			int temp,index=0;
			int count =0;
			byte[] cipher = new byte[32];
			byte[] decrypt= new byte[1];
			System.out.println(file_name);
			
			File doc = new File("client");
			if(!doc.exists())
				doc.mkdir();
			
			File fout = new File("client"+File.separator+file_name);
			
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
	int transmit(String file)throws Exception{
		int temp,index=0;
		byte[] plain = new byte[1];
		byte[] cipher;
		byte[] eof = new byte[1];
		eof[0] = (byte)-1;
		String route = "client"+File.separator+file;		
		
		File fout = new File(route);
		if(!fout.exists()){
			System.out.println("the file does not exist");
			return 0;
		}
		
		tran = new DataInputStream(new BufferedInputStream(new FileInputStream(fout)));
		while((temp = tran.read())!=-1){
			plain[index] = (byte)temp;
			cipher = CipherUtil.authEncrypt(key,iv,plain);
			out.write(cipher);
		}
		cipher = CipherUtil.authEncrypt(key,iv,eof);
		out.write(cipher);
		tran.close();
		System.out.println("file transmission succeed");
		return 1;
	}
	void connect(String ip,int port){
		String str = "";
		try{
			client = new Socket(ip,port);
		}catch(IOException e){
			System.out.println("error in transmission");
		}
		try{
		out = new PrintStream(client.getOutputStream());
		}catch(IOException e){
			
		}
		input = new BufferedReader(new InputStreamReader(System.in));
			//buf = new BufferedReader(new InputStream(s.getInputStream));
		boolean flag=true;
		while(flag){
			System.out.println("input the message:");
			try{
				
				str = input.readLine();
				out.println(str);
				if("upload".equals(str)){
					str = input.readLine();
					out.println(str);
					transmit(str);
				}
				else if("download".equals(str)){
					str =input.readLine();
					out.println(str);
					recieve(str);
					
				}
				else if("bye".equals(str)){
					flag = false;
				}
			}catch(Exception e){
				System.out.println("writer error");
				e.printStackTrace();
			}
			
		}
		try{
		close();
		}catch(IOException e){
			
		}
	}
	void close()throws IOException{
		out.close();
		client.close();
	}
}
