package com.softsandr.sortvisual.di.fragment

interface FragmentComponentBuilder<M : FragmentModule<*>, C : FragmentComponent<*>> {

    fun fragmentModule(fragmentModule: M): FragmentComponentBuilder<M, C>

    fun build(): C
}