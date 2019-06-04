import java.lang.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;
import java.io.*;
public class HashTable{
    String table2[];
    String table3[];
    String SICtable[][];
    public HashTable()throws IOException{
        this(57 , 2 , 300);
        int x = 0;
        FileReader fr = new FileReader("opCode.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        //readLine()�����|�s1���A�ҥH�n��line�s�_��
        while ((line=br.readLine()) != null) {
            //mnemonic��opcode���}�s��
            String table1[] = line.split(" ");
            table2[x] = table1[0];
            table3[x] = table1[1];
            x++;
        }         
        fr.close();
        int [] hashResult = new int[300];
        //��mnemonic���ꪺ���G�s�b�t�@�Ӱ}�C��
        for (int i = 0 ; i < table2.length ;i++ )
            hashResult[i] = this.hashfunc(table2[i]);
        for (int i = 0 ; i < 57 ; i++)
            this.setOpTable(table2[i] , table3[i] , hashResult[i]);
    }
    //��l�ư}�C
    public HashTable(int size , int row , int col){
        table2 = new String[size];
        for(int i = 0;i < table2.length;i++)
            table2[i] = "";
        table3 = new String[size];
        for(int i = 0;i < table3.length;i++)
            table3[i] = "";
        SICtable = new String [row][col];
    }
    
    //�Htwo dimension array (2 * 300)�إ� opcode table
    public void setOpTable(String mnemonic, String opcode , int address){
        //�ѨM�I��: �@����U�@�Ӧ�}���즳�Ū�
        while (SICtable[0][address] != null){
            address = (address + 1) % 300;
        }
        //[0][x]��mnemonic [1][x]��opcode
        SICtable [0][address] = mnemonic;
        SICtable [1][address] = opcode;
        //System.out.println(SICtable[0][address]+" "+SICtable [1][address] +" "+address );
    }
    //��Jmnemonic��۹�����opcode
    public String Search(String mm){
        int address = hashfunc(mm);
        while (!mm.equals(SICtable[0][address])){
            address = (address + 1 ) % 300;
            //���b
            if (SICtable [1][address] != null){             
               continue;
            }
            else {
                return "wrong";
            }                     
        }
        if (mm.equals(SICtable[0][address])){
            return SICtable[1][address];
        }
        return "0";
    }
    
    //��mnemonic��hash
    public int hashfunc(String m){
        String s[] = new String [m.length()];
        //��l��sum
        String sum = "";
        int[] a = new int[m.length()];
        //�r���@�ӭӦX��
        for(int i = 0;i < a.length;i++){
            a[i] = m.charAt(i);
            //�r����ASCII code
            s[i] = Integer.toString(a[i]);
        }
        //�r����ASCII code�ۥ[
        for(int i = 0 ; i < a.length;i++)
            sum = sum + s[i];
        //�]��6�Ӧr����ASCII�|�W�Xint���O����d��A�ҥH��long���A
        long result1 = Long.parseLong(sum);
        //������Ƚ����ƴ�ָI�����v
        int result = (int) (result1 % 98765 / 1000 );
        return result;
    }

    public static void main (String argv[])throws IOException{
        Scanner sc = new Scanner (System.in);
        HashTable hash = new HashTable();
        while(true){
            String input = sc.next();
            System.out.println(hash.Search(input));
        }
    }
}
