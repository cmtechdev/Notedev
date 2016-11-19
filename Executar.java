import java.io.*;
import javax.swing.*;
import java.awt.*;

// Classe responsável pela compilação e administração da execução do código 
public class Executar {
	
	// Método ficará responśavel por compilar o código do bloco de conteúdo
	public static boolean compilar() throws IOException {
		
		/*
		 * A variável 'retorno' cuidará de retornar 'true' se a compilação ocorrer com sucesso
		 * ou 'false' caso algo tenha ocorrido de forma incerta
		 */
		 
		// --------------
		String   comando;
		boolean retorno;
		int     errorLevel;
		// --------------
		retorno = false;
		errorLevel = 0;
		// --------------
		
		// Formula o comando que será executado para compilar o arquivo
		comando = String.format("javac -Xstdout .devnotecomp.log %s", Bloco.localSalvarArquivo);
		
		try {
			Runtime r = Runtime.getRuntime();  // Cria uma variável r do tipo Runtime
			Process processo = r.exec(comando); // Executa 'comando'
			errorLevel = processo.waitFor();   // Espera a conclusão do comando e recebe o retorno do processo
		}
		catch(Exception excessaoCompilacao) { System.out.println(excessaoCompilacao); }
		
		// Se não houver erro na compilação, então...
		if (errorLevel == 0)
			retorno = true; // Atribui à 'retorno' o valor 'true'
		// Caso haja algum problema no momento da compilação, então...
		else
			retorno = false; // Atribui à 'retorno' o valor 'false'
		
		return retorno;
	}
	
	// Método responśavel por mostrar no terminal os erros gerados no momento da compilação.
	public static void mostrarErros(JTextArea terminal) throws IOException {
		
		// ---------------------
		String conteudo, linha;
		// ---------------------
		conteudo = "";
		// ---------------------
		
		BufferedReader leitor = new BufferedReader(new FileReader(".devnotecomp.log")); // Abre o arquivo '.devnotecomp.log' para ser lido
		
		// Enquanto houver conteúdo para ser lido...
		while(true) {
			linha = leitor.readLine(); // 'linha' recebe o valor da última linha lida
			
			if (linha != null) // Se houver conteúdo, então...
				conteudo = String.format("%s%s\n", conteudo, linha); // Concatena em 'conteudo' tal conteudo
			else // Caso nao haja mais conteudo para ser lido do arquivo
				break; // Quebra o laço
		}
		
		leitor.close(); // Fecha o leitor do arquivo
		terminal.setText(conteudo); // Altera o texto de 'terminal' para o obtido a partir da leitura do arquivo
		
	}
	
	// Método responsável por rodar e mostrar a saída no terminal gerado pela execução do código
	public static void rodar(JTextArea terminal) throws IOException {
		
		// ----------------
		String nomeArquivo;
		// ----------------
		nomeArquivo = Bloco.localSalvarArquivo.substring(0, (Bloco.localSalvarArquivo.length() - 5));
		// ----------------
		
		// Verifica se o arquivo '.notedevexec.sh' existe
		// Se o mesmo existir, o 'BufferedReader' não irá gerar excessão
		try {
			BufferedReader leitor = new BufferedReader(new FileReader(".notedevexec.sh")); // Tenta abrir o arquivo 
			leitor.close(); // Finaliza a leitura do arquivo
		}
		// Caso o arquivo não exista
		catch(Exception excessaoArquivoSH) {
			BufferedWriter escritor = new BufferedWriter(new FileWriter(".notedevexec.sh")); // Abre(Cria) o arquivo
			/*
			 * Para que seja possível visualizar a saída do programa em execução
			 * se faz necessário a criação de um script, que realize o comando
			 * de execução do código, apontando a saída para um arquivo.
			 *
			 * O código do Shell Script será:
			 * #!/bin/bash
			 * java $1 > .devnoteout.log
			 */
			 
			 String codigo = "#!/bin/bash\njava $1 > .devnoteoutexec.log"; // Código do arquivo
			 escritor.append(codigo); // Insere o código no arquivo
			 escritor.close(); // Finaliza a escrita do arquivo
		}
		
		// Para cada novo processo, será executada uma nova Thread
		ThreadExecucao thread = new ThreadExecucao(terminal); // Cria uma nova Thread
		thread.start(); // Inicializa a execução da Thread
	}
}
