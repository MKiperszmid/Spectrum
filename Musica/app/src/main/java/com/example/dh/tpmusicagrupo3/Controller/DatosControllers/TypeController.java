package com.example.dh.tpmusicagrupo3.Controller.DatosControllers;

import android.support.v4.app.Fragment;

import java.util.List;

public abstract class TypeController<T> {
    public static final String KEY_T = "keytemplate";
    private T data;
    private Fragment fragment;

    public TypeController(T data) {
        this.data = data;
        this.fragment = getFragment();
    }

    public T getData() {
        return data;
    }

    public abstract Fragment getFragment();

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
