import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public long countOperationsToEmptyArray(int[] nums) {
        long res = 0L;
        int n = nums.length;
        int[] arr = new int[n];
        Arrays.fill(arr, 1);
        SegmentTree st = new SegmentTree(arr);
        int[][] valuesIndexes = new int[n][2];
        for(int i = 0; i < n; i++){
            valuesIndexes[i][0] = nums[i];
            valuesIndexes[i][1] = i;
        }
        Arrays.sort(valuesIndexes, Comparator.comparingInt(a-> a[0]));
        res = valuesIndexes[0][1] + 1;
        st.update(valuesIndexes[0][1], 0);
        int prev = valuesIndexes[0][1];
        for(int i = 1; i < n; i++){
            int ind = valuesIndexes[i][1];
            if(ind > prev){
                res += st.sumRange(prev, ind);
            }else {
                res += st.sumRange(prev, n - 1);
                res += st.sumRange(0, ind);
            }
            st.update(ind, 0);
            prev = ind;
           // System.out.println("ind : " + ind + " res = " + res);
        }
        return res;
    }
}
class SegmentTree {
    int length;
    int[] segmentTree;
    int[] nums;
    int n;

    public SegmentTree(int[] nums) {
        this.nums = nums;
        n = nums.length;
        if ((n != 1) && ((n & (n - 1)) == 0)) {
            length = n * 2 - 1;
        } else {
            int power = 1;
            while (power < n) {
                power *= 2;
            }
            length = power * 2 - 1;
        }
        segmentTree = new int[length];
        buildTree(0, n - 1, 0);
    }

    public void buildTree(int low, int high, int position) {
        if (low == high) {
            segmentTree[position] = nums[low];
            return;
        }
        int mid = low + (high - low) / 2;

        buildTree(low, mid, 2 * position + 1);
        buildTree(mid + 1, high, 2 * position + 2);
        segmentTree[position] = segmentTree[2 * position + 1] + segmentTree[2 * position + 2];
    }

    public void update(int index, int val) {
        updateTree(0, n - 1, 0, index, val);
    }

    public void updateTree(int low, int high, int position, int index, int val) {
        
        if (index < low || index > high) {
            return;
        }

        if (low == high) {
            nums[index] = val;
            segmentTree[position] = val;
            return;
        }

       
        int mid = low + (high - low) / 2;
        updateTree(low, mid, 2 * position + 1, index, val);
        updateTree(mid + 1, high, 2 * position + 2, index, val);

        
        segmentTree[position] = segmentTree[2 * position + 1] + segmentTree[2 * position + 2];
    }

    public int sumRange(int left, int right) {

        return rangeSumQuery(0, n - 1, 0, left, right);
    }

    public int rangeSumQuery(int low, int high, int position, int queryLow, int queryHigh) {
        
        if (low > queryHigh || high < queryLow) {
            return 0;
        }
      
        if (low >= queryLow && high <= queryHigh) {
            return segmentTree[position];
        }

        int mid = low + (high - low) / 2;
        int leftSum = rangeSumQuery(low, mid, 2 * position + 1, queryLow, queryHigh);
        int rightSum = rangeSumQuery(mid + 1, high, 2 * position + 2, queryLow, queryHigh);

        return leftSum + rightSum;
    }
}

