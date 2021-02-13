package lesson3;

public class MethodArgument {
    public static void suibian(int a){

    }
//main线程，执行main方法（栈帧）
    public static void main(String[] args) {
        int i=0;
        suibian(i);
        System.out.println(i);
    }
}
