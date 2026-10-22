package com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.presentation.screen

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
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.domain.model.TurmaProfessor
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.presentation.TurmasProfessorViewModel

@Composable
fun TurmasProfessorScreen(viewModel: TurmasProfessorViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(viewModel) { viewModel.carregar() }
    Column(modifier.fillMaxSize().padding(16.dp)) {
        Text("Minhas turmas", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Turmas vinculadas ao seu perfil", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp, bottom = 12.dp))
        when {
            state.isLoading -> Column(Modifier.fillMaxWidth().weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) { CircularProgressIndicator(); Text("Carregando turmas…", modifier = Modifier.padding(top = 12.dp)) }
            state.errorMessage != null -> Column(Modifier.fillMaxWidth().weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) { Text(state.errorMessage!!, color = MaterialTheme.colorScheme.error); Button(onClick = { viewModel.carregar() }, modifier = Modifier.padding(top = 12.dp)) { Text("Tentar novamente") } }
            state.turmas.isEmpty() -> Column(Modifier.fillMaxWidth().weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) { Text("Nenhuma turma encontrada.") }
            else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) { items(state.turmas, key = { it.id }) { turma -> TurmaCard(turma) } }
        }
    }
}

@Composable
private fun TurmaCard(turma: TurmaProfessor) {
    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(turma.codigo, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(turma.curso, style = MaterialTheme.typography.titleMedium)
            Text("Semestre: ${turma.semestre}")
            Text("Alunos: ${turma.quantidadeAlunos}")
            Text("Dias: ${turma.diasSemana.joinToString(", ")}")
            Text("Horário: ${turma.horarioInicio} – ${turma.horarioFim}")
        }
    }
}
