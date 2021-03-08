package v44;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SerializableDemo {
    public static void main(String[] args) throws IOException {
        User u1 = new User(1, "陈沛鑫", "男");
        User u2 = new User(2, "林黛玉", "女");

        // 把 u1 和 u2 对应的对象，序列化，并且写入到文件中
        String filename = "D:\\web\\process\\docBase\\users.obj";
        try (OutputStream outputStream = new FileOutputStream(filename)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(u1);
            objectOutputStream.writeObject(u2);

            objectOutputStream.flush();
        }
    }
}
