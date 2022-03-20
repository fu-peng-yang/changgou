package demo.racecondition;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.racecondition
 * @Author: yang
 * @CreateTime: 2021-04-30 11:13
 * @Description: 竞争条件
 */
public class Bank {
    private final double[] accounts;
    /*private Lock bankLock = new ReentrantLock();*///ReentrantLock implements the Lock interface
    private Lock bankLock;
    private Condition sufficientFunds;
    /**
     * Constructs the bank;
     * @param n the number of accounts
     * @param initialBalance the initial balance for each account
     */
    public Bank(int n,double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts,initialBalance);
        bankLock = new ReentrantLock();
        sufficientFunds = bankLock.newCondition();
    }

    /**
     * Transfers money from one account to another.
     * @param from the account to transfer from
     * @param to the account to transfer to
     * @param amount the amount to transfer
     */
    /*public void transfer(int from,int to,double amount){
        if (accounts[from] < amount){
            return;
        }
        System.out.println(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Total Balance: %10.2f%n",getTotalBalance());
    }*/
    public void transfer(int from,int to,double amount)throws InterruptedException{
        bankLock.lock();
        try {
            while (accounts[from] < amount)
                sufficientFunds.await();
            System.out.println(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
            sufficientFunds.signalAll();
        }finally {
            bankLock.unlock();
        }

    }

    public synchronized void transfer1(int from,int to,double amount)throws InterruptedException {
    while (accounts[from] < amount)
        wait();//wait on intrinsic object lock's single condition
        System.out.println(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
        notifyAll();//notify all threads waiting on the condition
    }
        /**
         * Gets the sum of all account balances.
         * @return to total balance
         */
    public synchronized double getTotalBalance(){
        /*bankLock.lock();
        try {
            double sum = 0;
            for (double a: accounts)
                sum += a;
            return sum;
        }finally {
            bankLock.unlock();
        }*/

        double sum = 0;
        for (double a: accounts)
            sum += a;
        return sum;
    }

    /**
     * Gets the number of accounts in the bank.
     * @return the number of accounts
     */
    public int size(){
        return accounts.length;
    }
}
