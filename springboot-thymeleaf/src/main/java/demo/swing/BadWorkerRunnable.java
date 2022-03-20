package demo.swing;

import javax.swing.*;
import java.util.Random;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.swing
 * @Author: yang
 * @CreateTime: 2021-05-01 09:14
 * @Description:
 */
public class BadWorkerRunnable implements Runnable {
    private JComboBox<Integer> combo;
    private Random generator;

    public BadWorkerRunnable(JComboBox<Integer> acombo) {
        this.combo = acombo;
        generator= new Random();
    }

    @Override
    public void run() {
        try {
            while (true){
                int i = Math.abs(generator.nextInt());
                if (i % 2 == 0){
                    combo.insertItemAt(i,0);
                }else if (combo.getItemCount() >0){
                    combo.removeItemAt(i % combo.getItemCount());
                }
                Thread.sleep(1);
            }
        }catch (InterruptedException e){

        }

    }
}
