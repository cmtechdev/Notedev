import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Bloco extends JFrame implements ActionListener {

	/*
	* abrir			- Item do menu que ficará responsável por chamar o método responsável por abrir um determinado arquivo
	* salvar		- Item do menu que ficará responsável por chamar o método responsável por salvar um determinado arquivo
	* salvarComo		- Item do menu que ficará responsável por chamar o método responsável por "salvar como" um determinado arquivo
	* salvarExecutar	- Item do menu que ficará responsável por chamar o método responsável por salvar e executar um determinado arquivo
	* sobre			- Item do menu que ficará responsável por chamar o método responsável por mostrar algumas informações sobre a ferramenta
	*
	* bloco			- Campo de texto que conterá o conteudo que será salvo
	* terminal		- Campo de texto que mostrará a saída do programa
	*	
	* localSalvarArquivo	- Variável responsável por armazenar o nome do arquivo que será salvo
	* localAbrirArquivo	- Variável responsável por armazenar o nome do arquivo que será aberto
	* terminalVerificador	- Variável responsável por armazenar um determinado valor caso o terminal já tenha sido adicionado à janela
	*
	* largura, altura	- Variáveis que conterão as dimensões da tela
	*/
	
	// ----------------------------------------------------------
	private JMenuItem abrir;
	private JMenuItem salvar;
	private JMenuItem salvarComo;
	private JMenuItem salvarExecutar;
	private JMenuItem sobre;
	
	public static JTextArea bloco;
	public static JTextArea terminal = new JTextArea(10, 10);
	
	public static String localSalvarArquivo = null;
	public static String localAbrirArquivo;
	
	private boolean terminalVerificador = false;
	
	private int largura, altura;
	// ----------------------------------------------------------
	
	// Método construtor da classe 'Bloco'
	public Bloco() {
		
		super("Notedev"); // Titulo da janela principal
		setVisible(true);  // Torna a janela visível
		
		Color fundo = new Color(199, 194, 194); // Cria uma variável do tipo 'Color' para ser o fundo do terminal
		terminal.setEditable(false);   // Impossibilita o usuário de alterar o campo do terminal
		terminal.setBackground(fundo); // Adiciona uma cor ao fundo do terminal
		
		Dimension tela = Toolkit.getDefaultToolkit().getScreenSize(); // Cria um tipo 'Dimension'
		largura = tela.width;  // Atribui em 'largura', a largura atual da tela
		altura  = tela.height; // Atribui em 'altura', a altura atual da tela
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Finaliza a janela em vez de oculta-la quando a mesma for fechada
		
		// Barra de menu
		JMenuBar barra = new JMenuBar();    // Cria uma barra de menu
		setJMenuBar(barra); // Adiciona à janela principal, a barra de menu criada
		
		/*
		 Árvore geral de estruturação dos menus contidos na barra de menu:
		 
		 Arquivo ------- Abrir
		 	    |
		 	    +--- Salvar
		 	    |
		 	    +--- Salvar Como
		 
		 Executar ------- Salvar e Executar
		 
		 Ajuda    ------- Sobre
		*/
		
		Icon icoArquivo = new ImageIcon("Icones/arquivo.png"); // Obtem um ícone para 'arquivo'
		JMenu arquivo = new JMenu("Arquivo"); // Cria um menu 'Arquivo' para a barra de menu
		arquivo.setIcon(icoArquivo); // Adiciona o ícone à 'arquivo'
		
		Icon icoExecutar = new ImageIcon("Icones/executar.png"); // Obtem um ícone para 'executar'
		JMenu executar = new JMenu("Executar"); // Cria um menu 'Executar' para a barra de menu
		executar.setIcon(icoExecutar); // Adiciona o ícone à 'executar'
		
		Icon icoAjuda = new ImageIcon("Icones/ajuda.png"); // Obtem um ícone para 'ajuda'
		JMenu ajuda = new JMenu("Ajuda"); // Cria um menu 'Ajuda' para a barra de menu
		ajuda.setIcon(icoAjuda); // Adiciona o ícone à 'ajuda'
		
		// São adicionados à barra de menu, os três menus criados anteriormente
		barra.add(arquivo);
		barra.add(executar);
		barra.add(ajuda);
		
		// Após adicionar os menus à barra de menu, a ideia é adicionar itens a esses menus
		
		// Ítens para 'Arquivo'
		Icon icoAbrir = new ImageIcon("Icones/abrir.png"); // Obtem um ícone para 'abrir'
		abrir = new JMenuItem("Abrir"); // Cria um item chamado 'Abrir' para o menu 'Arquivo'
		abrir.setIcon(icoAbrir); // Adiciona o ícone à 'abrir'
		abrir.addActionListener(this); // Adiciona um 'ouvinte' para verificar as ações que ocorrem em 'abrir'
		
		Icon icoSalvar = new ImageIcon("Icones/salvar.png"); // Obtem um ícone para 'salvar'
		salvar = new JMenuItem("Salvar"); // Cria um item chamado 'Salvar' para o menu 'Arquivo'
		salvar.setIcon(icoSalvar); // Adiciona o ícone à 'salvar'
		salvar.addActionListener(this); // Adiciona um 'ouvinte' para verificar as ações que ocorrem em 'salvar'
		
		Icon icoSalvarComo = new ImageIcon("Icones/salvarComo.png"); // Obtem um ícone para 'salvarComo'
		salvarComo = new JMenuItem("Salvar Como"); // Cria um item chamado 'Salvar Como' para o menu 'Arquivo'
		salvarComo.setIcon(icoSalvarComo); // Adiciona o ícone à 'salvarComo'
		salvarComo.addActionListener(this); // Adiciona um 'ouvinte' para verificar as ações que ocorrem em 'salvarComo'
		
		// São adicionados ao menu 'Arquivo', os três itens criados anteriormente
		arquivo.add(abrir);
		arquivo.add(salvar);
		arquivo.add(salvarComo);
		
		// Itens para 'Executar'
		Icon icoSalvarExecutar = new ImageIcon("Icones/rodar.png"); // Obtem um ícone para 'salvarExecutar'
		salvarExecutar = new JMenuItem("Salvar e Executar"); // Cria um item chamado 'Salvar e Executar' para o menu 'Executar'
		salvarExecutar.setIcon(icoSalvarExecutar); // Adiciona o ícone à 'salvarExecutar'
		salvarExecutar.addActionListener(this); // Adiciona um 'ouvinte' para verificar as ações que ocorrem em 'salvarExecutar'
		
		// É adicionado ao menu 'Executar' o item criado anteriormente
		executar.add(salvarExecutar);
		
		// Itens para 'Ajuda'
		Icon icoSobre = new ImageIcon("Icones/sobre.png"); // Obtem um ícone para 'sobre'
		sobre = new JMenuItem("Sobre"); // Cria um item chamado 'Sobre' para o menu 'Ajuda'
		sobre.setIcon(icoSobre); // Adiciona o ícone à 'sobre'
		sobre.addActionListener(this); // Adiciona um 'ouvinte' para verificar as ações que ocoreem em 'sobre'
		
		// É adicionado ao menu 'Ajuda' o item criado anteriormente
		ajuda.add(sobre);
		
		// Propriedades do bloco de conteúdo
		bloco = new JTextArea(1, 1); // Cria um novo campo de texto
		bloco.setLineWrap(true); // Habilita a quebra de linha para palvras que ultrapassem as margens do bloco de conteúdo
		getContentPane().add(new JScrollPane(bloco), BorderLayout.CENTER); // Adiciona o bloco de conteúdos na janela principal
		
		setSize(largura, altura); // Informa os limites de margem da janela
	}
	
	// Método responśavel por adicionar o terminal à janela principal (caso ele não esteja presente)
	public void adicionarTerminal() {
		// Se o terminal ainda não estiver na janela principal, então...
		if (terminalVerificador == false) {
			// 'terminal' é adicionado à janela principal
			getContentPane().add(new JScrollPane(terminal), BorderLayout.PAGE_END);
			terminalVerificador = true; // É atribuído o valor 'true' para 'terminalVerificador'
			pack(); // Redimensiona a janela principal
			setSize(largura, altura); // Maximiza a janela principal
		}
	}
	
	// Método responsável por tratar os eventos que ocorrem nos itens dispostos na janela principal
	@Override
	public void actionPerformed(ActionEvent evento) {
		// Para cada evento, há um método estático que é chamado na class 'Arquivo'
		
		// Caso o evento venha do item 'abrir'
		if (evento.getSource() == abrir) {
			/*
			 * Assinatura do método 'abrir' em 'Arquivo'
			 * void abrir(JFrame f, JTextArea a)
			 */
			 try { Arquivo.abrir(this, bloco); }
			 catch(Exception excessaoAbrir) { System.out.println(excessaoAbrir); }
		}
		// Caso o evento venha do item 'salvar'
		else if (evento.getSource() == salvar) {
			/* 
			 * Assinatura do método 'salvar' em 'Arquivo'
			 * void salvar(JFrame f, JTextArea t)
			 */
			try { Arquivo.salvar(this, bloco); }
			catch(Exception excessaoSalvar) { System.out.println(excessaoSalvar); }
		}
		// Caso o evento venha do item 'salvarComo'
		else if (evento.getSource() == salvarComo) {
			/*
			 * Para "Salvar Como", basta limpar o conteúdo da variável 'localSalvarArquivo'
			 * e charmar o método 'salvar' da classe 'Arquivo'
			 */
			localSalvarArquivo = null; // Deixa a variável sem conteúdo
			try { Arquivo.salvar(this, bloco); }
			catch(Exception excessaoSalvarComo) { System.out.println(excessaoSalvarComo); }
		}
		// Caso o evento venha do item 'salvarExecutar'
		else if (evento.getSource() == salvarExecutar) {
			/*
			 * Essa etapa se divide em compilação e execução
			 * onde o código só será executado após ser compilado com sucesso.
			 *
			 * As variáveis 'retornoCompilacao' e 'retornoExecucao' armazenaram o
			 * retorno dos métodos aos quais serão chamados.
			 */
			 
			// -----------------------------------------
			boolean retornoCompilacao, retornoExecucao;
			// -----------------------------------------
			
			retornoCompilacao = retornoExecucao = false; // Inicializa as variáveis em 'false'
			
			try { Arquivo.salvar(this, bloco); } // Salva o arquivo para só depois, compila-lo e executa-lo
			catch(Exception excessaoSalvarComoExecutar) { System.out.println(excessaoSalvarComoExecutar); }
			
			/* 
			 * É primeiramente chamado o método 'compilar' da classe 'Executar'
			 * O método retornará 'true' caso a compilação tenha ocorrido com sucesso;
			 * E 'false', caso a mesma não tenha sido concluída como deveria.
			 *
			 * Assinatura do método 'compilar' da classe 'Executar'
			 * boolean compilar(JTextArea a, JTextArea b)
			 */
			try { retornoCompilacao = Executar.compilar(); }
			catch(Exception excessaoSalvarExecutar) { System.out.println(excessaoSalvarExecutar); }
			
			adicionarTerminal(); // Chama o método responsável por adicionar o terminal na janela principal
			
			// Se a compilação for executada com êxito, então...
			if (retornoCompilacao) {
				// Executa o '.class' gerado pela compilação
				try { Executar.rodar(terminal); }
				catch(Exception excessao) {}
			}
			// Caso tenha ocorrido alguma incoerência no código ou algum erro a mais
			// no processo de compilação, então...
			else {
				// Chama o método estático 'mostrarError' da classe 'Executar'
				try { Executar.mostrarErros(terminal); }
				catch(Exception excessaoMostrarErros) { System.out.println(excessaoMostrarErros); }
			}
			
		}
		// Caso o evento venha do item 'sobre'
		else if (evento.getSource() == sobre) {
			// Apenas mostrar uma mensagem para o usuário
			String mensagem = "Notedev - Pode ser utilizado como um bloco de notas convencional, tanto como uma IDE para códigos em JAVA.\nVersão: 0.1\nPara mais contatos, enviar email para: cmtechx@gmail.com";
			JOptionPane.showMessageDialog(null, mensagem); // Mensagem que será apresentado ao usuário
		}
	}
	
}
