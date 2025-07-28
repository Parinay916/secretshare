import java.io.*;
import java.math.BigInteger;
import java.util.*;
import org.json.JSONObject;
import org.json.JSONException;

public class SecretSolver {

    static class Point {
        BigInteger x, y;
        Point(BigInteger x, BigInteger y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        try {
            String[] files = {"testcase1.json", "testcase2.json"};

            for (int i = 0; i < files.length; i++) {
                JSONObject json = new JSONObject(readFile(files[i]));

                JSONObject keys = json.getJSONObject("keys");
                int k = keys.getInt("k");

                List<Point> points = new ArrayList<>();

                for (String key : json.keySet()) {
                    if (key.equals("keys")) continue;

                    JSONObject root = json.getJSONObject(key);
                    int base = Integer.parseInt(root.getString("base"));
                    String value = root.getString("value");

                    BigInteger x = new BigInteger(key);
                    BigInteger y = new BigInteger(value, base);

                    points.add(new Point(x, y));
                }

                if (points.size() < k) {
                    System.out.println("Not enough points to interpolate.");
                    continue;
                }

               
                points.sort(Comparator.comparing(p -> p.x));
                List<Point> usedPoints = points.subList(0, k);

                BigInteger secret = lagrangeInterpolation(usedPoints);
                System.out.println("Secret from " + files[i] + ": " + secret);
            }

        } catch (IOException | JSONException e) {
            System.err.println("Error: " + e.getMessage());
        }
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

    private static String readFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line.trim());
        }

        br.close();
        return sb.toString();
    }
}
