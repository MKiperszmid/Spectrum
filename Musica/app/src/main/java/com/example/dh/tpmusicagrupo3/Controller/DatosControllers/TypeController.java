package com.example.dh.tpmusicagrupo3.Controller.DatosControllers;

import android.support.v4.app.Fragment;

import java.util.List;

public abstract class TypeController <T>{
    private List<T> data;
    private Fragment fragment;

    public TypeController(List<T> data) {
        this.data = data;
        this.fragment = getFragment();
    }

    public List<T> getData() {
        return data;
    }

    public abstract Fragment getFragment();

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
