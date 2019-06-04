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
        //readLine()本身會存1次，所以要用line存起來
        while ((line=br.readLine()) != null) {
            //mnemonic跟opcode分開存取
            String table1[] = line.split(" ");
            table2[x] = table1[0];
            table3[x] = table1[1];
            x++;
        }         
        fr.close();
        int [] hashResult = new int[300];
        //把mnemonic雜湊的結果存在另一個陣列裡
        for (int i = 0 ; i < table2.length ;i++ )
            hashResult[i] = this.hashfunc(table2[i]);
        for (int i = 0 ; i < 57 ; i++)
            this.setOpTable(table2[i] , table3[i] , hashResult[i]);
    }
    //初始化陣列
    public HashTable(int size , int row , int col){
        table2 = new String[size];
        for(int i = 0;i < table2.length;i++)
            table2[i] = "";
        table3 = new String[size];
        for(int i = 0;i < table3.length;i++)
            table3[i] = "";
        SICtable = new String [row][col];
    }
    
    //以two dimension array (2 * 300)建立 opcode table
    public void setOpTable(String mnemonic, String opcode , int address){
        //解決碰撞: 一直找下一個位址直到有空的
        while (SICtable[0][address] != null){
            address = (address + 1) % 300;
        }
        //[0][x]放mnemonic [1][x]放opcode
        SICtable [0][address] = mnemonic;
        SICtable [1][address] = opcode;
        //System.out.println(SICtable[0][address]+" "+SICtable [1][address] +" "+address );
    }
    //輸入mnemonic找相對應的opcode
    public String Search(String mm){
        int address = hashfunc(mm);
        while (!mm.equals(SICtable[0][address])){
            address = (address + 1 ) % 300;
            //防呆
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
    
    //對mnemonic做hash
    public int hashfunc(String m){
        String s[] = new String [m.length()];
        //初始化sum
        String sum = "";
        int[] a = new int[m.length()];
        //字元一個個合併
        for(int i = 0;i < a.length;i++){
            a[i] = m.charAt(i);
            //字串轉ASCII code
            s[i] = Integer.toString(a[i]);
        }
        //字元的ASCII code相加
        for(int i = 0 ; i < a.length;i++)
            sum = sum + s[i];
        //因為6個字元的ASCII會超出int的記憶體範圍，所以用long型態
        long result1 = Long.parseLong(sum);
        //把雜湊值複雜化減少碰撞機率
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
