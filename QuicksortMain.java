import java.util.Arrays;

public class QuicksortMain {
    public static void main(String[] args) {
        Sorter sorter = new Quicksorter();
        final int[] l1 = new int[]{3, 1, 2, 5, 4, 7, 8, 6};
        sorter.sort(l1);
        System.out.println(Arrays.toString(l1));

        sorter = new ThreeWayQuicksorter();
        final int[] l2 = new int[]{3, 3, 2, 2, 4, 7, 7, 6};
        sorter.sort(l2);
        System.out.println(Arrays.toString(l2));
    }

    private static interface Sorter {
        void sort(int[] nums);
    }

    private static final class Quicksorter implements Sorter {
        @Override
        public void sort(int[] nums) {
            final int len = nums.length;
            if (len <= 1) {
                return;
            }
            sort(nums, 0, len - 1);
        }

        private void sort(int[] nums, int lhs, int rhs) {
            if (lhs >= rhs) {
                return;
            }
            final int idx = partition(nums, lhs, rhs, lhs);
            sort(nums, lhs, idx - 1);
            sort(nums, idx + 1, rhs);
        }

        private int partition(int[] nums, int lhs, int rhs, int idx) {
            final int pivot = nums[idx];
            swap(nums, idx, rhs);
            int cur = lhs;
            for (int i = lhs; i < rhs; i++) {
                if (nums[i] <= pivot) {
                    swap(nums, cur, i);
                    cur++;
                }
            }
            swap(nums, rhs, cur);
            return cur;
        }

        private void swap(int[] nums, int src, int dst) {
            final int tmp = nums[src];
            nums[src] = nums[dst];
            nums[dst] = tmp;
        }
    }

    private static final class ThreeWayQuicksorter implements Sorter {
        @Override
        public void sort(int[] nums) {
            final int len = nums.length;
            if (len <= 1) {
                return;
            }
            sort(nums, 0, len);
        }

        private void sort(int[] nums, int lhs, int rhs) {
            // TODO
        }
    }
}
