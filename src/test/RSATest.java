/**
 *
 */
package test;

import imsCrypto.ImsInteger;
import imsCrypto.RSA;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class RSATest {
  private RSA rsa_33;
  private RSA rsa_35;

  @Before
  public void setUp() {
    rsa_33 = new RSA(new ImsInteger("3"), new ImsInteger("11"), new ImsInteger("3"));
    rsa_35 = new RSA(new ImsInteger("5"), new ImsInteger("7"), new ImsInteger("19"));
  }

  @Test
  public void test1EncryptMod33() {
    final ImsInteger encrypted = rsa_33.encrypt(rsa_33.getE(), new ImsInteger("5"));
    final ImsInteger decrypted = rsa_33.decrypt(encrypted);

    Assert.assertTrue(encrypted.compareTo(new ImsInteger("26")) == 0);
    Assert.assertTrue(decrypted.compareTo(new ImsInteger("5")) == 0);
  }

  @Test
  public void test2EncryptMod33() {
    final ImsInteger encrypted = rsa_33.encrypt(rsa_33.getE(), new ImsInteger("25"));
    final ImsInteger decrypted = rsa_33.decrypt(encrypted);

    Assert.assertTrue(encrypted.compareTo(new ImsInteger("16")) == 0);
    Assert.assertTrue(decrypted.compareTo(new ImsInteger("25")) == 0);
  }

  @Test
  public void test1EncryptMod35() {
    final ImsInteger encrypted = rsa_35.encrypt(rsa_35.getE(), new ImsInteger("12"));
    final ImsInteger decrypted = rsa_35.decrypt(encrypted);

    Assert.assertTrue(encrypted.compareTo(new ImsInteger("33")) == 0);
    Assert.assertTrue(decrypted.compareTo(new ImsInteger("12")) == 0);
  }

  @Test
  public void test2Encrypt5od33() {
    final ImsInteger encrypted = rsa_35.encrypt(rsa_35.getE(), new ImsInteger("32"));
    final ImsInteger decrypted = rsa_35.decrypt(encrypted);

    Assert.assertTrue(encrypted.compareTo(new ImsInteger("18")) == 0);
    Assert.assertTrue(decrypted.compareTo(new ImsInteger("32")) == 0);
  }

  @Test
  public void testRandomPandQ() {
    final RSA rsa = new RSA(1024, false);
    final ImsInteger encrypted = rsa.encrypt(rsa.getE(), new ImsInteger("3"));
    final ImsInteger decrypted = rsa.decrypt(encrypted);
    Assert.assertTrue(decrypted.compareTo(new ImsInteger("3")) == 0);
  }
}
