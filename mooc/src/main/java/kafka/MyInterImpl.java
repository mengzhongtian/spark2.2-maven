package kafka;

public class MyInterImpl  {
    public static void main(String[] args) {
        MyInter in = new MyInter() {
            public void run() {
                System.out.println("helloworld");
            }
        };
        in.run();
    }
}
