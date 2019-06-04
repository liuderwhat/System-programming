import java.lang.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ProgScanner{
    public static void main (String argv[])throws IOException{
        //接收程式碼每一行的內容
        String line;
        boolean isStart;
        //讀入testprog這個檔案*********************
        FileReader fr = new FileReader("testprog.S");
        BufferedReader br = new BufferedReader(fr);
        //存opcode table*****************************/
        HashTable hash = new HashTable();
        // 讀testprog的每一行，指派給line
        while ((line=br.readLine()) != null) {
            //******************先把程式碼該忽略的東西忽略*******************************
            //刪除頭尾的空格
            line = line.trim();
            //把tab換成一個空格
            line = line.replace("\t", " ");
            //把. comment和.comment換成一個空值
            line = line.replace(".comment", "");
            line = line.replace(". comment", "");
            line = line.replace(", X", ",X");
            //上面都取代完之後，1.只要有句號都不會印出來
            //設比較樣式:有句號
            String pattern= "\\.";
            //2. 有,X會被視為索引定址
            String pattern1= ",X";
            //有RSUB又空4格 (就符合格式一下)
            String pattern2= "RSUB";
            
            //進行字串比對**************************
            Pattern r = Pattern.compile(pattern);
            Pattern r1 = Pattern.compile(pattern1);
            Pattern r2 = Pattern.compile(pattern2);
            
            //比對字串是不是符合regular expression
            Matcher m = r.matcher(line);
            Matcher m1 = r1.matcher(line);
            Matcher m2 = r2.matcher(line);
            
            //用空格分割處理過的程式碼
            String table1[] = line.split(" ");
            
            //刪除空白行**************************
            if (!line.equals("")){
                //只要字串出現過RSUB就印出4個空白
                if (m2.find()){
                    System.out.print("    ");
                }
                //此行字串如果沒句點就依照長度來印出Label mnemonic operand
                if (! (m.find())){
                    if (table1.length == 3){
                        //先印出篩檢完的樣子
                        System.out.print(table1[0]);
                        System.out.print(" "+table1[1]);
                        System.out.println(" "+table1[2]);
                        //起始位址
                        if (table1[1].equals("START")){
                            System.out.println("Program name is "+table1[0]);
                            System.out.println();
                        }
                        else {
                            //如果沒有opcode就代表是指令
                            if ((hash.Search(table1[1]).equals("wrong"))){
                                System.out.println(table1[1]+" is pseudo code");
                                System.out.println();
                            }
                            else {
                                System.out.print("label: "+table1[0]);
                                System.out.print(" mnemonic: "+table1[1]);
                                System.out.println(" operand: "+table1[2]); 
                            }
     
                        }
                            
                    }
                    //代表沒有沒有label ，所以印出其他兩個
                    else if (table1.length == 2){
                        System.out.print(table1[0]);
                        System.out.println(" "+table1[1]);
                        //結束
                        if (table1[0].equals("END")){
                            System.out.println("End of the program");
                        }
                        //還沒結束就印出資訊
                        else {
                            System.out.print("mnemonic: "+table1[0]);
                            System.out.println(" operand: "+table1[1]);
                        }
                    }
                    else {
                        System.out.println(table1[0]);
                        System.out.println("mnemonic: "+table1[0]);
                        System.out.println();
                    }                
                    
                    //***********判斷定址模式*******************
                    //只要有,X就是索引定址
                    if ((m1.find())){
                        System.out.println("indexed addressing");
                        System.out.println();
                    }
                    //如果長度唯一必沒有定址模式
                    else if (table1.length > 1){
                        // 沒label的情況下，有opcode
                        if (! (hash.Search(table1[0]).equals("wrong"))){
                            System.out.println("direct addressing");
                            System.out.println();
                        }
                        // 有label的情況下，有opcode
                        else if(! (hash.Search(table1[1]).equals("wrong"))) {
                            System.out.println("direct addressing");
                            System.out.println();
                        }
                    }                    
                }
            }
        }         
        fr.close();
    }
}