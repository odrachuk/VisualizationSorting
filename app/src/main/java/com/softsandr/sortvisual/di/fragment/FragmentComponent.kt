package com.softsandr.sortvisual.di.fragment

import android.support.v4.app.Fragment

import dagger.MembersInjector

interface FragmentComponent<F : Fragment> : MembersInjector<F>