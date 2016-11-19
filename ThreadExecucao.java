import javax.swing.*;
import java.awt.*;
import java.io.*;

// Classe responsável por manipular a execução do arquivo que foi criado
public class ThreadExecucao extends Thread {
	
	// ----------------------------------
	private    JTextArea 	terminal;
	private    String   	conteudo;
	private    String    	nomeArquivo;
	private    String    	comando;
	private    int       	errorLevel;
	private    long	tempoInicial;
	private    double	tempoFinal;
	// ----------------------------------
	
	// Método construtor da classe
	public ThreadExecucao(JTextArea terminal) {
		this.terminal = terminal; // Referencia o terminal da classe ao terminal recebido como parâmetro
		nomeArquivo = Bloco.localSalvarArquivo.substring(0, (Bloco.localSalvarArquivo.length() - 5)); // Extrai o nome do arquivo
	}	
	
	// Método que é chamado no momento em que é chamado o 'start()' da Thread
	@Override
	public void run() {
		
		// A variável 'linha' sera concatenada para que possa ser expressada no terminal
		// ----------
		String linha;
		// ----------
		
		conteudo = String.format("Executando o código...\n----------------\n"); // 'conteudo' recebe 'Executando o código...\n\n'
		comando  = String.format("bash .notedevexec.sh %s", nomeArquivo); // 'comando' recebe o comando que sera executado
		terminal.setText(conteudo); // O texto do terminal é alterado 
		
		// Há a execução do comando que foi gerado
		try {
			Runtime r = Runtime.getRuntime();   // Cria um r do tipo Runtime
			tempoInicial = System.currentTimeMillis(); // Armazena em 'tempoInicio' os milissegundos do momento exato
			Process processo = r.exec(comando); // Cria um processo que executa o comando que foi gerado
			errorLevel = processo.waitFor();    // Espera pela finalização do processo
			tempoFinal = (System.currentTimeMillis() - tempoInicial) / 1000; // Calcula o tempo de execução do processo
		}
		catch(Exception excessaoExecutarArquivo) { System.out.println(excessaoExecutarArquivo); } 
		
		// Há a leitura do arquivo que contém a saída gerada pelo código que foi executado
		try {
			BufferedReader leitor = new BufferedReader(new FileReader(".devnoteoutexec.log")); // Abre o arquivo '.devnoteoutexec.log'
			
			// Enquanto houver conteúdo para ser lido, então...
			while(true) {
				linha = leitor.readLine(); // Ler a última linha não lida do arquivo
				
				// Se houver conteúdo, então...
				if (linha != null) 
					conteudo = String.format("%s%s\n", conteudo, linha); // 'conteudo' é concatenado com o novo conteúdo
				// Caso não haja mais conteúdo para ser lido do arquivo
				else
					break; // Quebra o laço de repetição
			}
			
			leitor.close(); // Finaliza a leitura do arquivo
			
			conteudo = String.format("%s----------------\nExecutado em: %.2f s\n", conteudo, tempoFinal);
			
			terminal.setText(conteudo); // Atualiza a saída do terminal
		}
		catch(Exception excessaoMostrarSaida) { System.out.println(excessaoMostrarSaida); }
	} 
}
