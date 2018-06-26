package com.example.dh.tpmusicagrupo3.Controller.DatosControllers;

import android.support.v4.app.Fragment;

import java.util.List;

public abstract class TypeController <T>{
    private List<T> data;
    private Fragment fragment;

    public List<T> getData() {
        return data;
    }

    public abstract Fragment getFragment();

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
