import java.lang.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ProgScanner{
    public static void main (String argv[])throws IOException{
        //�����{���X�C�@�檺���e
        String line;
        boolean isStart;
        //Ū�Jtestprog�o���ɮ�*********************
        FileReader fr = new FileReader("testprog.S");
        BufferedReader br = new BufferedReader(fr);
        //�sopcode table*****************************/
        HashTable hash = new HashTable();
        // Ūtestprog���C�@��A������line
        while ((line=br.readLine()) != null) {
            //******************����{���X�ө������F�詿��*******************************
            //�R���Y�����Ů�
            line = line.trim();
            //��tab�����@�ӪŮ�
            line = line.replace("\t", " ");
            //��. comment�M.comment�����@�Ӫŭ�
            line = line.replace(".comment", "");
            line = line.replace(". comment", "");
            line = line.replace(", X", ",X");
            //�W�������N������A1.�u�n���y�������|�L�X��
            //�]����˦�:���y��
            String pattern= "\\.";
            //2. ��,X�|�Q�������ީw�}
            String pattern1= ",X";
            //��RSUB�S��4�� (�N�ŦX�榡�@�U)
            String pattern2= "RSUB";
            
            //�i��r����**************************
            Pattern r = Pattern.compile(pattern);
            Pattern r1 = Pattern.compile(pattern1);
            Pattern r2 = Pattern.compile(pattern2);
            
            //���r��O���O�ŦXregular expression
            Matcher m = r.matcher(line);
            Matcher m1 = r1.matcher(line);
            Matcher m2 = r2.matcher(line);
            
            //�ΪŮ���γB�z�L���{���X
            String table1[] = line.split(" ");
            
            //�R���ťզ�**************************
            if (!line.equals("")){
                //�u�n�r��X�{�LRSUB�N�L�X4�Ӫť�
                if (m2.find()){
                    System.out.print("    ");
                }
                //����r��p�G�S�y�I�N�̷Ӫ��רӦL�XLabel mnemonic operand
                if (! (m.find())){
                    if (table1.length == 3){
                        //���L�X�z�˧����ˤl
                        System.out.print(table1[0]);
                        System.out.print(" "+table1[1]);
                        System.out.println(" "+table1[2]);
                        //�_�l��}
                        if (table1[1].equals("START")){
                            System.out.println("Program name is "+table1[0]);
                            System.out.println();
                        }
                        else {
                            //�p�G�S��opcode�N�N��O���O
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
                    //�N��S���S��label �A�ҥH�L�X��L���
                    else if (table1.length == 2){
                        System.out.print(table1[0]);
                        System.out.println(" "+table1[1]);
                        //����
                        if (table1[0].equals("END")){
                            System.out.println("End of the program");
                        }
                        //�٨S�����N�L�X��T
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
                    
                    //***********�P�_�w�}�Ҧ�*******************
                    //�u�n��,X�N�O���ީw�}
                    if ((m1.find())){
                        System.out.println("indexed addressing");
                        System.out.println();
                    }
                    //�p�G���װߤ@���S���w�}�Ҧ�
                    else if (table1.length > 1){
                        // �Slabel�����p�U�A��opcode
                        if (! (hash.Search(table1[0]).equals("wrong"))){
                            System.out.println("direct addressing");
                            System.out.println();
                        }
                        // ��label�����p�U�A��opcode
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