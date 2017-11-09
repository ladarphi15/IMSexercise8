package imsCrypto;


import java.math.BigInteger;

public class MillerRabinTest extends ProbabilityTest {

  /**
   * @param possiblePrime holds the number to check for primality
   * @param testNumber    is the test number used for the test execution
   * @return true if the test was passed, false if the test failed
   */

  public static final ImsInteger TWO = new ImsInteger(new BigInteger(String.valueOf(2)));
  public static final ImsInteger MINUS_ONE = new ImsInteger(new BigInteger(String.valueOf(-1)));

  boolean runOnce(ImsInteger possiblePrime, ImsInteger testNumber) {

    // The initial step of Miller-Rabin is a Fermat test
    ImsInteger exponent = possiblePrime.subtract(ImsInteger.ONE);
    boolean fermat = testNumber.modPow(exponent, possiblePrime).compareTo(ImsInteger.ONE) == 0;

    if (fermat) {
      // the exponent p-1 is now consecutively halfed
      ImsInteger divider = TWO;
      while (true) {
        final ImsInteger[] newExponent = exponent.divideAndRemainder(divider);
        if (newExponent[1].compareTo(ImsInteger.ZERO) != 0) {
          return true;
        }

        final ImsInteger resultOfRoot = testNumber.modPow(newExponent[0], possiblePrime);
        final ImsInteger standardName = MINUS_ONE.mod(possiblePrime);
        if (resultOfRoot.compareTo(standardName) == 0) {
          return true;
        }
        if (resultOfRoot.compareTo(ImsInteger.ONE) == 0) {
          divider = divider.multiply(TWO);
          continue;
        }
        return false;
      }

    }
    return false;
  }
}
