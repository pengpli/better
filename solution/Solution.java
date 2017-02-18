/*
*Write a program to reverse every k nodes of a linked list.
*If the list size is not a multiple of k, then leave the remainder nodes as is.
 
*Example:
*Inputs:  1->2->3->4->5->6->7->8->NULL and k = 3 
*Output:  3->2->1->6->5->4->7->8->NULL
*/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *
 *     ListNode(int x) {
 *       val = x; 
 *     }
 * }
 */
public class Solution {

	public static ListNode reverseKGroup(ListNode head, int k) {
		if (head == null || k == 1)
			return head;
		ListNode dummy = new ListNode(0);
		dummy.next = head;
		ListNode pre = dummy;
		int i = 0;
		while (head != null) {
			i++;
			if (i % k == 0) {
				pre = reverse(pre, head.next);
				head = pre.next;
			} else {
				head = head.next;
			}
		}
		return dummy.next;
	}

	private static ListNode reverse(ListNode pre, ListNode next) {
		ListNode last = pre.next;// where first will be doomed "last"
		ListNode cur = last.next;
		while (cur != next) {
			last.next = cur.next;
			cur.next = pre.next;
			pre.next = cur;
			cur = last.next;
		}
		return last;
	}

}
