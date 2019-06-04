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
        int result = (int) (result1 % 98765 / 1000 ) % 300;
        return result;
    }
    void setsymtab(int tableaddr , String label1 , String address1){
        //沒碰撞 直接放值到陣列裡
        temp = table[tableaddr];
        if (temp.Nlabel == ""){
            temp.Nlabel = label1;
            temp.Naddress = address1;
        }
        //collision
        else {
            System.out.println("collision");
            //輸入了一樣東西
            if (temp.Nlabel.equals(label1))        
                System.out.println("label輸入過了");
            else {
                //產生第一個node
                if (temp.next == null){
                    System.out.println("產生串列");
                    temp.next = new Node();
                    //放值到第一個node
                    temp.next.Nlabel = label1;
                    temp.next.Naddress = address1;  
                }
                //往下一個node找
                else{
                    // 又碰撞 
                    while(temp.next != null){
                        // 在node中有一樣的label
                        if ((temp.next.Nlabel).equals(label1)){
                            System.out.println("(node)label輸入過了");
                            break;
                        }
                        //如果都沒有 往下一個node
                        else {
                            temp = temp.next;
                        }
                    }
                    //為最後一個node時，建立一個node存值
                    if (temp.next == null){
                        System.out.println("(node)產生串列");
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
        //印出首節點
        if (table[tabaddr].Nlabel.equals(label2))
            System.out.println(table[tabaddr].Naddress);
        //一直往後找
        else{
            while(temp.next != null){
                //下一個node跟輸入的一樣
                if (temp.Nlabel.equals(label2)){
                    System.out.println(temp.Naddress);
                    break;
                }
                //不一樣就再往下一個
                else
                    temp = temp.next;
                //如果下一個是最後一個，且輸入也是最後一個
                if (temp.Nlabel.equals(label2) && temp.next == null){
                    System.out.println(temp.Naddress);
                    break;
                }
            }
        }
        //label不一樣也搜尋到最後一個
        if ( (!temp.Nlabel.equals(label2)) && temp.next == null)
            System.out.println("沒有此label");
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