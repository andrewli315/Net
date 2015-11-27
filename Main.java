import java.net.*;
import java.io.*;
import java.util.*;
public class Main{
	public static void main(String[] args){
 
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Server / 2. Client\n");  
        int opt = scanner.nextInt();
        if( opt==1 ){
            server s = new server();
        }else if(opt==2){
            client c = new client();
        }
    }

}
