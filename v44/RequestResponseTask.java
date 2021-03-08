package v44;

import java.io.*;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RequestResponseTask implements Runnable {
    private static final String DOC_BASE="D:\\web\\process\\docBase";
    private final Socket socket;
    public RequestResponseTask(Socket socket){
        this.socket=socket;
    }
    private static final Map<String,String> mimeTypeMap=new HashMap<>();
    static {
        mimeTypeMap.put("txt", "text/plain");
        mimeTypeMap.put("html", "text/html");
        mimeTypeMap.put("js", "application/javascript");
        mimeTypeMap.put("jpg","image/jpeg");
    }
    @Override
    public void run() {
        try {
            System.out.println("一条tcp已经连接");
            //读取请求
            InputStream inputStream = socket.getInputStream();
            Scanner scanner=new Scanner(inputStream,"utf-8");
            scanner.next();
            String path = scanner.next();
            scanner.nextLine();
            String requestURI=path;
            String queryString="";
            if(path.contains("?")){
                int i = path.indexOf("?");
                requestURI = path.substring(0, i);
                queryString = path.substring(i + 1);
            }
            System.out.println(requestURI);
            Map<String,String> headers=new HashMap<>();
            String headerLine;
            while (scanner.hasNextLine()&&!(headerLine=scanner.nextLine()).isEmpty()){
                String[] part = headerLine.split(":");
                String name=part[0].trim().toLowerCase();
                String value=part[1].trim().toLowerCase();
                headers.put(name,value);
            }
            if(requestURI.equals("/")){
                requestURI="/index.html";
            }
            String target="世界";
            for (String kv:queryString.split("&")) {
                if(kv.isEmpty()){
                    continue;
                }
                String[] part = kv.split("=");
                String key = URLEncoder.encode(part[0],"utf-8");
                String value = URLEncoder.encode(part[1],"utf-8");
                if(key.equals("target")){
                    target=value;
                }
            }
            if(requestURI.equals("/set-cookie")){
                OutputStream outputStream = socket.getOutputStream();
                Writer writer = new OutputStreamWriter(outputStream, "utf-8");
                PrintWriter printWriter = new PrintWriter(writer);
                //写响应行
                printWriter.printf("HTTP/1.0 307 Temporary Redirect\r\n");
                //写响应头
                printWriter.printf("set-cookie:username=xyk\r\n");
                printWriter.printf("Location: profile\r\n");
                printWriter.printf("\r\n");
                printWriter.flush();
            } else if(requestURI.equals("/profile")){
                OutputStream outputStream = socket.getOutputStream();
                Writer writer = new OutputStreamWriter(outputStream, "utf-8");
                PrintWriter printWriter = new PrintWriter(writer);
                String username=null;
                //从cookie中获取username
                String cookie = headers.getOrDefault("cookie", "");
                System.out.println("cookie value:"+cookie);
                for (String cookieKV:cookie.split(";")) {
                    if(cookieKV.isEmpty()){
                        continue;
                    }
                    String[] kv = cookieKV.split("=");
                    String cookieName=kv[0];
                    String cookieValue=kv[1];
                    if(cookieName.equals("username")){
                        username=cookieValue;
                    }
                }

                //写响应行
                printWriter.printf("HTTP/1.0 200 OK\r\n");
                //写响应头
                printWriter.printf("content-Type:text/html; charset=utf-8\r\n");
                printWriter.printf("\r\n");
                if(username!=null){
                    printWriter.printf("<h1>当前用户是: %s</h1>",username);
                }else {
                    printWriter.printf("<h1>你需要先登录</h1>");
                }
                printWriter.flush();
            } else if(requestURI.equals("/redirect-to")){
                OutputStream outputStream = socket.getOutputStream();
                Writer writer = new OutputStreamWriter(outputStream, "utf-8");
                PrintWriter printWriter = new PrintWriter(writer);
                //写响应行
                printWriter.printf("HTTP/1.0 307 Temporary Redirect\r\n");
                //写响应头
                printWriter.printf("Location: /1.jpg\r\n");
                printWriter.printf("\r\n");
                printWriter.flush();
            }else if(requestURI.equals("/goodbye.html")){
                OutputStream outputStream = socket.getOutputStream();
                Writer writer = new OutputStreamWriter(outputStream, "utf-8");
                PrintWriter printWriter = new PrintWriter(writer);
                //写响应行
                printWriter.printf("HTTP/1.0 200 OK\r\n");
                //写响应头
                printWriter.printf("content-Type:text/html; charset=utf-8\r\n");
                printWriter.printf("\r\n");
                printWriter.printf("<h1>再见 %s</h1>",target);
                printWriter.flush();
            }else {
                String filePath = DOC_BASE + requestURI;
                File resource = new File(filePath);
                if (resource.exists()) {
                    OutputStream outputStream = socket.getOutputStream();
                    Writer writer = new OutputStreamWriter(outputStream, "utf-8");
                    PrintWriter printWriter = new PrintWriter(writer);
                    String contentType = "text/plain";
                    if (path.contains(".")) {
                        int i = path.lastIndexOf(".");
                        String suffix = path.substring(i + 1);
                        contentType = mimeTypeMap.getOrDefault(suffix, contentType);
                    }
                    // 如果 contentType 是 "text/.."，代表是文本
                    // 我们都把字符集统一设定成 utf-8
                    if (contentType.startsWith("text/")) {
                        contentType = contentType + "; charset=utf-8";
                    }
                    //写响应行
                    printWriter.printf("HTTP/1.0 200 OK\r\n");
                    //写响应头
                    printWriter.printf("content-Type:%s;\r\n", contentType);
                    printWriter.printf("\r\n");
                    printWriter.flush();
                    //写body
                    byte[] buffer = new byte[1024];
                    try (InputStream resourceInputStream = new FileInputStream(resource)) {
                        while (true) {
                            int len = resourceInputStream.read(buffer);
                            if (len == -1) {
                                break;
                            }
                            outputStream.write(buffer, 0, len);
                        }
                    }
                    printWriter.flush();
                } else {
                    OutputStream outputStream = socket.getOutputStream();
                    Writer writer = new OutputStreamWriter(outputStream, "utf-8");
                    PrintWriter printWriter = new PrintWriter(writer);
                    //写响应行
                    printWriter.printf("HTTP/1.0 404 Not Found\r\n");
                    //写响应头
                    printWriter.printf("content-Type:text/html; charset=utf-8\r\n");
                    printWriter.printf("\r\n");
                    printWriter.printf("<h1>%s:对应的资源不存在</h1>", path);
                    printWriter.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }finally {
            try {
                socket.close();
                System.out.println("一条tcp已经释放");
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }
    }
}
