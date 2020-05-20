package com.example.tak_frontend;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

public class TakViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    protected String accessToken;
    protected String idToken;

    public TakViewModelFactory(Application applicationArg, Bundle args) {
        application = applicationArg;
        accessToken = args.getString("AuthToken");
        idToken = args.getString("IdToken");
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TakViewModel(application, accessToken, idToken);
    }
}

