package com.mobitill.mobitill_2.data.models.users;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DI on 8/9/2016.
 */
@Module
public class UserModule {

    @Provides
    UserParams provideUserParams(){
        return new UserParams();
    }

    @Provides
    User provideUser(){
        return new User();
    }

    @Provides
    UserData provideUserDataData(){
        return new UserData();
    }

    @Provides
    UserResponse provideUserResponse(){
        return new UserResponse();
    }

    @Provides
    Error provideError(){
        return new Error();
    }

    @Provides
    ErrorMessage provideErrorMessage(){
        return new ErrorMessage();
    }

}
