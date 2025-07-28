import java.math.BigInteger;
import java.util.*;
import java.io.*;

public class SecretSolver {

    static class Point {
        BigInteger x, y;
        Point(BigInteger x, BigInteger y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Paste JSON input below, end with an empty line:");
        StringBuilder jsonBuilder = new StringBuilder();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.trim().isEmpty()) break;
            jsonBuilder.append(line);
        }

        String json = jsonBuilder.toString().replaceAll("[{}\"]", "").trim();

        String[] entries = json.split(",");
        Map<String, String> map = new LinkedHashMap<>();
        for (String entry : entries) {
            String[] kv = entry.trim().split(":", 2);
            if (kv.length == 2)
                map.put(kv[0].trim(), kv[1].trim());
        }

        int n = 0, k = 0;
        List<Point> points = new ArrayList<>();

   
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.equals("keys.n")) {
                n = Integer.parseInt(value);
            } else if (key.equals("keys.k")) {
                k = Integer.parseInt(value);
            }
        }

  
        for (String key : map.keySet()) {
            if (key.equals("keys.n") || key.equals("keys.k")) continue;

      
            if (key.endsWith(".base")) {
                String id = key.split("\\.")[0];
                String baseKey = id + ".base";
                String valueKey = id + ".value";

                if (map.containsKey(baseKey) && map.containsKey(valueKey)) {
                    int base = Integer.parseInt(map.get(baseKey));
                    String encodedValue = map.get(valueKey);
                    BigInteger x = new BigInteger(id);
                    BigInteger y = new BigInteger(encodedValue, base);
                    points.add(new Point(x, y));
                }
            }
        }

        if (points.size() < k) {
            System.out.println("Not enough points to interpolate.");
            return;
        }

        points.sort(Comparator.comparing(p -> p.x));
        List<Point> usedPoints = points.subList(0, k);

        BigInteger secret = lagrangeInterpolation(usedPoints);
        System.out.println("Secret is: " + secret);
    }

    private static BigInteger lagrangeInterpolation(List<Point> points) {
        BigInteger secret = BigInteger.ZERO;

        for (int i = 0; i < points.size(); i++) {
            BigInteger xi = points.get(i).x;
            BigInteger yi = points.get(i).y;

            BigInteger num = BigInteger.ONE;
            BigInteger den = BigInteger.ONE;

            for (int j = 0; j < points.size(); j++) {
                if (i != j) {
                    BigInteger xj = points.get(j).x;
                    num = num.multiply(xj.negate());
                    den = den.multiply(xi.subtract(xj));
                }
            }

            BigInteger li = num.divide(den);
            secret = secret.add(yi.multiply(li));
        }

        return secret;
    }
}
