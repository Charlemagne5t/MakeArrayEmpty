import org.junit.Assert;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void test1(){
        int[] nums = {5,6,9,2,3,7,4,8};
        long expected = 14;
        long actual = new Solution().countOperationsToEmptyArray(nums);

        Assert.assertEquals(expected, actual);
    }
}
