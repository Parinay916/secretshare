# Secretshare
Catalog Placements Assignment – Polynomial Secret Extraction


Submitted by

Name:Parinay Pandey  
 
Language Used:** Java  



---

##  Problem Statement Summary

- You are given `n` encoded (x, y) values.
- The `y` value is encoded using a specified base (e.g., base 2, base 10, base 16, etc.)
- You must decode the values and use **k** of them to compute the **secret constant term `c`** from a degree `k - 1` polynomial.
- The decoding and interpolation should be done correctly and efficiently.

---

 Files Included

| File Name        | Description                                |
|------------------|--------------------------------------------|
| `SecretSolver.java` | Java program that decodes input and calculates the secret |
| `testcase1.json` | First test case from the assignment        |
| `testcase2.json` | Second test case from the assignment       |
| `README.md`      | This file, with explanation and instructions |

---

##  How to Run the Code

Requirements:
- Java 8+
- `json.jar` (org.json library) 

Compile and Run:

```bash
javac -cp .:json.jar SecretSolver.java
java -cp .:json.jar SecretSolver
