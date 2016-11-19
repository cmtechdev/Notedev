import java.io.*;
import javax.swing.*;
import java.awt.*;

// Classe responsável por manipular as manipulações entre os arquivos que serão criados e/ou lidos
public class Arquivo {
	
	/*
	 * Método responśavel por salvar o conteúdo do bloco de conteúdo em um arquivo
	 * Se o mesmo retornar 'true', é porque a operação foi realizada com sucesso
	 * Caso o retorno seja 'false', é porque algo indevido ocorreu.
	 */
	public static boolean salvar(JFrame janelaPrincipal, JTextArea bloco) throws IOException {
		
		/*
		 * retorno - Irá receber o 'ErrorLevel' do arquivo escrito em Shell Script
		 * opcao   - Definirá se o conteúdo irá ou não ser salvo em arquivo
		 */
		 
		// -------------------------
		boolean retorno;
		int     opcao;
		// -------------------------
		retorno = false;
		opcao   = 0;
		// -------------------------
		
		// Se nenhum arquivo tiver sido salvo antes, uma janela pedirá o nome do arquivo
		// para que seja possível salvar o conteúdo.
		while ( Bloco.localSalvarArquivo == null ) { // Enquanto não houve nome válido para o arquivo...
			Bloco.localSalvarArquivo = JOptionPane.showInputDialog("Digite o nome do arquivo");
		}
		
		BufferedWriter escritor = new BufferedWriter(new FileWriter(Bloco.localSalvarArquivo)); // Abre (ou cria) o arquivo que foi informado
		
		/*
		 * Antes de salvar o conteúdo no arquivo, há uma verificação no bloco de conteúdo.
		 * Se não houver informação alguma, surgirá na tela uma mensagem perguntando se o
		 * usuário deseja salvar o arquivo em branco.
		 */
		if ( bloco.getText().equals("") ) {
			/*
			 * Para 'opção', pode haver o retorno de três valores do tipo inteiro, aos quais são eles:
			 * (YES_OPTION)    0 - Caso o usuário realmente deseja salvar o arquivo sem conteúdo algum;
			 * (NO_OPTION)     1 - Caso o usuário não deseja salvar o arquivo sem conteúdo;
			 * (CANCEL_OPTION) 2 - Caso o usuário deseja cancelar o salvamento do arquivo;
			 */
			opcao = JOptionPane.showConfirmDialog(null, "Não há conteúdo no atual arquivo, deseja salvar mesmo assim?");
		}
		
		/*
		 * Se 'opcao' tiver como valor armazenado 0 (zero), pode ter ocorrido duas situações:
		 * Ou o bloco de conteúdo possúi alguma informação que poderá ser salvar;
		 * Ou então, o bloco de conteúdo não possúi nenhuma informação, porém, o usuário deseja salvar o arquivo mesmo assim;
		 */
		if (opcao == 0) { // Se houver a permissão para salvar o conteúdo no arquivo, então...
			escritor.append(bloco.getText() + "\n"); // Obtem-se as informações do bloco de conteúdo e acrescenta no arquivo que foi aberto
			retorno = true; // A varíavel 'retorno' passa então, a possúi 'true' em vez de 'false'
		}
		else // Caso não haja permissão para salver o conteúdo no arquivo, então...
			Bloco.localSalvarArquivo = null; // A variável 'localSalvarArquivo' receberá 'null', para que no próximo evento, haja um arquivo válido
		
		escritor.close(); // Fecha o arquivo que foi aberto
		janelaPrincipal.setTitle(Bloco.localSalvarArquivo); // Altera o título da janela para o nome do arquivo que foi manipulado
		
		return retorno; // Retorno do método ('true' ou 'false')
		
	}
	
	// Método responsável por abrir um arquivo no bloco de conteúdo da janela principal
	public static void abrir(JFrame janelaPrincipal, JTextArea bloco) throws IOException {
		
		// ----------------------------------
		String nomeArquivo, linha, conteudo;
		// ----------------------------------
		conteudo    = "";
		nomeArquivo = null;
		linha 	    = "";
		// ----------------------------------
		
		// Sempre que o método for chamado, uma janela será aberta
		// pedindo para que o usuário informe o nome do arquivo que deverá ser aberto
		while ( nomeArquivo == null ) { // Enquanto não houver nome válido, então...
			nomeArquivo = JOptionPane.showInputDialog(null, "Digite o nome do arquivo que deseja abrir");
		}
		
		// Para que haja um contole maior, a abertura do arquivo informado é tratado
		// se ele existir, é aberto. Caso não exista, é informado ao usuário
		try {
			BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo)); // Tenta abrir o arquivo informado
			
			// A partir de então, se não houver nenhuma excessão, então...
			while (true) { 
				linha = leitor.readLine(); // Ler a linha atual do arquivo que foi aberto
				
				// Se o que foi lido possúi conteúdo, então...
				if ( linha != null ) { 
					conteudo = String.format("%s%s\n", conteudo, linha); // Concatena o conteúdo das linhas em 'conteudo'
				}
				// Caso não haja conteúdo na linha lida, então, é porque é o final do arquivo.
				// Logo...
				else 
					break; // Quebra o laço de repetição
			}
			
			bloco.setText(conteudo); // Atualiza o conteúdo do bloco
			
			leitor.close(); // Feche o arquivo que foi lido
			janelaPrincipal.setTitle(nomeArquivo); // Altera o título da janela para o nome do arquivo
			Bloco.localSalvarArquivo = nomeArquivo; // Altera o local de salvamente do arquivo
		}
		// Caso haja alguma excessão, é devido a inexistência do arquivo informado
		// Portanto, haverá uma notificação para o usuário
		catch(Exception excessaoAbrir) {
			String mensagem = String.format("O arquivo '%s' não existe.\nVerifique o nome e tente novamente.", nomeArquivo);
			JOptionPane.showMessageDialog(null, mensagem); // Mensagem que surgirá para o usuário
		}
		
	}
	
}
