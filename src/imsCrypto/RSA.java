package imsCrypto;

import java.math.BigInteger;
import java.util.Random;

public class RSA {
  private ImsInteger p, q, n, e, d;

  /**
   * For Tests only
   *
   * @param p
   * @param q
   * @param e
   */
  public RSA(final ImsInteger p, final ImsInteger q, final ImsInteger e) {
    this.p = p;
    this.q = q;
    this.e = e;
  }

  public RSA(final Integer bitlength, final boolean optimized) {
    initializeRandomPAndQ(bitlength);
    n = p.multiply(q);
    ImsInteger eMaxRange = p.multiply(q);
    ImsInteger randomE;
    do {
      randomE = new ImsInteger(new BigInteger(eMaxRange.bitLength(), new Random()));
    } while (randomE.compareTo(eMaxRange) >= 0 && eMaxRange.getGcd(randomE).compareTo(ImsInteger.ONE) != 0);
    e = randomE;
    d = e.getBezout(e);
  }

  private void initializeRandomPAndQ(final Integer bitlength) {
    final Integer halfedBitlength = bitlength / 2;
    p = this.generateRandomPrimeFromRange(halfedBitlength);
    q = this.generateRandomPrimeFromRange(halfedBitlength);
  }

  public ImsInteger generateRandomPrimeFromRange(final Integer length) {
    ProbabilityTest test = new MillerRabinTest();
    ImsInteger r;
    do {
      r = new ImsInteger(new BigInteger(length, new Random()));
    } while (!test.run(r, 44, false));

    return r;
  }

  private void createPrivateKey() {

  }

  public void encrypt(ImsInteger key) {

  }

  public ImsInteger getN() {
    return n;
  }

  public ImsInteger getE() {
    return e;
  }
}
