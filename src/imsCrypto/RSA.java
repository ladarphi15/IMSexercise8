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
    n = p.multiply(q);
    ImsInteger phiN = p.subtract(ImsInteger.ONE).multiply(q.subtract(ImsInteger.ONE));
    d = e.getBezout(phiN)[0];
    if (d.compareTo(ImsInteger.ZERO) < 0) {
      d = d.mod(phiN);
    }
  }

  public RSA(final Integer bitlength, final boolean optimized) {
    initializeRandomPAndQ(bitlength);
    n = p.multiply(q);
    ImsInteger phiN = p.subtract(ImsInteger.ONE).multiply(q.subtract(ImsInteger.ONE));
    if (optimized) {
      e = ImsInteger.ONE.add(ImsInteger.ONE).pow(16).add(ImsInteger.ONE);
    } else {
      ImsInteger randomE;
      do {
        randomE = new ImsInteger(new BigInteger(phiN.bitLength(), new Random()));
      } while (randomE.compareTo(phiN) >= 0 || phiN.getGcd(randomE).compareTo(ImsInteger.ONE) != 0);
      e = randomE;
    }
    d = e.getBezout(phiN)[0];
    if (d.compareTo(ImsInteger.ZERO) < 0) {
      d = d.mod(phiN);
    }

//    System.out.println("phiN: " + phiN);
//    System.out.println("p: " + p);
//    System.out.println("q: " + q);
//    System.out.println("e: " + e);
//    System.out.println("n: " + n);
//    System.out.println("d: " + d);
  }

  private void initializeRandomPAndQ(final Integer bitlength) {
    final Integer halfedBitlength = bitlength / 2;
    p = this.generateRandomPrimeFromRange(halfedBitlength);
    q = this.generateRandomPrimeFromRange(halfedBitlength);
  }

  private ImsInteger generateRandomPrimeFromRange(final Integer length) {
    ProbabilityTest test = new MillerRabinTest();
    ImsInteger r;
    do {
      r = new ImsInteger(new BigInteger(length, new Random()));
    } while (!test.run(r, 100, false));

    return r;
  }

  public ImsInteger encrypt(ImsInteger key, ImsInteger value) {
    return value.modPow(key, n);
  }

  public ImsInteger decrypt(ImsInteger value) {
    return value.modPow(d, n);
  }

  public ImsInteger decryptOptimized(ImsInteger value) {
    return value.modPow(p, n).multiply(value.modPow(q, n));
  }

  public ImsInteger getN() {
    return n;
  }

  public ImsInteger getE() {
    return e;
  }
}

