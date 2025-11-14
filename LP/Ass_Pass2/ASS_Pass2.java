import java.io.*;
import java.util.HashMap; 

public class ASS_Pass2   
{
	public static void main(String[] args) throws Exception {    // mandotry write the throwing the exception 
	    //file reader 
        BufferedReader b1 = new BufferedReader(new FileReader("intermediate.txt")); //creating buffer to read intermideate code 
        BufferedReader b2 = new BufferedReader(new FileReader("sym.txt")); //read symbol table  
        BufferedReader b3 = new BufferedReader(new FileReader("lit.txt")); //read literal table 
        
        FileWriter f1 = new FileWriter("machinecode2.txt");    //opening file to write the output
	    String s;                        //declaring string to read the each line of file
	    
        HashMap<Integer, String> symAddr = new HashMap<Integer, String>(); 
        HashMap<Integer, String> litAddr = new HashMap<Integer, String>(); 
        
        int symtabPointer=1,littabPointer=1,offset=0;
        
        System.out.println("Symbol Address");
        while((s=b2.readLine())!=null)  
            { 
            String word[]=s.split("\\s+"); 
            symAddr.put(symtabPointer++,word[1]); 
            System.out.println(word[1]);
            }
         
        System.out.println("");
        System.out.println("literal Address");
        while((s=b3.readLine())!=null)
            { 
                String word[]=s.split("\\s+"); 
                litAddr.put(littabPointer++,word[1]);
                System.out.println(word[1]);
            } 
         
         
	    while((s = b1.readLine())!= null)
	    {
	        
	        if(s.substring(1,6).compareToIgnoreCase("IS,00")==0)
                { f1.write("+ 00 0 000\n"); }
                
	        else if(s.substring(1,3).compareToIgnoreCase("IS")==0){    // comparision of string 
	            f1.write("+ "+s.substring(4,6)+" ");        //writing in file 
	        
	    
	    
	                if(s.charAt(9)==')')
                    {    
                        f1.write(s.charAt(8)+" ");
                        offset = 3;
	                   }
	                
	                if(s.charAt(8+offset)=='S'){
	                    f1.write(symAddr.get(Integer.parseInt(s.substring(10+offset,s.length()-1)))+"\n");//symbol table 
	                }
	                else if(s.charAt(8+offset)=='L'){
	                    f1.write(litAddr.get(Integer.parseInt(s.substring(10+offset,s.length()-1)))+"\n"); //literal table use 
	                }
	                   
	            }
	           
	        else if (s.substring(1,6).compareToIgnoreCase("DL,01")==0){
        	    
        	    String s1=s.substring(10,s.length()-1),s2="";
        	    for(int i=0;i<3-s1.length();i++) {
                    s2+="0"; 
        	    }
        	    s2+=s1; 
                f1.write("+ 00 0 "+s2+"\n"); 
	        }     
	        else {
	           f1.write("\n");  // this file skips the line value AD 
	        }
        }
       // must close the file 
        b1.close();
        b2.close();
        b3.close();
        f1.close(); 
    }
}
