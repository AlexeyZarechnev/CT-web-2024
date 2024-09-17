import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Task3 {
    public static void main(String[] none) {
        String begin = "curl 'http://1d3p.wp.codeforces.com/new' \\" + System.lineSeparator() 
        + "-H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7' \\" + System.lineSeparator()
        + "-H 'Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7' \\" + System.lineSeparator()
        + "-H 'Cache-Control: max-age=0' \\" + System.lineSeparator()
        + "-H 'Connection: keep-alive' \\" + System.lineSeparator()
        + "-H 'Content-Type: application/x-www-form-urlencoded' \\" + System.lineSeparator()
        + "-H 'Cookie: _gid=GA1.2.1606809902.1726586254; _ga=GA1.1.945110055.1726586254; _ga_K230KVN22K=GS1.1.1726586254.1.1.1726587610.0.0.0; JSESSIONID=D8C0B3D5107D52046822AF2A5F2F2CC1' \\" + System.lineSeparator()
        + "-H 'Origin: http://1d3p.wp.codeforces.com' \\" + System.lineSeparator()
        + "-H 'Referer: http://1d3p.wp.codeforces.com/' \\" + System.lineSeparator()
        + "-H 'Upgrade-Insecure-Requests: 1' \\" + System.lineSeparator()
        + "-H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36' \\" + System.lineSeparator()
        + "--data-raw '_af=34be50b38beccce4&proof=";
            String mid = "&amount="; 
            String end = "&submit=Submit' \\" + System.lineSeparator() + "--insecure";
            try {
            Writer out = new FileWriter("task3.sh");
            for (int i = 1; i <= 100; ++i) {
                String command = begin + (i * i) + mid + i + end;
                // System.out.println(command);
                out.write(command);
                out.write(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}