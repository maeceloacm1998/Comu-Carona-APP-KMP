package org.app.marcelodev.comucarona.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("com.jetbrains.kmpapp.screens")
class ViewModelModule

@Module
@ComponentScan("com.jetbrains.kmpapp.native")
expect class NativeModule()

@Module(includes = [ViewModelModule::class, NativeModule::class])
class AppModule
