import java.io.*;

public class Test {

    public static class TEST implements Serializable{
        private boolean a;
        private int x,y;
        private String s;
        private Object o;

        TEST(boolean a, int x, int y, String s, Object o){
            this.a=a;
            this.x=x;
            this.y=y;
            this.s=s;
            this.o=o;
        }

        public String toString(){
            String st= ""+a+x+y+s+o;
            return st;
        }

    }

    public static void main() throws IOException, ClassNotFoundException {
        FileOutputStream out = new FileOutputStream("theTime");
        ObjectOutputStream s = new ObjectOutputStream(out);
        s.writeObject(new TEST(true,5,6,"JITA", new String("EHEHE")));

        FileInputStream in = new FileInputStream("theTime");
        ObjectInputStream is = new ObjectInputStream(in);
        TEST test= (TEST)is.readObject();
        System.out.println(test.toString());

    }
}
