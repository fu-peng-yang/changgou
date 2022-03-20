package niuke.easy.rukoujiedian;

import sun.plugin2.util.NativeLibLoader;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Administrator
 * 链表中环的入口结点
 */
public class Solution {
    /**
     * 给一个长度为n链表,若其中包含环,请找出该链表的环的入口节点,否则,返回null
     * @param pHead
     * @return
     */
    public static ListNode entryNodeOfLoop(ListNode pHead){
        ListNode pos = pHead;
        //哈希表记录访问过的节点
        Set<ListNode> visited = new HashSet<>();
        while (pos != null){
            //判断节点是否被访问
            if (visited.contains(pos)){
                return pos;
            }else {
                //结点记录添加到哈希表中
                visited.add(pos);
            }
            //遍历

            pos = pos.next;
        }
        return null;
    }

    /**
     * 判断链表中是否有环,如果有环则返回true,否则返回false
     */
    public boolean hasCycle(ListNode head){

        ListNode pos = head;
        Set<ListNode> set = new HashSet<>();
        while (pos!=null){
            if (set.contains(pos)){
                return true;
            }else {
                set.add(pos);
            }
            pos = pos.next;
        }
        return false;
    }

    /**
     * 买股票的最好时机
     * 假设有一个数组prices,长度为n,其中prices[i]是股票在第i天的价格,
     * 请跟进这个价格数组,返回买卖股票能获得的最大收益
     * 1.可以买入一次股票和卖出一个股票,并非每天都可以买入或卖出一次,
     * 总共只能买入和卖出一次,且买入必须在卖出的前面的某一天
     * 2.如果不能获取到任何利润,请返回0
     * 3.假设买入卖出均无手续费
     */
    public static int maxProfit(int[] prices){
        int ans  = 0;
        int len= prices.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j<len;j++){
                int tmp_ans = prices[j]-prices[i];
                if (tmp_ans>ans){
                    ans = tmp_ans;
                }
            }

        }
        return ans;
    }

    /**
     * 给定一个二叉树root和一个值sum,判断是否有从根节点到叶子节点的节点值之和等于sum的路径.
     * 1.该题路径定义为从树的根节点开始往下一直到叶子节点锁经过的节点
     * 2.叶子节点是指没有子节点的节点
     * 3.路径只能从父节点到子节点,不能从子节点到父节点
     * 4.总节点数目为n
     */

    public boolean hasPathSum(TreeNode root, int sum){
        //递归终止条件
        if (root == null){
            return false;
        }
        //每次递归都减掉经过的节点的值
        sum -= root.val;
        //当前节点是叶子节点,并且sum刚好为0,表明该路径和刚好为sum
        if (sum ==0 && root.left==null && root.right==null){
            //保证能递归每个节点,且只需要有一条路径满足即可
            //本质上是DFS深度优先遍历,只是由递归实现
            return true;
        }
        return hasPathSum(root.left,sum) || hasPathSum(root.right,sum);
    }

    /**
     *给定一个升序排序的数组,将其转化为平衡二叉搜索树(BST)
     * 平衡二叉搜索树指树上每个节点node都满足左子树中所有节点的值都小于node的值,
     * 右子树中所有节点的值都大于node的值,并且左右子树的节点数量之差不大于1
     */
    public TreeNodes sortedArrayToBST(int[] num){
        if (num.length ==0 || num==null){
            return null;
        }
        return buildAvlTree(num,0,num.length-1);
    }

    public TreeNodes buildAvlTree(int[] num,int start,int end){
        if (start == end){
            return new TreeNodes(num[start]);
        }
        if (start > end){
            return null;
        }
        //不能写成(start+end)/2,防止start+end越界超出int范围
        //是num的中位  所以要+1
        int mid = start + (end-start+1)/2;
        TreeNodes root = new TreeNodes(num[mid]);
        root.left = buildAvlTree(num,start,mid-1);
        root.right = buildAvlTree(num, mid + 1, end);
        return root;
    }

    /**
     * 二叉树的最大深度
     */
    public int maxDepth(TreeNode root){
        if (root==null){
            return 0;
        }
        return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
    }
    public static void main(String[] args) {
        int[] s =new int[]{};
        int i = maxProfit(new int[]{2, 7, 0, 6, 5});
        System.out.println(i);
    }
}
