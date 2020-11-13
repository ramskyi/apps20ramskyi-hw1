package ua.edu.ucu.tempseries;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysisTest {
    public TemperatureSeriesAnalysis noArrayResult;
    public TemperatureSeriesAnalysis isArrayResult;


    @Before
    public void init() {
        noArrayResult = new TemperatureSeriesAnalysis();
        isArrayResult = new TemperatureSeriesAnalysis(
                new double[] {13, -8, 2, 1, 12, 11, -4, -13, 11, -8, 9, -2, -14, 10, -1, 8, 9, 2, 3, -9});
    }

    // Testing with no array

    @Test (expected = InputMismatchException.class)
    public void testEmptyConstructor() {
        new TemperatureSeriesAnalysis(new double[] {-1000});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverageWithNoArray() {
        noArrayResult.average();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeviationWithNoArray() {
        noArrayResult.deviation();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinWithNoArray() {
        noArrayResult.min();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxWithNoArray() {
        noArrayResult.max();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testClosestToZeroWithNoArray() {
        noArrayResult.findTempClosestToZero();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindLessThanWithNoArray() {
        noArrayResult.findTempsLessThan(0);
    }

    @Test
    public void testAddTempsWithNoArray() {
        double expResult = 15.0;
        double actualResult = noArrayResult.addTemps(5, 1, 2, 4, 3);
        assertEquals(expResult, actualResult, 1e-6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSummaryStatisticsWithNoArray() {
        noArrayResult.summaryStatistics();
    }

    // Testing with present array

    @Test
    public void testAddTemps() {
        double expResult = 47.0;  // because 32 is already a sum of array + 15 added
        double actualResult = isArrayResult.addTemps(5, 1, 2, 4, 3);
        assertEquals(expResult, actualResult, 1e-6);

    }

    @Test
    public void testAverage() {
        double expResult = 1.6;  // 32 / 20
        double actualResult = isArrayResult.average();
        assertEquals(expResult, actualResult, 1e-6);
    }

    @Test
    public void testDeviation() {
        double expResult = 8.493526947;
        double actualResult = isArrayResult.deviation();
        assertEquals(expResult, actualResult, 1e-6);
    }

    @Test
    public void testMin() {
        double expResult = -14;
        double actualResult = isArrayResult.min();
        assertEquals(expResult, actualResult, 1e-6);
    }

    @Test
    public void testMax() {
        double expResult = 13;
        double actualResult = isArrayResult.max();
        assertEquals(expResult, actualResult, 1e-6);
    }

    @Test
    public void testFindTempClosestToZero() {
        double expResult = 1;
        double actualResult = isArrayResult.findTempClosestToZero();
        assertEquals(expResult, actualResult, 1e-6);
    }

    @Test
    public void testFindTempClosestToValue() {
        double expResult = 3;
        double actualResult = isArrayResult.findTempClosestToValue(3);
        assertEquals(expResult, actualResult, 1e-6);
    }

    @Test
    public void testFindTempsGreaterThan() {
        double[] expResult = new double[] {13, 12, 11, 11, 9, 10, 8, 9};
        double[] actualResult = isArrayResult.findTempsGreaterThan(3);
        assertArrayEquals(expResult, actualResult, 1e-6);
    }

    @Test
    public void testSummaryStatistics() {
        double expAvg = 1.6;
        double expDev = 8.493526947;
        double expMin = -14;
        double expMax = 13;
        TempSummaryStatistics actualResult = isArrayResult.summaryStatistics();
        assertArrayEquals(new double[] {expAvg, expDev, expMin, expMax},
                new double[] {actualResult.getAvgTemp(), actualResult.getDevTemp(), actualResult.getMinTemp(), actualResult.getMaxTemp()},
                1e-6);
    }
}
