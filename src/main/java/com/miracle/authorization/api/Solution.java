package com.miracle.authorization.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * Created at 2021/9/22 17:58
 **/
public class Solution {

    public ListNode[] splitListToParts(ListNode head, int k) {
        if (head == null) {
            return new ListNode[k];
        }
        int size = 0;
        ListNode temp = head;
        while (temp != null) {
            size++;
            temp = temp.next;
        }


        return null;
    }
}



class ListNode {

    int val;
    ListNode next;
}
