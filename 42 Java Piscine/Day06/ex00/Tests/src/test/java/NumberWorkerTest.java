import edu.school21.numbers.IllegalNumberException;
import edu.school21.numbers.NumberWorker;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NumberWorkerTest {
    private NumberWorker numberWorker;

    @BeforeEach
    public void createNumberWorker() {
        numberWorker = new NumberWorker();
    }

    @ParameterizedTest
    @ValueSource(ints = {557, 313, 53})
    public void isPrimeForPrime(int number) {
        Assert.assertTrue(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 621, 265})
    public void isPrimeForNotPrimes(int number) {
        Assert.assertFalse(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, -1000})
    public void isPrimeForIncorrectNumbers(int number) {
        assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void checkSumDigit(int number, int sum) {
        Assert.assertEquals(sum, numberWorker.digitSum(number), 1e-9);
    }
}
