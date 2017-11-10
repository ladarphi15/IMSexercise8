/**
 *
 */
package test;

import imsCrypto.RSA;
import org.junit.Test;


public class RSATest {
  RSA rsa = new RSA(1024, false);

  @Test
  public void testRSA() {
    System.out.println(rsa.getE() + "\n");
    System.out.println(rsa.getN() + "");
  }
}
