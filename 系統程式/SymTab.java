import java.util.Scanner;
public class SymTab{
    public Node table[];
    public Node temp;
    public SymTab(int tabsize){
        table = new Node[tabsize];
        for (int i = 0; i < tabsize ;i++){
            table[i] = new Node();
            table[i].Nlabel = "";
            table[i].Naddress = null;
        }
    }
    int hash (String m){
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
        int result = (int) (result1 % 98765 / 1000 ) % 300;
        return result;
    }
    void setsymtab(int tableaddr , String label1 , String address1){
        //�S�I�� ������Ȩ�}�C��
        temp = table[tableaddr];
        if (temp.Nlabel == ""){
            temp.Nlabel = label1;
            temp.Naddress = address1;
        }
        //collision
        else {
            System.out.println("collision");
            //��J�F�@�˪F��
            if (temp.Nlabel.equals(label1))        
                System.out.println("label��J�L�F");
            else {
                //���ͲĤ@��node
                if (temp.next == null){
                    System.out.println("���ͦ�C");
                    temp.next = new Node();
                    //��Ȩ�Ĥ@��node
                    temp.next.Nlabel = label1;
                    temp.next.Naddress = address1;  
                }
                //���U�@��node��
                else{
                    // �S�I�� 
                    while(temp.next != null){
                        // �bnode�����@�˪�label
                        if ((temp.next.Nlabel).equals(label1)){
                            System.out.println("(node)label��J�L�F");
                            break;
                        }
                        //�p�G���S�� ���U�@��node
                        else {
                            temp = temp.next;
                        }
                    }
                    //���̫�@��node�ɡA�إߤ@��node�s��
                    if (temp.next == null){
                        System.out.println("(node)���ͦ�C");
                        temp.next = new Node();
                        temp.next.Nlabel = label1;
                        temp.next.Naddress = address1;
                    }
                }                    
            }
        }
    }
    void print(int index){
        temp = table[index];
        if (table[index].Nlabel != ""){
            System.out.println("index:"+index+"  "+table[index].Nlabel + " " + table[index].Naddress);
            while(temp.next != null){
                System.out.println("link: "+index +" "+ temp.next.Nlabel + " " + temp.next.Naddress);
                temp = temp.next;
            }
        }
    }
    void search (String label2){
        int tabaddr = hash(label2);
        temp = table[tabaddr];
        //�L�X���`�I
        if (table[tabaddr].Nlabel.equals(label2))
            System.out.println(table[tabaddr].Naddress);
        //�@�������
        else{
            while(temp.next != null){
                //�U�@��node���J���@��
                if (temp.Nlabel.equals(label2)){
                    System.out.println(temp.Naddress);
                    break;
                }
                //���@�˴N�A���U�@��
                else
                    temp = temp.next;
                //�p�G�U�@�ӬO�̫�@�ӡA�B��J�]�O�̫�@��
                if (temp.Nlabel.equals(label2) && temp.next == null){
                    System.out.println(temp.Naddress);
                    break;
                }
            }
        }
        //label���@�ˤ]�j�M��̫�@��
        if ( (!temp.Nlabel.equals(label2)) && temp.next == null)
            System.out.println("�S����label");
    }
    public static void main (String argv[]){
        SymTab symtab = new SymTab(300);
        Scanner sc = new Scanner(System.in);
        System.out.println("input * , 87 to look table ");
        System.out.println("input & , 87 to search table ");
        while(true){
            String label = sc.next();
            String address = sc.next();
            if ((label.equals("*")) && (address.equals("87"))){
                for (int i = 0 ; i < 300 ; i++)
                    symtab.print(i);
            }
            else if (label.equals("&") && (address.equals("87"))){
                System.out.println("input label to find address");
                String label1 = sc.next();
                symtab.search(label1);
            }
            else{
                symtab.setsymtab(symtab.hash(label) , label ,address);
            }
        }
    }
}