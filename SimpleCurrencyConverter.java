import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class SimpleCurrencyConverter {

    public static double getExchangeRate(String base, String target) {
        try {
            String apiUrl = "https://open.er-api.com/v6/latest/" + base; 
            URI uri = new URI(apiUrl);  
            URL url = uri.toURL();       

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            String search = "\"" + target + "\":";
            int start = response.indexOf(search) + search.length();
            int end = response.indexOf(",", start);
            if (end == -1) {
                end = response.indexOf("}", start);
            }
            String value = response.substring(start, end).trim();

            return Double.parseDouble(value);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return -1;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter base currency (e.g., USD, INR, EUR): ");
        String baseCurrency = scanner.next().toUpperCase();

        System.out.print("Enter target currency (e.g., USD, INR, EUR): ");
        String targetCurrency = scanner.next().toUpperCase();

        System.out.print("Enter amount in " + baseCurrency + ": ");
        double amount = scanner.nextDouble();

        double rate = getExchangeRate(baseCurrency, targetCurrency);

        if (rate != -1) {
            double converted = amount * rate;
            System.out.println(amount + " " + baseCurrency + " = " + converted + " " + targetCurrency);
        } else {
            System.out.println("Conversion failed.");
        }

        scanner.close();
    }
}
