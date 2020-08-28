package com.boco.app.core.widget.wcviewpager;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

public abstract class ObjectAtPositionPagerAdapter extends PagerAdapter implements ObjectAtPositionInterface {
    protected SparseArray<Object> objects = new SparseArray<>();

    @Override
    public final Object instantiateItem(ViewGroup container, int position) {
        Object object = instantiateItemObject(container, position);
        objects.put(position, object);
        return object;
    }

    /**
     * Replaces @see PagerAdapter#instantiateItem and handles objects tracking for getObjectAtPosition
     */
    public abstract Object instantiateItemObject(ViewGroup container, int position);

    @Override
    public final void destroyItem(ViewGroup container, int position, Object object) {
        objects.remove(position);
        destroyItemObject(container, position, object);
    }

    /**
     * Replaces @see PagerAdapter#destroyItem and handles objects tracking for getObjectAtPosition
     */
    public abstract void destroyItemObject(ViewGroup container, int position, Object object);


    @Override
    public Object getObjectAtPosition(int position) {
        return objects.get(position);
    }
}