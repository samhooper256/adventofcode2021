package utils;

public final class Maths {

	private Maths() {
		
	}
	
	public static int max(int... nums) {
		if(nums.length == 0)
			throw new IllegalArgumentException("nums.length == 0");
		int max = nums[0];
		for(int i : nums)
			max = Math.max(i, max);
		return max;
	}
	
}
