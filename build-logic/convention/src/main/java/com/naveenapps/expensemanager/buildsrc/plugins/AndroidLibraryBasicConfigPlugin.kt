package com.naveenapps.expensemanager.buildsrc.plugins

import com.android.build.gradle.LibraryExtension
import com.naveenapps.expensemanager.buildsrc.extensions.configureAndroid
import com.naveenapps.expensemanager.buildsrc.extensions.configureBuildFeatures
import com.naveenapps.expensemanager.buildsrc.extensions.configureJVM
import com.naveenapps.expensemanager.buildsrc.extensions.configureJacoco
import com.naveenapps.expensemanager.buildsrc.extensions.configureTestOptions
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryBasicConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("jacoco")
            }

            extensions.configure<LibraryExtension> {
                configureJVM()
                configureAndroid()
                configureBuildFeatures()
                configureJacoco()
                configureTestOptions(this)
            }
        }
    }
}
