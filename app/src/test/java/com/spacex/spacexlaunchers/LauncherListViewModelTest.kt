package com.spacex.spacexlaunchers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.spacex.api.model.Launcher
import com.spacex.api.model.Links
import com.spacex.api.model.Patch
import com.spacex.spacexlaunchers.repository.LauncherListRepository
import com.spacex.spacexlaunchers.viewmodel.LauncherListViewModel
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LauncherListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var launcherListRepository: LauncherListRepository

    private var viewModel: LauncherListViewModel? = null

    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this)
        viewModel = LauncherListViewModel(launcherListRepository, Schedulers.trampoline(),
                Schedulers.trampoline(), CompositeDisposable()
        )
    }

    @Test
    fun testApiSuccess() {
        val launcherList =  mutableListOf(
                Launcher(
                        "1",
                        "FalconSat",
                        "2006-03-25T10:30:00+12:00",
                        "FalconSat",
                            Links(Patch("https://images2.imgbox.com/4f/e3/I0lkuJ2e_o.png", "https://images2.imgbox.com/4f/e3/I0lkuJ2e_o.png"))
                ),
            Launcher(
                "1",
                "FalconSat",
                "2006-03-25T10:30:00+12:00",
                "FalconSat",
                Links(Patch("https://images2.imgbox.com/4f/e3/I0lkuJ2e_o.png", "https://images2.imgbox.com/4f/e3/I0lkuJ2e_o.png"))
            ),
            Launcher(
                "1",
                "FalconSat",
                "2006-03-25T10:30:00+12:00",
                "FalconSat",
                Links(Patch("https://images2.imgbox.com/4f/e3/I0lkuJ2e_o.png", "https://images2.imgbox.com/4f/e3/I0lkuJ2e_o.png"))
            ),
            Launcher(
                "1",
                "FalconSat",
                "2006-03-25T10:30:00+12:00",
                "FalconSat",
                Links(Patch("https://images2.imgbox.com/4f/e3/I0lkuJ2e_o.png", "https://images2.imgbox.com/4f/e3/I0lkuJ2e_o.png"))
            )
        )

        Mockito.`when`(launcherListRepository.geLauncherList())
                .thenReturn(Single.just(launcherList))

        viewModel?.let {
            it.loadLauncherList()
            Assert.assertEquals(launcherList, it.ldOnLaunchersListLoaded.value)
            Assert.assertNull(it.ldOnError.value)
        }
    }

    @Test
    fun testApiError() {
        val throwable = Throwable("Api Error")

        Mockito.`when`(launcherListRepository.geLauncherList()).thenReturn(Single.create {

            it.onError(throwable)

        })

        viewModel?.let {
            it.loadLauncherList()
            Assert.assertNull(it.ldOnLaunchersListLoaded.value)
            Assert.assertEquals(throwable.message, it.ldOnError.value)
        }
    }

    @After
    fun afterTest() {
        viewModel = null
    }
}