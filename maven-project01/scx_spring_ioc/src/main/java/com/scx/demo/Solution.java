package com.scx.demo;

import java.util.LinkedList;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        s.restoreIpAddresses("25525511135");
    }

    List<String> res = new LinkedList<String>();

    public List<String> restoreIpAddresses(String s) {
        dfs(s, 0);
        return res;
    }

    public void dfs(String s, int start) {
        if (start == s.length() + 3) {
            if (validIP(s, start, s.length() - 1)) {
                res.add(s);
                return;
            }
            return;
        }
        for (int i = start; i < s.length(); i++) {
            if (validIP(s, start, i)) {
                s = s.substring(0, i + 1) + "." + s.substring(i + 1);
                dfs(s, i + 2);
                s = s.substring(0, i + 1) + s.substring(i + 2);
            } else {
                break;
            }
        }
    }

    // 判断字符串s在左闭⼜闭区间[start, end]所组成的数字是否合法
    private Boolean validIP(String s, int start, int end) {
        if (start > end) {
            return false;
        }
        if (s.charAt(start) == '0' && start != end) { // 0开头的数字不合法
            return false;
        }
        int num = 0;
        for (int i = start; i <= end; i++) {
            if (s.charAt(i) > '9' || s.charAt(i) < '0') { // 遇到⾮数字字符不合法
                return false;
            }
            num = num * 10 + (s.charAt(i) - '0');
            if (num > 255) { // 如果⼤于255了不合法
                return false;
            }
        }
        return true;
    }
}
