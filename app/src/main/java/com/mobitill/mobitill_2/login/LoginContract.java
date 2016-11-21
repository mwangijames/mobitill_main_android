package com.mobitill.mobitill_2.login;

import com.mobitill.mobitill_2.BasePresenter;
import com.mobitill.mobitill_2.BaseView;

/**
 * Created by DI on 8/5/2016.
 */
public class LoginContract {
    interface View extends BaseView<Presenter>{
        void showProgress(boolean show);
        void showEmptyUsernameError();
        void showEmptyPasswordError();
        void showLoginFailError();
        void showTaskActivity();
        void setTitle();
        boolean isActive();

    }

    interface Presenter extends BasePresenter {
        void login(String username, String password);
        void automaticallyLogin();
    }
}
