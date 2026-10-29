package com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.presentation.screen

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
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.domain.model.TurmaProfessor
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.presentation.TurmasProfessorViewModel

private val RoxoTurmas = Color(0xFF5A00FC)

@Composable
fun TurmasProfessorScreen(
    viewModel: TurmasProfessorViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    var pesquisa by remember { mutableStateOf("") }
    var filtroSelecionado by remember { mutableStateOf("Todas") }
    var ordenacaoSelecionada by remember { mutableStateOf("Nome") }

    LaunchedEffect(viewModel) {
        viewModel.carregar()
    }

    val turmasFiltradas = remember(
        state.turmas,
        pesquisa,
        filtroSelecionado,
        ordenacaoSelecionada
    ) {
        val busca = pesquisa.trim().lowercase()

        val filtradas = state.turmas.filter { turma ->
            val correspondePesquisa =
                turma.codigo.lowercase().contains(busca) ||
                        turma.curso.lowercase().contains(busca) ||
                        turma.semestre.lowercase().contains(busca)

            val correspondeFiltro = when (filtroSelecionado) {
                "Com alunos" -> turma.quantidadeAlunos > 0
                "Sem alunos" -> turma.quantidadeAlunos == 0
                else -> true
            }

            correspondePesquisa && correspondeFiltro
        }

        when (ordenacaoSelecionada) {
            "Alunos" -> filtradas.sortedByDescending { it.quantidadeAlunos }
            "Código" -> filtradas.sortedBy { it.codigo.lowercase() }
            else -> filtradas.sortedBy { it.curso.lowercase() }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = RoxoTurmas)
                        Text(
                            text = "Carregando suas turmas...",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                }
            }

            state.errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("⚠️", style = MaterialTheme.typography.headlineLarge)
                    Text(
                        text = "Não foi possível carregar",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = state.errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Button(
                        onClick = { viewModel.carregar() },
                        modifier = Modifier.padding(top = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RoxoTurmas
                        )
                    ) {
                        Text("Tentar novamente")
                    }
                }
            }

            state.turmas.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("📚", style = MaterialTheme.typography.displaySmall)
                    Text(
                        text = "Nenhuma turma encontrada",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    Text(
                        text = "As turmas vinculadas ao seu perfil aparecerão aqui.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Button(
                        onClick = { viewModel.carregar() },
                        modifier = Modifier.padding(top = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RoxoTurmas
                        )
                    ) {
                        Text("Atualizar turmas")
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        horizontal = 20.dp,
                        vertical = 18.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Column {
                            Text(
                                text = "ÁREA DO PROFESSOR",
                                style = MaterialTheme.typography.labelMedium,
                                color = RoxoTurmas,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Minhas Turmas",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                            Text(
                                text = "Organize suas turmas e acompanhe suas aulas.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                            )
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = RoxoTurmas
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 3.dp
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Visão geral",
                                        color = Color.White.copy(alpha = 0.85f),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "${state.turmas.size} ${
                                            if (state.turmas.size == 1) "turma" else "turmas"
                                        } vinculadas",
                                        color = Color.White,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    Text(
                                        text = "Total de alunos: ${
                                            state.turmas.sumOf { it.quantidadeAlunos }
                                        }",
                                        color = Color.White.copy(alpha = 0.9f),
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 6.dp)
                                    )
                                }
                                Text(
                                    text = "🎓",
                                    style = MaterialTheme.typography.displaySmall
                                )
                            }
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = pesquisa,
                            onValueChange = { pesquisa = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            label = { Text("Pesquisar turma") },
                            placeholder = { Text("Nome do curso ou código") },
                            leadingIcon = { Text("🔎") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = RoxoTurmas,
                                focusedLabelColor = RoxoTurmas,
                                cursorColor = RoxoTurmas,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface
                            )
                        )
                    }

                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Filtrar turmas",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf("Todas", "Com alunos", "Sem alunos").forEach { filtro ->
                                    FilterChip(
                                        selected = filtroSelecionado == filtro,
                                        onClick = { filtroSelecionado = filtro },
                                        label = { Text(filtro) }
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Ordenar por",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                listOf("Nome", "Código", "Alunos").forEach { ordem ->
                                    FilterChip(
                                        selected = ordenacaoSelecionada == ordem,
                                        onClick = { ordenacaoSelecionada = ordem },
                                        label = { Text(ordem) }
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Button(
                                    onClick = { viewModel.carregar() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = RoxoTurmas
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("↻")
                                }
                            }
                        }
                    }

                    item {
                        Text(
                            text = "${turmasFiltradas.size} resultado(s)",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (turmasFiltradas.isEmpty()) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 28.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("🔎", style = MaterialTheme.typography.displaySmall)
                                Text(
                                    text = "Nenhuma turma corresponde à pesquisa",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                Button(
                                    onClick = {
                                        pesquisa = ""
                                        filtroSelecionado = "Todas"
                                    },
                                    modifier = Modifier.padding(top = 12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = RoxoTurmas
                                    )
                                ) {
                                    Text("Limpar pesquisa e filtros")
                                }
                            }
                        }
                    } else {
                        items(
                            items = turmasFiltradas,
                            key = { it.id }
                        ) { turma ->
                            TurmaCard(turma)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TurmaCard(turma: TurmaProfessor) {
    val scheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = scheme.surface
        ),
        border = BorderStroke(1.dp, scheme.outlineVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                        .size(50.dp)
                        .background(
                            color = scheme.secondaryContainer,
                            shape = RoundedCornerShape(15.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🎓",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = turma.codigo,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = scheme.onSurface
                    )
                    Text(
                        text = turma.curso,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
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

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("📅")
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = "Semestre",
                        style = MaterialTheme.typography.labelMedium,
                        color = scheme.onSurfaceVariant
                    )
                    Text(
                        text = turma.semestre,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = scheme.onSurface
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("👥")
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = "Alunos matriculados",
                        style = MaterialTheme.typography.labelMedium,
                        color = scheme.onSurfaceVariant
                    )
                    Text(
                        text = "${turma.quantidadeAlunos} ${
                            if (turma.quantidadeAlunos == 1) "aluno" else "alunos"
                        }",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = scheme.onSurface
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        scheme.surfaceVariant,
                        RoundedCornerShape(14.dp)
                    )
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "HORÁRIOS DAS AULAS",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = RoxoTurmas
                )
                Text(
                    text = turma.diasSemana.joinToString(", ") {
                        it.lowercase().replaceFirstChar { letra -> letra.uppercase() }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = scheme.onSurface
                )
                Text(
                    text = "${turma.horarioInicio} – ${turma.horarioFim}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = scheme.onSurface
                )
            }
        }
    }
}