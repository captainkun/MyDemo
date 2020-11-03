package com.jike.demo;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;


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

    /*
    给出两个非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照逆序的方式存储的，并且它们的每个节点只能存储一位数字。

    如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。

    您可以假设除了数字 0 之外，这两个数都不会以 0开头。

    示例：

    输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
    输出：7 -> 0 -> 8
    原因：342 + 465 = 807

    来源：力扣（LeetCode）
    链接：https://leetcode-cn.com/problems/add-two-numbers
     */
    @Test
    public void addTwoNumbers() {
        //[9]
        //[1,9,9,9,9,9,9,9,9,9]
        ListNode l1 = new ListNode();
        l1.val = 9;
//        l1.next = new ListNode(4, new ListNode(3, null));
        ListNode l2 = new ListNode();
        l2.val = 1;
        l2.next = new ListNode(9,
                new ListNode(9,
                        new ListNode(9,
                                new ListNode(9,
                                        new ListNode(9,
                                                new ListNode(9,
                                                        new ListNode(9,
                                                                new ListNode(9,
                                                                        new ListNode(9)))))))));
        ListNode listNode = addTwoNumbers(l1, l2);
        System.out.println(listNode);
    }

    private ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 获取ListNode内的值，按规则组装成正确的结果值
        BigDecimal num1 = getNum(l1, "");
        BigDecimal num2 = getNum(l2, "");

        // 相加后的结果
        BigDecimal result = num1.add(num2);

        // 对结果值进行规格封装到
        return getListNode(result);
    }

    private BigDecimal getNum(ListNode listNode, String numStr) {
        int val = listNode.val;
        ListNode next = listNode.next;
        numStr = numStr == null ? "" : numStr;
        if (next != null) {
            int nextVal = next.val;
            if ("".equals(numStr)) {
                numStr = numStr + val + nextVal;
            } else {
                numStr = numStr + nextVal;
            }
            return getNum(next, numStr);
        } else if ("".equals(numStr)) {
            numStr = numStr + val;
        }

        char[] chars = numStr.toCharArray();
        int length = chars.length;
        StringBuilder numSortStr = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char num = chars[length - 1 - i];
            numSortStr.append(num);
        }

        return new BigDecimal(numSortStr.toString());
    }

    private ListNode getListNode(BigDecimal result) {
        String resultStr = "" + result;
        char[] chars = resultStr.toCharArray();
        int length = chars.length;
        ListNode newListNode = new ListNode(chars[length - 1] - '0');
        for (int i = 1; i < length; i++) {
            ListNode nextListNode = new ListNode();
            nextListNode.val = chars[length - i - 1] - '0';
            ListNode nextNode = getNextNode(newListNode);
            nextNode.next = nextListNode;
        }
        return newListNode;
    }

    private ListNode getNextNode(ListNode listNode) {
        ListNode next = listNode.next;
        if (next != null) {
            return getNextNode(next);
        }
        return listNode;
    }




}
