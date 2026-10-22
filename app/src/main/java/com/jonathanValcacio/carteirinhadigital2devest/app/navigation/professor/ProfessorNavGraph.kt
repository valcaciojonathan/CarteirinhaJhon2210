package com.jonathanValcacio.carteirinhadigital2devest.app.navigation.professor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jonathanValcacio.carteirinhadigital2devest.app.navigation.GraphRoutes
import com.jonathanValcacio.carteirinhadigital2devest.app.navigation.ProfileGuard
import com.jonathanValcacio.carteirinhadigital2devest.app.ui.AppShell
import com.jonathanValcacio.carteirinhadigital2devest.feature.login.domain.model.PerfilAcesso
import com.jonathanValcacio.carteirinhadigital2devest.feature.login.domain.model.UsuarioLogado
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.home.presentation.screen.HomeProfessorScreen
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.domain.repository.TurmaProfessorRepository
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.presentation.TurmasProfessorViewModel
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.presentation.TurmasProfessorViewModelFactory
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.presentation.screen.TurmasProfessorScreen
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.domain.repository.UnidadeCurricularProfessorRepository
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.presentation.UnidadesCurricularesProfessorViewModel
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.presentation.UnidadesCurricularesProfessorViewModelFactory
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.presentation.screen.UnidadesCurricularesProfessorScreen
import kotlinx.coroutines.flow.StateFlow

fun NavGraphBuilder.professorNavGraph(
    navController: NavHostController,
    usuarioLogadoFlow: StateFlow<UsuarioLogado?>,
    turmaProfessorRepository: TurmaProfessorRepository,
    unidadeCurricularProfessorRepository: UnidadeCurricularProfessorRepository,
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    onLogout: () -> Unit,
    onSessaoAusente: () -> Unit,
    onPerfilInvalido: (PerfilAcesso) -> Unit
) {
    navigation(startDestination = ProfessorRoutes.HOME, route = GraphRoutes.PROFESSOR) {
        composable(ProfessorRoutes.HOME) {
            ProfileGuard(usuarioFlow = usuarioLogadoFlow, perfilEsperado = PerfilAcesso.PROFESSOR, onSessaoAusente = onSessaoAusente, onPerfilInvalido = onPerfilInvalido) { usuario ->
                AppShell(title = "Início", usuarioLogado = usuario, canNavigateBack = false, darkTheme = darkTheme, onDarkThemeChange = onDarkThemeChange, onNavigateBack = {}, onLogout = onLogout) { innerPadding ->
                    HomeProfessorScreen(usuarioLogado = usuario,
                        onTurmasClick = { navController.navigate(ProfessorRoutes.TURMAS) },
                        onUnidadesCurricularesClick = { navController.navigate(ProfessorRoutes.UNIDADES_CURRICULARES) },
                        modifier = Modifier.fillMaxSize().padding(innerPadding))
                }
            }
        }
        composable(ProfessorRoutes.TURMAS) {
            ProfileGuard(usuarioFlow = usuarioLogadoFlow, perfilEsperado = PerfilAcesso.PROFESSOR, onSessaoAusente = onSessaoAusente, onPerfilInvalido = onPerfilInvalido) { usuario ->
                val factory = remember(turmaProfessorRepository) { TurmasProfessorViewModelFactory(turmaProfessorRepository) }
                val vm: TurmasProfessorViewModel = viewModel(factory = factory)
                AppShell(title = "Minhas Turmas", usuarioLogado = usuario, canNavigateBack = true, darkTheme = darkTheme, onDarkThemeChange = onDarkThemeChange, onNavigateBack = { navController.popBackStack() }, onLogout = onLogout) { innerPadding ->
                    TurmasProfessorScreen(vm, Modifier.fillMaxSize().padding(innerPadding))
                }
            }
        }
        composable(ProfessorRoutes.UNIDADES_CURRICULARES) {
            ProfileGuard(usuarioFlow = usuarioLogadoFlow, perfilEsperado = PerfilAcesso.PROFESSOR, onSessaoAusente = onSessaoAusente, onPerfilInvalido = onPerfilInvalido) { usuario ->
                val factory = remember(unidadeCurricularProfessorRepository) { UnidadesCurricularesProfessorViewModelFactory(unidadeCurricularProfessorRepository) }
                val vm: UnidadesCurricularesProfessorViewModel = viewModel(factory = factory)
                AppShell(title = "Unidades Curriculares", usuarioLogado = usuario, canNavigateBack = true, darkTheme = darkTheme, onDarkThemeChange = onDarkThemeChange, onNavigateBack = { navController.popBackStack() }, onLogout = onLogout) { innerPadding ->
                    UnidadesCurricularesProfessorScreen(vm, Modifier.fillMaxSize().padding(innerPadding))
                }
            }
        }
    }
}
