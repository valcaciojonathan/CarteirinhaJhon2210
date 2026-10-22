
package com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.domain.model.UnidadeCurricularProfessor
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.presentation.UnidadesCurricularesProfessorViewModel

@Composable
fun UnidadesCurricularesProfessorScreen(
    viewModel: UnidadesCurricularesProfessorViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.carregar()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Unidades curriculares",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Componentes que você ministra",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
        )

        when {
            state.isLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = "Carregando unidades...",
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }

            state.errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.errorMessage!!,
                        color = MaterialTheme.colorScheme.error
                    )

                    Button(
                        onClick = { viewModel.carregar() },
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        Text("Tentar novamente")
                    }
                }
            }

            state.unidades.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Nenhuma unidade curricular encontrada.")
                }
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = state.unidades,
                        key = { it.id }
                    ) { unidade ->
                        UnidadeCurricularCardProfessor(unidade)
                    }
                }
            }
        }
    }
}

@Composable
private fun UnidadeCurricularCardProfessor(
    unidade: UnidadeCurricularProfessor
) {
    val progresso =
        if (unidade.quantidadeAulas > 0) {
            (unidade.aulasRealizadas.toFloat() /
                    unidade.quantidadeAulas).coerceIn(0f, 1f)
        } else {
            0f
        }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = unidade.nome,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Turma: ${unidade.turma}",
                style = MaterialTheme.typography.titleMedium
            )

            Text("Carga horária: ${unidade.cargaHoraria} h")

            Text(
                "Aulas realizadas: ${unidade.aulasRealizadas} de ${unidade.quantidadeAulas}"
            )

            LinearProgressIndicator(
                progress = { progresso },
                modifier = Modifier.fillMaxWidth()
            )

            Text("Dias: ${unidade.diasSemana.joinToString(", ")}")

            Text(
                "Horário: ${unidade.horarioInicio} – ${unidade.horarioFim}"
            )
        }
    }
}
