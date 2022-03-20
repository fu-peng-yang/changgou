package demo.racecondition;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.racecondition
 * @Author: yang
 * @CreateTime: 2021-04-30 11:29
 * @Description:
 */
public class UnsynchBankTest {
    public static final int NACCOUNTS = 100;
    public static final double INITIAL_BALANCE = 1000;
    public static final double MAX_AMOUNT = 1000;
    public static final int DELAY= 10;

    public static void main(String[] args) {
        Bank bank = new Bank(NACCOUNTS,INITIAL_BALANCE);
        for (int i = 0;i<NACCOUNTS;i++){
            int fromAccount = i;
            Runnable r = () ->{
                try {
                while (true){
                    int toAccount = (int) (bank.size() *Math.random());
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer1(fromAccount,toAccount,amount);

                        Thread.sleep((int)(DELAY*Math.random()));
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            Thread t = new Thread(r);
            t.start();
        }
    }
}
