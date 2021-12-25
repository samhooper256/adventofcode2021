package utils;

public final class Arrs {

	private Arrs() {
		
	}
	
	public static boolean contains(int[] nums, int n) {
		return indexOf(nums, n) >= 0;
	}
	
	public static int indexOf(int[] nums, int n) {
		for(int i = 0; i < nums.length; i++)
			if(nums[i] == n)
				return i;
		return -1;
	}
	
}
