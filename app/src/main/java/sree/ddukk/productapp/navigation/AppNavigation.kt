import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import sree.ddukk.productapp.domain.ProductRepository
import sree.ddukk.productapp.ui.screens.AddProductScreen
import sree.ddukk.productapp.ui.screens.DetailScreen
import sree.ddukk.productapp.ui.screens.ProductScreen
import sree.ddukk.productapp.ui.viewmodel.ProductViewModel

@Composable
fun AppNavigation() {
    // Create an instance of ProductRepository
    val productRepository = ProductRepository()

    // Create the ProductViewModel and pass the repository to it
    val viewModel = ProductViewModel(productRepository = productRepository)

    // Set up the navController
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "productList") {
        composable("productList") {
            ProductScreen(navController, viewModel)
        }
        composable(
            "detailScreen/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            DetailScreen(productId, viewModel)
        }
        composable("addProduct") {
            AddProductScreen(navController, viewModel)
        }
    }
}
