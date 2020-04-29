package com.ascri.koinsample

import com.ascri.koinsample.utils.di.appModules
import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules

@Category(CheckModuleTest::class)
class CheckKoinModule {
    @Test
    fun checkCoffeeModule() = checkModules { modules(appModules) }
}
