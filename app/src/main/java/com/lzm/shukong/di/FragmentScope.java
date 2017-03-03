package com.lzm.shukong.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2017/2/19.
 */
@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
    
    
}
