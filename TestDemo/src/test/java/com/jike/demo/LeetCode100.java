package com.jike.demo;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * @author qukun
 * @description LeetCode前100题
 * @date 2020-11-02
 */

public class LeetCode100 {

    /*
    给定一个整数数组 nums和一个目标值 target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
    你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
    示例：
    给定 nums = [2, 7, 11, 15], target = 9

    因为 nums[0] + nums[1] = 2 + 7 = 9
    所以返回 [0, 1]

    链接：https://leetcode-cn.com/problems/two-sum
     */
    @Test
    public void twoSum() {
        // 初始化数组的值
        int arraySize = 100000;
        int target = 9527;
        int[] array = new int[arraySize];
        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            int randomNum = random.nextInt();
            array[i] = randomNum;
        }
        array[arraySize - 1] = 9000;
        array[arraySize - 2] = 527;

        System.out.println("************方法一************");
        // 执行方法一逻辑
        Instant now = Instant.now();
        int[] ints = twoSum_method1(array, target);
        System.out.println("运行时长：" + Duration.between(now, Instant.now()).toMillis());

        // 方法一结果输出
        for (int i = 0; i < ints.length; i++) {
            System.out.println("第" + i + "索引为：" + ints[i]);
        }

        System.out.println("************方法二************");
        // 执行方法一逻辑
        Instant now2 = Instant.now();
        int[] ints2 = twoSum_method2(array, target);
        System.out.println("运行时长：" + Duration.between(now2, Instant.now()).toMillis());

        // 方法一结果输出
        for (int i = 0; i < ints2.length; i++) {
            System.out.println("第" + i + "索引为：" + ints[i]);
        }

    }

    private int[] twoSum_method1(int[] nums, int target) {
        int forNums = 0;
        int length = nums.length;
        for (int i = 0; i < length - 1; i++) {
            int firstNum = nums[i];
            for (int j = i + 1; j < length; j++) {
                forNums++;
                int secondNum = nums[j];
                if (firstNum + secondNum == target) {
                    System.out.println("遍历次数：" + forNums);
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }

    private int[] twoSum_method2(int[] nums, int target) {
        Map<Integer, Integer> numAndIndexMap = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            Integer index1 = numAndIndexMap.get(target - nums[i]);
            if (index1 != null) {
                return new int[]{index1, i};
            }
            numAndIndexMap.put(nums[i], i);
        }
        return new int[0];
    }

    @Test
    public void test02() {
        
    }




}
