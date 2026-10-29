package com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.domain.model.UnidadeCurricularProfessor
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.presentation.UnidadesCurricularesProfessorViewModel

private val RoxoCarteirinha = Color(0xFF5A00FC)

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
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "ÁREA DO PROFESSOR",
                style = MaterialTheme.typography.labelMedium,
                color = RoxoCarteirinha,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Unidades curriculares",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 6.dp)
            )

            Text(
                text = "Confira os componentes que você ministra.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 6.dp, bottom = 20.dp)
            )
        }

        when {
            state.isLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(color = RoxoCarteirinha)

                    Text(
                        text = "Carregando unidades...",
                        modifier = Modifier.padding(top = 14.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
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
                        text = "Não foi possível carregar as unidades",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = state.errorMessage!!,
                        modifier = Modifier.padding(top = 8.dp),
                        color = MaterialTheme.colorScheme.error
                    )

                    Button(
                        onClick = { viewModel.carregar() },
                        modifier = Modifier.padding(top = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RoxoCarteirinha
                        )
                    ) {
                        Text("Tentar novamente")
                    }
                }
            }

            state.unidades.isEmpty() -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Nenhuma unidade curricular",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = "As unidades disponíveis aparecerão aqui quando forem encontradas.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(
                        items = state.unidades,
                        key = { it.id }
                    ) { unidade ->
                        UnidadeCurricularCardProfessor(
                            unidade = unidade
                        )
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
    val scheme = MaterialTheme.colorScheme

    val progresso = if (unidade.quantidadeAulas > 0) {
        (
                unidade.aulasRealizadas.toFloat() /
                        unidade.quantidadeAulas
                ).coerceIn(0f, 1f)
    } else {
        0f
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = scheme.surface
        ),
        border = BorderStroke(
            1.dp,
            scheme.outlineVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = scheme.secondaryContainer,
                            shape = RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = obterIconeMateria(unidade.nome),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = unidade.nome,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = scheme.onSurface
                    )

                    Text(
                        text = "Turma: ${unidade.turma}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = scheme.onSurfaceVariant
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(scheme.outlineVariant)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Carga horária",
                    style = MaterialTheme.typography.labelLarge,
                    color = scheme.onSurfaceVariant
                )

                Text(
                    text = "${unidade.cargaHoraria} horas",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = scheme.onSurface
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Aulas realizadas",
                    style = MaterialTheme.typography.bodyMedium,
                    color = scheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "${unidade.aulasRealizadas} / ${unidade.quantidadeAulas}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = RoxoCarteirinha
                )
            }

            LinearProgressIndicator(
                progress = { progresso },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp),
                color = RoxoCarteirinha,
                trackColor = scheme.surfaceVariant,
                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Dias da semana",
                    style = MaterialTheme.typography.labelLarge,
                    color = scheme.onSurfaceVariant
                )

                Text(
                    text = unidade.diasSemana.joinToString(", "),
                    style = MaterialTheme.typography.bodyMedium,
                    color = scheme.onSurface
                )

                Text(
                    text = "Horário",
                    style = MaterialTheme.typography.labelLarge,
                    color = scheme.onSurfaceVariant
                )

                Text(
                    text = "${unidade.horarioInicio} – ${unidade.horarioFim}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = scheme.onSurface
                )
            }
        }
    }
}

private fun obterIconeMateria(nome: String): String {
    val materia = nome.lowercase()

    return when {
        "programação" in materia ||
                "programacao" in materia ||
                "desenvolvimento" in materia -> "💻"

        "banco de dados" in materia ||
                "dados" in materia -> "🗄️"

        "rede" in materia -> "🌐"

        "matemática" in materia ||
                "matematica" in materia -> "📐"

        "português" in materia ||
                "portugues" in materia ||
                "comunicação" in materia -> "📝"

        "inglês" in materia ||
                "ingles" in materia -> "🌎"

        "hardware" in materia ||
                "eletrônica" in materia ||
                "eletronica" in materia -> "⚙️"

        "projeto" in materia -> "🚀"

        else -> "📚"
    }
}