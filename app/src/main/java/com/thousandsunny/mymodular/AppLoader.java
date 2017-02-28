package com.thousandsunny.mymodular;

import com.thousandsunny.module_login.LoginLoader;
import com.thousandsunny.module_mine.MineLoader;

/**
 * Created by Steve on 2017/2/28.
 */

public class AppLoader {

    public static void init() {
        LoginLoader.init();
        MineLoader.init();
    }
}
