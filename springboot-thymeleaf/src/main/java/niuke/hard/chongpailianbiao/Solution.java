package niuke.hard.chongpailianbiao;

/**
 * @author Administrator
 * 重排链表
 * 使用原地算法,不能只改变节点内部的值,需要对实际的节点进行交换
 */
public class Solution {

    public static void reorderList(ListNode head){
        if (head == null || head.next ==null){
            return;
        }
        //找中点,链表分成两个
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast =fast.next.next;
        }
        ListNode newHead = slow.next;
        slow.next = null;
        //第二个链表倒置,反转后半部分链表
        newHead =reverseList(newHead);
        //链表节点依次连接
        while (newHead != null){
            ListNode temp = newHead.next;
            newHead.next = head.next;
            head.next = newHead;
            head = newHead.next;
            newHead = temp;
        }
    }
    public static ListNode reverseList(ListNode head){
        if (head == null){
            return null;
        }
        ListNode tail = head;
        head = head.next;
        tail.next = null;
        while (head != null){
            ListNode temp = head.next;
            head.next = tail ;
            tail = head;
            head =temp;
        }
        return tail;
    }
    public static void main(String[] args) {




    }
}
