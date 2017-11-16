package test;

import imsCrypto.ImsInteger;
import imsCrypto.RSA;
import org.junit.Assert;
import org.junit.Test;

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
}
