package test;

import imsCrypto.ImsInteger;
import imsCrypto.RSA;
import imsCrypto.StopWatch;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Philipp Ladar <philipp.ladar@dccs.at>
 */
public class RSAPerformanceTest {

  @Test
  public void testOptimized() {
    RSA rsa = new RSA(1024, false);
    final ImsInteger encrypted = rsa.encrypt(rsa.getE(), ImsInteger.TEN);
    final ImsInteger decrypted = rsa.decryptOptimized(encrypted);
    Assert.assertTrue(decrypted.compareTo(ImsInteger.TEN) == 0);
  }

  public static void main(String[] args) {

    try (final Scanner in = new Scanner(System.in)) {

      Integer bits = null;
      System.out.println("\nPlease specify the number of bits for n: ");
      bits = new Integer(in.nextLine());

      System.out.println("\nPlease if decryption should be executed optimzed [Y/n]:");
      boolean optimzed = "y".equals(in.nextLine().toLowerCase());
      if (optimzed)
        System.out.println("\nTests will be run optimized");

      System.out.println("\nPlease specify how many testruns should be executed:");
      Integer testruns = new Integer(in.nextLine());

      final Random ran = new Random();
      final ImsInteger[] encryptedValues = new ImsInteger[testruns];
      StopWatch watch = new StopWatch();
      watch.start();
      RSA rsa = new RSA(bits, optimzed);

      for (Integer i = 0; i < testruns; i++) {
        final BigInteger randomNum = new BigInteger(2048, ran);
        final ImsInteger encrypted = rsa.encrypt(rsa.getE(), new ImsInteger(randomNum));
        encryptedValues[i] = encrypted;
      }
      watch.stop();
      System.out.println("\nElapsed time for encryption: " + watch.getElapsedTime());

      watch.reset();
      watch.start();
      for (ImsInteger encryptedValue : encryptedValues) {
        if (optimzed) {
          rsa.decryptOptimized(encryptedValue);
        } else {
          rsa.decrypt(encryptedValue);
        }
      }
      watch.stop();
      System.out.println("\nElapsed time for decryption: " + watch.getElapsedTime());

    } finally {
      System.out.println("Program finished...");
    }
  }
}
