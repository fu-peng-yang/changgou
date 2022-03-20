package shejimoshi;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: shejimoshi
 * @Author: yang
 * @CreateTime: 2021-08-18 06:20
 * @Description: 工厂模式
 */
public class MyFactory {

    public static final int TYPE_MI = 1;// 大米

    public static final int TYPE_YU = 2;// 油

    public static final int TYPE_SC = 3;// 蔬菜

    public static Food getFoods(int foodType) {

        switch (foodType) {

            case TYPE_MI:

                return new DaMi();

            case TYPE_YU:

                return new You();

            case TYPE_SC:

            default:

                return new ShuCai();

        }

    }

}

abstract class Food {

}

class DaMi extends Food {

}

class You extends Food {

}

class ShuCai extends Food {

}