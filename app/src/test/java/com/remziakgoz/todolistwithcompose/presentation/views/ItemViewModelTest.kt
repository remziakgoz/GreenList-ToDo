import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.remziakgoz.todolistwithcompose.MainCoroutineRule
import com.remziakgoz.todolistwithcompose.domain.model.Item
import com.remziakgoz.todolistwithcompose.domain.repository.TodoRepository
import com.remziakgoz.todolistwithcompose.presentation.views.ItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class ItemViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var mockRepository: TodoRepository

    private lateinit var viewModel: ItemViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = ItemViewModel(mockRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `saveItem() saves item in repository`() = runBlockingTest {
        val newItem = Item("New ToDo")

        viewModel.saveItem(newItem)

        verify(mockRepository).saveItem(newItem)
    }

    @Test
    fun deleteItem() = runBlockingTest {
        val item = Item(toDoName = "Test ToDo")

        viewModel.deleteItem(item)

        verify(mockRepository).deleteItem(item)
    }
}